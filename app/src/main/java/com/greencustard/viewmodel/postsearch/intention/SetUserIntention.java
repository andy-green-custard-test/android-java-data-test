package com.greencustard.viewmodel.postsearch.intention;

public class SetUserIntention implements PostSearchIntention {

    private final int mUserId;

    public SetUserIntention(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getUserId() {
        return mUserId;
    }
}
