package com.example.testapplication.di.modules;

import com.example.testapplication.data.network.service.ProjectApiService;
import com.example.testapplication.data.network.service.UserApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.testapplication.data.network.config.DefaultNetworkConfigImpl;
import com.example.testapplication.data.network.config.factory.RxErrorHandlingCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    OkHttpClient provideDefaultOkHttpClient(final DefaultNetworkConfigImpl defaultNetworkConfig) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .readTimeout(defaultNetworkConfig.getConnectionTimeout(), TimeUnit.SECONDS)
                .writeTimeout(defaultNetworkConfig.getConnectionTimeout(), TimeUnit.SECONDS);
        for (Interceptor interceptor : defaultNetworkConfig.getInterceptors()) {
            okHttpBuilder.addInterceptor(interceptor);
        }
        return okHttpBuilder.build();
    }


    @Singleton
    @Provides
    Retrofit provideDefaultRetrofit(final OkHttpClient httpClient, final DefaultNetworkConfigImpl defaultNetworkConfig) {
        Gson gson = new GsonBuilder()
                .create();
        return new Retrofit.Builder()
                .addCallAdapterFactory(new RxErrorHandlingCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(defaultNetworkConfig.getBaseUrl())
                .client(httpClient)
                .build();
    }

    @Singleton
    @Provides
    UserApiService provideUserApiService(final Retrofit retrofit) {
        return retrofit.create(UserApiService.class);
    }

    @Singleton
    @Provides
    ProjectApiService provideProjectApiService(final Retrofit retrofit){
        return retrofit.create(ProjectApiService.class);
    }
}
