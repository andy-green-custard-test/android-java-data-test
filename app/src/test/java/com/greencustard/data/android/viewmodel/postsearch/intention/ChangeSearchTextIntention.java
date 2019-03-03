package com.greencustard.data.android.viewmodel.postsearch.intention;

public class ChangeSearchTextIntention implements PostSearchIntention {

    private final String mSearchText;

    public ChangeSearchTextIntention(String mSearchText) {
        this.mSearchText = mSearchText;
    }

    public String getSearchText() {
        return mSearchText;
    }
}
