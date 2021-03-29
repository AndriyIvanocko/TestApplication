package com.example.testapplication.presentation.ui.splash;

import com.example.testapplication.di.scopes.ConfigPersistentScope;
import com.example.testapplication.domain.model.user.UserModel;
import com.example.testapplication.presentation.base.presenter.BaseErrorHandlingPresenter;

import javax.inject.Inject;

@ConfigPersistentScope
public class SplashPresenter extends BaseErrorHandlingPresenter<SplashView> {
    private UserModel model;

    @Inject
    public SplashPresenter() {
    }

    public void setModel() {
        if(mUserStorage.isSignedIn()){
            getView().openMainActivity();
        } else {
            getView().openLoginActivity();
        }
    }
}
