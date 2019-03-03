package com.greencustard.viewmodel.usersearch;

import com.google.common.collect.Sets;
import com.greencustard.viewmodel.usersearch.mappers.SelectUserMapper;
import com.greencustard.viewmodel.usersearch.dependencies.UserSearchDependencies;
import com.greencustard.viewmodel.usersearch.intention.UserSearchIntention;
import com.greencustard.viewmodel.usersearch.mappers.LoadResultsMapper;
import com.greencustard.viewmodel.usersearch.mappers.UserSearchMapper;
import com.greencustard.viewmodel.usersearch.state.DefaultUserSearchState;
import com.greencustard.viewmodel.usersearch.state.UserSearchState;

import java.util.Optional;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

public class UserSearchViewModel {

    private final UserSearchDependencies mDependencies;
    private final Set<UserSearchMapper> mMappers;
    private final ReplaySubject<UserSearchIntention> mSubject;
    private final Scheduler mProcessingScheduler;
    private final Observable<UserSearchState> mState;
    private UserSearchState mLatest;


    public UserSearchViewModel(UserSearchDependencies dependencies) {
        mDependencies = dependencies;
        mMappers = Sets.newHashSet(new LoadResultsMapper(mDependencies),new SelectUserMapper(mDependencies));
        mSubject = ReplaySubject.create();
        mProcessingScheduler = Schedulers.single();
        mState = mSubject
                .observeOn(mProcessingScheduler)
                .scan(
                new DefaultUserSearchState(Optional.<Integer>empty()),
                new BiFunction<UserSearchState, UserSearchIntention, UserSearchState>() {
                    @Override
                    public UserSearchState apply(UserSearchState previous, UserSearchIntention intention) throws Exception {
                        UserSearchMapper mapper = mapperFor(intention);
                        return mapper.map(previous,intention).blockingGet();
                    }
                }
        );
        mState.subscribe(new Consumer<UserSearchState>() {
            @Override
            public void accept(UserSearchState userSearchState) throws Exception {
                mLatest = userSearchState;
            }
        });
    }

    private UserSearchMapper mapperFor(final UserSearchIntention intention) {
        return Observable.fromIterable(mMappers)
                .filter(new Predicate<UserSearchMapper>() {
                    @Override
                    public boolean test(UserSearchMapper mapper) throws Exception {
                        return mapper.supports(intention);
                    }
                })
                .toList()
                .blockingGet()
                .get(0);
    }

    public Observable<UserSearchState> getStateObservable() {
        return mState;
    }

    public void submit(UserSearchIntention intention) {
        mSubject.onNext(intention);
    }

    public UserSearchState getLatest() {
        return mLatest;
    }



    @Override
    protected void finalize() throws Throwable {
        mProcessingScheduler.shutdown();
        super.finalize();
    }
}
