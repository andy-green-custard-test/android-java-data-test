package com.greencustard.viewmodel.postsearch.mapper;

import com.greencustard.viewmodel.postsearch.dependencies.PostSearchDependencies;
import com.greencustard.viewmodel.postsearch.intention.PostSearchIntention;
import com.greencustard.viewmodel.postsearch.state.PostSearchState;
import com.greencustard.viewmodel.usersearch.state.UserSearchState;

import io.reactivex.Single;

public abstract class PostSearchMapper {

    final PostSearchDependencies mDependencies;

    protected PostSearchMapper(PostSearchDependencies dependencies) {
        mDependencies = dependencies;
    }

    public abstract boolean supports(PostSearchIntention intention);

    public abstract Single<PostSearchState> map(PostSearchState state, PostSearchIntention intention);

}
