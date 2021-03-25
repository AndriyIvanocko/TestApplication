package com.example.testapplication.presentation.base.presenter;

public interface BackgroundAction<T> {
    void onComplete(T data);
}
