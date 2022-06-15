package com.yc.wxchb.di.module;


import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.wxchb.di.ContextLife;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.personModule.PersonApiModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ccc
 */


@Module
public class AppModule {

    private App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    App provideApplicationContext() {
        return application;
    }



    @Provides
    @Singleton
    HomeApiModule provideHomeApis() {
        return new HomeApiModule();
    }


    @Provides
    @Singleton
    PersonApiModule providePersonApis() {
        return new PersonApiModule();
    }
}
