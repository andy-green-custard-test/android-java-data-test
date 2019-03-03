package com.greencustard.viewmodel.usersearch.intention;

public class SelectUserIntention implements UserSearchIntention {

    private final int mId;

    public SelectUserIntention(int mId) {
        this.mId = mId;
    }

    public int getId() {
        return mId;
    }
}
