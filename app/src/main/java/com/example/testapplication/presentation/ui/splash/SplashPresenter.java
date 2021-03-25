package com.example.testapplication.presentation.ui.splash;

import com.example.testapplication.di.scopes.ConfigPersistentScope;
import com.example.testapplication.presentation.base.presenter.BasePresenter;

import javax.inject.Inject;

@ConfigPersistentScope
public class SplashPresenter extends BasePresenter<SplashView> {

    @Inject
    public SplashPresenter() {
    }
}
