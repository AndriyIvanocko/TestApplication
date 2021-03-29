package com.example.testapplication.data.network.model.response.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectResponseData {

    @Expose
    @SerializedName("projects")
    private List<ProjectData> mProjects;

    public List<ProjectData> getProjects() {
        return mProjects;
    }

    public void setProjects(List<ProjectData> mProjects) {
        this.mProjects = mProjects;
    }
}
