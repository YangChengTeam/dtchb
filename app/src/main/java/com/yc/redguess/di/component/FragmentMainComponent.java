package com.yc.redguess.di.component;

import android.app.Activity;


import com.yc.redguess.di.FragmentScope;
import com.yc.redguess.di.module.FragmentMainModule;
import com.yc.redguess.homeModule.fragment.AnswerFragment;
import com.yc.redguess.homeModule.fragment.FrequencyFragment;

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
