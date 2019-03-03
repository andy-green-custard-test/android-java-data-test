package com.greencustard.data.android.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Comment {

    private final int mPostId;
    private final int mId;
    private final String mName;
    private final String mEmail;
    private final String mBody;

    @JsonCreator
    public Comment(@JsonProperty("postId") int mPostId, @JsonProperty("id") int mId, @JsonProperty("name") String mName, @JsonProperty("email") String mEmail, @JsonProperty("body") String mBody) {
        this.mPostId = mPostId;
        this.mId = mId;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mBody = mBody;
    }

    public int getPostId() {
        return mPostId;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getBody() {
        return mBody;
    }
}
