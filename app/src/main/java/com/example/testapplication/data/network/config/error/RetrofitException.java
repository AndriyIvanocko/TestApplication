package com.example.testapplication.data.network.config.error;

import com.example.testapplication.data.network.config.enumclass.Kind;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * {@code RetrofitException} is the class of those
 * exceptions that can be thrown during the normal operation of the requests or responses.
 */

public class RetrofitException extends RuntimeException {

    public static RetrofitException serverError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.SERVER, exception, null);
    }

    /**
     * Returns exception associated with HTTP, something like INTERNAL_SERVER_ERROR or other.
     *
     * @param url      The url which has a mistake
     * @param response The data returned from the API
     * @param retrofit See {@link Retrofit}
     */
    public static RetrofitException httpError(String url, Response response, Retrofit retrofit) {
        String message = response.message();
        return new RetrofitException(message, url, response, Kind.HTTP, null, retrofit);
    }

    /**
     * Returns exception associated with network, something like TimeoutException or other.
     *
     * @param exception The exception that arise between client and server
     */
    public static RetrofitException networkError(IOException exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.NETWORK, exception, null);
    }

    /**
     * Returns exception associated with an application internal error.
     *
     * @param exception Unknown to us exception
     */
    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.UNEXPECTED, exception, null);
    }

    private final String url;
    private final Response response;
    private final Kind kind;
    private final Retrofit retrofit;

    public RetrofitException(String message, String url, Response response, Kind kind, Throwable exception, Retrofit retrofit) {
        super(message, exception);
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
    }

    /**
     * The request URL which produced the error.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Response object containing status code, headers, body, etc.
     */
    public Response getResponse() {
        return response;
    }

    /**
     * The event kind which triggered this error.
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * The Retrofit this request was executed on
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }


    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (response == null || response.errorBody() == null) {
            return null;
        }
        Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(type, new Annotation[0]);
        return converter.convert(response.errorBody());
    }
}
