package com.example.testapplication.di.components;

import com.example.testapplication.di.modules.ActivityModule;
import com.example.testapplication.di.scopes.PerActivityScope;
import com.example.testapplication.presentation.ui.splash.SplashActivity;

import dagger.Subcomponent;

@PerActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);

    //void inject(LoginActivity loginActivity);

}
