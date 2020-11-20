package com.yc.redevenlopes.homeModule.module;



import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.RetrofitHelper;
import com.yc.redevenlopes.homeModule.module.bean.SplashBeans;


import javax.inject.Singleton;

import io.reactivex.Flowable;


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

    public Flowable<HttpResult<SplashBeans>> initLog(String imei, String agentId, String versionCode, String versionName, String sysVersion){
        return apis.initLog(imei, agentId, versionCode, versionName, "2", sysVersion);
    }

}

