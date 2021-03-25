package com.example.testapplication.data.network.config;

import java.util.List;

import okhttp3.Interceptor;

/**
 * Created by Anatoliy Mizyakin on 27.09.2018.
 */
public interface NetworkConfiguration {

    int getConnectionTimeout();

    String getBaseUrl();

    List<Interceptor> getInterceptors();
}