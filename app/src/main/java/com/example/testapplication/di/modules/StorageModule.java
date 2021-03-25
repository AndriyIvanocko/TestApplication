package com.example.testapplication.di.modules;

import android.content.Context;

import com.example.testapplication.data.local.UserStorage;
import com.example.testapplication.data.local.UserStorageImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

    @Singleton
    @Provides
    UserStorage provideUserStorage(Context context) {
        return new UserStorageImpl(context);
    }
}
