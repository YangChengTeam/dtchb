package com.yc.qqzz.homeModule.module;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.yc.qqzz.homeModule.bean.AnswerFgBeans;
import com.yc.qqzz.homeModule.bean.AnswerFgQuestionBeans;
import com.yc.qqzz.homeModule.bean.GetHomeLineRedBeans;
import com.yc.qqzz.homeModule.bean.HomeLineRedBeans;
import com.yc.qqzz.homeModule.bean.HomeNewHbBeans;
import com.yc.qqzz.homeModule.bean.SignBeans;
import com.yc.qqzz.homeModule.module.bean.AdCodeBeans;
import com.yc.qqzz.homeModule.module.bean.EmptyBeans;
import com.yc.qqzz.homeModule.module.bean.HomeAllBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeGetRedMoneyBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeMsgBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeOnlineBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeRedMessagezq;
import com.yc.qqzz.homeModule.module.bean.Info0Beanzq;
import com.yc.qqzz.homeModule.module.bean.InvitationCodeBeans;
import com.yc.qqzz.homeModule.module.bean.InvitationInfoBeans;
import com.yc.qqzz.homeModule.module.bean.InvitationShareBeans;
import com.yc.qqzz.homeModule.module.bean.OpenRedEvenlopeszq;
import com.yc.qqzz.homeModule.module.bean.OtherBeanszq;
import com.yc.qqzz.homeModule.module.bean.RedDetailsBeans;
import com.yc.qqzz.homeModule.module.bean.SplashBeanszq;
import com.yc.qqzz.homeModule.module.bean.TurnGoPrizeBeanszq;
import com.yc.qqzz.homeModule.module.bean.TurnTablePrizeInfoBeanszq;
import com.yc.qqzz.homeModule.module.bean.UpQuanNumsBeanszq;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.utils.UpDataVersion;

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
    Flowable<HttpResult<UserInfozq>> login(@Field("app_type") int app_type, @Field("wx_openid") String wx_openid, @Field("qq_openid") String qq_openid,
                                           @Field("age") String age, @Field("nickname") String nickname, @Field("sex") int sex, @Field("face") String face, @Field("agent_id") String agent_id, @Field("imei")String imei, @Field("oaid")String oaid, @Field("mac")String mac, @Field("imei2")String imei2, @Field("phone_brand")String phone_brand);


    @POST("v1.show/applogtj")
    @FormUrlEncoded
    Flowable<HttpResult<UserInfozq>> reglog(@Field("imei")String imei, @Field("agent_id")String agent_id);


    @POST("v1.show/applogtj")
    @FormUrlEncoded
    Flowable<HttpResult<SplashBeanszq>> initLog(@Field("imei") String imei,
                                                @Field("agent_id") String agent_id,
                                                @Field("version_code") String version_code,
                                                @Field("version_name") String version_name,
                                                @Field("device_type") String device_type,
                                                @Field("sys_version") String sys_version);

    @POST("v1.show/index")
    @FormUrlEncoded
    Flowable<HttpResult<HomeAllBeanszq>> getHomeData(@Field("group_id") String groupId, @Field("imei")String imei);


    @POST("v1.user/getother")
    @FormUrlEncoded
    Flowable<HttpResult<OtherBeanszq>> getOtherInfo(@Field("group_id") String group_id, @Field("user_id") String user_id, @Field("imei")String imei);

    @POST("v1.show/getred")
    @FormUrlEncoded
    Flowable<HttpResult<List<HomeRedMessagezq>>> getHomeMessageRedDataInfo(@Field("group_id") String group_id, @Field("hongbao_id") String hongbao_id, @Field("imei")String imei);

    @POST("v1.user/gethongbao")
    @FormUrlEncoded
    Flowable<HttpResult<OpenRedEvenlopeszq>> getRedEvenlopsInfo(@Field("group_id") String group_id, @Field("hongbao_id") String hongbao_id, @Field("imei")String imei);


    @POST("v1.show/msglist")
    @FormUrlEncoded
    Flowable<HttpResult<List<HomeMsgBeanszq>>> getMsgList(@Field("group_id")String group_id, @Field("page")int page, @Field("pagesize")String pagesize, @Field("imei")String imei);

    @POST("v1.show/getmsg")
    @FormUrlEncoded
    Flowable<HttpResult<List<Info0Beanzq>>> getHomeMsgDataPolling(@Field("group_id")String group_id, @Field("msg_id") String msgId, @Field("imei")String imei);

    @POST("v1.user/getmoney")
    @FormUrlEncoded
    Flowable<HttpResult<HomeGetRedMoneyBeanszq>> getMoneyRed(@Field("group_id")String group_id, @Field("hongbao_id") String hongbao_id, @Field("imei")String imei);

    @POST("v1.user/updtreasure")
    @FormUrlEncoded
    Flowable<HttpResult<UpQuanNumsBeanszq>> updtreasure(@Field("group_id")String group_id, @Field("imei")String imei);

    @POST("v1.show/getonline")
    @FormUrlEncoded
    Flowable<HttpResult<HomeOnlineBeanszq>> getonLineRed(@Field("group_id")String group_id, @Field("on_money") String on_money, @Field("imei")String imei);

    @POST("v1.version/versioninfo")
    @FormUrlEncoded
    Flowable<HttpResult<UpDataVersion>> upVersion(@Field("agent_id")String agent_id);


    @POST("v1.show/signdouble")
    @FormUrlEncoded
    Flowable<HttpResult<SignBeans>> getSign(@Field("user_id")String valueOf, @Field("sign_id") String signId);


    @POST("v1.show/reguserlog")
    @FormUrlEncoded
    Flowable<HttpResult<EmptyBeans>> getRegUserLog(@Field("user_id")String id, @Field("type")String type);


    @POST("v1.task/prizeinfo")
    @FormUrlEncoded
    Flowable<HttpResult<TurnTablePrizeInfoBeanszq>> getPrizeInfoData(@Field("group_id")String group_id, @Field("imei")String imei);

    @POST("v1.task/getprize")
    @FormUrlEncoded
    Flowable<HttpResult<TurnGoPrizeBeanszq>> getGoPrize(@Field("group_id")String group_id, @Field("imei")String imei);


    @POST("v1.ad/info")
    @FormUrlEncoded
    Flowable<HttpResult<AdCodeBeans>> getAdCode(@Field("imei")String imei, @Field("oaid")String oid, @Field("mac")String macAddress, @Field("imei2") String imie2, @Field("is_new_ver") String is_new_ver);

    @POST("v1.user/hbdetail")
    @FormUrlEncoded
    Flowable<HttpResult<RedDetailsBeans>> getRedEvenlopesDetails(@Field("group_id")String group_id, @Field("hongbao_id")String id, @Field("imei")String imei);


    @POST("v1.usershare/invitelist")
    @FormUrlEncoded
    Flowable<HttpResult<List<InvitationShareBeans>>> getShareList(@Field("user_id")String userId, @Field("mobile") String mobile, @Field("page")String page, @Field("pagesize")String pagesize);

    @POST("v1.usershare/index")
    @FormUrlEncoded
    Flowable<HttpResult<InvitationInfoBeans>> getInvitationInfo(@Field("user_id")String userId, @Field("mobile")String mobile);

    @POST("v1.usershare/inviteadd")
    @FormUrlEncoded
    Flowable<HttpResult<InvitationCodeBeans>> getInvitationCode(@Field("user_id")String userId, @Field("mobile")String mobile, @Field("invite_code")String code);

    @POST("v1.task/questionlist")
    @FormUrlEncoded
    Flowable<HttpResult<AnswerFgBeans>> getAnswerList(@Field("imei")String imei, @Field("group_id")String group_id, @Field("page")String page, @Field("pagesize")String pagesize);

    @POST("v1.task/questionadd")
    @FormUrlEncoded
    Flowable<HttpResult<AnswerFgQuestionBeans>> questionAdd(@Field("imei")String imei, @Field("group_id")String group_id, @Field("iserror")String iserror, @Field("continue_num")String continue_num);

    @POST("v1.show/getnewhb")
    @FormUrlEncoded
    Flowable<HttpResult<HomeNewHbBeans>> getNewHb(@Field("imei")String imei, @Field("group_id")String group_id);

    @POST("v1.task/hbonline")
    @FormUrlEncoded
    Flowable<HttpResult<HomeLineRedBeans>> getLineRed(@Field("imei")String imei, @Field("group_id")String group_id);

    @POST("v1.task/gethbonline")
    @FormUrlEncoded
    Flowable<HttpResult<GetHomeLineRedBeans>> gethbonline(@Field("imei")String imei, @Field("group_id")String group_id);
}
