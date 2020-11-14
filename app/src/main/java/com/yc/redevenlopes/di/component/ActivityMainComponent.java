package com.yc.redevenlopes.di.component;

import android.app.Activity;


import com.yc.redevenlopes.di.ActivityScope;
import com.yc.redevenlopes.di.module.ActivityMainModule;
import com.yc.redevenlopes.homeModule.activity.MainActivity;

import dagger.Component;


/**
 * Created by ccc
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityMainModule.class)
public interface ActivityMainComponent {

    Activity getActivity();

    void inject(MainActivity mainActivity);



}
