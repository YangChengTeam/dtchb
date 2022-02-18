package com.yc.wxchb.di.component;


import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.wxchb.di.ContextLife;
import com.yc.wxchb.di.module.AppModule;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.personModule.PersonApiModule;

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
