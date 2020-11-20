package com.yc.redevenlopes.homeModule.module;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.RetrofitHelper;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;


import javax.inject.Singleton;

import io.reactivex.Flowable;
import retrofit2.http.Field;


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


    public Flowable<HttpResult<UserInfo>> login(int app_type, String wx_openid, String qq_openid,
                                                String age, String nickname, int sex, String face) {
        return apis.login(app_type, wx_openid, qq_openid, age, nickname, sex, face);
    }

    public Flowable<HttpResult<UserInfo>> reglog() {
        return apis.reglog();
    }
}

