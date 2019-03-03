package com.greencustard.viewmodel.postsearch.state;

import com.greencustard.data.android.pojo.User;

import java.util.List;
import java.util.Optional;

public class DisplayingResultsSearchState extends PostSearchState {

    private final String mSearchText;
    private final List<User> mResults;

    public DisplayingResultsSearchState(Optional<Integer> selectedUserId, String searchText, List<User> results) {
        super(selectedUserId);
        this.mSearchText = searchText;
        this.mResults = results;
    }

    public String getSearchText() {
        return mSearchText;
    }

    public List<User> getResults() {
        return mResults;
    }
}
