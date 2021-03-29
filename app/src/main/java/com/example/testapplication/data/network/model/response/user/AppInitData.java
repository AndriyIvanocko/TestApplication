package com.example.testapplication.data.network.model.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppInitData {
    @Expose
    @SerializedName("user")
    private UserData mUserData;

    public UserData getUser() {
        return mUserData;
    }

    public void setUser(UserData mUserData) {
        this.mUserData = mUserData;
    }
}
