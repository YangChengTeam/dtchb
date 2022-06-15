package com.yc.wxchb.beans.module;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.RetrofitHelper;
import com.yc.wxchb.beans.module.beans.AdCodeBeans;
import com.yc.wxchb.beans.module.beans.AnswerFanBeiBeans;
import com.yc.wxchb.beans.module.beans.AnswerFgBeans;
import com.yc.wxchb.beans.module.beans.AnswerFgQuestionBeans;
import com.yc.wxchb.beans.module.beans.CashRecordBeans;
import com.yc.wxchb.beans.module.beans.ComplainBeans;
import com.yc.wxchb.beans.module.beans.EmptyBeans;
import com.yc.wxchb.beans.module.beans.ExpressBeans;
import com.yc.wxchb.beans.module.beans.FalseUserBeans;
import com.yc.wxchb.beans.module.beans.GameInfoBeans;
import com.yc.wxchb.beans.module.beans.GamedolaBeans;
import com.yc.wxchb.beans.module.beans.HelpQuestionBeans;
import com.yc.wxchb.beans.module.beans.HotIndexBeans;
import com.yc.wxchb.beans.module.beans.HotNumsInfoBeans;
import com.yc.wxchb.beans.module.beans.HotTaskBeans;
import com.yc.wxchb.beans.module.beans.HotWithDrawBeans;
import com.yc.wxchb.beans.module.beans.InvationFriendExchangeBeans;
import com.yc.wxchb.beans.module.beans.InvationPeopleListBeans;
import com.yc.wxchb.beans.module.beans.InvitationInfoBeans;
import com.yc.wxchb.beans.module.beans.InvitationShareBeans;
import com.yc.wxchb.beans.module.beans.LimitedBeans;
import com.yc.wxchb.beans.module.beans.LimitedRedBeans;
import com.yc.wxchb.beans.module.beans.LotterBeans;
import com.yc.wxchb.beans.module.beans.LotterInfoBeans;
import com.yc.wxchb.beans.module.beans.MoneyTaskBeans;
import com.yc.wxchb.beans.module.beans.MoneysBeans;
import com.yc.wxchb.beans.module.beans.NesRedBeans;
import com.yc.wxchb.beans.module.beans.OtherBeans;
import com.yc.wxchb.beans.module.beans.PayInfoBeans;
import com.yc.wxchb.beans.module.beans.QuesTionsHotBeans;
import com.yc.wxchb.beans.module.beans.QuestionRightBeans;
import com.yc.wxchb.beans.module.beans.RedTaskBeans;
import com.yc.wxchb.beans.module.beans.RedWallInfoBeans;
import com.yc.wxchb.beans.module.beans.SavaMonyeHotBeans;
import com.yc.wxchb.beans.module.beans.SaveMoneysInfo;
import com.yc.wxchb.beans.module.beans.SplashBeanszq;
import com.yc.wxchb.beans.module.beans.TaskLineBean;
import com.yc.wxchb.beans.module.beans.TelBeans;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.beans.module.beans.WallMoneyBeansTwo;
import com.yc.wxchb.beans.module.beans.WithDrawStatusBeans;
import com.yc.wxchb.utils.UpDataVersion;

import java.util.List;

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


    public Flowable<HttpResult<UpDataVersion>> upVersion(String agentId) {
        return apis.upVersion(agentId);
    }
    public Flowable<HttpResult<HotNumsInfoBeans>> getHotInfo(String userId, String agentId,String stype) {
        return apis.getHotInfo(userId,agentId,stype);
    }

    public Flowable<HttpResult<HotIndexBeans>> getHotInfoIndex(String userId, String agentId) {
        return apis.getHotInfoIndex(userId,agentId);
    }
    public Flowable<HttpResult<UserInfo>> login(int app_type, String wx_openid, String qq_openid, String age, String nickname, int sex, String face, String agent_id, String imei, String oid, String macAddress, String iemi2, String phone_brand, String unionid, String isIc) {
        return apis.login(app_type, wx_openid, qq_openid, age, nickname, sex, face,agent_id,imei,oid,macAddress,iemi2,phone_brand,unionid,isIc);
    }

    public Flowable<HttpResult<HotTaskBeans>> gethotTask(String userId, String agentId, String appPack, String appName, String type) {
        return apis.gethotTask(userId,agentId,appPack,appName,type);
    }

    public Flowable<HttpResult<HotWithDrawBeans>> getHotIndex(String userId) {
        return apis.getHotIndex(userId);

    }
    public Flowable<HttpResult<List<ComplainBeans>>>  getComplainList(String userId) {
        return apis.getComplainList(userId);
    }

    public Flowable<HttpResult<EmptyBeans>> comPlaint(String userId, String trim, String infoId) {
        return apis.comPlaint(userId,trim,infoId);
    }
    public  Flowable<HttpResult<List<MoneyTaskBeans>>> getMoneyTask(String userId) {
        return apis.getMoneyTask(userId);
    }

    public Flowable<HttpResult<EmptyBeans>> getMoneyTaskTx(String userId, String txid, String wx_openid, String appVersionCode) {
        return apis.getMoneyTaskTx(userId,txid,wx_openid,appVersionCode);
    }
    public Flowable<HttpResult<OtherBeans>>   getOtherInfo(String group_id, String user_id, String imei) {
        return apis.getOtherInfo(group_id,user_id,imei);
    }

    public Flowable<HttpResult<MoneysBeans>> hottixian(String userId, String wx_openid, String tixianMoneys, String appVersionCode) {
        return apis.hottixian(userId,wx_openid,tixianMoneys,appVersionCode);
    }

    public Flowable<HttpResult<QuesTionsHotBeans>> getQuestionHot(String userId, String agentId) {
        return apis.getQuestionHot(userId,agentId);
    }

    public Flowable<HttpResult<TelBeans>> getTel(String userId) {
        return apis.getTel(userId);
    }

    public Flowable<HttpResult<InvitationShareBeans>> getShareList(String userId) {
        return apis.getShareList(userId);
    }

    public Flowable<HttpResult<InvitationInfoBeans>> getInvitationInfo(String userId, String mobile) {
        return apis.getInvitationInfo(userId,mobile);
    }

    public Flowable<HttpResult<EmptyBeans>> getInvitationCode(String userId, String code) {
        return apis.getInvitationCode(userId,code);
    }

    public Flowable<HttpResult<InvationFriendExchangeBeans>> getExchangeadd(String id, String exchange_id, String wx_openid, String appVersionCode) {
        return apis.getExchangeadd(id,exchange_id,wx_openid,appVersionCode);
    }


    public  Flowable<HttpResult<List<HelpQuestionBeans>>>  getaboutplayList(String userid) {
        return apis.getaboutplayList(userid);
    }


    public Flowable<HttpResult<AnswerFgBeans>> getAnswerList(String userId, String page, String pagesize) {
        return apis.getAnswerList(userId,page,pagesize);
    }

    public Flowable<HttpResult<AnswerFgQuestionBeans>> questionAdd(String userId, String iserror) {
        return apis.questionAdd(userId,iserror);
    }
    public Flowable<HttpResult<QuestionRightBeans>> getQuestionright(String id, String righNums) {
        return apis.getQuestionright(id,righNums);
    }
    public Flowable<HttpResult<AnswerFanBeiBeans>> getDoubleVideo(String userId, String info_id) {
        return apis.getDoubleVideo(userId,info_id);
    }

    public Flowable<HttpResult<AdCodeBeans>> getAdCode(String imei, String oid, String macAddress, String imie2) {
        return apis.getAdCode(imei,oid,macAddress,imie2);
    }
    public Flowable<HttpResult<AnswerFanBeiBeans>> getAnswerRed(String userId, String info_id, String isDouble) {
        return apis.getAnswerRed(userId,info_id,isDouble);
    }

    public  Flowable<HttpResult<List<LimitedBeans>>> getLimitedData(String userId) {
        return apis.getLimitedData(userId);
    }

    public Flowable<HttpResult<RedTaskBeans>> getRedTaskData(String userId) {
        return apis.getRedTaskData(userId);
    }

    public Flowable<HttpResult<LimitedRedBeans>> getLimiteRed(String userId, String limitedId) {
        return apis.getLimiteRed(userId,limitedId);
    }

    public Flowable<HttpResult<TaskLineBean>> getTaskLine(String userId) {
        return apis.getTaskLine(userId);
    }

    public Flowable<HttpResult<PayInfoBeans>> getPayInfo(String userId) {
        return apis.getPayInfo(userId);
    }

    public Flowable<HttpResult<TaskLineBean>> getTaskMoney(String userId, String taskid) {
        return apis.getTaskMoney(userId,taskid);
    }

    public  Flowable<HttpResult<List<LotterInfoBeans>>> getlotterInfo(String userId) {
        return apis.getlotterInfo(userId);
    }

    public Flowable<HttpResult<LotterBeans>> getlotter(String userId, String appVersionCode) {
        return apis.getlotter(userId,appVersionCode);
    }

    public Flowable<HttpResult<WithDrawStatusBeans>> weixinCash(String userId, String wx, String wx_openid, String lotterMoneys, String appVersionCode) {
        return apis.weixinCash(userId,wx,wx_openid,lotterMoneys,appVersionCode);
    }

    public Flowable<HttpResult<List<CashRecordBeans>>> getCashrecord(String userId, String page, String pagezise) {
        return apis.getCashrecord(userId,page,pagezise);
    }

    public Flowable<HttpResult<RedWallInfoBeans>> getWallInfo(String userId) {
        return apis.getWallInfo(userId);
    }

    public Flowable<HttpResult<EmptyBeans>> wallCash(String userId, String wx_openid, String moneys, String appVersionCode) {
        return apis.wallCash(userId,wx_openid,moneys,appVersionCode);
    }

    public Flowable<HttpResult<WallMoneyBeansTwo>> getWallMoneys(String adNetworkRitId) {
        return apis.getWallMoneys(adNetworkRitId);
    }

    public  Flowable<HttpResult<List<FalseUserBeans>>> getFalseuser(String pagesize) {
        return apis.getFalseuser(pagesize);
    }

    public Flowable<HttpResult<SplashBeanszq>> initLog(String imei, String agentId, String
            versionCode, String versionName, String sysVersion) {
        return apis.initLog(imei, agentId, versionCode, versionName, "2", sysVersion);

    }

    public Flowable<HttpResult<SaveMoneysInfo>> getSaveMoneyInfos(String userId) {
        return apis.getSaveMoneyInfos(userId);
    }
    public Flowable<HttpResult<SavaMonyeHotBeans>> getHomSaveMoney(String userId) {
        return apis.getHomSaveMoney(userId);
    }

    public Flowable<HttpResult<NesRedBeans>> getNewRed(String userId) {
        return apis.getNewRed(userId);
    }

    public Flowable<HttpResult<List<ExpressBeans>>> getExpressData(String userId) {
        return apis.getExpressData(userId);
    }

    public Flowable<HttpResult<List<InvationPeopleListBeans>>> getPeople(String userId, String page, String pagesize) {
        return apis.getPeople(userId,page,pagesize);
    }

    public Flowable<HttpResult<GamedolaBeans>> getGameloadInfo(String userId) {
        return apis.getGameloadInfo(userId);
    }

    public Flowable<HttpResult<GameInfoBeans>> gameloadAdd(String userId) {
        return apis.gameloadAdd(userId);
    }

    public Flowable<HttpResult<GameInfoBeans>> getGamehot(String userId) {
        return apis.getGamehot(userId);
    }
}

