package com.example.testapplication.domain.model.project;

import com.example.testapplication.domain.model.user.UserModel;

import java.util.List;

public class ProjectModel {
    private List<UserModel> mUsers;
    private String mDtaUserSince;
    private boolean mIsOwnerWatcher;
    private int mIsActive;
    private int mPosition;
    private String mLogoUrl;
    private String mUid;
    private String mName;
    private int mId;

    public List<UserModel> getUsers() {
        return mUsers;
    }

    public void setUsers(List<UserModel> mUsers) {
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
