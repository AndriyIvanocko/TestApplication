package com.example.testapplication.data.network.model.response.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserData {
    @Expose
    @SerializedName("telegram_connect_url")
    private String mTelegramConnectUrl;
    @Expose
    @SerializedName("is_telegram_connected")
    private boolean mIsTelegramConnected;
    @Expose
    @SerializedName("email")
    private String mEmail;
    @Expose
    @SerializedName("projects")
    private List<String> mProjects;
    @Expose
    @SerializedName("is_shared_from_me")
    private boolean mIsSharedFromMe;
    @Expose
    @SerializedName("is_timer_online")
    private int mIsTimerOnline;
    @Expose
    @SerializedName("due_penalties")
    private int mDuePenalties;
    @Expose
    @SerializedName("role")
    private String mRole;
    @Expose
    @SerializedName("id_company")
    private int mIdCompany;
    @Expose
    @SerializedName("is_active")
    private boolean mIsActive;
    @Expose
    @SerializedName("dta_activity")
    private String mDtaActivity;
//    @Expose
//    @SerializedName("is_online")
//    private boolean mIsOnline;
    @Expose
    @SerializedName("timezone_offset")
    private int mTimezoneOffset;
    @Expose
    @SerializedName("dta_create")
    private String mDtaCreate;
    @Expose
    @SerializedName("avatar_url")
    private String mAvatarUrl;
    @Expose
    @SerializedName("nick")
    private String mNick;
    @Expose
    @SerializedName("name")
    private String mName;
    @Expose
    @SerializedName("id")
    private int mId;

    public String getTelegramConnectUrl() {
        return mTelegramConnectUrl;
    }

    public void setTelegramConnectUrl(String mTelegramConnectUrl) {
        this.mTelegramConnectUrl = mTelegramConnectUrl;
    }

    public boolean getIsTelegramConnected() {
        return mIsTelegramConnected;
    }

    public void setIsTelegramConnected(boolean mIsTelegramConnected) {
        this.mIsTelegramConnected = mIsTelegramConnected;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public List<String> getProjects() {
        return mProjects;
    }

    public void setProjects(List<String> mProjects) {
        this.mProjects = mProjects;
    }

    public boolean getIsSharedFromMe() {
        return mIsSharedFromMe;
    }

    public void setIsSharedFromMe(boolean mIsSharedFromMe) {
        this.mIsSharedFromMe = mIsSharedFromMe;
    }

    public int getIsTimerOnline() {
        return mIsTimerOnline;
    }

    public void setIsTimerOnline(int mIsTimerOnline) {
        this.mIsTimerOnline = mIsTimerOnline;
    }

    public int getDuePenalties() {
        return mDuePenalties;
    }

    public void setDuePenalties(int mDuePenalties) {
        this.mDuePenalties = mDuePenalties;
    }

    public String getRole() {
        return mRole;
    }

    public void setRole(String mRole) {
        this.mRole = mRole;
    }

    public int getIdCompany() {
        return mIdCompany;
    }

    public void setIdCompany(int mIdCompany) {
        this.mIdCompany = mIdCompany;
    }

    public boolean getIsActive() {
        return mIsActive;
    }

    public void setIsActive(boolean mIsActive) {
        this.mIsActive = mIsActive;
    }

    public String getDtaActivity() {
        return mDtaActivity;
    }

    public void setDtaActivity(String mDtaActivity) {
        this.mDtaActivity = mDtaActivity;
    }

    public boolean getIsOnline() {
        return false;
    }

//    public void setIsOnline(boolean mIsOnline) {
//        this.mIsOnline = mIsOnline;
//    }

    public int getTimezoneOffset() {
        return mTimezoneOffset;
    }

    public void setTimezoneOffset(int mTimezoneOffset) {
        this.mTimezoneOffset = mTimezoneOffset;
    }

    public String getDtaCreate() {
        return mDtaCreate;
    }

    public void setDtaCreate(String mDtaCreate) {
        this.mDtaCreate = mDtaCreate;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String mAvatarUrl) {
        this.mAvatarUrl = mAvatarUrl;
    }

    public String getNick() {
        return mNick;
    }

    public void setNick(String mNick) {
        this.mNick = mNick;
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
