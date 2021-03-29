package com.example.testapplication.domain.model.project;

import java.util.List;

public class ProjectResponseModel {

    private List<ProjectModel> mProjects;

    public List<ProjectModel> getProjects() {
        return mProjects;
    }

    public void setProjects(List<ProjectModel> mProjects) {
        this.mProjects = mProjects;
    }
}
