package com.example.testapplication.domain.interactors.base;

import com.example.testapplication.data.network.config.error.ResponseError;
import com.example.testapplication.data.network.config.error.RetrofitException;

public class ErrorHandlerImpl implements ErrorHandler {

    @Override
    public boolean handleNetworkError(RetrofitException exception) {
        return false;
    }

    @Override
    public boolean handleUnauthorizedError(ResponseError error) {
        return false;
    }

    @Override
    public boolean handleNotFoundError(ResponseError error) {
        return false;
    }

    @Override
    public boolean handleResponseError(final ResponseError error) {
        return false;
    }

    @Override
    public boolean handleUnknownHttpError(final RetrofitException exception) {
        return false;
    }

    @Override
    public boolean handleServerError(final RetrofitException exception) {
        return false;
    }

    @Override
    public boolean handleAppError(final Throwable throwable) {
        return false;
    }
}
