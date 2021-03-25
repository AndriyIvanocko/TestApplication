package com.example.testapplication.domain.interactors.base;

import com.google.gson.Gson;
import com.example.testapplication.data.network.config.enumclass.Kind;
import com.example.testapplication.data.network.config.error.ResponseError;
import com.example.testapplication.data.network.config.error.RetrofitException;


public class ErrorParser {
    private final ErrorHandler mErrorHandler;

    public ErrorParser(ErrorHandler errorHandler) {
        mErrorHandler = errorHandler;
    }

    public void onError(final Throwable e) {
        if (e instanceof RetrofitException) {
            RetrofitException retrofitException = (RetrofitException) e;
            if (retrofitException.getKind() == Kind.NETWORK) {
                mErrorHandler.handleNetworkError(retrofitException);
            } else if (retrofitException.getKind() == Kind.HTTP) {
                handleHttpError(retrofitException);
            } else if (retrofitException.getKind() == Kind.SERVER) {
                mErrorHandler.handleServerError(retrofitException);
            } else if (retrofitException.getKind() == Kind.UNEXPECTED) {
                mErrorHandler.handleAppError(retrofitException);
            }
        } else {
            mErrorHandler.handleAppError(e);
        }
    }

    private void handleHttpError(final RetrofitException exception) {
        ResponseError parsedError;
        switch (exception.getResponse().code()) {
            case 401:
                parsedError = parseError(exception);
                if (parsedError == null) {
                    mErrorHandler.handleUnauthorizedError(new ResponseError(exception.getMessage()));
                    return;
                }
                mErrorHandler.handleUnauthorizedError(parsedError);
                break;
            case 400:
                parsedError = parseError(exception);
                if (parsedError == null) {
                    mErrorHandler.handleUnknownHttpError(exception);
                    return;
                }
                mErrorHandler.handleResponseError(parsedError);
                break;
            case 404:
                parsedError = parseError(exception);
                if (parsedError == null) {
                    mErrorHandler.handleUnknownHttpError(exception);
                    return;
                }
                mErrorHandler.handleNotFoundError(parsedError);
                break;
            case 403:
                parsedError = parseError(exception);
                if (parsedError == null) {
                    mErrorHandler.handleUnknownHttpError(exception);
                    return;
                }
                mErrorHandler.handleNotFoundError(parsedError);
                break;
            case 422:
                parsedError = parseError(exception);
                if (parsedError == null) {
                    mErrorHandler.handleUnknownHttpError(exception);
                    return;
                }
                mErrorHandler.handleResponseError(parsedError);
                break;
            default:
                mErrorHandler.handleUnknownHttpError(exception);
                break;
        }
    }

    private ResponseError parseError(final RetrofitException exception) {
        try {
            if (exception.getResponse() == null || exception.getResponse().errorBody() == null) {
                return null;
            }
            ResponseError responseError = new Gson().fromJson(exception.getResponse().errorBody().string(), ResponseError.class);
            return responseError;
        } catch (Exception e) {
            return null;
        }
    }

}
