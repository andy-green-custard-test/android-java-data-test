package com.greencustard.viewmodel.postsearch.state;

import com.greencustard.data.android.pojo.User;

import java.util.List;
import java.util.Optional;

public abstract class PostSearchState {

    final Optional<Integer> mSelectedUserId;

    PostSearchState(Optional<Integer> selectedUserId) {
        mSelectedUserId = selectedUserId;
    }

    public Optional<Integer> getSelectedUserId() {
        return mSelectedUserId;
    }
}
