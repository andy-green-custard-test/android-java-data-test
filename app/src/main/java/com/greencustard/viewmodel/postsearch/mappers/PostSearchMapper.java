package com.greencustard.viewmodel.postsearch.mappers;

import com.greencustard.viewmodel.postsearch.dependencies.PostSearchDependenciesInterface;
import com.greencustard.viewmodel.postsearch.intention.PostSearchIntention;
import com.greencustard.viewmodel.postsearch.state.PostSearchState;

import io.reactivex.Single;

public abstract class PostSearchMapper {

    final PostSearchDependenciesInterface mDependencies;

    protected PostSearchMapper(PostSearchDependenciesInterface mDependencies) {
        this.mDependencies = mDependencies;
    }

    public abstract boolean supports(PostSearchIntention intention);

    public abstract Single<PostSearchState> map(PostSearchState state, PostSearchIntention intention);
}
