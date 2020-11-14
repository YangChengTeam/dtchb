package com.yc.redevenlopes.homeModule.personModule;


import com.lq.lianjibusiness.base_libary.http.RetrofitHelper;

import javax.inject.Singleton;



@Singleton
public class PersonApiModule {
    private PersonApi apis;

    public PersonApiModule() {
        creatPersonApis();
    }

    private void creatPersonApis() {
        apis = RetrofitHelper.getInstance().createApis(PersonApi.class);
    }



}
