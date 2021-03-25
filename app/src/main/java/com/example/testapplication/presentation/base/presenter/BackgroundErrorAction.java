package com.example.testapplication.presentation.base.presenter;

public interface BackgroundErrorAction<T> extends BackgroundAction<T> {
    void onError(Error error);
}
