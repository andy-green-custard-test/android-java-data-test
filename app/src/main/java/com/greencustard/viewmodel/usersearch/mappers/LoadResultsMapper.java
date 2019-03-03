package com.greencustard.viewmodel.usersearch.mappers;

import com.greencustard.data.android.pojo.User;
import com.greencustard.viewmodel.usersearch.dependencies.UserSearchDependencies;
import com.greencustard.viewmodel.usersearch.intention.LoadResultsIntention;
import com.greencustard.viewmodel.usersearch.intention.UserSearchIntention;
import com.greencustard.viewmodel.usersearch.state.DisplayingResultsSearchState;
import com.greencustard.viewmodel.usersearch.state.UserSearchState;

import java.util.List;
import java.util.Optional;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class LoadResultsMapper extends UserSearchMapper {
    public LoadResultsMapper(UserSearchDependencies mDependencies) {
        super(mDependencies);
    }

    @Override
    public boolean supports(UserSearchIntention intention) {
        return intention instanceof LoadResultsIntention;
    }

    //Given more time, I would have an intermediate loading state and return a BehaviourSubject to offer that intermediate state

    @Override
    public Single<UserSearchState> map(UserSearchState state, UserSearchIntention intention) {

        Single<List<User>> users = mDependencies.allUsers();

        return users.map(new Function<List<User>, UserSearchState>() {
            @Override
            public UserSearchState apply(List<User> results) throws Exception {
                return new DisplayingResultsSearchState(Optional.<Integer>empty(),results);
            }
        });
    }
}
