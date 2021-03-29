package com.example.testapplication.domain.repository;

import android.util.Log;

import com.example.testapplication.data.network.model.request.UpdateProjectRequest;
import com.example.testapplication.data.network.service.ProjectApiService;
import com.example.testapplication.data.network.transformers.ProjectTransformer;
import com.example.testapplication.data.repository.ProjectRepository;
import com.example.testapplication.domain.model.project.ProjectModel;
import com.example.testapplication.domain.model.user.LoginModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class ProjectRepositoryImpl implements ProjectRepository {
    private final ProjectApiService mProjectApiService;
    private final ProjectTransformer mProjectTransformer;

    @Inject
    public ProjectRepositoryImpl(ProjectApiService mProjectApiService,
                                 ProjectTransformer mProjectTransformer) {
        this.mProjectApiService = mProjectApiService;
        this.mProjectTransformer = mProjectTransformer;
    }

    @Override
    public Observable<List<ProjectModel>> getProjectList() {
        return mProjectApiService.getListProject()
                .compose(mProjectTransformer.transformProjectListDataToModel());
    }

    @Override
    public Completable updateProject(UpdateProjectRequest request, int id) {
        Log.d("GGG", "updateProject: " + id);
        return mProjectApiService.updateProject(request, id);
    }
}
