package com.example.testapplication.presentation.base.view.dialog;

import android.view.View;
import android.widget.Space;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.testapplication.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageDialog extends BaseDialogFragment {
    public static MessageDialog newInstance(String title,
                                            String message,
                                            String positiveButtonText,
                                            String negativeButtonText,
                                            DialogListener dialogListener) {
        MessageDialog fragment = new MessageDialog();
        fragment.mTitle = title;
        fragment.mMessage = message;
        fragment.mPositiveButtonText = positiveButtonText;
        fragment.mNegativeButtonText = negativeButtonText;
        fragment.mDialogListener = dialogListener;
        return fragment;
    }

    @BindView(R.id.space)
    Space mSpace;
    @BindView(R.id.button_positive)
    AppCompatButton mButtonPositive;
    @BindView(R.id.button_negative)
    AppCompatButton mButtonNegative;
    @BindView(R.id.text_title)
    AppCompatTextView mTextTitle;
    @BindView(R.id.text_message)
    AppCompatTextView mTextMessage;

    @Override
    protected void setupView() {
        if (mNegativeButtonText != null) {
            mSpace.setVisibility(View.VISIBLE);
            mButtonNegative.setVisibility(View.VISIBLE);
        }

        mTextTitle.setText(mTitle);
        mTextMessage.setText(mMessage);
        mButtonPositive.setText(mPositiveButtonText);
        mButtonNegative.setText(mNegativeButtonText);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dialog_message;
    }

    @OnClick(R.id.button_positive)
    void onPositiveButtonClick() {
        if (mDialogListener != null) mDialogListener.onPositivePressed();
        dismiss();
    }

    @OnClick(R.id.button_negative)
    void onNegativeButtonClick() {
        if (mDialogListener != null) mDialogListener.onNegativePressed();
        dismiss();
    }
}
