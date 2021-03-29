package com.example.testapplication.domain.interactors.project;

import com.example.testapplication.data.repository.ProjectRepository;
import com.example.testapplication.domain.interactors.base.AsyncUseCase;
import com.example.testapplication.domain.interactors.base.BaseDisposableObserver;
import com.example.testapplication.domain.model.project.ProjectModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetProjectListUseCase extends AsyncUseCase<BaseDisposableObserver, Observable> {
    private final ProjectRepository mProjectRepository;

    @Inject
    public GetProjectListUseCase(ProjectRepository mProjectRepository) {
        this.mProjectRepository = mProjectRepository;
    }

    @Override
    protected Observable<List<ProjectModel>> buildTask() {
        return mProjectRepository.getProjectList();
    }
}
