package com.example.testapplication.presentation.base.view.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import com.example.testapplication.R;
import com.example.testapplication.common.display.DpiTransform;
import com.example.testapplication.presentation.base.presenter.Presenter;
import com.example.testapplication.presentation.base.view.BaseView;
import com.example.testapplication.presentation.base.view.ToolbarButtonType;


public abstract class BaseToolbarActivity<P extends Presenter> extends BaseActivity<P> implements BaseView<P> {

    protected Toolbar mToolbar;
    protected TextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar();
    }

    @NonNull
    protected abstract ToolbarButtonType getToolbarButtonType();

    protected abstract String getToolbarTitle();

    protected void setupToolbar() {
        mToolbar = mRootView.findViewById(getToolbarId());
        mToolbarTitle = mRootView.findViewById(getToolbarTitleId());

        setSupportActionBar(mToolbar);

        ToolbarButtonType buttonType = getToolbarButtonType();
        getSupportActionBar().setHomeButtonEnabled(buttonType != ToolbarButtonType.NONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(buttonType != ToolbarButtonType.NONE);

        if (getNavigationIconRes() != INVALID_RES_ID) {
            centerText();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable upArrow = AppCompatResources.getDrawable(this, getNavigationIconRes());
            if (upArrow == null) return;
            if (getNavigationIconColor() != 0) {
                upArrow.setColorFilter(getNavigationIconColor(), PorterDuff.Mode.SRC_ATOP);
            }
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        if (getToolbarLogoRes() != INVALID_RES_ID) {
            getSupportActionBar().setLogo(getToolbarLogoRes());
        }

        setTitle(getToolbarTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                return INVALID_RES_ID;
            default:
                return INVALID_RES_ID;
        }
    }

    @DrawableRes
    protected int getToolbarLogoRes() {
        return INVALID_RES_ID;
    }

    protected int getNavigationIconColor() {
        return 0;
    }

    @Override
    public void setTitle(int titleId) {
        if (mToolbarTitle == null) return;
        mToolbarTitle.setText(titleId);
    }

    @Override
    public void setTitle(CharSequence title) {
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

    protected void centerText() {
        Toolbar.LayoutParams params = (Toolbar.LayoutParams) mToolbarTitle.getLayoutParams();
        params.setMarginEnd(DpiTransform.dpToPx(56f));
    }
}
