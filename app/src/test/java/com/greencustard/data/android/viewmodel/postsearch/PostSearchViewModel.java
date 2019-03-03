package com.greencustard.data.android.viewmodel.postsearch;

import com.google.common.collect.Sets;
import com.greencustard.data.android.viewmodel.postsearch.dependencies.PostSearchDependenciesInterface;
import com.greencustard.data.android.viewmodel.postsearch.intention.PostSearchIntention;
import com.greencustard.data.android.viewmodel.postsearch.mappers.ChangeSearchTextMapper;
import com.greencustard.data.android.viewmodel.postsearch.mappers.PostSearchMapper;
import com.greencustard.data.android.viewmodel.postsearch.mappers.SelectUserMapper;
import com.greencustard.data.android.viewmodel.postsearch.state.DefaultPostSearchState;
import com.greencustard.data.android.viewmodel.postsearch.state.PostSearchState;

import java.util.Optional;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

public class PostSearchViewModel {

    private final PostSearchDependenciesInterface mDependencies;
    private final Set<PostSearchMapper> mMappers;
    private final ReplaySubject<PostSearchIntention> mSubject;
    private final Scheduler mProcessingScheduler;
    private final Observable<PostSearchState> mState;
    private PostSearchState mLatest;


    public PostSearchViewModel(PostSearchDependenciesInterface dependencies) {
        mDependencies = dependencies;
        mMappers = Sets.newHashSet(new ChangeSearchTextMapper(mDependencies),new SelectUserMapper(mDependencies));
        mSubject = ReplaySubject.create();
        mProcessingScheduler = Schedulers.single();
        mState = mSubject
                .observeOn(mProcessingScheduler)
                .scan(
                new DefaultPostSearchState(Optional.<Integer>empty()),
                new BiFunction<PostSearchState, PostSearchIntention, PostSearchState>() {
                    @Override
                    public PostSearchState apply(PostSearchState previous, PostSearchIntention intention) throws Exception {
                        PostSearchMapper mapper = mapperFor(intention);
                        return mapper.map(previous,intention).blockingGet();
                    }
                }
        );
        mState.subscribe(new Consumer<PostSearchState>() {
            @Override
            public void accept(PostSearchState postSearchState) throws Exception {
                mLatest = postSearchState;
            }
        });
    }

    private PostSearchMapper mapperFor(final PostSearchIntention intention) {
        return Observable.fromIterable(mMappers)
                .filter(new Predicate<PostSearchMapper>() {
                    @Override
                    public boolean test(PostSearchMapper mapper) throws Exception {
                        return mapper.supports(intention);
                    }
                })
                .toList()
                .blockingGet()
                .get(0);
    }

    public Observable<PostSearchState> getStateObservable() {
        return mState;
    }

    public void submit(PostSearchIntention intention) {
        mSubject.onNext(intention);
    }

    public PostSearchState getLatest() {
        return mLatest;
    }



    @Override
    protected void finalize() throws Throwable {
        mProcessingScheduler.shutdown();
        super.finalize();
    }
}
