package com.yc.redevenlopes.homeModule.module;



import com.lq.lianjibusiness.base_libary.http.RetrofitHelper;


import javax.inject.Singleton;



/**
 * 作者：ccc
 * 创建日期：2018/5/29
 * 描述：
 */
@Singleton
public class HomeApiModule {
    private HomeApi apis;

    public HomeApiModule() {
        creatHomeApis();
    }

    private void creatHomeApis() {
        apis = RetrofitHelper.getInstance().createApis(HomeApi.class);
    }



}

