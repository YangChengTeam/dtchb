package com.yc.redkingguess.di.component;

import android.app.Activity;


import com.yc.redkingguess.di.FragmentScope;
import com.yc.redkingguess.di.module.FragmentMainModule;
import com.yc.redkingguess.homeModule.fragment.AnswerFragment;
import com.yc.redkingguess.homeModule.fragment.FrequencyFragment;

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
