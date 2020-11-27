package com.yc.redevenlopes.homeModule.personModule;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.yc.redevenlopes.homeModule.module.bean.CashBeans;
import com.yc.redevenlopes.homeModule.module.bean.RedReceiveInfo;
import com.yc.redevenlopes.homeModule.module.bean.TithDrawBeans;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfoWrapper;
import com.yc.redevenlopes.homeModule.module.bean.WeixinCashBeans;
import com.yc.redevenlopes.homeModule.module.bean.WithDrawRecordBeans;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PersonApi {

    @FormUrlEncoded
    @POST("v1.task/getupgrade")
    Flowable<HttpResult<List<VipTaskInfo>>> getUpgradeRewardInfos(@Field("group_id") int groupId, @Field("grade_id") int grade_id);

    @FormUrlEncoded
    @POST("v1.task/upgradeinfo")
    Flowable<HttpResult<List<VipTaskInfo>>> upgradeRewardInfos(@Field("group_id") int groupId);

    @FormUrlEncoded
    @POST("v1.task/taskinfo")
    Flowable<HttpResult<VipTaskInfoWrapper>> getUserTaskInfo(@Field("group_id") int group_id);

    @FormUrlEncoded
    @POST("v1.task/levelprize")
    Flowable<HttpResult<RedReceiveInfo>> getReceiveInfo(@Field("group_id") int groupId, @Field("task_id") int task_id);

    @FormUrlEncoded
    @POST("v1.show/payinfo")
    Flowable<HttpResult<TithDrawBeans>> getWithDrawData(@Field("group_id") String groupId);

    @FormUrlEncoded
    @POST("v1.pay/bindtxid")
    Flowable<HttpResult<CashBeans>> weixinCash(@Field("group_id")String groupId, @Field("paystype")String wx,@Field("tx_id")String wx_openid,@Field("nickname")String name,@Field("face")String weixinImg);

    @FormUrlEncoded
    @POST("v1.pay/appwxpay")
    Flowable<HttpResult<WeixinCashBeans>> cashMoney(@Field("group_id")String groupId, @Field("paystype") String wx, @Field("amount")String cashMoney);

    @FormUrlEncoded
    @POST("v1.user/cashrecord")
    Flowable<HttpResult<List<WithDrawRecordBeans>>> getDetailsQuestionList(@Field("group_id")String groupId, @Field("page")String page, @Field("pagesize")String pagesize);
}
