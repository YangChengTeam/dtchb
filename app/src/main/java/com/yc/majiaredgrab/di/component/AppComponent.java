package com.yc.majiaredgrab.di.component;


import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.majiaredgrab.di.ContextLife;
import com.yc.majiaredgrab.di.module.AppModule;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;
import com.yc.majiaredgrab.homeModule.personModule.PersonApiModule;

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
