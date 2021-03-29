package com.example.testapplication.data.network.config.factory;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.testapplication.data.network.config.error.RetrofitException;
import com.example.testapplication.data.network.config.error.ServerError;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {

    private static final String TAG = RxCallAdapterWrapper.class.getSimpleName();
    private final Retrofit retrofit;
    private final CallAdapter<R, ?> wrapped;

    public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<R, ?> wrapped) {
        this.retrofit = retrofit;
        this.wrapped = wrapped;
    }

    @Override
    public Type responseType() {
        return wrapped.responseType();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object adapt(@NonNull final Call<R> call) {
        return convert(wrapped.adapt(call));
    }

    private Object convert(final Object o) {
        if (o instanceof Completable)
            return ((Completable) o).onErrorResumeNext(throwable -> Completable.error(handleErrorToShow(throwable)));
        else if (o instanceof Single)
            return ((Single) o).doOnSuccess(o1 -> {
            }).onErrorResumeNext(new Function<Throwable, SingleSource>() {
                @Override
                public SingleSource apply(Throwable throwable) throws Exception {
                    return Single.error(handleErrorToShow(throwable));
                }
            });
        else if (o instanceof Observable) {
            return ((Observable) o).doOnNext(o1 -> {
            }).onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                @Override
                public ObservableSource apply(final Throwable throwable) throws Exception {
                    return Observable.error(handleErrorToShow(throwable));
                }
            });
        } else
            throw new RuntimeException("Not allowed reactive call = " + o.toString());
    }

    private RetrofitException handleErrorToShow(Throwable throwable) {
        RetrofitException retrofitException = asRetrofitException(throwable);
        return retrofitException;
    }

    public RetrofitException asRetrofitException(Throwable throwable) {
        if (throwable instanceof NullPointerException) {
            Log.e(TAG, throwable.getLocalizedMessage());
        }

        if (throwable instanceof ServerError) {
            return RetrofitException.serverError(throwable);
        }

        // We had non-200 http error
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            Response response = httpException.response();

            return RetrofitException.httpError(response.raw().request().url().toString(), response, retrofit);
        }
        // A network error happened
        if (throwable instanceof TimeoutException || throwable instanceof ConnectException ||
                throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException) {
            return RetrofitException.networkError(new IOException(throwable.getMessage(), throwable));
        }

        // We don't know what happened. We need to simply convert to an unknown error
        return RetrofitException.unexpectedError(throwable);
    }
}
