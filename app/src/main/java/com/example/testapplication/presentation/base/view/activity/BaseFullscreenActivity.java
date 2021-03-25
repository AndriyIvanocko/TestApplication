package com.example.testapplication.presentation.base.view.activity;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.testapplication.presentation.base.presenter.Presenter;
import com.example.testapplication.presentation.base.view.BaseView;

public abstract class BaseFullscreenActivity<P extends Presenter> extends BaseActivity<P> implements BaseView<P> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareWindowConfig();
    }

    protected void prepareWindowConfig() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (v, insets) -> new WindowInsetsCompat.Builder()
                    .setSystemWindowInsets(Insets.NONE)
                    .build());
        }
    }
}
