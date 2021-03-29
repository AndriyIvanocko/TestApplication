package com.example.testapplication.data.repository;

import com.example.testapplication.data.network.model.request.LoginRequest;
import com.example.testapplication.domain.model.user.LoginModel;
import com.example.testapplication.domain.model.user.UserModel;

import io.reactivex.Observable;

public interface UserRepository {

    Observable<LoginModel> login(LoginRequest mLoginRequest);
}
