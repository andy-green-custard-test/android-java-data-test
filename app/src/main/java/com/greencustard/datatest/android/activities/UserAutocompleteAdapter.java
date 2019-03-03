package com.greencustard.datatest.android.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.greencustard.data.android.pojo.User;
import com.greencustard.viewmodel.usersearch.UserSearchViewModel;
import com.greencustard.viewmodel.usersearch.state.DisplayingResultsSearchState;
import com.greencustard.viewmodel.usersearch.state.UserSearchState;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

class UserAutocompleteAdapter extends ArrayAdapter<User> implements Filterable {

    private final Disposable mDisposable;

    public UserAutocompleteAdapter(Context context, UserSearchViewModel mSearchViewModel) {
        super(context, android.R.layout.simple_dropdown_item_1line);

        mDisposable = mSearchViewModel
                .getStateObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserSearchState>() {
            @Override
            public void accept(UserSearchState userSearchState) throws Exception {

                clear();

                if (userSearchState instanceof DisplayingResultsSearchState) {

                    List<User> users = ((DisplayingResultsSearchState) userSearchState).getResults();

                    addAll(users);
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