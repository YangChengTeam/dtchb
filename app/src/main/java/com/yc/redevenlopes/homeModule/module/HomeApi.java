package com.yc.redevenlopes.homeModule.module;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.yc.redevenlopes.homeModule.module.bean.SplashBeans;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者：ccc
 * 创建日期：2018/5/29
 * 描述：
 */
public interface HomeApi {
    @POST("v1.show/applogtj")
    @FormUrlEncoded
    Flowable<HttpResult<SplashBeans>> initLog(@Field("imei") String imei,
                                              @Field("agent_id") String agent_id,
                                              @Field("version_code") String version_code,
                                              @Field("version_name") String version_name,
                                              @Field("device_type") String device_type,
                                              @Field("sys_version") String sys_version);
}
