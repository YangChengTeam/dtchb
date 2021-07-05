package com.yc.majiaredgrab.di.module;

import android.app.Activity;


import androidx.fragment.app.Fragment;

import com.yc.majiaredgrab.di.FragmentScope;

import dagger.Module;
import dagger.Provides;


/**
 * Created by ccc
 */

@Module
public class FragmentMainModule {

    private Fragment fragment;

    public FragmentMainModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
