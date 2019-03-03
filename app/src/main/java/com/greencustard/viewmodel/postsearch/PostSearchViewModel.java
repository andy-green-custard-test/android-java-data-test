package com.greencustard.viewmodel.postsearch;

import com.google.common.collect.Sets;
import com.greencustard.viewmodel.postsearch.dependencies.PostSearchDependencies;
import com.greencustard.viewmodel.postsearch.intention.PostSearchIntention;
import com.greencustard.viewmodel.postsearch.mapper.PostSearchMapper;
import com.greencustard.viewmodel.postsearch.mapper.SetUserMapper;
import com.greencustard.viewmodel.postsearch.state.DefaultPostSearchState;
import com.greencustard.viewmodel.postsearch.state.PostSearchState;

import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

public class PostSearchViewModel {

    private final PostSearchDependencies mDependencies;
    private final Set<PostSearchMapper> mMappers;
    private final ReplaySubject<PostSearchIntention> mSubject;
    private final Scheduler mProcessingScheduler;
    private final Observable<PostSearchState> mState;
    private PostSearchState mLatest;


    public PostSearchViewModel(PostSearchDependencies dependencies) {
        mDependencies = dependencies;
        mMappers = Sets.<PostSearchMapper>newHashSet(new SetUserMapper(dependencies));
        mSubject = ReplaySubject.create();
        mProcessingScheduler = Schedulers.single();
        mState = mSubject
                .observeOn(mProcessingScheduler)
                .scan(
                        new DefaultPostSearchState(),
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
            public void accept(PostSearchState userSearchState) throws Exception {
                mLatest = userSearchState;
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
