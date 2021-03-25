package com.example.testapplication.presentation.base.list;

import com.example.testapplication.domain.interactors.base.AsyncUseCase;
import com.example.testapplication.domain.interactors.base.BaseDisposableObserver;

import java.util.List;

import io.reactivex.Observable;

public interface BaseListPresenterInterface<M> {
    void prepareUseCaseData();

    void performDataRequestCallback();

    void onDataLoadedCallback(List<M> data);

    AsyncUseCase<BaseDisposableObserver, Observable> getDataLoadingUseCase();
}
