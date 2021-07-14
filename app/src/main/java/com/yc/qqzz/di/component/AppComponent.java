package com.yc.qqzz.di.component;


import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.qqzz.di.ContextLife;
import com.yc.qqzz.di.module.AppModule;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.personModule.PersonApiModule;

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
