package com.example.testapplication.presentation.ui.splash;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.testapplication.R;
import com.example.testapplication.presentation.base.view.activity.BaseFullscreenActivity;
import com.example.testapplication.presentation.ui.main.MainActivity;

public class SplashActivity extends BaseFullscreenActivity<SplashPresenter> implements SplashView{
    @Override
    protected void injectToComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().setModel();
    }

    @Override
    public void openMainActivity() {
        startActivity(MainActivity.getLaunchIntent(this));
    }
}
