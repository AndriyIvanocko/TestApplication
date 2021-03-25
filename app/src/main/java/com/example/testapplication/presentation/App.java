package com.example.testapplication.presentation;

import android.app.Application;

import com.example.testapplication.di.components.ApplicationComponent;
import com.example.testapplication.di.components.DaggerApplicationComponent;
import com.example.testapplication.di.modules.ApplicationModule;

public class App extends Application {
    private static App sInstance;
    protected ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        getAppComponent().inject(this);
    }

    public static App getInstance() {
        return sInstance;
    }

    public ApplicationComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mAppComponent;
    }

}
