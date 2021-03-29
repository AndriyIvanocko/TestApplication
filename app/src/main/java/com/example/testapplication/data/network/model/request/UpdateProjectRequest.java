package com.example.testapplication.data.network.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProjectRequest {
    @SerializedName("name")
    @Expose
    private String name;

    public UpdateProjectRequest() {
    }

    public UpdateProjectRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
