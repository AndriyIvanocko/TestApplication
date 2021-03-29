package com.example.testapplication.domain.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserModel implements Parcelable {

    private String mTelegramConnectUrl;
    private boolean mIsTelegramConnected;
    private String mEmail;
    private List<String> mProjects;
    private boolean mIsSharedFromMe;
    private int mIsTimerOnline;
    private int mDuePenalties;
    private String mRole;
    private int mIdCompany;
    private boolean mIsActive;
    private String mDtaActivity;
    private boolean mIsOnline;
    private int mTimezoneOffset;
    private String mDtaCreate;
    private String mAvatarUrl;
    private String mNick;
    private String mName;
    private int mId;

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        mTelegramConnectUrl = in.readString();
        mIsTelegramConnected = in.readByte() != 0;
        mEmail = in.readString();
        mProjects = in.createStringArrayList();
        mIsSharedFromMe = in.readByte() != 0;
        mIsTimerOnline = in.readInt();
        mDuePenalties = in.readInt();
        mRole = in.readString();
        mIdCompany = in.readInt();
        mIsActive = in.readByte() != 0;
        mDtaActivity = in.readString();
        mIsOnline = in.readByte() != 0;
        mTimezoneOffset = in.readInt();
        mDtaCreate = in.readString();
        mAvatarUrl = in.readString();
        mNick = in.readString();
        mName = in.readString();
        mId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTelegramConnectUrl);
        dest.writeByte((byte) (mIsTelegramConnected ? 1 : 0));
        dest.writeString(mEmail);
        dest.writeStringList(mProjects);
        dest.writeByte((byte) (mIsSharedFromMe ? 1 : 0));
        dest.writeInt(mIsTimerOnline);
        dest.writeInt(mDuePenalties);
        dest.writeString(mRole);
        dest.writeInt(mIdCompany);
        dest.writeByte((byte) (mIsActive ? 1 : 0));
        dest.writeString(mDtaActivity);
        dest.writeInt((byte) (mIsOnline ? 1 : 0));
        dest.writeInt(mTimezoneOffset);
        dest.writeString(mDtaCreate);
        dest.writeString(mAvatarUrl);
        dest.writeString(mNick);
        dest.writeString(mName);
        dest.writeInt(mId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

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
        return mIsOnline;
    }

    public void setIsOnline(boolean mIsOnline) {
        this.mIsOnline = mIsOnline;
    }

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
