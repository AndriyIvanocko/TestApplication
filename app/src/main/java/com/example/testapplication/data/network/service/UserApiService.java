package com.example.testapplication.data.network.service;

import android.service.autofill.UserData;

import com.example.testapplication.data.network.model.request.LoginRequest;
import com.example.testapplication.data.network.model.response.user.LoginData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("auth/login")
    Observable<LoginData> login(@Body LoginRequest request);
}
