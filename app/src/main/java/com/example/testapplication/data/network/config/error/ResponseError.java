package com.example.testapplication.data.network.config.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseError {
    //TODO correct after adding api
    @SerializedName("messages")
    @Expose
    private List<String> mMesssagesList;

    @SerializedName("error")
    @Expose
    private String mError;

    @SerializedName("message")
    @Expose
    private String mMessage;

    public ResponseError() {
    }

    public ResponseError(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getFormattedMessage() {
        StringBuilder result = new StringBuilder();
        if (mMesssagesList != null) {
            for (String message : mMesssagesList) {
                result.append(message).append("\n");
            }
        } else if (mMessage != null) {
            result = new StringBuilder(mMessage);
        } else if (mError != null) {
            result = new StringBuilder(mError);
        }
        return result.toString();
    }
}
