package com.example.testapplication.presentation.ui.login;

import com.example.testapplication.presentation.base.view.BaseView;

public interface LoginView extends BaseView<LoginPresenter> {
    void openMainActivity();

    void showEmailError(boolean show);

    void showPasswordError(boolean show);
}
