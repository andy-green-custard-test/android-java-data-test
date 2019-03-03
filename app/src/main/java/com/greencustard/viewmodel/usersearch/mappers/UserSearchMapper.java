package com.greencustard.viewmodel.usersearch.mappers;

import com.greencustard.viewmodel.usersearch.dependencies.UserSearchDependencies;
import com.greencustard.viewmodel.usersearch.intention.UserSearchIntention;
import com.greencustard.viewmodel.usersearch.state.UserSearchState;

import io.reactivex.Single;

public abstract class UserSearchMapper {

    final UserSearchDependencies mDependencies;

    protected UserSearchMapper(UserSearchDependencies mDependencies) {
        this.mDependencies = mDependencies;
    }

    public abstract boolean supports(UserSearchIntention intention);

    public abstract Single<UserSearchState> map(UserSearchState state, UserSearchIntention intention);
}
