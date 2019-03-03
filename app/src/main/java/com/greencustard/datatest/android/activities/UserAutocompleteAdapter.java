package com.greencustard.datatest.android.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListAdapter;

import com.greencustard.data.android.pojo.User;
import com.greencustard.viewmodel.postsearch.PostSearchViewModel;
import com.greencustard.viewmodel.postsearch.state.DisplayingResultsSearchState;
import com.greencustard.viewmodel.postsearch.state.PostSearchState;

import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

class UserAutocompleteAdapter extends ArrayAdapter implements Filterable {

    private final Disposable mDisposable;

    public UserAutocompleteAdapter(Context context, PostSearchViewModel mSearchViewModel) {
        super(context, android.R.layout.simple_dropdown_item_1line);

        mDisposable = mSearchViewModel
                .getStateObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PostSearchState>() {
            @Override
            public void accept(PostSearchState postSearchState) throws Exception {

                clear();

                if (postSearchState instanceof DisplayingResultsSearchState) {

                    List<String> usernames = Observable.fromIterable(((DisplayingResultsSearchState) postSearchState).getResults())
                            .map(new Function<User, String>() {
                                @Override
                                public String apply(User user) throws Exception {
                                    return user.getName();
                                }
                            })
                            .toList()
                            .blockingGet();

                    addAll(usernames);
                }
                else {
                    ; //Otherwise not in a state that yields results
                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        mDisposable.dispose();
        super.finalize();
    }
}