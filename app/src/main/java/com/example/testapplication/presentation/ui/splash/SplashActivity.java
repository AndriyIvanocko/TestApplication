package com.example.testapplication.presentation.ui.splash;

import com.example.testapplication.R;
import com.example.testapplication.presentation.base.view.activity.BaseFullscreenActivity;

public class SplashActivity extends BaseFullscreenActivity<SplashPresenter> implements SplashView{
    @Override
    protected void injectToComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_splash;
    }
}
