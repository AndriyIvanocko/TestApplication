package com.example.testapplication.domain.interactors.user;

import com.example.testapplication.data.network.model.request.LoginRequest;
import com.example.testapplication.data.repository.UserRepository;
import com.example.testapplication.domain.interactors.base.AsyncUseCase;
import com.example.testapplication.domain.interactors.base.BaseDisposableObserver;
import com.example.testapplication.domain.model.user.LoginModel;
import com.example.testapplication.domain.model.user.UserModel;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LoginUseCase extends AsyncUseCase<BaseDisposableObserver, Observable> {
    private final UserRepository mUserRepository;
    private LoginRequest mLoginRequest;
    @Inject
    public LoginUseCase(UserRepository userRepository) {
        mUserRepository = userRepository;
    }


    public void setData(String email, String password){
        mLoginRequest = new LoginRequest(email, password);
    }

    @Override
    protected Observable<LoginModel> buildTask() {
        return mUserRepository.login(mLoginRequest);
    }
}
