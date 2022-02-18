package com.yc.wxchb.beans.module;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.yc.wxchb.beans.module.beans.ComplainBeans;
import com.yc.wxchb.beans.module.beans.EmptyBeans;
import com.yc.wxchb.beans.module.beans.HelpQuestionBeans;
import com.yc.wxchb.beans.module.beans.HotIndexBeans;
import com.yc.wxchb.beans.module.beans.HotNumsInfoBeans;
import com.yc.wxchb.beans.module.beans.HotTaskBeans;
import com.yc.wxchb.beans.module.beans.HotWithDrawBeans;
import com.yc.wxchb.beans.module.beans.InvationFriendExchangeBeans;
import com.yc.wxchb.beans.module.beans.InvitationInfoBeans;
import com.yc.wxchb.beans.module.beans.InvitationShareBeans;
import com.yc.wxchb.beans.module.beans.MoneyTaskBeans;
import com.yc.wxchb.beans.module.beans.MoneysBeans;
import com.yc.wxchb.beans.module.beans.OtherBeans;
import com.yc.wxchb.beans.module.beans.QuesTionsHotBeans;
import com.yc.wxchb.beans.module.beans.TelBeans;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.utils.UpDataVersion;

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

    @POST("v1.version/appskip")
    @FormUrlEncoded
    Flowable<HttpResult<UpDataVersion>> upVersion(@Field("agent_id")String agent_id);

    @POST("v1.download/info")
    @FormUrlEncoded
    Flowable<HttpResult<HotNumsInfoBeans>> getHotInfo(@Field("user_id")String userId, @Field("agent_id")String agent_id);

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

    @POST("v1.music/outvideo")
    @FormUrlEncoded
    Flowable<HttpResult<List<MoneyTaskBeans>>> getMoneyTask(@Field("user_id")String userId);

    @POST("v1.music/outvideocash")
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

}
