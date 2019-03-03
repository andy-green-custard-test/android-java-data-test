package com.greencustard.viewmodel.usersearch.state;

import com.greencustard.data.android.pojo.User;

import java.util.List;
import java.util.Optional;

public class DisplayingResultsSearchState extends UserSearchState {
    private final List<User> mResults;

    public DisplayingResultsSearchState(Optional<Integer> selectedUserId, List<User> results) {
        super(selectedUserId);
        this.mResults = results;
    }

    public List<User> getResults() {
        return mResults;
    }
}
