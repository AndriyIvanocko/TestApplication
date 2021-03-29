package com.example.testapplication.presentation.ui.login;

import android.text.Editable;

import com.example.testapplication.common.validation.Validator;
import com.example.testapplication.di.scopes.ConfigPersistentScope;
import com.example.testapplication.domain.interactors.user.LoginUseCase;
import com.example.testapplication.domain.model.user.LoginModel;
import com.example.testapplication.domain.model.user.UserModel;
import com.example.testapplication.presentation.base.presenter.BackgroundAction;
import com.example.testapplication.presentation.base.presenter.BaseErrorHandlingPresenter;

import javax.inject.Inject;

@ConfigPersistentScope
public class LoginPresenter extends BaseErrorHandlingPresenter<LoginView> {
    private final LoginUseCase mLoginUseCase;

    @Inject
    public LoginPresenter(LoginUseCase mLoginUseCase) {
        this.mLoginUseCase = mLoginUseCase;
    }

    public void login(Editable email, Editable password) {
        if (validateFields(email.toString(), password.toString())) {
            mLoginUseCase.setData(email.toString(), password.toString());
            runBackgroundAction(mLoginUseCase, (BackgroundAction<LoginModel>) data -> {
                getView().showProgress(false);
                mUserStorage.setToken(data.getToken());
                mUserStorage.setUser(data.getAppInit().getUser());
                getView().openMainActivity();
            });
        }
    }

    private boolean validateFields(String email, String password) {
        boolean valid = true;
        if (!Validator.isCorrectEmail(email)) {
            valid = false;
            getView().showEmailError(true);
        } else {
            getView().showEmailError(false);
        }

        if (!Validator.isCorrectPassword(password)) {
            valid = false;
            getView().showPasswordError(true);
        } else {
            getView().showPasswordError(false);
        }

        return valid;
    }

}
