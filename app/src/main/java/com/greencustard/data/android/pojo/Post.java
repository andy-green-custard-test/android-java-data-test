package com.greencustard.data.android.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {

    private final int mUserId;
    private final int mId;
    private final String mTitle;
    private final String mBody;

    @JsonCreator
    public Post(@JsonProperty("userId") int mUserId, @JsonProperty("id") int mId, @JsonProperty("title") String mTitle, @JsonProperty("body") String mBody) {
        this.mUserId = mUserId;
        this.mId = mId;
        this.mTitle = mTitle;
        this.mBody = mBody;
    }

    public int getUserId() {
        return mUserId;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }
}
