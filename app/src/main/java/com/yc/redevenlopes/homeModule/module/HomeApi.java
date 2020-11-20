package com.yc.redevenlopes.homeModule.module;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.widget.PrizeTextView;

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


    @FormUrlEncoded
    @POST("v1.user/login")
    Flowable<HttpResult<UserInfo>> login(@Field("app_type") int app_type, @Field("wx_openid") String wx_openid, @Field("qq_openid") String qq_openid,
                                         @Field("age") String age, @Field("nickname") String nickname, @Field("sex") int sex, @Field("face") String face);


    @POST("v1.user/reglog")
    Flowable<HttpResult<UserInfo>> reglog();

}
