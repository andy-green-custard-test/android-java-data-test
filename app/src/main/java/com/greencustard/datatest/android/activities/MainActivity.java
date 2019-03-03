package com.greencustard.datatest.android.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.greencustard.data.android.data.ApacheRestClient;
import com.greencustard.data.android.data.Deserialiser;
import com.greencustard.data.android.data.JacksonJsonDeserialiser;
import com.greencustard.data.android.data.RestClient;
import com.greencustard.data.android.pojo.Post;
import com.greencustard.data.android.pojo.User;
import com.greencustard.datatest.android.R;
import com.greencustard.viewmodel.postsearch.PostSearchViewModel;
import com.greencustard.viewmodel.postsearch.dependencies.PostSearchDependencies;
import com.greencustard.viewmodel.postsearch.intention.SetUserIntention;
import com.greencustard.viewmodel.postsearch.state.PostSearchState;
import com.greencustard.viewmodel.usersearch.UserSearchViewModel;
import com.greencustard.viewmodel.usersearch.dependencies.UserSearchDependencies;
import com.greencustard.viewmodel.usersearch.intention.LoadResultsIntention;
import com.greencustard.viewmodel.usersearch.intention.SelectUserIntention;
import com.greencustard.viewmodel.usersearch.state.DisplayingResultsSearchState;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.SingleSubject;

public class MainActivity extends AppCompatActivity {


    private final BehaviorSubject<Integer> mSelectedUserId;
    private final RestClient mRestClient;
    private final Deserialiser mDeserialiser;

    private AutoCompleteTextView mSearchField;
    private UserSearchDependencies mSearchDependencies;
    private UserSearchViewModel mSearchViewModel;

    private ListView mResultTable;
    private PostSearchDependencies mPostDependencies;
    private PostSearchViewModel mPostViewModel;

    private Disposable mDisposable;

    public MainActivity() {
        mSelectedUserId = BehaviorSubject.create();
        mRestClient = new ApacheRestClient();
        mDeserialiser = new JacksonJsonDeserialiser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        connectSearch();
        connectPost();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSearchViewModel.submit(new LoadResultsIntention());
    }

    private void findViews() {
        mSearchField = findViewById(R.id.activity_main_usersearchfield);
        mResultTable = findViewById(R.id.activity_main_postresulttable);
    }

    private void connectSearch() {
        mSearchDependencies = new UserSearchDependencies() {
            @Override
            public Single<List<User>> allUsers() {
                try {
                    //Would abstract this out if I had more time!

                    return mRestClient.getUrl(new URL(RestConfig.USER_URL_STRING))
                            .map(new Function<String, List<User>>() {
                                @Override
                                public List<User> apply(String body) throws Exception {
                                    List<User> unfilteredUsers = Arrays.asList(mDeserialiser.deserialise(User[].class, body));
                                    return unfilteredUsers;
                                }
                            });
                } catch (MalformedURLException e) {
                    return SingleSubject.error(e);
                }
            }

            @Override
            public void onSelectUser(int selectedUserId) {
                mSelectedUserId.onNext(selectedUserId);
            }
        };
        mSearchViewModel = new UserSearchViewModel(mSearchDependencies);

        mSearchField.setAdapter(new UserAutocompleteAdapter(this,mSearchViewModel));
        mSearchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               User selected = (User) adapterView.getItemAtPosition(position);
               mSearchViewModel.submit(new SelectUserIntention(selected.getId()));
            }
        });
        mSearchField.setThreshold(1);
    }

    private void connectPost() {
        mPostDependencies = new PostSearchDependencies() {
            @Override
            public Single<List<Post>> postsByUserId(int userId) {
                try {
                    return mRestClient.getUrl(new URL(RestConfig.POST_URL_STRING + "?userId=" + userId))
                            .map(new Function<String, List<Post>>() {
                                @Override
                                public List<Post> apply(String body) throws Exception {
                                    return Arrays.asList(mDeserialiser.deserialise(Post[].class, body));
                                }
                            });
                } catch (MalformedURLException e) {
                    return Single.error(e);
                }
            }
        };

        mPostViewModel = new PostSearchViewModel(mPostDependencies);

        mDisposable = mSelectedUserId.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer userId) throws Exception {
                mPostViewModel.submit(new SetUserIntention(userId));
            }
        });

        mPostViewModel
                .getStateObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PostSearchState>() {
                               @Override
                               public void accept(PostSearchState postSearchState) throws Exception {
                                   List<Post> posts = postSearchState.getPosts();
                                   mResultTable.setAdapter(new PostArrayAdapter(MainActivity.this, R.layout.result_post, posts));
                               }
                           });
    }

    @Override
    protected void finalize() throws Throwable {
        mDisposable.dispose();
        super.finalize();
    }
}
