package com.greencustard.data.android.viewmodel.postsearch.mappers;

import android.icu.text.RelativeDateTimeFormatter;

import com.greencustard.data.android.pojo.User;
import com.greencustard.data.android.viewmodel.postsearch.dependencies.PostSearchDependenciesInterface;
import com.greencustard.data.android.viewmodel.postsearch.intention.PostSearchIntention;
import com.greencustard.data.android.viewmodel.postsearch.intention.SelectUserIntention;
import com.greencustard.data.android.viewmodel.postsearch.state.DefaultPostSearchState;
import com.greencustard.data.android.viewmodel.postsearch.state.DisplayingResultsSearchState;
import com.greencustard.data.android.viewmodel.postsearch.state.PostSearchState;

import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Predicate;

public class SelectUserMapper extends PostSearchMapper {

    public SelectUserMapper(PostSearchDependenciesInterface mDependencies) {
        super(mDependencies);
    }

    @Override
    public boolean supports(PostSearchIntention intention) {
        return intention instanceof SelectUserIntention;
    }

    @Override
    public Single<PostSearchState> map(PostSearchState state, PostSearchIntention intention) {

        final int selectedId = ((SelectUserIntention)intention).getId();

        if (!(state instanceof DisplayingResultsSearchState))
           throw new IllegalStateException("Cannot select a result if no results are displayed");

        Long count = Observable.fromIterable(((DisplayingResultsSearchState) state).getResults())
                .filter(new Predicate<User>() {
                    @Override
                    public boolean test(User user) throws Exception {
                        return user.getId() == selectedId;
                    }
                })
                .count()
                .blockingGet();

        if (count != 1)
            throw new IllegalStateException("Must select an id from results");

        mDependencies.onSelectUser(selectedId);

        return Single.<PostSearchState>just(new DefaultPostSearchState(Optional.of(selectedId)));
    }
}
