package com.example.testapplication.presentation.ui.main;

import com.example.testapplication.di.scopes.ConfigPersistentScope;
import com.example.testapplication.domain.interactors.base.AsyncUseCase;
import com.example.testapplication.domain.interactors.base.BaseDisposableObserver;
import com.example.testapplication.domain.interactors.project.GetProjectListUseCase;
import com.example.testapplication.domain.model.project.ProjectModel;
import com.example.testapplication.presentation.base.list.BaseListPresenter;
import com.example.testapplication.presentation.base.presenter.BackgroundAction;
import com.example.testapplication.presentation.base.presenter.BaseErrorHandlingPresenter;
import com.example.testapplication.presentation.base.presenter.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ConfigPersistentScope
public class MainPresenter extends BaseListPresenter<ProjectModel, MainView> {

    private final GetProjectListUseCase getProjectListUseCase;

    @Inject
    public MainPresenter(GetProjectListUseCase getProjectListUseCase) {
        this.getProjectListUseCase = getProjectListUseCase;
    }

    @Override
    public void prepareUseCaseData() {

    }

    @Override
    public void onDataLoadedCallback(List<ProjectModel> data) {

    }

    @Override
    public AsyncUseCase<BaseDisposableObserver, Observable> getDataLoadingUseCase() {
        return getProjectListUseCase;
    }
}
