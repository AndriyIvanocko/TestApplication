package com.example.testapplication.data.network.service;

import com.example.testapplication.data.network.model.request.LoginRequest;
import com.example.testapplication.data.network.model.response.project.ProjectResponseData;
import com.example.testapplication.data.network.model.response.user.LoginData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProjectApiService {
    @GET("projects-manage/index")
    Observable<ProjectResponseData> getListProject();

}
