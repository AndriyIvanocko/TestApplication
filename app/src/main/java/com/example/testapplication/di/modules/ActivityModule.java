package com.example.testapplication.di.modules;


import com.example.testapplication.presentation.base.view.activity.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private BaseActivity mActivity;

    public ActivityModule(final BaseActivity activity) {
        mActivity = activity;
    }

    @Provides
    BaseActivity provideActivity() {
        return mActivity;
    }

}
