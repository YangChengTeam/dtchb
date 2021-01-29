package com.yc.redevenlopes.homeModule.module;


import android.app.TaskInfo;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.yc.redevenlopes.homeModule.module.bean.AdCodeBeans;
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
import com.yc.redevenlopes.homeModule.module.bean.RedRainBeans;
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
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;
import com.yc.redevenlopes.homeModule.module.bean.WalletDetailBeans;
import com.yc.redevenlopes.utils.UpDataVersion;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

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
                                         @Field("age") String age, @Field("nickname") String nickname, @Field("sex") int sex, @Field("face") String face,@Field("agent_id") String agent_id,@Field("imei")String imei,@Field("oaid")String oaid,@Field("mac")String mac,@Field("imei2")String imei2,@Field("phone_brand")String phone_brand);


    @POST("v1.show/applogtj")
    @FormUrlEncoded
    Flowable<HttpResult<UserInfo>> reglog(@Field("imei")String imei,@Field("agent_id")String agent_id);


    @POST("v1.show/applogtj")
    @FormUrlEncoded
    Flowable<HttpResult<SplashBeans>> initLog(@Field("imei") String imei,
                                              @Field("agent_id") String agent_id,
                                              @Field("version_code") String version_code,
                                              @Field("version_name") String version_name,
                                              @Field("device_type") String device_type,
                                              @Field("sys_version") String sys_version);

    @POST("v1.show/index")
    @FormUrlEncoded
    Flowable<HttpResult<HomeAllBeans>> getHomeData(@Field("group_id") String groupId,@Field("imei")String imei);


    @POST("v1.user/getother")
    @FormUrlEncoded
    Flowable<HttpResult<OtherBeans>> getOtherInfo(@Field("group_id") String group_id, @Field("user_id") String user_id,@Field("imei")String imei);

    @POST("v1.show/getred")
    @FormUrlEncoded
    Flowable<HttpResult<List<HomeRedMessage>>> getHomeMessageRedDataInfo(@Field("group_id") String group_id, @Field("hongbao_id") String hongbao_id,@Field("imei")String imei);

    @POST("v1.user/gethongbao")
    @FormUrlEncoded
    Flowable<HttpResult<OpenRedEvenlopes>> getRedEvenlopsInfo(@Field("group_id") String group_id, @Field("hongbao_id") String hongbao_id,@Field("imei")String imei);


    @POST("v1.task/prizeinfo")
    @FormUrlEncoded
    Flowable<HttpResult<TurnTablePrizeInfoBeans>> getPrizeInfoData(@Field("group_id")String group_id,@Field("imei")String imei);

    @POST("v1.task/getprize")
    @FormUrlEncoded
    Flowable<HttpResult<TurnGoPrizeBeans>> getGoPrize(@Field("group_id")String group_id,@Field("imei")String imei);

    @POST("v1.user/hbdetail")
    @FormUrlEncoded
    Flowable<HttpResult<RedDetailsBeans>> getRedEvenlopesDetails(@Field("group_id")String group_id,@Field("hongbao_id")String id,@Field("imei")String imei);

    @POST("v1.task/guessinfo")
    @FormUrlEncoded
    Flowable<HttpResult<GuessBeans>> getGuessData(@Field("group_id")String group_id,@Field("imei")String imei);

    @POST("v1.task/guessadd")
    @FormUrlEncoded
    Flowable<HttpResult<PostGuessNoBeans>> submitGuessNo(@Field("group_id")String group_id,@Field("info_id") String info_id, @Field("num")String num,@Field("imei")String imei);

    @POST("v1.task/oldguess")
    @FormUrlEncoded
    Flowable<HttpResult<GuessHistoryBeans>> getGuessHistory(@Field("info_id")String info_id, @Field("page")int page,@Field("pagesize")String pagesize,@Field("imei")String imei);

    @POST("v1.task/treasureindex")
    @FormUrlEncoded
    Flowable<HttpResult<SnatchDetailsBeans>> getSnatchinfoDetails(@Field("group_id")String groupId,@Field("imei")String imei);

    @POST("v1.task/treasureadd")
    @FormUrlEncoded
    Flowable<HttpResult<SnatchPostBeans>> getSnatchPost(@Field("group_id")String group_id, @Field("num")String num, @Field("info_id")String info_id,@Field("imei")String imei);

    @POST("v1.task/usertreasure")
    @FormUrlEncoded
    Flowable<HttpResult<List<FrequencyFgBeans>>> getSnatchNums(@Field("group_id")String group_id, @Field("page")int page, @Field("pagesize")String pagesize,@Field("imei")String imei);

    @POST("v1.task/treasurelist")
    @FormUrlEncoded
    Flowable<HttpResult<List<FrequencyFgBeans>>> getNearSnatchNums(@Field("pagesize")String pagesize,@Field("imei")String imei);

    @POST("v1.task/treasureinfo")
    @FormUrlEncoded
    Flowable<HttpResult<SnatchTreasureDetailssBeans>> getSnatchDetailss(@Field("group_id")String group_id, @Field("info_id")String id,@Field("imei")String imei);

    @POST("v1.show/msglist")
    @FormUrlEncoded
    Flowable<HttpResult<List<HomeMsgBeans>>> getMsgList(@Field("group_id")String group_id, @Field("page")int page, @Field("pagesize")String pagesize,@Field("imei")String imei);

    @POST("v1.show/getmsg")
    @FormUrlEncoded
    Flowable<HttpResult<List<Info0Bean>>> getHomeMsgDataPolling(@Field("group_id")String group_id,@Field("msg_id") String msgId,@Field("imei")String imei);

    @POST("v1.user/getmoney")
    @FormUrlEncoded
    Flowable<HttpResult<HomeGetRedMoneyBeans>> getMoneyRed(@Field("group_id")String group_id, @Field("hongbao_id") String hongbao_id,@Field("imei")String imei);

    @POST("v1.user/updtreasure")
    @FormUrlEncoded
    Flowable<HttpResult<UpQuanNumsBeans>> updtreasure(@Field("group_id")String group_id,@Field("imei")String imei);

    @POST("v1.show/getonline")
    @FormUrlEncoded
    Flowable<HttpResult<HomeOnlineBeans>> getonLineRed(@Field("group_id")String group_id,@Field("on_money") String on_money,@Field("imei")String imei);

    @POST("v1.version/versioninfo")
    @FormUrlEncoded
    Flowable<HttpResult<UpDataVersion>> upVersion(@Field("agent_id")String agent_id);

    @POST("v1.task/questionindex")
    @FormUrlEncoded
    Flowable<HttpResult<List<AnswerBeans>>> AnswerQuestionListBeans(@Field("group_id")String groupId,@Field("imei")String imei);

    @POST("v1.task/questionlist")
    @FormUrlEncoded
    Flowable<HttpResult<List<AnswerQuestionListBeans>>> getDetailsQuestionList(@Field("group_id")String groupId, @Field("info_id")String answerId,@Field("imei")String imei);

    @POST("v1.task/questionadd")
    @FormUrlEncoded
    Flowable<HttpResult<AnsPostRecordBeans>> postAnserRecord(@Field("group_id")String groupId, @Field("info_id")String answerId, @Field("iserror")String iserror,@Field("imei")String imei);

    @POST("v1.user/userphb")
    @FormUrlEncoded
    Flowable<HttpResult<List<LeaderRankInfo>>> getAllLeaderList(@Field("group_id")String groupId,@Field("imei")String imei);

    @POST("v1.user/userwallet")
    @FormUrlEncoded
    Flowable<HttpResult<List<WalletDetailBeans>>> getWalletDetailsData(@Field("group_id")String groupId,@Field("page")String page,@Field("pagesize")String pagesize,@Field("imei")String imei);

    @POST("v1.task/getprizemoney")
    @FormUrlEncoded
    Flowable<HttpResult<TurnGetPrizeBeans>> getTurn(@Field("group_id")String group_id, @Field("prize_id")String prizeId,@Field("imei")String imei);

    @POST("v1.show/signdouble")
    @FormUrlEncoded
    Flowable<HttpResult<SignBeans>> getSign(@Field("user_id")String valueOf,@Field("sign_id") String signId);

    @POST("v1.user/videoinfo")
    @FormUrlEncoded
    Flowable<HttpResult<LookVideoBeans>> getlookVideo(@Field("imei")String imei, @Field("group_id")String group_id);

    @POST("v1.user/getvideo")
    @FormUrlEncoded
    Flowable<HttpResult<LookVideoMoneyBeans>> getlookVideoRedMoney(@Field("imei")String imei,@Field("group_id") String group_id,@Field("is_double") String is_double,@Field("info_id") String info_id, @Field("money")String money);

    @POST("v1.user/luckylist")
    @FormUrlEncoded
    Flowable<HttpResult<SmokeHbBeans>> getLuckyRed(@Field("imei")String imei, @Field("group_id")String group_id);

    @POST("v1.user/getlucky")
    @FormUrlEncoded
    Flowable<HttpResult<SmokeBeans>> getLuckyMoney(@Field("imei")String imei, @Field("group_id")String group_id, @Field("is_double")String is_double,@Field("info_id") String redId);

    @POST("v1.user/randfind")
    @FormUrlEncoded
    Flowable<HttpResult<SeekBeans>> getSeekRed(@Field("imei")String imei, @Field("group_id")String group_id);

    @POST("v1.user/getfind")
    @FormUrlEncoded
    Flowable<HttpResult<SeekRedMoneyBean>> getSeekGetRedMoney(@Field("imei")String imei,@Field("group_id") String group_id,@Field("is_double") String is_double, @Field("info_id")String info_id,@Field("money") String money);

    @POST("v1.user/updfind")
    @FormUrlEncoded
    Flowable<HttpResult<UpFindRedBeans>> getUpFindRed(@Field("imei")String imei, @Field("group_id")String group_id, @Field("type")String type);

    @POST("v1.user/signedindex")
    @FormUrlEncoded
    Flowable<HttpResult<SignInfoBeans>> getSignInfo(@Field("imei")String imei,@Field("group_id") String group_id);

    @POST("v1.user/updsigned")
    @FormUrlEncoded
    Flowable<HttpResult<GoToSignBeans>> sign(@Field("imei")String imei, @Field("group_id")String group_id);

    @POST("v1.user/updlucky")
    @FormUrlEncoded
    Flowable<HttpResult<AutoGetLuckyBeans>> getLuckyAutoRed(@Field("imei")String imei, @Field("group_id")String group_id);

    @POST("v1.show/reguserlog")
    @FormUrlEncoded
    Flowable<HttpResult<EmptyBeans>> getRegUserLog(@Field("user_id")String id, @Field("type")String type);

    @POST("v1.show/getnewhb")
    @FormUrlEncoded
    Flowable<HttpResult<NewsLoginBeans>> getNewsLoginHb(@Field("imei")String imei, @Field("group_id")String group_id);

    @POST("v1.show/getnewtrea")
    @FormUrlEncoded
    Flowable<HttpResult<NewsLoginBeans>> getFirstWithDrawMoney(@Field("imei")String imei, @Field("group_id")String group_id);

    @POST("v1.user/getrain")
    @FormUrlEncoded
    Flowable<HttpResult<RedRainBeans>> getRedRainMoney(@Field("imei")String imei, @Field("group_id")String groupId, @Field("info_id")String info_id);

    @POST("v1.ad/info")
    @FormUrlEncoded
    Flowable<HttpResult<AdCodeBeans>> getAdCode(@Field("imei")String imei, @Field("oaid")String oid, @Field("mac")String macAddress,@Field("imei2") String imie2);
}
