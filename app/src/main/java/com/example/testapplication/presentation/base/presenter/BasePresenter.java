package com.example.testapplication.presentation.base.presenter;

import androidx.annotation.NonNull;

import com.example.testapplication.presentation.base.view.BaseView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends BaseView> implements Presenter<V> {

    WeakReference<V> mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private List<Runnable> mActionsToDeliver = new ArrayList<>();

    @Override
    public void attachView(final V view) {
        mView = new WeakReference<V>(view);
        restoreUndeliveredActions();
    }

    @Override
    public V getView() {
        return mView.get();
    }

    @Override
    public boolean isViewAttached() {
        return mView != null && mView.get() != null;
    }

    @Override
    public void addDisposable(@NonNull final Disposable disposable) {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed())
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed())
            mCompositeDisposable.dispose();
        mCompositeDisposable.clear();
    }

    @Override
    public void detachView(final boolean isRemovingViewCompletely) {
        if (isRemovingViewCompletely) {
            onPresenterDestroy();
        }
        if (mView != null)
            mView.clear();
        mView = null;
    }

    protected void onPresenterDestroy() {
        mActionsToDeliver.clear();
        if (!mCompositeDisposable.isDisposed())
            mCompositeDisposable.dispose();
        mCompositeDisposable.clear();
    }

    protected void startSafeAction(Runnable deliverToViewAction) {
        if (isViewAttached()) {
            deliverToViewAction.run();
        } else {
            mActionsToDeliver.add(deliverToViewAction);
        }
    }

    private void restoreUndeliveredActions() {
        for (Runnable action : mActionsToDeliver)
            action.run();
        mActionsToDeliver.clear();
    }
}
