package com.example.testapplication.di.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class RepositoryModule {

    //TODO provides repositories

    /*@Singleton
    @Provides
    UserRepository provideUserRepository(final UserApiService userApiService,
                                         final UserTransformer userTransformer) {
        return new UserRepositoryImpl(userApiService, userTransformer);
    }*/
}
