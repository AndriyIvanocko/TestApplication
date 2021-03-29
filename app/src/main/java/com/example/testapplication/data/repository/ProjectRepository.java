package com.example.testapplication.data.repository;

import com.example.testapplication.data.network.model.request.UpdateProjectRequest;
import com.example.testapplication.domain.model.project.ProjectModel;
import com.example.testapplication.domain.model.user.LoginModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface ProjectRepository {

    Observable<List<ProjectModel>> getProjectList();

    Completable updateProject(UpdateProjectRequest request, int id);
}
