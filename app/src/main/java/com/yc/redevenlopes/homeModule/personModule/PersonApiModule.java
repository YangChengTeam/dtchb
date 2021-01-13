package com.yc.redevenlopes.homeModule.personModule;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.RetrofitHelper;
import com.yc.redevenlopes.homeModule.module.bean.CashBeans;
import com.yc.redevenlopes.homeModule.module.bean.EmptyBeans;
import com.yc.redevenlopes.homeModule.module.bean.RedReceiveInfo;
import com.yc.redevenlopes.homeModule.module.bean.TithDrawBeans;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfoWrapper;
import com.yc.redevenlopes.homeModule.module.bean.WeixinCashBeans;
import com.yc.redevenlopes.homeModule.module.bean.WithDrawRecordBeans;
import com.yc.redevenlopes.utils.CacheDataUtils;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import retrofit2.http.Field;


@Singleton
public class PersonApiModule {
    private PersonApi apis;

    public PersonApiModule() {
        creatPersonApis();
    }

    private void creatPersonApis() {
        apis = RetrofitHelper.getInstance().createApis(PersonApi.class);
    }

    public Flowable<HttpResult<List<VipTaskInfo>>> upgradeRewardInfos(int groupId) {
        return apis.upgradeRewardInfos(groupId, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<List<VipTaskInfo>>> getUpgradeRewardInfos(int groupId, int grade_id) {
        return apis.getUpgradeRewardInfos(groupId, grade_id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<VipTaskInfoWrapper>> getUserTaskInfo(int group_id) {
        return apis.getUserTaskInfo(group_id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<RedReceiveInfo>> getReceiveInfo(int groupId, int task_id) {
        return apis.getReceiveInfo(groupId, task_id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public  Flowable<HttpResult<TithDrawBeans>>  getWithDrawData(String groupId) {
        return apis.getWithDrawData(groupId, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<CashBeans>> weixinCash(String groupId,String wx, String wx_openid,String name,String weixinImg) {
        return apis.weixinCash(groupId,wx,wx_openid,name,weixinImg, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<WeixinCashBeans>> cashMoney(String groupId, String wx, String cashMoney) {
        return apis.cashMoney(groupId,wx,cashMoney, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<List<WithDrawRecordBeans>>>  getDetailsQuestionList(String groupId, String page, String pagesize) {
        return apis.getDetailsQuestionList(groupId,page,pagesize, CacheDataUtils.getInstance().getUserInfo().getImei());
    }


    public Flowable<HttpResult<EmptyBeans>> getRegUserLog(String id, String type) {
        return apis.getRegUserLog(id,type);
    }
}
