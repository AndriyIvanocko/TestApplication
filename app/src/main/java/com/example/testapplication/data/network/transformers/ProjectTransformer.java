package com.example.testapplication.data.network.transformers;

import com.example.testapplication.data.network.model.response.project.ProjectData;
import com.example.testapplication.data.network.model.response.project.ProjectResponseData;
import com.example.testapplication.domain.model.project.ProjectModel;
import com.example.testapplication.domain.model.project.ProjectResponseModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class ProjectTransformer {

    private final UserTransformer userTransformer;

    @Inject
    public ProjectTransformer(UserTransformer userTransformer) {
        this.userTransformer = userTransformer;
    }

    private ProjectModel provideProjectModel(ProjectData data) {
        ProjectModel model = new ProjectModel();
        if (data != null) {
            model.setUsers(userTransformer.provideUserModelList(data.getUsers()));
            model.setUid(data.getUid());
            model.setPosition(data.getPosition());
            model.setName(data.getName());
            model.setLogoUrl(data.getLogoUrl());
            model.setIsOwnerWatcher(data.getIsOwnerWatcher());
            model.setIsActive(data.getIsActive());
            model.setId(data.getId());
            model.setDtaUserSince(data.getDtaUserSince());
        }

        return model;
    }

    private List<ProjectModel> provideProjectModelList(List<ProjectData> dataList) {
        List<ProjectModel> list = new ArrayList<>();
        if (dataList != null) {
            for (ProjectData data : dataList) {
                list.add(provideProjectModel(data));
            }
        }

        return list;
    }

    ProjectResponseModel provideProjectResponseModel(ProjectResponseData data) {
        ProjectResponseModel model = new ProjectResponseModel();
        if (data != null) {
            model.setProjects(provideProjectModelList(data.getProjects()));
        }

        return model;
    }

    public ObservableTransformer<ProjectResponseData, List<ProjectModel>> transformProjectListDataToModel() {
        return new ObservableTransformer<ProjectResponseData, List<ProjectModel>>() {
            @NonNull
            @Override
            public ObservableSource<List<ProjectModel>> apply(@NonNull Observable<ProjectResponseData> upstream) {
                return upstream.flatMap(new Function<ProjectResponseData, ObservableSource<List<ProjectModel>>>() {
                    @Override
                    public ObservableSource<List<ProjectModel>> apply(@NonNull ProjectResponseData data) throws Exception {
                        return Observable.fromIterable(data.getProjects())
                                .map(new Function<ProjectData, ProjectModel>() {
                                    @Override
                                    public ProjectModel apply(@NonNull ProjectData data) throws Exception {
                                        return provideProjectModel(data);
                                    }
                                }).toList().toObservable();
                    }
                });
            }
        };
    }
}
