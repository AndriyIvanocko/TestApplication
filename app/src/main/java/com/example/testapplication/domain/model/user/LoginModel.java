package com.example.testapplication.domain.model.user;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginModel implements Parcelable {

    private AppInitModel mAppInitModel;
    private String mToken;

    public LoginModel() {
    }

    protected LoginModel(Parcel in) {
        mToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };

    public AppInitModel getAppInit() {
        return mAppInitModel;
    }

    public void setAppInit(AppInitModel mAppInitModel) {
        this.mAppInitModel = mAppInitModel;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }
}
