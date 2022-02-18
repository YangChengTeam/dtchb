package com.yc.wxchb.utils.ad;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsInterstitialAd;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsVideoPlayConfig;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.yc.wxchb.application.Constant;
import com.yc.wxchb.utils.AppSettingUtils;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.DisplayUtil;
import com.yc.wxchb.utils.LogUtils;


import java.util.List;


public class GromoreInsetAdShow {
    private Activity mContext;
    private static GromoreInsetAdShow instance;
    public static GromoreInsetAdShow getInstance() {
        if (instance == null) {
            synchronized (GromoreInsetAdShow.class) {
                if (instance == null) {
                    instance = new GromoreInsetAdShow();
                }
            }
        }
        return instance;
    }


    public void setContextsInit(Context contexts){
        this.mContext = (Activity) contexts;
        loadCSJInteractionAd();
        loadTxInsertAd();
    }

    private int index;//
    private String indexType;//现在播放的是哪个 1 穿山甲  2腾讯  3快手
    private boolean loadSuccess;
    private boolean isComplete;
    private boolean isVideoClick;
    private String ad_positions;
    private int insertWidth = 300;
    private int insertHeight = 450;
    private String isTxLoadAdSuccess="0";//0 默认状态  1：开始播放  2：拉去广告失败  3：拉去广告成功
    public void showInset(Context context,String positions,OnInsetAdShowCaback onAdShowCaback){
        LogUtils.showAdLog("---插屏-----------:");
        this.mContext = (Activity) context;
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        if (onAdShowCaback!=null){
            this.onAdShowCaback=onAdShowCaback;
        }
        if (!TextUtils.isEmpty(positions)){
            ad_positions=positions;
        }
        isComplete=false;
        isVideoClick=false;
        isTxLoadAdSuccess="1";
        int screenWidth = CommonUtils.getScreenWidth(mContext);
        int w = (int) (screenWidth)*9/10;
        int h = screenWidth;
        insertWidth = DisplayUtil.px2dip(mContext, w);
        insertHeight= DisplayUtil.px2dip(mContext, h);
        List<String> adType = CacheDataUtils.getInstance().getAdInsetType();
        if (index<adType.size()){
            if (adType!=null&&adType.size()>0){
                indexType = adType.get(index);
                if ("1".equals(indexType)){//播放穿山甲
                    showCSJinset();
                    LogUtils.showAdLog("---插屏------穿山甲-----:");
                }else if ("2".equals(indexType)){//播放腾讯
                    showTxInsertAd();
                    LogUtils.showAdLog("---播放腾讯------插屏-----:");
                }else if ("3".equals(indexType)){//播放快手
                    requestInterstitialAd();
                    LogUtils.showAdLog("---播放快手------插屏-----:");
                }else {
                    showCSJinset();
                }
                index++;
            }else {
                showCSJinset();
            }
        }else {
            if (adType!=null&&adType.size()>0){
                index=0;
                indexType = adType.get(index);
                if ("1".equals(indexType)){//播放穿山甲
                    showCSJinset();
                    LogUtils.showAdLog("---插屏------v插屏-----:");
                }else if ("2".equals(indexType)){//播放腾讯
                    showTxInsertAd();
                    LogUtils.showAdLog("---播放腾讯------插屏-----:");
                }else if ("3".equals(indexType)){//播放快手
                    requestInterstitialAd();
                    LogUtils.showAdLog("---播放快手------插屏-----:");
                }else {
                    showCSJinset();
                }
            }else {
                showCSJinset();
            }
        }
    }

    public void setIndex(int type){
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        if ("1".equals(indexType)){//播放穿山甲
            if (type==1){
                showTxInsertAd();//播腾讯
            }else if (type==2){
                requestInterstitialAd();//播快手
            }else {

            }
        }else if ("2".equals(indexType)){//播放腾讯
            if (type==2){
                loadCSJInteractionAd();//播穿山甲
            }else if (type==1){
                requestInterstitialAd();//播快手
            }else {

            }
        }else if ("3".equals(indexType)){//播放快手
            if (type==3){
                loadCSJInteractionAd();//播穿山甲
            }else if (type==1){
                showTxInsertAd();//播快手
            }else {

            }
        }
    }



//==============start========穿山甲插屏=======================================================================================
    /**
     * 加载插屏广告
     */
    public TTNativeExpressAd ttInteractionAds;
    private void loadCSJInteractionAd() {
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        //step4:创建插屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(Constant.INSTER)
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(this.insertWidth, this.insertHeight) //根据广告平台选择的尺寸，传入同比例尺寸
                .build();
        //step5:请求广告，调用插屏广告异步请求接口
        TTAdManagerHolder.get().createAdNative(mContext).loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtils.showAdLog("--555---onError---穿山甲插屏"+message);
                ttInteractionAds=null;
                if ("1".equals(isTxLoadAdSuccess)){
                    setIndex(1);
                }
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (CommonUtils.isDestory(mContext)){
                    ttInteractionAds=null;
                    return;
                }
                if (ads == null || ads.size() == 0) {
                    ttInteractionAds=null;
                    LogUtils.showAdLog("--444------穿山甲插屏");
                    if ("1".equals(isTxLoadAdSuccess)){
                        setIndex(1);
                    }
                    return;
                }
                TTNativeExpressAd ttNativeExpressAd = ads.get(0);
                if (ttNativeExpressAd!=null){
                    ttInteractionAds=ttNativeExpressAd;
                    ttInteractionAds.render();
                    ttInteractionAds.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
                        //广告关闭回调
                        @Override
                        public void onAdDismiss() {
                            ttInteractionAds=null;
                            loadCSJInteractionAd();
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onRewardedAdClosed(true,true);
                            }
                        }
                        //广告点击回调
                        @Override
                        public void onAdClicked(View view, int type) {
                            AppSettingUtils.showTxClick("ad_insert", Constant.INSTER);
                        }
                        //广告展示回调
                        @Override
                        public void onAdShow(View view, int type) {
                            AppSettingUtils.showTxShow("ad_insert", Constant.INSTER);
                            isTxLoadAdSuccess="3";
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onRewardedAdShow();
                            }
                        }
                        //广告渲染失败回调
                        @Override
                        public void onRenderFail(View view, String msg, int code) {
                            if ("1".equals(isTxLoadAdSuccess)){
                                ttInteractionAds=null;
                                setIndex(1);
                            }
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onRewardedAdShowFail();
                            }
                        }
                        //广告渲染成功回调
                        @Override
                        public void onRenderSuccess(View view, float width, float height) {
                            //在渲染成功回调时展示广告，提升体验
                        }
                    });
                }else {
                    ttInteractionAds=null;
                }
            }
        });
    }

    public void showCSJinset(){
        if (ttInteractionAds!=null){
            if (CommonUtils.isDestory(mContext)){
                return;
            }
            ttInteractionAds.showInteractionExpressAd(mContext);
        }else {
            loadCSJInteractionAd();
            setIndex(1);
        }
    }

//==============end========穿山甲插屏=======================================================================================


//==============start========腾讯激励视频=======================================================================================
private UnifiedInterstitialAD unifiedInterstitialAD;
    public void loadTxInsertAd(){
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        unifiedInterstitialAD=new UnifiedInterstitialAD(mContext, Constant.TXINSTER, new UnifiedInterstitialADListener() {
            @Override
            public void onADReceive() {
                if (CommonUtils.isDestory(mContext)){
                    return;
                }
                isTxLoadAdSuccess="3";
                AppSettingUtils.showTxShow("ad_insert", Constant.TXINSTER);
                LogUtils.showAdLog("--------腾讯插屏show");
                if (onAdShowCaback!=null){
                    onAdShowCaback.onRewardedAdShow();
                }
            }

            @Override
            public void onVideoCached() {

            }

            @Override
            public void onNoAD(com.qq.e.comm.util.AdError adError) {
                unifiedInterstitialAD=null;
                if ("1".equals(isTxLoadAdSuccess)){
                    setIndex(2);
                }
                if (onAdShowCaback!=null){
                    onAdShowCaback.onRewardedAdShowFail();
                }
            }

            @Override
            public void onADOpened() {

            }

            @Override
            public void onADExposure() {

            }

            @Override
            public void onADClicked() {
                AppSettingUtils.showTxClick("ad_insert", Constant.TXINSTER);
            }

            @Override
            public void onADLeftApplication() {

            }

            @Override
            public void onADClosed() {
                unifiedInterstitialAD=null;
                loadTxInsertAd();
                if (onAdShowCaback!=null){
                    onAdShowCaback.onRewardedAdClosed(true,true);
                }
            }

            @Override
            public void onRenderSuccess() {

            }

            @Override
            public void onRenderFail() {
                LogUtils.showAdLog("---播放腾讯------onRenderFail--插屏---:");
                unifiedInterstitialAD=null;
                if ("1".equals(isTxLoadAdSuccess)){
                    setIndex(2);
                }
            }
        });
        unifiedInterstitialAD.loadAD();
    }

    public void showTxInsertAd(){
        try {
            if (unifiedInterstitialAD!=null&&unifiedInterstitialAD.isValid()){
                if (CommonUtils.isDestory(mContext)){
                    return;
                }
                unifiedInterstitialAD.show();
            }else {
                loadTxInsertAd();
                setIndex(2);
            }
        }catch (Exception e){
            loadTxInsertAd();
            setIndex(2);
        }
    }
    //=================end===================腾讯激励视频=====================================================================================
    private boolean isShow;
    //=================start===================快手激励视频=====================================================================================
    // 1.请求插屏广告，获取广告对象，InterstitialAd
    public void requestInterstitialAd() {
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        long aLong = Long.parseLong(Constant.KSINSTER);
// 此为测试posId，请联系快手平台申请正式posId
        KsScene scene = new KsScene.Builder(aLong)
                .build();
        KsLoadManager loadManager = KsAdSDK.getLoadManager();
        if (loadManager!=null&&scene!=null){
            loadManager.loadInterstitialAd(scene,
                    new KsLoadManager.InterstitialAdListener() {
                        @Override
                        public void onError(int code, String msg) {
                            if ("1".equals(isTxLoadAdSuccess)){
                                setIndex(3);
                            }
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onRewardedAdShowFail();
                            }
                        }

                        @Override
                        public void onRequestResult(int adNumber) {

                        }


                        @Override
                        public void onInterstitialAdLoad(@Nullable List<KsInterstitialAd> adList) {
                            if (CommonUtils.isDestory(mContext)){
                                return;
                            }
                            if (adList != null && adList.size() > 0) {
                                mKsInterstitialAd = adList.get(0);
                                KsVideoPlayConfig videoPlayConfig = new KsVideoPlayConfig.Builder()
                                        .videoSoundEnable(true)
                                        .showLandscape(mContext.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                                        .build();
                                if (CommonUtils.isDestory(mContext)){
                                    return;
                                }
                                showInterstitialAd(videoPlayConfig);
                            }else {
                                if ("1".equals(isTxLoadAdSuccess)){
                                    setIndex(3);
                                }
                            }
                        }
                    });
        }
    }
    private int insetInedx;
    private KsInterstitialAd mKsInterstitialAd;
    private void showInterstitialAd(KsVideoPlayConfig videoPlayConfig) {
        if (isShow){
            insetInedx++;
            if (insetInedx>=1){
                isShow=false;
            }
            setIndex(3);
            return;
        }
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        insetInedx=0;
        if (mKsInterstitialAd != null) {
            mKsInterstitialAd
                    .setAdInteractionListener(new KsInterstitialAd.AdInteractionListener() {
                        @Override
                        public void onAdClicked() {
                            AppSettingUtils.showTxClick("ad_insert", Constant.KSINSTER);
                        }

                        @Override
                        public void onAdShow() {
                            isShow=true;
                            AppSettingUtils.showTxShow("ad_insert", Constant.KSINSTER);
                            isTxLoadAdSuccess="3";
                            LogUtils.showAdLog("--------快手插屏show");
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onRewardedAdShow();
                            }
                        }

                        @Override
                        public void onAdClosed() {
                            isShow=false;
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onRewardedAdClosed(true,true);
                            }
                        }

                        @Override
                        public void onPageDismiss() {
                            isShow=false;
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onRewardedAdClosed(true,true);
                            }
                        }

                        @Override
                        public void onVideoPlayError(int code, int extra) {
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onRewardedAdShowFail();
                            }
                        }

                        @Override
                        public void onVideoPlayEnd() {

                        }

                        @Override
                        public void onVideoPlayStart() {

                        }

                        @Override
                        public void onSkippedAd() {

                        }

                    });
            if (CommonUtils.isDestory(mContext)){
                return;
            }
            mKsInterstitialAd.showInterstitialAd(mContext, videoPlayConfig);
        }else {
            setIndex(3);
        }
    }
    //=================end===================快手激励视频=====================================================================================


    private OnInsetAdShowCaback onAdShowCaback;


    public interface OnInsetAdShowCaback{
        void onRewardedAdShow();
        void onRewardedAdShowFail();
        void onRewardClick();
        void onVideoComplete();
        void setVideoCallBacks();
        void onRewardedAdClosed(boolean isVideoClick, boolean isCompeter);
    }


}
