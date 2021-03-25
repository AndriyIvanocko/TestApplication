package com.example.testapplication.presentation.base.presenter;

import com.example.testapplication.di.scopes.ConfigPersistentScope;
import com.example.testapplication.presentation.base.view.EmptyView;

import javax.inject.Inject;

@ConfigPersistentScope
public class EmptyPresenter extends BaseErrorHandlingPresenter<EmptyView> {

    @Inject
    public EmptyPresenter() {
    }
}
