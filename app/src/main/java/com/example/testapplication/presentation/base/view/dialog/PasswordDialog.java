package com.example.testapplication.presentation.base.view.dialog;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;
import com.example.testapplication.R;

import butterknife.BindView;
import butterknife.OnClick;

public class PasswordDialog extends BaseDialogFragment {

    public static PasswordDialog newInstance(DialogListener dialogListener) {
        PasswordDialog fragment = new PasswordDialog();
        fragment.mDialogListener = dialogListener;
        return fragment;
    }

    @BindView(R.id.button_positive)
    AppCompatButton mButtonPositive;
    @BindView(R.id.button_negative)
    AppCompatButton mButtonNegative;
    @BindView(R.id.layout_password_dialog)
    TextInputLayout mLayoutPassword;
    @BindView(R.id.edittext_password)
    AppCompatEditText mEditTextPassword;

    @Override
    protected void setupView() {
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dialog_password;
    }

    @OnClick(R.id.button_positive)
    void onPositiveButtonClick() {
        if (mDialogListener != null) {
            if (mEditTextPassword==null || mEditTextPassword.getText().toString().trim().isEmpty()) {
                showErrorEmptyPassword();
            } else {
                mDialogListener.onPositivePressed();
                dismiss();
            }
        } else {
            dismiss();
        }
    }

    @OnClick(R.id.button_negative)
    void onNegativeButtonClick() {
        if (mDialogListener != null) mDialogListener.onNegativePressed();
        dismiss();
    }

    public String getPassword() {
        return mEditTextPassword.getText().toString().trim();
    }

    private void showErrorEmptyPassword() {
        //mLayoutPassword.setError();
    }
}
