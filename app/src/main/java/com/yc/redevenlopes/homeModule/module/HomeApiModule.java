package com.yc.redevenlopes.homeModule.module;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.RetrofitHelper;
import com.yc.redevenlopes.homeModule.module.bean.SplashBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;

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


    public Flowable<HttpResult<UserInfo>> login(int app_type, String wx_openid, String qq_openid, String age, String nickname, int sex, String face) {
        return apis.login(app_type, wx_openid, qq_openid, age, nickname, sex, face);
    }

    public Flowable<HttpResult<SplashBeans>> initLog(String imei, String agentId, String
            versionCode, String versionName, String sysVersion) {
        return apis.initLog(imei, agentId, versionCode, versionName, "2", sysVersion);

    }

    public Flowable<HttpResult<UserInfo>> reglog() {
        return apis.reglog();
    }
}

