package com.example.testapplication.di.components;


import com.example.testapplication.di.modules.ActivityModule;
import com.example.testapplication.di.scopes.ConfigPersistentScope;

import dagger.Subcomponent;

@ConfigPersistentScope
@Subcomponent
public interface ConfigPersistentComponent {
    ActivityComponent plusActivityComponent(final ActivityModule activityModule);

    FragmentComponent plusFragmentComponent();
}
