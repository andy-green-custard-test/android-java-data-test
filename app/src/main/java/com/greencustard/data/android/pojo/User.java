package com.greencustard.data.android.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"address","company"}) //App has no use for the fields this time to meet its current requirements
public class User {
    private final int mId;
    private final String mName;
    private final String mUsername;
    private final String mEmail;
    private final String mPhone;
    private final String mWebsite;

    @JsonCreator
    public User(@JsonProperty("id") int mId, @JsonProperty("name") String mName, @JsonProperty("username") String mUsername, @JsonProperty("email") String mEmail, @JsonProperty("phone") String mPhone, @JsonProperty("website") String mWebsite) {
        this.mId = mId;
        this.mName = mName;
        this.mUsername = mUsername;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
        this.mWebsite = mWebsite;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmail;
    }

    @Override
    public String toString() {
        return getName(); //Cludge for AutocompleteTextView - should do this properly using a Filter
    }
}
