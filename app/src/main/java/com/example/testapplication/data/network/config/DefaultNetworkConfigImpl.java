package com.example.testapplication.data.network.config;

//import com.example.testapplication.BuildConfig;
import com.example.testapplication.data.local.UserStorage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Anatoliy Mizyakin on 27.09.2018.
 */
public class DefaultNetworkConfigImpl implements NetworkConfiguration {
    private final UserStorage mUserStorage;

    @Inject
    public DefaultNetworkConfigImpl(UserStorage userStorage) {
        mUserStorage = userStorage;
    }

    @Override
    public int getConnectionTimeout() {
        return ApiPathConst.CONNECTION_TIMEOUT;
    }

    private String getAuthTokenHeaderKey() {
        return ApiPathConst.HEADER_SESSION_TOKEN;
    }

    private String getAuthToken() {
        //return mUserStorage.getToken() != null ? ApiPathConst.HEADER_TOKEN_PREFIX + mUserStorage.getToken() : null;
        //TODO get token from user data
        return "";
    }

    @Override
    public String getBaseUrl() {
        return ApiPathConst.API_BASE_URL + ApiPathConst.API_PATH;
    }

    @Override
    public List<Interceptor> getInterceptors() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(getAuthRequestInterceptor());
        if (getLoggingInterceptor() != null) {
            interceptors.add(getLoggingInterceptor());
        }
        return interceptors;
    }

    private Interceptor getLoggingInterceptor() {
//        if (!BuildConfig.DEBUG) return null;
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private Interceptor getAuthRequestInterceptor() {
        return chain -> {
            Request request = chain.request();

            final String authHeaderKey = getAuthTokenHeaderKey();
            final String authHeaderToken = getAuthToken();

            if (authHeaderKey != null && authHeaderToken != null) {
                request = request.newBuilder()
                        .addHeader(authHeaderKey, authHeaderToken)
                        .build();
            }
            return chain.proceed(request);
        };
    }
}
