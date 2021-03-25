package com.example.testapplication.presentation.base.view.dialog;

import androidx.appcompat.widget.AppCompatTextView;

import com.example.testapplication.R;

import butterknife.BindView;


public class CustomProgressDialog extends BaseDialogFragment {

    public static CustomProgressDialog newInstance(String message) {
        CustomProgressDialog fragment = new CustomProgressDialog();
        fragment.mMessage = message;
        return fragment;
    }

    @BindView(R.id.text_progress)
    AppCompatTextView mTextMessage;

    @Override
    protected void setupView() {
        mTextMessage.setText(mMessage);
    }

    @Override
    protected boolean isDialogCancelableOnTouchOutside() {
        return false;
    }

    @Override
    protected boolean isDialogCancelable() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dialog_progress;
    }
}
