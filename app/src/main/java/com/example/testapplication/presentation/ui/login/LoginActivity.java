package com.example.testapplication.presentation.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.testapplication.R;
import com.example.testapplication.presentation.base.view.activity.BaseActivity;
import com.example.testapplication.presentation.ui.main.MainActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.email_error)
    AppCompatTextView emailError;
    @BindView(R.id.password_error)
    AppCompatTextView passwordError;
    @BindView(R.id.email)
    AppCompatEditText email;
    @BindView(R.id.password)
    AppCompatEditText password;

    public static Intent getLaunchIntent(BaseActivity pBaseActivity) {
        return new Intent(pBaseActivity, LoginActivity.class);
    }

    @Override
    protected void injectToComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.login_button)
    protected void login() {
        getPresenter().login(Objects.requireNonNull(email.getText()),
                Objects.requireNonNull(password.getText()));
    }

    @OnTextChanged(R.id.email)
    protected void editEmail(CharSequence text, int start, int before, int count) {
        showEmailError(false);
    }

    @OnTextChanged(R.id.password)
    protected void editPassword(CharSequence text, int start, int before, int count) {
        showPasswordError(false);
    }

    @Override
    public void openMainActivity() {
        startActivityResetBackStack(MainActivity.getLaunchIntent(this));
    }

    @Override
    public void showEmailError(boolean show) {
        emailError.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showPasswordError(boolean show) {
        passwordError.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
