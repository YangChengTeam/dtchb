package com.yc.jsdps.di.component;


import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.jsdps.di.ContextLife;
import com.yc.jsdps.di.module.AppModule;
import com.yc.jsdps.beans.module.HomeApiModule;
import com.yc.jsdps.beans.personModule.PersonApiModule;

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
