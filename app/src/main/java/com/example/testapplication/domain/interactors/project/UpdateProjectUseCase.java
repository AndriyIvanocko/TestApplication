package com.example.testapplication.domain.interactors.project;

import com.example.testapplication.data.network.model.request.UpdateProjectRequest;
import com.example.testapplication.data.repository.ProjectRepository;
import com.example.testapplication.domain.interactors.base.AsyncUseCase;
import com.example.testapplication.domain.interactors.base.BaseCompletableObserver;

import javax.inject.Inject;

import io.reactivex.Completable;

public class UpdateProjectUseCase extends AsyncUseCase<BaseCompletableObserver, Completable> {
    private final ProjectRepository mProjectRepository;
    private UpdateProjectRequest request;
    private int id;

    @Inject
    public UpdateProjectUseCase(ProjectRepository mProjectRepository) {
        this.mProjectRepository = mProjectRepository;
    }

    public void setData(String name, int id){
        request = new UpdateProjectRequest(name);
        this.id = id;
    }

    @Override
    protected Completable buildTask() {
        return mProjectRepository.updateProject(request, id);
    }
}
