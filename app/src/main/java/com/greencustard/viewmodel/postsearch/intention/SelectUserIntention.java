package com.greencustard.viewmodel.postsearch.intention;

public class SelectUserIntention implements PostSearchIntention {

    private final int mId;

    public SelectUserIntention(int mId) {
        this.mId = mId;
    }

    public int getId() {
        return mId;
    }
}
