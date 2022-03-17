package com.yc.wxchb.utils.adgromore;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.AdLoadInfo;
import com.bytedance.msdk.api.GMAdEcpmInfo;
import com.bytedance.msdk.api.reward.RewardItem;
import com.bytedance.msdk.api.v2.GMAdConstant;
import com.bytedance.msdk.api.v2.GMMediationAdSdk;
import com.bytedance.msdk.api.v2.GMSettingConfigCallback;
import com.bytedance.msdk.api.v2.ad.reward.GMRewardAd;
import com.bytedance.msdk.api.v2.ad.reward.GMRewardedAdListener;
import com.bytedance.msdk.api.v2.ad.reward.GMRewardedAdLoadCallback;
import com.bytedance.msdk.api.v2.slot.GMAdOptionUtil;
import com.bytedance.msdk.api.v2.slot.GMAdSlotRewardVideo;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.constants.Constant;
import com.yc.wxchb.utils.AppSettingUtils;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.ClickListenNameThree;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.VUiKit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GromoreAdShowThree {
    private String codes;
    private Activity context;
    private static GromoreAdShowThree instance;
    public static GromoreAdShowThree getInstance() {
        if (instance == null) {
            synchronized (GromoreAdShowThree.class) {
                if (instance == null) {
                    instance = new GromoreAdShowThree();
                }
            }
        }
        return instance;
    }

    public void setContexts(Context contexts,String code){
        this.codes=code;
        this.context = (Activity) contexts;
        loadVideo();
    }

    //=================start===================激励视频=====================================================================================
    private GMRewardAd mttRewardAd;
    private boolean loadSuccess;
    private boolean isComplete;
    private boolean isVideoClick;
    private String ad_positions;
    private String isTxLoadAdSuccess="0";//0 默认状态  1：开始播放  2：拉去广告失败  3：拉去广告成功
    public void showjiliAd(String positions,OnAdShowCaback onAdShowCaback ){
        if (onAdShowCaback!=null){
            this.onAdShowCaback=onAdShowCaback;
        }
        if (!TextUtils.isEmpty(positions)){
            ad_positions=positions;
        }
        isComplete=false;
        isVideoClick=false;
        isTxLoadAdSuccess="1";
            if (loadSuccess && mttRewardAd != null&&mttRewardAd.isReady()) {
                //在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
                //该方法直接展示广告，如果展示失败了（如过期），会回调onVideoError()
                //展示广告，并传入广告展示的场景
                if (!CommonUtils.isDestory(context)){
                    mttRewardAd.setRewardAdListener(mTTRewardedAdListener);
                    mttRewardAd.showRewardAd(context);
                }
            }else {
                ToastUtil.showToast("广告正在加载中");
                if (ClickListenNameThree.isFastClick()){
                    loadVideo();
                }
            }
    }

    public void isReady(){
        if (context!=null&&instance!=null){
            if (loadSuccess && mttRewardAd != null&&mttRewardAd.isReady()) {
                //在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
                //该方法直接展示广告，如果展示失败了（如过期），会回调onVideoError()
                //展示广告，并传入广告展示的场景
            }else {
                loadVideo();
            }
        }
    }



    public void loadVideo() {
        /**
         * 判断当前是否存在config 配置 ，如果存在直接加载广告 ，如果不存在则注册config加载回调
         */
      if (GMMediationAdSdk.configLoadSuccess()) {
            LogUtils.showAdLog("load GromoreAdShow_激励视频ad 当前config配置存在，直接加载广告");
            loadAd();
        } else {
            LogUtils.showAdLog("load GromoreAdShow_激励视频ad 当前config配置存在，直接加载广告");
            GMMediationAdSdk.registerConfigCallback(mSettingConfigCallback); //不用使用内部类，否则在ondestory中无法移除该回调
        }
    }

    /**
     * config回调
     */
    private GMSettingConfigCallback mSettingConfigCallback = new GMSettingConfigCallback() {
        @Override
        public void configLoad() {
            LogUtils.showAdLog("load GromoreAdShow_激励视频ad 在config 回调中加载广告");
            //loadAd();
        }
    };
    private int loadAdCount;
    private boolean isLoad;
    private   Map<String, String> customData;
    private   GMAdSlotRewardVideo adSlotRewardVideo;
    private void loadAd() {
        if (CommonUtils.isDestory(context)){
            return;
        }
        if (isLoad){
            return;
        }
        isLoad=true;
        Log.d("ccc", "------------loadAd: "+codes);
        mttRewardAd = new GMRewardAd(context,codes);
        //创建广告请求参数AdSlot,具体参数含义参考文档
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        String userId="";
        if (userInfo!=null){
            userId=userInfo.getId()+"";
        }
         customData = new HashMap<>();
         customData.put("cashvideo", "1");
         adSlotRewardVideo = new GMAdSlotRewardVideo.Builder()
                .setMuted(false)//对所有SDK的激励广告生效，除需要在平台配置的SDK，如穿山甲SDK
                .setVolume(0.6f)//配合Admob的声音大小设置[0-1]
                .setGMAdSlotGDTOption(GMAdOptionUtil.getGMAdSlotGDTOption().build())
                .setGMAdSlotBaiduOption(GMAdOptionUtil.getGMAdSlotBaiduOption().build())
                .setCustomData(customData)
                .setRewardName("金币奖励") //奖励的名称
                .setRewardAmount(1)  //奖励的数量
                .setUserID(userId)//用户id,必传参数
                .setUseSurfaceView(true)
                .setOrientation(GMAdConstant.VERTICAL)//必填参数，期望视频的播放方向：GMAdConstant.HORIZONTAL 或 GMAdConstant.VERTICAL
                .build();


        LogUtils.showAdLog("load GromoreAdShow_激励视频adloadAd ");
        //请求广告
        mttRewardAd.loadAd(adSlotRewardVideo, new GMRewardedAdLoadCallback() {
            @Override
            public void onRewardVideoLoadFail(AdError adError) {
                LogUtils.showAdLog("load GromoreAdShow_激励视频ad 失败onRewardVideoLoadFail"+adError.message+"---"+adError.thirdSdkErrorMessage+"----"+adError.code);
                isLoad=false;
                loadSuccess = false;
                loadAdCount++;
                mttRewardAd=null;
                if ("1".equals(isTxLoadAdSuccess)){
                      if (onAdShowCaback!=null){
                          onAdShowCaback.onRewardedAdShowFail();
                      }
                }
                isTxLoadAdSuccess="0";
                if (loadAdCount<=2){
                    loadVideo();
                }
            }

            @Override
            public void onRewardVideoAdLoad() {
                isLoad=false;
                loadSuccess = true;
            }

            @Override
            public void onRewardVideoCached() {
                LogUtils.showAdLog("load GromoreAdShow_激励视频ad onRewardVideoCached");
                isLoad=false;
                loadSuccess = true;
                VUiKit.postDelayed(100, new Runnable() {
                    @Override
                    public void run() {
                        if (mttRewardAd!=null){
                            boolean ready = mttRewardAd.isReady();
                            if (!ready){
                                loadAdCount++;
                                if (loadAdCount<=2){
                                    loadVideo();
                                }else {
                                    isTxLoadAdSuccess="0";
                                }
                            }else {
                                if ("1".equals(isTxLoadAdSuccess)){
                                    if (!CommonUtils.isDestory(context)){
                                        isTxLoadAdSuccess="5";
                                        mttRewardAd.setRewardAdListener(mTTRewardedAdListener);
                                        mttRewardAd.showRewardAd(context);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
    }
    private String adNetworkRitId;
    /**
     * 激励视频交互回调
     */
    private GMRewardedAdListener mTTRewardedAdListener = new GMRewardedAdListener() {
        public void onRewardedAdShow() {
             adNetworkRitId = mttRewardAd.getAdNetworkRitId();
            Log.d("ccc", "-----ddd----onRewardedAdShow: "+adNetworkRitId);
          //  Log.d("ccc", "---onRewardedAdShow: "+"---code:"+codes+"---"+preEcpm+"---"+multiBiddingEcpm+"---adNetworkPlatformId:"+adNetworkPlatformId+"----"+adLoadInfoList+"---"+adNetworkRitId+"----"+bestEcpm+"----"+showEcpm);
            loadAdCount=0;
            isTxLoadAdSuccess="3";
            loadAd();
            if (onAdShowCaback!=null){
                onAdShowCaback.onRewardedAdShow();
            }
            if (!TextUtils.isEmpty(ad_positions)){
                AppSettingUtils.showTxShow(ad_positions,codes);
            }else {
                AppSettingUtils.showTxShow("jili",codes);
            }
        }
        @Override
        public void onRewardedAdShowFail(AdError adError) {
            mttRewardAd=null;
            if ("1".equals(isTxLoadAdSuccess)){
                if (onAdShowCaback!=null){
                    onAdShowCaback.onRewardedAdShowFail();
                }
            }
            if ("1".equals(isTxLoadAdSuccess)||"5".equals(isTxLoadAdSuccess)){
                isTxLoadAdSuccess="2";
            }
            loadAd();
        }
        @Override
        public void onRewardClick() {
            if (onAdShowCaback!=null){
                onAdShowCaback.onRewardClick();
            }
            isVideoClick = true;
            if (!TextUtils.isEmpty(ad_positions)){
                AppSettingUtils.showTxClick(ad_positions,codes);
            }else {
                AppSettingUtils.showTxClick("jili",codes);
            }
        }

        public void onRewardedAdClosed() {
            if (onAdShowCaback!=null){
                onAdShowCaback.onRewardedAdClosed(isVideoClick,isComplete,adNetworkRitId);
            }
        }

        public void onVideoComplete() {
            isComplete=true;
            if (onAdShowCaback!=null){
                onAdShowCaback.onVideoComplete();
            }
        }

        public void onVideoError() {
            if ("1".equals(isTxLoadAdSuccess)||"5".equals(isTxLoadAdSuccess)){
                isTxLoadAdSuccess="2";
                //失败了播放腾讯的
            }
        }

        public void onRewardVerify(RewardItem rewardItem) {
            boolean b = rewardItem.rewardVerify();
            isComplete=b;
        }

        @Override
        public void onSkippedVideo() {

        }
    };
    //=================end===================激励视频=====================================================================================

    private OnAdShowCaback onAdShowCaback;


    public interface OnAdShowCaback{
           void onRewardedAdShow();
           void onRewardedAdShowFail();
           void onRewardClick();
           void onVideoComplete();
           void setVideoCallBacks();
           void onRewardedAdClosed(boolean isVideoClick, boolean isCompeter,String adNetworkRitId);
    }

}
