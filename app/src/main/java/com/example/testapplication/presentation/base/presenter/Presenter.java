package com.example.testapplication.presentation.base.presenter;

import androidx.annotation.NonNull;

import com.example.testapplication.presentation.base.view.BaseView;

import io.reactivex.disposables.Disposable;

public interface Presenter<V extends BaseView> {

    void attachView(final V view);

    V getView();

    boolean isViewAttached();

    void addDisposable(@NonNull final Disposable disposable);

    void unsubscribe();

    void detachView(final boolean isRemovingViewCompletely);
}
