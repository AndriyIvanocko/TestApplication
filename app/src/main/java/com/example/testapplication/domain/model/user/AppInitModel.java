package com.example.testapplication.domain.model.user;

import android.os.Parcel;
import android.os.Parcelable;

public class AppInitModel implements Parcelable {
    private UserModel mUserModel;

    public AppInitModel() {
    }

    protected AppInitModel(Parcel in) {
        mUserModel = in.readParcelable(UserModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mUserModel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppInitModel> CREATOR = new Creator<AppInitModel>() {
        @Override
        public AppInitModel createFromParcel(Parcel in) {
            return new AppInitModel(in);
        }

        @Override
        public AppInitModel[] newArray(int size) {
            return new AppInitModel[size];
        }
    };

    public UserModel getUser() {
        return mUserModel;
    }

    public void setUser(UserModel mUserModel) {
        this.mUserModel = mUserModel;
    }
}
