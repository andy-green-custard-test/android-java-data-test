package com.greencustard.viewmodel.usersearch.mappers;

import com.greencustard.data.android.pojo.User;
import com.greencustard.viewmodel.usersearch.dependencies.UserSearchDependencies;
import com.greencustard.viewmodel.usersearch.intention.SelectUserIntention;
import com.greencustard.viewmodel.usersearch.intention.UserSearchIntention;
import com.greencustard.viewmodel.usersearch.state.DefaultUserSearchState;
import com.greencustard.viewmodel.usersearch.state.DisplayingResultsSearchState;
import com.greencustard.viewmodel.usersearch.state.UserSearchState;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Predicate;

public class SelectUserMapper extends UserSearchMapper {

    public SelectUserMapper(UserSearchDependencies mDependencies) {
        super(mDependencies);
    }

    @Override
    public boolean supports(UserSearchIntention intention) {
        return intention instanceof SelectUserIntention;
    }

    @Override
    public Single<UserSearchState> map(UserSearchState state, UserSearchIntention intention) {

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

        return Single.<UserSearchState>just(new DisplayingResultsSearchState(Optional.of(selectedId),((DisplayingResultsSearchState) state).getResults()));
    }
}
