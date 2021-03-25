package com.example.testapplication.data.network.config.error;

public class ServerError extends RuntimeException {
    public static final int CODE_SUCCESS = 0;

    private int mErrorCode;

    public ServerError(int errorCode, String message) {
        super(message);
        mErrorCode = errorCode;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
}
