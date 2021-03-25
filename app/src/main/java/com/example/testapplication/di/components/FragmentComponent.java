package com.example.testapplication.di.components;


import com.example.testapplication.di.scopes.PerFragmentScope;

import dagger.Subcomponent;

@PerFragmentScope
@Subcomponent
public interface FragmentComponent {

    //void inject(DealsFragment merchantsFragment2);

}
