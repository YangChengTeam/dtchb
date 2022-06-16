package com.yc.jsdsp.di.component;

import android.app.Activity;

import com.yc.jsdsp.beans.fragment.AnswerFragment;
import com.yc.jsdsp.beans.fragment.HomeFragment;
import com.yc.jsdsp.beans.fragment.MineFragment;
import com.yc.jsdsp.beans.fragment.TaskFragment;
import com.yc.jsdsp.beans.fragment.WithDrawFragment;
import com.yc.jsdsp.di.FragmentScope;
import com.yc.jsdsp.di.module.FragmentMainModule;

import dagger.Component;


/**
 * Created by ccc
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentMainModule.class)
public interface FragmentMainComponent {

    Activity getActivity();

    void inject(HomeFragment homeFragment);
    void inject(TaskFragment taskFragment);
    void inject(MineFragment mineFragment);
    void inject(WithDrawFragment withDrawFragment);
    void inject(AnswerFragment answerFragment);
}
