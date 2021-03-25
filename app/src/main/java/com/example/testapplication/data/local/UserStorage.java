package com.example.testapplication.data.local;


/**
 * Created by Anatoliy Mizyakin on 27.09.2018.
 */
public interface UserStorage {
    String getToken();

    void clearUserData();

    //TODO uncomment when user model will be added

    boolean isSignedIn();

//    UserModel getUser();

//    void setUser(UserModel user);
}
