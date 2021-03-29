package com.example.testapplication.di.modules;

import com.example.testapplication.data.network.service.ProjectApiService;
import com.example.testapplication.data.network.service.UserApiService;
import com.example.testapplication.data.network.transformers.ProjectTransformer;
import com.example.testapplication.data.network.transformers.UserTransformer;
import com.example.testapplication.data.repository.ProjectRepository;
import com.example.testapplication.data.repository.UserRepository;
import com.example.testapplication.domain.repository.ProjectRepositoryImpl;
import com.example.testapplication.domain.repository.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class RepositoryModule {

    @Singleton
    @Provides
    UserRepository provideUserRepository(final UserApiService userApiService,
                                         final UserTransformer userTransformer) {
        return new UserRepositoryImpl(userApiService, userTransformer);
    }

    @Singleton
    @Provides
    ProjectRepository provideProjectRepository(final ProjectApiService projectApiService,
                                               final ProjectTransformer projectTransformer) {
        return new ProjectRepositoryImpl(projectApiService, projectTransformer);
    }
}
