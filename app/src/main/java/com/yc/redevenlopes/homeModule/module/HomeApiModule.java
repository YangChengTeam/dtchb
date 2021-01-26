package com.yc.redevenlopes.homeModule.module;



import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.RetrofitHelper;
import com.yc.redevenlopes.homeModule.module.bean.AnsPostRecordBeans;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;
import com.yc.redevenlopes.homeModule.module.bean.AnswerQuestionListBeans;
import com.yc.redevenlopes.homeModule.module.bean.AutoGetLuckyBeans;
import com.yc.redevenlopes.homeModule.module.bean.EmptyBeans;
import com.yc.redevenlopes.homeModule.module.bean.FrequencyFgBeans;
import com.yc.redevenlopes.homeModule.module.bean.GoToSignBeans;
import com.yc.redevenlopes.homeModule.module.bean.GuessBeans;
import com.yc.redevenlopes.homeModule.module.bean.GuessHistoryBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeGetRedMoneyBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeMsgBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeOnlineBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeRedMessage;
import com.yc.redevenlopes.homeModule.module.bean.Info0Bean;
import com.yc.redevenlopes.homeModule.module.bean.LeaderRankInfo;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoBeans;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoMoneyBeans;
import com.yc.redevenlopes.homeModule.module.bean.NewsLoginBeans;
import com.yc.redevenlopes.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;
import com.yc.redevenlopes.homeModule.module.bean.PostGuessNoBeans;
import com.yc.redevenlopes.homeModule.module.bean.RedDetailsBeans;
import com.yc.redevenlopes.homeModule.module.bean.SeekBeans;
import com.yc.redevenlopes.homeModule.module.bean.SeekRedMoneyBean;
import com.yc.redevenlopes.homeModule.module.bean.SignBeans;
import com.yc.redevenlopes.homeModule.module.bean.SignInfoBeans;
import com.yc.redevenlopes.homeModule.module.bean.SmokeBeans;
import com.yc.redevenlopes.homeModule.module.bean.SmokeHbBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchDetailsBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchPostBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchTreasureDetailssBeans;
import com.yc.redevenlopes.homeModule.module.bean.SplashBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnGetPrizeBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnTablePrizeInfoBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpFindRedBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.module.bean.WalletDetailBeans;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.UpDataVersion;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
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


    public Flowable<HttpResult<UserInfo>> login(int app_type, String wx_openid, String qq_openid, String age, String nickname, int sex, String face,String agent_id,String imei,String oid,String macAddress,String iemi2,String phone_brand) {
        return apis.login(app_type, wx_openid, qq_openid, age, nickname, sex, face,agent_id,imei,oid,macAddress,iemi2,phone_brand);
    }

    public Flowable<HttpResult<SplashBeans>> initLog(String imei, String agentId, String
            versionCode, String versionName, String sysVersion) {
        return apis.initLog(imei, agentId, versionCode, versionName, "2", sysVersion);

    }

    public Flowable<HttpResult<UserInfo>> reglog(String imei,String agent_id) {
        return apis.reglog(imei,agent_id);
    }

    public Flowable<HttpResult<HomeAllBeans>> getHomeData(String groupId,String imei) {
        return apis.getHomeData(groupId,imei);
    }

    public Flowable<HttpResult<OtherBeans>>   getOtherInfo(String group_id, String user_id,String imei) {
        return apis.getOtherInfo(group_id,user_id,imei);
    }

    public Flowable<HttpResult<List<HomeRedMessage>>> getHomeMessageRedDataInfo(String group_id, String hongbao_id,String imei) {
        return apis.getHomeMessageRedDataInfo(group_id,hongbao_id,imei);
    }

    public Flowable<HttpResult<OpenRedEvenlopes>>  getRedEvenlopsInfo(String group_id, String hongbao_id) {
        return apis.getRedEvenlopsInfo(group_id,hongbao_id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<TurnTablePrizeInfoBeans>>  getPrizeInfoData(String group_id) {
        return apis.getPrizeInfoData(group_id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<TurnGoPrizeBeans>> getGoPrize(String group_id) {
        return apis.getGoPrize(group_id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<RedDetailsBeans>> getRedEvenlopesDetails(String group_id,String id) {
        return apis.getRedEvenlopesDetails(group_id,id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public  Flowable<HttpResult<GuessBeans>> getGuessData(String group_id) {
        return apis.getGuessData(group_id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<PostGuessNoBeans>> submitGuessNo(String group_id, String info_id, String num) {
        return apis.submitGuessNo(group_id,info_id,num, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public  Flowable<HttpResult<GuessHistoryBeans>> getGuessHistory(String info_id, int page, String pagesize) {
        return apis.getGuessHistory(info_id,page,pagesize, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<SnatchDetailsBeans>> getSnatchinfoDetails(String groupId) {
        return apis.getSnatchinfoDetails(groupId, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<SnatchPostBeans>> getSnatchPost(String group_id, String num, String info_id) {
        return apis.getSnatchPost(group_id,num,info_id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<List<FrequencyFgBeans>>> getSnatchNums(String group_id, int page, String pagesize) {
        return apis.getSnatchNums(group_id,page,pagesize, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<List<FrequencyFgBeans>>> getNearSnatchNums(String pagesize) {
        return apis.getNearSnatchNums(pagesize, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<SnatchTreasureDetailssBeans>> getSnatchDetailss(String group_id, String id) {
        return apis.getSnatchDetailss(group_id,id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<List<HomeMsgBeans>>> getMsgList(String group_id, int page, String pagesize) {
        return apis.getMsgList(group_id,page,pagesize, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<List<Info0Bean>>> getHomeMsgDataPolling(String group_id, String msgId) {
        return apis.getHomeMsgDataPolling(group_id,msgId, CacheDataUtils.getInstance().getUserInfo().getImei());

    }

    public Flowable<HttpResult<HomeGetRedMoneyBeans>> getMoneyRed(String group_id, String hongbao_id) {
        return apis.getMoneyRed(group_id,hongbao_id, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<UpQuanNumsBeans>> updtreasure(String group_id) {
        return apis.updtreasure(group_id, CacheDataUtils.getInstance().getUserInfo().getImei());

    }

    public Flowable<HttpResult<HomeOnlineBeans>> getonLineRed(String group_id, String on_money) {
        return apis.getonLineRed(group_id,on_money, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<UpDataVersion>> upVersion(String agentId) {
        return apis.upVersion(agentId);
    }

    public Flowable<HttpResult<List<AnswerBeans>>> getAnswerQuestionList(String groupId) {
        return apis.AnswerQuestionListBeans(groupId, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<List<AnswerQuestionListBeans>>> getDetailsQuestionList(String groupId,String answerId) {
        return apis.getDetailsQuestionList(groupId,answerId, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<AnsPostRecordBeans>> postAnserRecord(String groupId, String answerId,String iserror) {
        return apis.postAnserRecord(groupId,answerId,iserror, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public  Flowable<HttpResult<List<LeaderRankInfo>>> getAllLeaderList(String groupId,String imei) {
        return apis.getAllLeaderList(groupId,CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<List<WalletDetailBeans>>> getWalletDetailsData(String groupId,String page,String pagesize) {
        return apis.getWalletDetailsData(groupId,page,pagesize, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<TurnGetPrizeBeans>> getTurn(String group_id, String prizeId) {
        return apis.getTurn(group_id,prizeId, CacheDataUtils.getInstance().getUserInfo().getImei());
    }

    public Flowable<HttpResult<SignBeans>> getSign(String valueOf, String signId) {
        return apis.getSign(valueOf,signId);
    }

    public Flowable<HttpResult<LookVideoBeans>> getlookVideo(String imei, String group_id) {
        return apis.getlookVideo(imei,group_id);
    }

    public Flowable<HttpResult<LookVideoMoneyBeans>> getlookVideoRedMoney(String imei, String group_id, String is_double, String info_id, String money) {
        return apis.getlookVideoRedMoney(imei,group_id,is_double,info_id,money);
    }

    public Flowable<HttpResult<SmokeHbBeans>> getLuckyRed(String imei, String group_id) {
        return apis.getLuckyRed(imei,group_id);
    }

    public Flowable<HttpResult<SmokeBeans>> getLuckyMoney(String imei, String group_id, String is_double, String redId) {
        return apis.getLuckyMoney(imei,group_id,is_double,redId);
    }

    public Flowable<HttpResult<SeekBeans>> getSeekRed(String imei, String group_id) {
        return apis.getSeekRed(imei,group_id);
    }

    public Flowable<HttpResult<SeekRedMoneyBean>> getSeekGetRedMoney(String imei, String group_id, String is_double, String info_id, String money) {
        return apis.getSeekGetRedMoney(imei,group_id,is_double,info_id,money);
    }

    public Flowable<HttpResult<UpFindRedBeans>> getUpFindRed(String imei, String group_id, String type) {
        return apis.getUpFindRed(imei,group_id,type);
    }

    public Flowable<HttpResult<SignInfoBeans>> getSignInfo(String imei, String group_id) {
        return apis.getSignInfo(imei,group_id);
    }

    public Flowable<HttpResult<GoToSignBeans>> sign(String imei, String group_id) {
        return apis.sign(imei,group_id);
    }

    public Flowable<HttpResult<AutoGetLuckyBeans>> getLuckyAutoRed(String imei, String group_id) {
        return apis.getLuckyAutoRed(imei,group_id);
    }

    public Flowable<HttpResult<EmptyBeans>> getRegUserLog(String id, String type) {
        return apis.getRegUserLog(id,type);
    }

    public Flowable<HttpResult<NewsLoginBeans>>  getNewsLoginHb(String imei, String group_id) {
        return apis.getNewsLoginHb(imei,group_id);
    }

    public Flowable<HttpResult<NewsLoginBeans>> getFirstWithDrawMoney(String imei, String group_id) {
        return apis.getFirstWithDrawMoney(imei,group_id);
    }
}

