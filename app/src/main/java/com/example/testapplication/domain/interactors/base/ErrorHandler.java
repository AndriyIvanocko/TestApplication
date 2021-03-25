package com.example.testapplication.domain.interactors.base;

import com.example.testapplication.data.network.config.error.ResponseError;
import com.example.testapplication.data.network.config.error.RetrofitException;

public interface ErrorHandler {
    /**
     * Handles wifi or gsm connection error.
     *
     * @param exception
     * @return true if error was handled or false otherwise
     */
    boolean handleNetworkError(RetrofitException exception);

    /**
     * Handles not authorized error (401)
     *
     * @param error
     * @return true if error was handled or false otherwise
     */
    boolean handleUnauthorizedError(ResponseError error);

    /**
     * Handles not found error (404)
     *
     * @param error
     * @return true if error was handled or false otherwise
     */
    boolean handleNotFoundError(final ResponseError error);

    /**
     * Handles other server errors
     *
     * @param error
     * @return true if error was handled or false otherwise
     */
    boolean handleResponseError(final ResponseError error);

    /**
     * Handles unknown error
     *
     * @param exception
     * @return true if error was handled or false otherwise
     */
    boolean handleUnknownHttpError(final RetrofitException exception);

    /**
     * Handles server error message with response code 200 OK.
     *
     * @param exception
     * @return true if error was handled or false otherwise
     */
    boolean handleServerError(final RetrofitException exception);

    /**
     * Handles app side errors (parser, nullpointer, classcast etc.) It is recommended to show this
     * errors only in debug mode.
     *
     * @param throwable
     * @return true if error was handled or false otherwise
     */
    boolean handleAppError(final Throwable throwable);
}
