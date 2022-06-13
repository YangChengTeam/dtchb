package com.yc.rrdsprj.beans.module;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.yc.rrdsprj.beans.module.beans.AdCodeBeans;
import com.yc.rrdsprj.beans.module.beans.AnswerFanBeiBeans;
import com.yc.rrdsprj.beans.module.beans.AnswerFgBeans;
import com.yc.rrdsprj.beans.module.beans.AnswerFgQuestionBeans;
import com.yc.rrdsprj.beans.module.beans.CashRecordBeans;
import com.yc.rrdsprj.beans.module.beans.ComplainBeans;
import com.yc.rrdsprj.beans.module.beans.EmptyBeans;
import com.yc.rrdsprj.beans.module.beans.ExpressBeans;
import com.yc.rrdsprj.beans.module.beans.FalseUserBeans;
import com.yc.rrdsprj.beans.module.beans.GameInfoBeans;
import com.yc.rrdsprj.beans.module.beans.GamedolaBeans;
import com.yc.rrdsprj.beans.module.beans.HelpQuestionBeans;
import com.yc.rrdsprj.beans.module.beans.HotIndexBeans;
import com.yc.rrdsprj.beans.module.beans.HotNumsInfoBeans;
import com.yc.rrdsprj.beans.module.beans.HotTaskBeans;
import com.yc.rrdsprj.beans.module.beans.HotWithDrawBeans;
import com.yc.rrdsprj.beans.module.beans.InvationFriendExchangeBeans;
import com.yc.rrdsprj.beans.module.beans.InvationPeopleListBeans;
import com.yc.rrdsprj.beans.module.beans.InvitationInfoBeans;
import com.yc.rrdsprj.beans.module.beans.InvitationShareBeans;
import com.yc.rrdsprj.beans.module.beans.LimitedBeans;
import com.yc.rrdsprj.beans.module.beans.LimitedRedBeans;
import com.yc.rrdsprj.beans.module.beans.LotterBeans;
import com.yc.rrdsprj.beans.module.beans.LotterInfoBeans;
import com.yc.rrdsprj.beans.module.beans.MoneyTaskBeans;
import com.yc.rrdsprj.beans.module.beans.MoneysBeans;
import com.yc.rrdsprj.beans.module.beans.NesRedBeans;
import com.yc.rrdsprj.beans.module.beans.OtherBeans;
import com.yc.rrdsprj.beans.module.beans.PayInfoBeans;
import com.yc.rrdsprj.beans.module.beans.QuesTionsHotBeans;
import com.yc.rrdsprj.beans.module.beans.QuestionRightBeans;
import com.yc.rrdsprj.beans.module.beans.RedTaskBeans;
import com.yc.rrdsprj.beans.module.beans.RedWallInfoBeans;
import com.yc.rrdsprj.beans.module.beans.SavaMonyeHotBeans;
import com.yc.rrdsprj.beans.module.beans.SaveMoneysInfo;
import com.yc.rrdsprj.beans.module.beans.SplashBeanszq;
import com.yc.rrdsprj.beans.module.beans.TaskLineBean;
import com.yc.rrdsprj.beans.module.beans.TelBeans;
import com.yc.rrdsprj.beans.module.beans.UserInfo;
import com.yc.rrdsprj.beans.module.beans.WallMoneyBeansTwo;
import com.yc.rrdsprj.beans.module.beans.WithDrawStatusBeans;
import com.yc.rrdsprj.utils.UpDataVersion;

import java.util.List;

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
                                         @Field("age") String age, @Field("nickname") String nickname, @Field("sex") int sex, @Field("face") String face, @Field("agent_id") String agent_id, @Field("imei")String imei, @Field("oaid")String oaid, @Field("mac")String mac, @Field("imei2")String imei2, @Field("phone_brand")String phone_brand, @Field("unionid")String unionid, @Field("is_ic")String isIc);

    @POST("v1.version/versioninfo")
    @FormUrlEncoded
    Flowable<HttpResult<UpDataVersion>> upVersion(@Field("agent_id")String agent_id);

    @POST("v1.download/info")
    @FormUrlEncoded
    Flowable<HttpResult<HotNumsInfoBeans>> getHotInfo(@Field("user_id")String userId, @Field("agent_id")String agent_id, @Field("stype")String stype);


    @POST("v1.download/index")
    @FormUrlEncoded
    Flowable<HttpResult<HotIndexBeans>> getHotInfoIndex(@Field("user_id")String userId, @Field("agent_id")String agent_id);

    @POST("v1.download/upddownload")
    @FormUrlEncoded
    Flowable<HttpResult<HotTaskBeans>> gethotTask(@Field("user_id")String userId, @Field("agent_id")String agentId, @Field("package")String appPack, @Field("app_name")String appName, @Field("ad_type")String ad_type);

    @POST("v1.download/huoliindex")
    @FormUrlEncoded
    Flowable<HttpResult<HotWithDrawBeans>> getHotIndex(@Field("user_id")String userId);

    @POST("v1.download/complaint")
    @FormUrlEncoded
    Flowable<HttpResult<List<ComplainBeans>>> getComplainList(@Field("user_id")String userId);

    @POST("v1.download/complaintadd")
    @FormUrlEncoded
    Flowable<HttpResult<EmptyBeans>> comPlaint(@Field("user_id")String userId, @Field("content")String trim, @Field("info_id")String infoId);

    @POST("v1.user/outvideo")
    @FormUrlEncoded
    Flowable<HttpResult<List<MoneyTaskBeans>>> getMoneyTask(@Field("user_id")String userId);

    @POST("v1.user/outvideocash")
    @FormUrlEncoded
    Flowable<HttpResult<EmptyBeans>> getMoneyTaskTx(@Field("user_id")String userId, @Field("video_id")String txid, @Field("tx_wxid")String wx_openid, @Field("version_code")String appVersionCode);

    @POST("v1.user/getother")
    @FormUrlEncoded
    Flowable<HttpResult<OtherBeans>> getOtherInfo(@Field("group_id") String group_id, @Field("user_id") String user_id, @Field("imei")String imei);


    @POST("v1.download/huoliout")
    @FormUrlEncoded
    Flowable<HttpResult<MoneysBeans>> hottixian(@Field("user_id")String userId, @Field("tx_wxid")String wx_openid, @Field("amount")String tixianMoneys, @Field("version_code")String appVersionCode);


    @POST("v1.download/questionclick")
    @FormUrlEncoded
    Flowable<HttpResult<QuesTionsHotBeans>> getQuestionHot(@Field("user_id")String userId, @Field("agent_id")String agentId);

    @POST("v1.user/about")
    @FormUrlEncoded
    Flowable<HttpResult<TelBeans>> getTel(@Field("user_id")String userId);


    @POST("v1.invite/exchangeadd")
    @FormUrlEncoded
    Flowable<HttpResult<InvationFriendExchangeBeans>> getExchangeadd(@Field("user_id")String id, @Field("exchange_id")String exchange_id, @Field("tx_wxid")String wx_openid, @Field("version_code")String appVersionCode);

    @POST("v1.usershare/index")
    @FormUrlEncoded
    Flowable<HttpResult<InvitationInfoBeans>> getInvitationInfo(@Field("user_id")String userId, @Field("mobile")String mobile);

    @POST("v1.invite/inviteadd")
    @FormUrlEncoded
    Flowable<HttpResult<EmptyBeans>> getInvitationCode(@Field("user_id")String userId, @Field("invite_code")String code);

    @POST("v1.invite/index")
    @FormUrlEncoded
    Flowable<HttpResult<InvitationShareBeans>> getShareList(@Field("user_id")String userId);

    @POST("v1.user/aboutplay")
    @FormUrlEncoded
    Flowable<HttpResult<List<HelpQuestionBeans>>> getaboutplayList(@Field("user_id")String userid);



    @POST("v1.task/getquestionright")
    @FormUrlEncoded
    Flowable<HttpResult<QuestionRightBeans>> getQuestionright(@Field("user_id")String id, @Field("right_num")String righNums);

    @POST("v1.task/questionlist")
    @FormUrlEncoded
    Flowable<HttpResult<AnswerFgBeans>> getAnswerList(@Field("user_id")String userId,  @Field("page")String page, @Field("pagesize")String pagesize);

    @POST("v1.task/questionadd")
    @FormUrlEncoded
    Flowable<HttpResult<AnswerFgQuestionBeans>> questionAdd(@Field("user_id")String userId,  @Field("iserror")String iserror);

    @POST("v1.task/getDoubleVideo")
    @FormUrlEncoded
    Flowable<HttpResult<AnswerFanBeiBeans>> getDoubleVideo(@Field("user_id")String userId, @Field("info_id")String info_id);

    @POST("v1.ad/info")
    @FormUrlEncoded
    Flowable<HttpResult<AdCodeBeans>> getAdCode(@Field("imei")String imei, @Field("oaid")String oid, @Field("mac")String macAddress, @Field("imei2") String imie2);

    @POST("v1.task/getVideoMoney")
    @FormUrlEncoded
    Flowable<HttpResult<AnswerFanBeiBeans>> getAnswerRed(@Field("user_id")String userId, @Field("info_id")String info_id, @Field("is_double")String isDouble);

    @POST("v1.task/limitvideo")
    @FormUrlEncoded
    Flowable<HttpResult<List<LimitedBeans>>> getLimitedData(@Field("user_id")String userId);

    @POST("v1.task/hbonline")
    @FormUrlEncoded
    Flowable<HttpResult<RedTaskBeans>> getRedTaskData(@Field("user_id")String userId);

    @POST("v1.task/limitvideoadd")
    @FormUrlEncoded
    Flowable<HttpResult<LimitedRedBeans>> getLimiteRed(@Field("user_id")String userId, @Field("video_id")String limitedId);

    @POST("v1.task/gethbonline")
    @FormUrlEncoded
    Flowable<HttpResult<TaskLineBean>> getTaskLine(@Field("user_id")String userId);

    @POST("v1.show/payinfo")
    @FormUrlEncoded
    Flowable<HttpResult<PayInfoBeans>> getPayInfo(@Field("user_id")String userId);

    @POST("v1.task/gethbtask")
    @FormUrlEncoded
    Flowable<HttpResult<TaskLineBean>> getTaskMoney(@Field("user_id")String userId, @Field("hb_task_id")String taskid);


    @POST("v1.show/lotteryinfo")
    @FormUrlEncoded
    Flowable<HttpResult<List<LotterInfoBeans>>> getlotterInfo(@Field("user_id")String userId);

    @POST("v1.show/getlottery")
    @FormUrlEncoded
    Flowable<HttpResult<LotterBeans>> getlotter(@Field("user_id")String userId,@Field("version_code") String appVersionCode);

    @POST("v1.pay/appwxpay")
    @FormUrlEncoded
    Flowable<HttpResult<WithDrawStatusBeans>> weixinCash(@Field("user_id")String userId, @Field("paystype")String wx, @Field("tx_wxid")String wx_openid, @Field("amount")String lotterMoneys,@Field("version_code") String appVersionCode);


    @POST("v1.user/cashrecord")
    @FormUrlEncoded
    Flowable<HttpResult<List<CashRecordBeans>>> getCashrecord(@Field("user_id")String id,  @Field("page")  String page, @Field("pagesize") String pagezise);

    @POST("v1.show/cashgold")
    @FormUrlEncoded
    Flowable<HttpResult<RedWallInfoBeans>> getWallInfo(@Field("user_id")String userId);

    @POST("v1.show/goldout")
    @FormUrlEncoded
    Flowable<HttpResult<EmptyBeans>> wallCash(@Field("user_id")String userId, @Field("tx_wxid")String wx_openid, @Field("amount")String moneys, @Field("version_code")String appVersionCode);

    @POST("v1.ad/adgold")
    @FormUrlEncoded
    Flowable<HttpResult<WallMoneyBeansTwo>> getWallMoneys(@Field("ad_code")String adNetworkRitId);

    @POST("v1.user/falseuser")
    @FormUrlEncoded
    Flowable<HttpResult<List<FalseUserBeans>>> getFalseuser(@Field("pagesize")String pagesize);

    @POST("v1.show/applogtj")
    @FormUrlEncoded
    Flowable<HttpResult<SplashBeanszq>> initLog(@Field("imei") String imei,
                                                @Field("agent_id") String agent_id,
                                                @Field("version_code") String version_code,
                                                @Field("version_name") String version_name,
                                                @Field("device_type") String device_type,
                                                @Field("sys_version") String sys_version);

    @POST("v1.download/cqghuoli")
    @FormUrlEncoded
    Flowable<HttpResult<SaveMoneysInfo>> getSaveMoneyInfos(@Field("user_id")String userId);


    @POST("v1.download/cqgadd")
    @FormUrlEncoded
    Flowable<HttpResult<SavaMonyeHotBeans>> getHomSaveMoney(@Field("user_id")String userId);

    @POST("v1.user/getnewhb")
    @FormUrlEncoded
    Flowable<HttpResult<NesRedBeans>> getNewRed(@Field("user_id")String userId);

    @POST("v1.show/expresslist")
    @FormUrlEncoded
    Flowable<HttpResult<List<ExpressBeans>>> getExpressData(@Field("user_id")String userId);

    @POST("v1.invite/invitelist")
    @FormUrlEncoded
    Flowable<HttpResult<List<InvationPeopleListBeans>>> getPeople(@Field("user_id")String userId, @Field("page")String page, @Field("pagesize")String pagesize);

    @POST("v1.task/gameuser")
    @FormUrlEncoded
    Flowable<HttpResult<GamedolaBeans>> getGameloadInfo(@Field("user_id")String userId);

    @POST("v1.task/gameadd")
    @FormUrlEncoded
    Flowable<HttpResult<GameInfoBeans>> gameloadAdd(@Field("user_id")String userId);

    @POST("v1.task/getgamereward")
    @FormUrlEncoded
    Flowable<HttpResult<GameInfoBeans>> getGamehot(@Field("user_id")String userId);

}
