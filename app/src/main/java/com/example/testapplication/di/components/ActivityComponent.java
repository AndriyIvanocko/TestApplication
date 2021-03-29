package com.example.testapplication.di.components;

import com.example.testapplication.di.modules.ActivityModule;
import com.example.testapplication.di.scopes.PerActivityScope;
import com.example.testapplication.presentation.ui.login.LoginActivity;
import com.example.testapplication.presentation.ui.main.MainActivity;
import com.example.testapplication.presentation.ui.splash.SplashActivity;

import dagger.Subcomponent;

@PerActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    //void inject(LoginActivity loginActivity);

}
