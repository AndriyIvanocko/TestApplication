package com.example.testapplication.domain.repository;

import com.example.testapplication.data.network.model.request.LoginRequest;
import com.example.testapplication.data.network.service.UserApiService;
import com.example.testapplication.data.network.transformers.UserTransformer;
import com.example.testapplication.data.repository.UserRepository;
import com.example.testapplication.domain.model.user.LoginModel;
import com.example.testapplication.domain.model.user.UserModel;

import javax.inject.Inject;

import io.reactivex.Observable;

public class UserRepositoryImpl implements UserRepository {
    private final UserApiService userApiService;
    private final UserTransformer userTransformer;

    @Inject
    public UserRepositoryImpl(UserApiService userApiService, UserTransformer userTransformer) {
        this.userTransformer = userTransformer;
        this.userApiService = userApiService;
    }

    @Override
    public Observable<LoginModel> login(LoginRequest mLoginRequest) {
        return userApiService.login(mLoginRequest)
                .compose(userTransformer.tranformation());
    }
}
