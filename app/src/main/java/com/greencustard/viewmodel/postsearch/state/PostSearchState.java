package com.greencustard.viewmodel.postsearch.state;

import com.greencustard.data.android.pojo.Post;

import java.util.List;
import java.util.Optional;

public abstract class PostSearchState {
    private final Optional<Integer> mSelectedUserId;
    private final List<Post> mPosts;

    public PostSearchState(Optional<Integer> selectedUserId, List<Post> posts) {
        mSelectedUserId = selectedUserId;
        mPosts = posts;
    }

    public Optional<Integer> getSelectedUserId() {
        return mSelectedUserId;
    }

    public List<Post> getPosts() {
        return mPosts;
    }
}
