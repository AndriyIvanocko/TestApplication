package com.example.testapplication.presentation.base.view.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.testapplication.R;
import com.example.testapplication.common.display.DpiTransform;
import com.example.testapplication.presentation.base.presenter.Presenter;
import com.example.testapplication.presentation.base.view.BaseView;
import com.example.testapplication.presentation.base.view.ToolbarButtonType;
import com.example.testapplication.presentation.base.view.activity.BaseActivity;

public abstract class BaseToolbarFragment<P extends Presenter> extends BaseFragment<P> implements BaseView<P> {

    protected Toolbar mToolbar;
    protected AppCompatTextView mToolbarTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupToolbar();
        return view;
    }

    @NonNull
    protected abstract ToolbarButtonType getToolbarButtonType();

    protected abstract String getToolbarTitle();

    protected void setupToolbar() {
        mToolbar = mRootView.findViewById(getToolbarId());
        mToolbarTitle = mRootView.findViewById(getToolbarTitleId());

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        ToolbarButtonType buttonType = getToolbarButtonType();
        actionBar.setHomeButtonEnabled(buttonType != ToolbarButtonType.NONE);
        actionBar.setDisplayHomeAsUpEnabled(buttonType != ToolbarButtonType.NONE);

        if (getNavigationIconRes() != BaseActivity.INVALID_RES_ID) {
            centerText();
            actionBar.setDisplayHomeAsUpEnabled(true);
            Drawable upArrow = AppCompatResources.getDrawable(getContext(), getNavigationIconRes());
            if (upArrow == null) return;
            if (getNavigationIconColor() != 0) {
                upArrow.setColorFilter(getNavigationIconColor(), PorterDuff.Mode.SRC_ATOP);
            }
            actionBar.setHomeAsUpIndicator(upArrow);
        }

        if (getToolbarLogoRes() != BaseActivity.INVALID_RES_ID) {
            actionBar.setLogo(getToolbarLogoRes());
        }

        setTitle(getToolbarTitle());
    }

    protected int getToolbarId() {
        return R.id.toolbar;
    }

    protected int getToolbarTitleId() {
        return R.id.text_toolbar_title;
    }

    @DrawableRes
    protected int getNavigationIconRes() {
        switch (getToolbarButtonType()) {
            case BACK:
                return R.drawable.ic_back;
            case NONE:
                return BaseActivity.INVALID_RES_ID;
            default:
                return BaseActivity.INVALID_RES_ID;
        }
    }

    @DrawableRes
    protected int getToolbarLogoRes() {
        return BaseActivity.INVALID_RES_ID;
    }

    protected int getNavigationIconColor() {
        return 0;
    }

    public void setTitle(String title) {
        if (mToolbarTitle == null) return;
        mToolbarTitle.setText(title);
    }

    protected void setTransparentTitle(Boolean isTransparent) {
        if (mToolbarTitle == null) return;
        if (isTransparent)
            mToolbarTitle.animate().alpha(0f).setDuration(100L);
        else
            mToolbarTitle.animate().alpha(1f).setDuration(100L);
    }

    private void centerText() {
        Toolbar.LayoutParams params = (Toolbar.LayoutParams) mToolbarTitle.getLayoutParams();
        params.setMarginEnd(DpiTransform.dpToPx(56f));
    }
}
