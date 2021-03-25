package com.example.testapplication.presentation.base.view;

import androidx.annotation.LayoutRes;

import com.example.testapplication.presentation.base.presenter.Presenter;
import com.example.testapplication.presentation.base.view.dialog.DialogListener;

public interface BaseView<P extends Presenter> {
    @LayoutRes
    int getLayoutResource();

    P getPresenter();

    void showMessage(final String title, final String message);

    void showMessage(final String title, final String message, DialogListener listener);

    void showMessage(final String title, final String message, final String positiveButtonText, DialogListener listener);

    void showConfirmation(final String title, final String message, DialogListener listener);

    void showConfirmation(final String title, final String message, final String positiveButtonText, final String negativeButtonText, DialogListener listener);

    void showError(final String title, final String message);

    void showError(final String title, final String message, DialogListener listener);

    void showErrorWithRetry(final String title, final String message, final Runnable retryAction);

    void showProgress(final boolean visible);

    void showProgressDialog(final boolean visible);

    void showSnackbar(final String message);

    //====================== CURRENT APP SPECIFIC ====================

    void showNotAuthorizedError();

    void openLoginActivity();
}
