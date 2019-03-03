package com.greencustard.datatest.android.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.greencustard.data.android.data.ApacheRestClient;
import com.greencustard.data.android.data.Deserialiser;
import com.greencustard.data.android.data.JacksonJsonDeserialiser;
import com.greencustard.data.android.data.RestClient;
import com.greencustard.data.android.pojo.User;
import com.greencustard.datatest.android.R;
import com.greencustard.viewmodel.postsearch.PostSearchViewModel;
import com.greencustard.viewmodel.postsearch.dependencies.PostSearchDependenciesInterface;
import com.greencustard.viewmodel.postsearch.intention.LoadResultsIntention;
import com.greencustard.viewmodel.postsearch.intention.SelectUserIntention;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.SingleSubject;

public class MainActivity extends AppCompatActivity {


    private final BehaviorSubject<Integer> mSelectedUserId;
    private final RestClient mRestClient;
    private final Deserialiser mDeserialiser;

    private AutoCompleteTextView mSearchField;
    private ListView mResultTable;
    private PostSearchDependenciesInterface mSearchDependencies;
    private PostSearchViewModel mSearchViewModel;

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
    }

    private void findViews() {
        mSearchField = findViewById(R.id.activity_main_usersearchfield);
        mResultTable = findViewById(R.id.activity_main_postresulttable);
    }

    private void connectSearch() {
        mSearchDependencies = new PostSearchDependenciesInterface() {
            @Override
            public Single<List<User>> allUsers() {
                try {
                    //Would abstract this out if I had more time!

                    //Would cache rather than hit REST needlessly on every keystroke ... if I had time

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
        mSearchViewModel = new PostSearchViewModel(mSearchDependencies);

        mSearchField.setAdapter(new UserAutocompleteAdapter(this,mSearchViewModel));
        mSearchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mSearchViewModel.submit(new LoadResultsIntention());
            }
        });
        mSearchField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSearchViewModel.submit(new SelectUserIntention(1)); //TODO
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSearchField.setThreshold(1);
    }

}
