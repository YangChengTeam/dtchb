package com.yc.redevenlopes.homeModule.module;


import android.app.TaskInfo;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
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
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;
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
                                         @Field("age") String age, @Field("nickname") String nickname, @Field("sex") int sex, @Field("face") String face,@Field("agent_id") String agent_id);


    @POST("v1.user/reglog")
    Flowable<HttpResult<UserInfo>> reglog();


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
    Flowable<HttpResult<HomeAllBeans>> getHomeData(@Field("group_id") String groupId);


    @POST("v1.user/getother")
    @FormUrlEncoded
    Flowable<HttpResult<OtherBeans>> getOtherInfo(@Field("group_id") String group_id, @Field("user_id") String user_id);

    @POST("v1.show/getred")
    @FormUrlEncoded
    Flowable<HttpResult<List<HomeRedMessage>>> getHomeMessageRedDataInfo(@Field("group_id") String group_id, @Field("hongbao_id") String hongbao_id);

    @POST("v1.user/gethongbao")
    @FormUrlEncoded
    Flowable<HttpResult<OpenRedEvenlopes>> getRedEvenlopsInfo(@Field("group_id") String group_id, @Field("hongbao_id") String hongbao_id);


    @POST("v1.task/prizeinfo")
    @FormUrlEncoded
    Flowable<HttpResult<TurnTablePrizeInfoBeans>> getPrizeInfoData(@Field("group_id")String group_id);

    @POST("v1.task/getprize")
    @FormUrlEncoded
    Flowable<HttpResult<TurnGoPrizeBeans>> getGoPrize(@Field("group_id")String group_id);

    @POST("v1.user/hbdetail")
    @FormUrlEncoded
    Flowable<HttpResult<RedDetailsBeans>> getRedEvenlopesDetails(@Field("group_id")String group_id);

    @POST("v1.task/guessinfo")
    @FormUrlEncoded
    Flowable<HttpResult<GuessBeans>> getGuessData(@Field("group_id")String group_id);

    @POST("v1.task/guessadd")
    @FormUrlEncoded
    Flowable<HttpResult<PostGuessNoBeans>> submitGuessNo(@Field("group_id")String group_id,@Field("info_id") String info_id, @Field("num")String num);

    @POST("v1.task/oldguess")
    @FormUrlEncoded
    Flowable<HttpResult<GuessHistoryBeans>> getGuessHistory(@Field("info_id")String info_id, @Field("page")int page,@Field("pagesize")String pagesize);

    @POST("v1.task/treasureindex")
    @FormUrlEncoded
    Flowable<HttpResult<SnatchDetailsBeans>> getSnatchinfoDetails(@Field("group_id")String groupId);

    @POST("v1.task/treasureadd")
    @FormUrlEncoded
    Flowable<HttpResult<SnatchPostBeans>> getSnatchPost(@Field("group_id")String group_id, @Field("num")String num, @Field("info_id")String info_id);

    @POST("v1.task/usertreasure")
    @FormUrlEncoded
    Flowable<HttpResult<List<FrequencyFgBeans>>> getSnatchNums(@Field("group_id")String group_id, @Field("page")int page, @Field("pagesize")String pagesize);

    @POST("v1.task/treasurelist")
    @FormUrlEncoded
    Flowable<HttpResult<List<FrequencyFgBeans>>> getNearSnatchNums(@Field("pagesize")String pagesize);

    @POST("v1.task/treasureinfo")
    @FormUrlEncoded
    Flowable<HttpResult<SnatchTreasureDetailssBeans>> getSnatchDetailss(@Field("group_id")String group_id, @Field("info_id")String id);

    @POST("v1.show/msglist")
    @FormUrlEncoded
    Flowable<HttpResult<List<HomeMsgBeans>>> getMsgList(@Field("group_id")String group_id, @Field("page")int page, @Field("pagesize")String pagesize);

    @POST("v1.show/getmsg")
    @FormUrlEncoded
    Flowable<HttpResult<List<Info0Bean>>> getHomeMsgDataPolling(@Field("group_id")String group_id,@Field("msg_id") String msgId);

    @POST("v1.user/getmoney")
    @FormUrlEncoded
    Flowable<HttpResult<HomeGetRedMoneyBeans>> getMoneyRed(@Field("group_id")String group_id, @Field("hongbao_id") String hongbao_id);

    @POST("v1.user/updtreasure")
    @FormUrlEncoded
    Flowable<HttpResult<UpQuanNumsBeans>> updtreasure(@Field("group_id")String group_id);

    @POST("v1.user/getonline")
    @FormUrlEncoded
    Flowable<HttpResult<HomeOnlineBeans>> getonLineRed(@Field("group_id")String group_id,@Field("on_money") String on_money);

    @POST("v1.version/versioninfo")
    @FormUrlEncoded
    Flowable<HttpResult<UpDataVersion>> upVersion(@Field("agent_id")String agent_id);

    @POST("v1.task/questionindex")
    @FormUrlEncoded
    Flowable<HttpResult<List<AnswerBeans>>> AnswerQuestionListBeans(@Field("group_id")String groupId);

    @POST("v1.task/questionlist")
    @FormUrlEncoded
    Flowable<HttpResult<List<AnswerQuestionListBeans>>> getDetailsQuestionList(@Field("group_id")String groupId, @Field("info_id")String answerId);

    @POST("v1.task/questionadd")
    @FormUrlEncoded
    Flowable<HttpResult<AnsPostRecordBeans>> postAnserRecord(@Field("group_id")String groupId, @Field("info_id")String answerId, @Field("iserror")String iserror);
}
