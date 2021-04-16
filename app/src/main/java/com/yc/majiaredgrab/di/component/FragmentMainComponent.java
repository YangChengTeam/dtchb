package com.yc.majiaredgrab.di.component;

import android.app.Activity;


import com.yc.majiaredgrab.di.FragmentScope;
import com.yc.majiaredgrab.di.module.FragmentMainModule;
import com.yc.majiaredgrab.homeModule.fragment.AnswerFragment;
import com.yc.majiaredgrab.homeModule.fragment.FrequencyFragment;

import dagger.Component;


/**
 * Created by ccc
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentMainModule.class)
public interface FragmentMainComponent {

    Activity getActivity();

    void inject(FrequencyFragment frequencyFragment);

    void inject(AnswerFragment answerFragment);

}
