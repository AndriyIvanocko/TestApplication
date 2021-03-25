package com.example.testapplication.presentation.base.list;

import androidx.annotation.CallSuper;

import com.example.testapplication.data.network.config.error.ResponseError;
import com.example.testapplication.di.scopes.ConfigPersistentScope;
import com.example.testapplication.domain.interactors.base.AsyncUseCase;
import com.example.testapplication.domain.interactors.base.BaseDisposableObserver;
import com.example.testapplication.domain.interactors.base.ErrorHandlerImpl;
import com.example.testapplication.presentation.base.presenter.BackgroundAction;
import com.example.testapplication.presentation.base.presenter.BaseErrorHandlingPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

@ConfigPersistentScope
public abstract class BaseListPresenter<M, V extends BaseListView> extends BaseErrorHandlingPresenter<V> implements BaseListPresenterInterface<M> {
    protected final List<M> mData = new ArrayList<>();
    protected AsyncUseCase<BaseDisposableObserver, Observable> mUseCase;
    private boolean mUsePagination = false;

    void setUsePagination(boolean usePagination) {
        mUsePagination = usePagination;
    }

    public List<M> getData() {
        return mData;
    }

    private boolean mNeedToClearData = false;

    @CallSuper
    public void startLoading() {
        mNeedToClearData = true;
        getPaginationManager().reset();
        performDataRequestCallback();
    }

    @Override
    public void performDataRequestCallback() {
        prepareUseCaseData();
        load();
    }

    protected int getNextPage() {
        return getPaginationManager().getNextPage();
    }

    private void load() {
        mUseCase = getDataLoadingUseCase();
        if (mUseCase == null) return;
        if (mUsePagination) {
            if (!getPaginationManager().canLoadMore()) {
//                if (mData.size() == 0) return;
//                startSafeAction(() -> getView().notifyItemChanged(mData.size() - 1));
                return;
            }
            boolean showProgress = false;
            if (getNextPage() == PaginationManager.PAGINATION_START_POSITION)
                showProgress = true;
            runBackgroundWithPaginationManager(mUseCase, showProgress, (BackgroundAction<List<M>>) data -> {
                getView().showProgress(false);
                if (mNeedToClearData) mData.clear();
                mNeedToClearData = false;
                mData.addAll(data);
                onDataLoadedCallback(mData);
                getView().setData(mData);
            }, new ErrorHandlerImpl() {
                @Override
                public boolean handleNotFoundError(ResponseError error) {
                    getView().showProgress(false);
                    mData.clear();
                    mNeedToClearData = false;
                    onDataLoadedCallback(mData);
                    getView().setData(mData);
                    return true;
                }
            });
        } else {
            runBackgroundAction(mUseCase, true, (BackgroundAction<List<M>>) data -> {
                getView().showProgress(false);
                if (mNeedToClearData) mData.clear();
                mNeedToClearData = false;
                mData.addAll(data);
                onDataLoadedCallback(mData);
                getView().setData(mData);
            }, new ErrorHandlerImpl() {
                @Override
                public boolean handleNotFoundError(ResponseError error) {
                    getView().showProgress(false);
                    mData.clear();
                    mNeedToClearData = false;
                    onDataLoadedCallback(mData);
                    getView().setData(mData);
                    return true;
                }
            });
        }
    }
}
