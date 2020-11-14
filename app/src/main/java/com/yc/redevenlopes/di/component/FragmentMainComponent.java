package com.yc.redevenlopes.di.component;

import android.app.Activity;


import com.yc.redevenlopes.di.FragmentScope;
import com.yc.redevenlopes.di.module.FragmentMainModule;

import dagger.Component;


/**
 * Created by ccc
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentMainModule.class)
public interface FragmentMainComponent {

    Activity getActivity();




}
