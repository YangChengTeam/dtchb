package com.yc.redevenlopes.di.component;

import android.app.Activity;


import com.yc.redevenlopes.di.FragmentScope;
import com.yc.redevenlopes.di.module.FragmentMainModule;
import com.yc.redevenlopes.homeModule.fragment.FrequencyFragment;

import dagger.Component;


/**
 * Created by ccc
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentMainModule.class)
public interface FragmentMainComponent {

    Activity getActivity();

    void inject(FrequencyFragment frequencyFragment);

}
