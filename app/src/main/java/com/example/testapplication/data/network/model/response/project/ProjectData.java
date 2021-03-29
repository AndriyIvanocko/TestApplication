package com.example.testapplication.data.network.model.response.project;

import com.example.testapplication.data.network.model.response.user.UserData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectData {
    @Expose
    @SerializedName("users")
    private List<UserData> mUsers;
    @Expose
    @SerializedName("dta_user_since")
    private String mDtaUserSince;
    @Expose
    @SerializedName("is_owner_watcher")
    private boolean mIsOwnerWatcher;
    @Expose
    @SerializedName("is_active")
    private int mIsActive;
    @Expose
    @SerializedName("position")
    private int mPosition;
    @Expose
    @SerializedName("logo_url")
    private String mLogoUrl;
    @Expose
    @SerializedName("uid")
    private String mUid;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("id")
    private int mId;

    public List<UserData> getUsers() {
        return mUsers;
    }

    public void setUsers(List<UserData> mUsers) {
        this.mUsers = mUsers;
    }

    public String getDtaUserSince() {
        return mDtaUserSince;
    }

    public void setDtaUserSince(String mDtaUserSince) {
        this.mDtaUserSince = mDtaUserSince;
    }

    public boolean getIsOwnerWatcher() {
        return mIsOwnerWatcher;
    }

    public void setIsOwnerWatcher(boolean mIsOwnerWatcher) {
        this.mIsOwnerWatcher = mIsOwnerWatcher;
    }

    public int getIsActive() {
        return mIsActive;
    }

    public void setIsActive(int mIsActive) {
        this.mIsActive = mIsActive;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public String getLogoUrl() {
        return mLogoUrl;
    }

    public void setLogoUrl(String mLogoUrl) {
        this.mLogoUrl = mLogoUrl;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
}
