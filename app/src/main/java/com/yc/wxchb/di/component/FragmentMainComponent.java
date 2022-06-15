package com.yc.wxchb.di.component;

import android.app.Activity;

import com.yc.wxchb.beans.fragment.AnswerFragment;
import com.yc.wxchb.beans.fragment.HomeFragment;
import com.yc.wxchb.beans.fragment.MineFragment;
import com.yc.wxchb.beans.fragment.TaskFragment;
import com.yc.wxchb.beans.fragment.WithDrawFragment;
import com.yc.wxchb.di.FragmentScope;
import com.yc.wxchb.di.module.FragmentMainModule;

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
