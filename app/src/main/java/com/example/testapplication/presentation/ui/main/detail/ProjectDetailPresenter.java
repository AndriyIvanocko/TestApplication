package com.example.testapplication.presentation.ui.main.detail;

import android.text.Editable;

import com.example.testapplication.R;
import com.example.testapplication.di.scopes.ConfigPersistentScope;
import com.example.testapplication.domain.interactors.project.UpdateProjectUseCase;
import com.example.testapplication.presentation.base.presenter.BaseErrorHandlingPresenter;
import com.example.testapplication.presentation.base.presenter.CompletableAction;

import javax.inject.Inject;

@ConfigPersistentScope
public class ProjectDetailPresenter extends BaseErrorHandlingPresenter<ProjectDetailView> {
    private final UpdateProjectUseCase mUpdateProjectUseCase;
    private int projectId = -1;

    @Inject
    public ProjectDetailPresenter(UpdateProjectUseCase mUpdateProjectUseCase) {
        this.mUpdateProjectUseCase = mUpdateProjectUseCase;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void saveNewName(Editable text) {
        if (text != null && !text.toString().isEmpty()) {
            mUpdateProjectUseCase.setData(text.toString(), projectId);
            runCompletableAction(mUpdateProjectUseCase, () -> {
                getView().showProgress(false);
                getView().updateSuccessFull(text.toString());
            });
        } else {
            getView().showError(mContext.getString(R.string.error_title),
                    mContext.getString(R.string.error_project_name));
        }
    }
}
