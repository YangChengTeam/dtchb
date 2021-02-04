package com.yc.redguess.di.component;


import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.redguess.di.ContextLife;
import com.yc.redguess.di.module.AppModule;
import com.yc.redguess.homeModule.module.HomeApiModule;
import com.yc.redguess.homeModule.personModule.PersonApiModule;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by ccc
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();  // 提供App的Context


    HomeApiModule getHomeApis();


    PersonApiModule getPersonApis();


}
