package com.example.testapplication.presentation.base.view.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.testapplication.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseDialogFragment extends DialogFragment {
    protected String mTitle;
    protected String mMessage;
    protected String mPositiveButtonText;
    protected String mNegativeButtonText;
    protected DialogListener mDialogListener;
    protected Unbinder mUnbinder;
    protected View mRootView;
    protected boolean mShowing;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = getLayoutInflater().inflate(getLayoutResource(), container);
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawableResource(getBackgroundResId());
        getDialog().getWindow().requestFeature(DialogFragment.STYLE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(isDialogCancelableOnTouchOutside());
        getDialog().setOnCancelListener(dialog -> {
            if (mDialogListener != null) mDialogListener.onCancelPressed();
        });
        setCancelable(isDialogCancelable());
        mUnbinder = ButterKnife.bind(this, mRootView);
        setupView();

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    protected abstract void setupView();

    @DrawableRes
    protected int getBackgroundResId() {
        return R.drawable.shape_dialog_background;
    }

    @LayoutRes
    protected abstract int getLayoutResource();

    protected boolean isDialogCancelableOnTouchOutside() {
        return true;
    }

    protected boolean isDialogCancelable() {
        return true;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        mShowing = true;
        super.show(manager, tag);
    }

    @Override
    public void dismiss() {
        mShowing = false;
        super.dismiss();
    }

    public boolean isShowing() {
        return mShowing;
    }
}
