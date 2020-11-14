package com.yc.redevenlopes.di.module;


import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.redevenlopes.di.ContextLife;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.personModule.PersonApiModule;

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
