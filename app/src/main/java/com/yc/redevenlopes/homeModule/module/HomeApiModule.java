package com.yc.redevenlopes.homeModule.module;


import android.view.animation.Transformation;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.RetrofitHelper;
import com.yc.redevenlopes.homeModule.module.bean.AnsPostRecordBeans;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;
import com.yc.redevenlopes.homeModule.module.bean.AnswerQuestionListBeans;
import com.yc.redevenlopes.homeModule.module.bean.FrequencyFgBeans;
import com.yc.redevenlopes.homeModule.module.bean.GuessBeans;
import com.yc.redevenlopes.homeModule.module.bean.GuessHistoryBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeGetRedMoneyBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeMsgBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeOnlineBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeRedMessage;
import com.yc.redevenlopes.homeModule.module.bean.Info0Bean;
import com.yc.redevenlopes.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;
import com.yc.redevenlopes.homeModule.module.bean.PostGuessNoBeans;
import com.yc.redevenlopes.homeModule.module.bean.RedDetailsBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchDetailsBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchPostBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchTreasureDetailssBeans;
import com.yc.redevenlopes.homeModule.module.bean.SplashBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnTablePrizeInfoBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.utils.UpDataVersion;

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


    public Flowable<HttpResult<UserInfo>> login(int app_type, String wx_openid, String qq_openid, String age, String nickname, int sex, String face,String agent_id) {
        return apis.login(app_type, wx_openid, qq_openid, age, nickname, sex, face,agent_id);
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

    public Flowable<HttpResult<PostGuessNoBeans>> submitGuessNo(String group_id, String info_id, String num) {
        return apis.submitGuessNo(group_id,info_id,num);
    }

    public  Flowable<HttpResult<GuessHistoryBeans>> getGuessHistory(String info_id, int page, String pagesize) {
        return apis.getGuessHistory(info_id,page,pagesize);
    }

    public Flowable<HttpResult<SnatchDetailsBeans>> getSnatchinfoDetails(String groupId) {
        return apis.getSnatchinfoDetails(groupId);
    }

    public Flowable<HttpResult<SnatchPostBeans>> getSnatchPost(String group_id, String num, String info_id) {
        return apis.getSnatchPost(group_id,num,info_id);
    }

    public Flowable<HttpResult<List<FrequencyFgBeans>>> getSnatchNums(String group_id, int page, String pagesize) {
        return apis.getSnatchNums(group_id,page,pagesize);
    }

    public Flowable<HttpResult<List<FrequencyFgBeans>>> getNearSnatchNums(String pagesize) {
        return apis.getNearSnatchNums(pagesize);
    }

    public Flowable<HttpResult<SnatchTreasureDetailssBeans>> getSnatchDetailss(String group_id, String id) {
        return apis.getSnatchDetailss(group_id,id);
    }

    public Flowable<HttpResult<List<HomeMsgBeans>>> getMsgList(String group_id, int page, String pagesize) {
        return apis.getMsgList(group_id,page,pagesize);
    }

    public Flowable<HttpResult<List<Info0Bean>>> getHomeMsgDataPolling(String group_id, String msgId) {
        return apis.getHomeMsgDataPolling(group_id,msgId);

    }

    public Flowable<HttpResult<HomeGetRedMoneyBeans>> getMoneyRed(String group_id, String hongbao_id) {
        return apis.getMoneyRed(group_id,hongbao_id);
    }

    public Flowable<HttpResult<UpQuanNumsBeans>> updtreasure(String group_id) {
        return apis.updtreasure(group_id);

    }

    public Flowable<HttpResult<HomeOnlineBeans>> getonLineRed(String group_id, String on_money) {
        return apis.getonLineRed(group_id,on_money);
    }

    public Flowable<HttpResult<UpDataVersion>> upVersion(String agentId) {
        return apis.upVersion(agentId);
    }

    public Flowable<HttpResult<List<AnswerBeans>>> getAnswerQuestionList(String groupId) {
        return apis.AnswerQuestionListBeans(groupId);
    }

    public Flowable<HttpResult<List<AnswerQuestionListBeans>>> getDetailsQuestionList(String groupId,String answerId) {
        return apis.getDetailsQuestionList(groupId,answerId);
    }

    public Flowable<HttpResult<AnsPostRecordBeans>> postAnserRecord(String groupId, String answerId,String iserror) {
        return apis.postAnserRecord(groupId,answerId,iserror);
    }
}

