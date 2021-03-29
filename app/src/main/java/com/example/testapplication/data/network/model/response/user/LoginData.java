package com.example.testapplication.data.network.model.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @Expose
    @SerializedName("app_init")
    private AppInitData mAppInitData;
    @Expose
    @SerializedName("token")
    private String mToken;

    public AppInitData getAppInit() {
        return mAppInitData;
    }

    public void setAppInit(AppInitData mAppInitData) {
        this.mAppInitData = mAppInitData;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }
}
