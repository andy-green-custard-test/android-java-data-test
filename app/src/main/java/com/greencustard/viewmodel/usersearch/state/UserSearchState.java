package com.greencustard.viewmodel.usersearch.state;

import java.util.Optional;

public abstract class UserSearchState {

    final Optional<Integer> mSelectedUserId;

    public UserSearchState(Optional<Integer> selectedUserId) {
        mSelectedUserId = selectedUserId;
    }

    public Optional<Integer> getSelectedUserId() {
        return mSelectedUserId;
    }
}
