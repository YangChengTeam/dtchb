package com.yc.redevenlopes.homeModule.module;


import android.view.animation.Transformation;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.RetrofitHelper;
import com.yc.redevenlopes.homeModule.module.bean.GuessBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeRedMessage;
import com.yc.redevenlopes.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;
import com.yc.redevenlopes.homeModule.module.bean.RedDetailsBeans;
import com.yc.redevenlopes.homeModule.module.bean.SplashBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnTablePrizeInfoBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;


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

    public Flowable<HttpResult<HomeAllBeans>> getHomeData(String groupId) {
        return apis.getHomeData(groupId);
    }

    public Flowable<HttpResult<OtherBeans>>   getOtherInfo(String group_id, String user_id) {
        return apis.getOtherInfo(group_id,user_id);
    }

    public Flowable<HttpResult<List<HomeRedMessage>>> getHomeMessageRedDataInfo(String group_id, String hongbao_id) {
        return apis.getHomeMessageRedDataInfo(group_id,hongbao_id);
    }

    public Flowable<HttpResult<OpenRedEvenlopes>>  getRedEvenlopsInfo(String group_id, String hongbao_id) {
        return apis.getRedEvenlopsInfo(group_id,hongbao_id);
    }

    public Flowable<HttpResult<TurnTablePrizeInfoBeans>>  getPrizeInfoData(String group_id) {
        return apis.getPrizeInfoData(group_id);
    }

    public Flowable<HttpResult<TurnGoPrizeBeans>> getGoPrize(String group_id) {
        return apis.getGoPrize(group_id);
    }

    public Flowable<HttpResult<RedDetailsBeans>> getRedEvenlopesDetails(String group_id) {
        return apis.getRedEvenlopesDetails(group_id);
    }

    public  Flowable<HttpResult<GuessBeans>> getGuessData(String group_id) {
        return apis.getGuessData(group_id);
    }
}

