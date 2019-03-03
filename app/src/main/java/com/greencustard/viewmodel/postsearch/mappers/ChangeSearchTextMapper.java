package com.greencustard.viewmodel.postsearch.mappers;

import com.greencustard.data.android.pojo.User;
import com.greencustard.viewmodel.postsearch.dependencies.PostSearchDependenciesInterface;
import com.greencustard.viewmodel.postsearch.intention.ChangeSearchTextIntention;
import com.greencustard.viewmodel.postsearch.intention.PostSearchIntention;
import com.greencustard.viewmodel.postsearch.state.DisplayingResultsSearchState;
import com.greencustard.viewmodel.postsearch.state.PostSearchState;

import java.util.List;
import java.util.Optional;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class ChangeSearchTextMapper extends PostSearchMapper {
    public ChangeSearchTextMapper(PostSearchDependenciesInterface mDependencies) {
        super(mDependencies);
    }

    @Override
    public boolean supports(PostSearchIntention intention) {
        return intention instanceof ChangeSearchTextIntention;
    }

    //Given more time, I would have an intermediate loading state and return a BehaviourSubject to offer that intermediate state

    @Override
    public Single<PostSearchState> map(PostSearchState state, PostSearchIntention intention) {

        final String searchText = ((ChangeSearchTextIntention)intention).getSearchText();

        Single<List<User>> users = mDependencies.usersByName(searchText);

        return users.map(new Function<List<User>, PostSearchState>() {
            @Override
            public PostSearchState apply(List<User> results) throws Exception {
                return new DisplayingResultsSearchState(Optional.<Integer>empty(),searchText,results);
            }
        });
    }
}
