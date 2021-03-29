package com.example.testapplication.di.components;

import android.app.Application;
import android.content.Context;


import com.example.testapplication.presentation.App;
import com.example.testapplication.data.local.UserStorage;
import com.example.testapplication.di.modules.ApplicationModule;
import com.example.testapplication.di.modules.NetworkModule;
import com.example.testapplication.di.modules.RepositoryModule;
import com.example.testapplication.di.modules.StorageModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, RepositoryModule.class,
        StorageModule.class})
public interface ApplicationComponent {

    void inject(final App app);

    ConfigPersistentComponent plusConfigPersistentComponent();

    Application plusApplication();

    Context plusContext();

    UserStorage plusAuthCache();
}
