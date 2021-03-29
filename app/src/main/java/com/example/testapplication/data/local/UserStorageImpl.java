package com.example.testapplication.data.local;

import android.content.Context;
import android.content.SharedPreferences;


import androidx.annotation.Nullable;

import com.example.testapplication.domain.model.user.LoginModel;
import com.example.testapplication.domain.model.user.UserModel;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserStorageImpl implements UserStorage {
    private static final String USER_PREF_PATH = "user_prefferences";
    private static final String USER_PREF = "user";
    private static final String LOGIN_PREF = "login";

    private SharedPreferences mPreferences;
    private UserModel mUser;
    private String token;

    @Inject
    public UserStorageImpl(final Context context) {
        mPreferences = context.getSharedPreferences(USER_PREF_PATH, Context.MODE_PRIVATE);
        mUser = new Gson().fromJson(mPreferences.getString(USER_PREF, null), UserModel.class);
        token = mPreferences.getString(LOGIN_PREF, "");
    }

    @Override
    public String getToken() {
        return mUser != null ? token : null;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
        mPreferences.edit().putString(LOGIN_PREF, token).apply();
    }

    @Override
    public void clearUserData() {
        setUser(null);
    }

    @Nullable
    @Override
    public UserModel getUser() {
        return mUser;
    }

    @Override
    public void setUser(UserModel user) {
        mUser = user;
        if (mUser == null) {
            mPreferences.edit().putString(USER_PREF, null).apply();
        } else {
            mPreferences.edit().putString(USER_PREF, new Gson().toJson(mUser, UserModel.class)).apply();
        }
    }

    @Override
    public boolean isSignedIn() {
        return mUser != null;
    }
}
