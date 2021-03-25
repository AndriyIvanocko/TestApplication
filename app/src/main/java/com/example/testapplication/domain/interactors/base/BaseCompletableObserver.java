package com.example.testapplication.domain.interactors.base;

import androidx.annotation.CallSuper;

import com.example.testapplication.data.network.config.error.ResponseError;
import com.example.testapplication.data.network.config.error.RetrofitException;

import io.reactivex.observers.DisposableCompletableObserver;

public abstract class BaseCompletableObserver extends DisposableCompletableObserver implements ErrorHandler {
    private final ErrorParser mErrorParser = new ErrorParser(this);

    @CallSuper
    @Override
    public void onError(final Throwable e) {
        mErrorParser.onError(e);
    }

    @Override
    public boolean handleNetworkError(RetrofitException exception) {
        return false;
    }

    @Override
    public boolean handleUnauthorizedError(ResponseError error) {
        return false;
    }

    @Override
    public boolean handleNotFoundError(ResponseError error) {
        return false;
    }

    @Override
    public boolean handleResponseError(final ResponseError error) {
        return false;
    }

    @Override
    public boolean handleUnknownHttpError(final RetrofitException exception) {
        return false;
    }

    @Override
    public boolean handleServerError(final RetrofitException exception) {
        return false;
    }

    @Override
    public boolean handleAppError(final Throwable throwable) {
        return false;
    }
}