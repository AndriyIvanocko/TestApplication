package com.example.testapplication.data.local;

import android.content.Context;
import android.content.SharedPreferences;


import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserStorageImpl implements UserStorage {
    private static final String USER_PREF_PATH = "user_prefferences";
    private static final String USER_PREF = "user";

    private SharedPreferences mPreferences;
    //private UserModel mUser;

    @Inject
    public UserStorageImpl(final Context context) {
        mPreferences = context.getSharedPreferences(USER_PREF_PATH, Context.MODE_PRIVATE);
        //TODO create user from Json
        //mUser = new Gson().fromJson(mPreferences.getString(USER_PREF, null), UserModel.class);
    }

    @Override
    public String getToken() {
        // TODO get token from user
        //return mUser != null ? mUser.getAuthenticationToken() : null;
        return "";
    }

    @Override
    public void clearUserData() {
        //TODO uncomment when user model will be added
        //setUser(null);
    }

    /*@Nullable
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
    }*/

    @Override
    public boolean isSignedIn() {
//        return mUser != null;
        return false;
    }
}
