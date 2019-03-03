package com.greencustard.data.android.viewmodel.postsearch;

import com.greencustard.viewmodel.postsearch.MockPostSearchDependencies;
import com.greencustard.viewmodel.postsearch.PostSearchViewModel;
import com.greencustard.viewmodel.postsearch.dependencies.PostSearchDependenciesInterface;
import com.greencustard.viewmodel.postsearch.intention.LoadResultsIntention;
import com.greencustard.viewmodel.postsearch.intention.SelectUserIntention;
import com.greencustard.viewmodel.postsearch.state.DefaultPostSearchState;
import com.greencustard.viewmodel.postsearch.state.DisplayingResultsSearchState;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class PostSearchViewModelTest {

    //With more time, would use a more appropriate way to check results
    private static final long TIMEOUT = 500;

    private PostSearchDependenciesInterface mDependencies;
    private PostSearchViewModel mViewModel;

    @Before
    public void setUp() {
        mDependencies = spy(new MockPostSearchDependencies());
        mViewModel = new PostSearchViewModel(mDependencies);
    }

    @Test
    public void searchAll() throws InterruptedException {
        mViewModel.submit(new LoadResultsIntention());

        Thread.sleep(TIMEOUT);
        DisplayingResultsSearchState state = (DisplayingResultsSearchState) mViewModel.getLatest();

        assertThat(state.getResults().size(),is(3));
    }

    @Test
    public void selectValid() throws InterruptedException {
        mViewModel.submit(new LoadResultsIntention());
        mViewModel.submit(new SelectUserIntention(1));

        Thread.sleep(TIMEOUT);
        DefaultPostSearchState state = (DefaultPostSearchState) mViewModel.getLatest();
        assertThat(state.getSelectedUserId().get(),is(1));
        verify(mDependencies).onSelectUser(eq(1));
    }

    @Test
    public void selectWithoutLoad() throws InterruptedException {
        mViewModel.submit(new SelectUserIntention(1));

        Thread.sleep(TIMEOUT);
        DefaultPostSearchState state = (DefaultPostSearchState) mViewModel.getLatest();
        assertThat(state.getSelectedUserId().isPresent(),is(false));
        verify(mDependencies,never()).onSelectUser(any(Integer.class));
    }

    @Test (expected = Throwable.class)
    public void selectNonExistentResult() throws InterruptedException {
        mViewModel.submit(new LoadResultsIntention());
        mViewModel.submit(new SelectUserIntention(666));

        Thread.sleep(TIMEOUT);
        DefaultPostSearchState state = (DefaultPostSearchState) mViewModel.getLatest();
        assertThat(state.getSelectedUserId().isPresent(),is(false));
        verify(mDependencies,never()).onSelectUser(any(Integer.class));
    }
};