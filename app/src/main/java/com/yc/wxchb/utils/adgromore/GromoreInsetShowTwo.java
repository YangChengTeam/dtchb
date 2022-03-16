package com.yc.wxchb.utils.adgromore;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.bytedance.msdk.api.AdError;
import com.bytedance.msdk.api.v2.GMMediationAdSdk;
import com.bytedance.msdk.api.v2.GMSettingConfigCallback;
import com.bytedance.msdk.api.v2.ad.interstitial.GMInterstitialAd;
import com.bytedance.msdk.api.v2.ad.interstitial.GMInterstitialAdListener;
import com.bytedance.msdk.api.v2.ad.interstitial.GMInterstitialAdLoadCallback;
import com.bytedance.msdk.api.v2.slot.GMAdOptionUtil;
import com.bytedance.msdk.api.v2.slot.GMAdSlotInterstitial;
import com.yc.wxchb.constants.Constant;
import com.yc.wxchb.utils.AppSettingUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.DisplayUtil;
import com.yc.wxchb.utils.VUiKit;


public class GromoreInsetShowTwo {
    private Activity context;
    private static GromoreInsetShowTwo instance;
    public static GromoreInsetShowTwo getInstance() {
        if (instance == null) {
            synchronized (GromoreInsetShowTwo.class) {
                if (instance == null) {
                    instance = new GromoreInsetShowTwo();
                }
            }
        }
        return instance;
    }

   private boolean insetIsShow;
    private int   loadAdCount;;
    private int loadAdCountCache;
    private int dpw;
    private int dph;
    public void setContexts(Context contexts){
        this.context = (Activity) contexts;
        int screenWidth = CommonUtils.getScreenWidth(context);
        int w = (int) (screenWidth)*9/10;
        int h = w*3/2;
         dpw = DisplayUtil.px2dip(context, w);
         dph = DisplayUtil.px2dip(context, h);
    }
    //=================start===================插屏=====================================================================================
    private String isTxLoadAdSuccess="0";//0 默认状态  1：开始播放  2：拉去广告失败  3：拉去广告成功
    private GMInterstitialAd mInterstitialAd;
    private boolean insetIsLoadSuccess;
    public void  showInset(OnInsetAdShowCaback onInsetAdShowCabacks){
        this.onAdShowCaback=onInsetAdShowCabacks;
        isTxLoadAdSuccess="1";
        if (insetIsLoadSuccess && mInterstitialAd != null && mInterstitialAd.isReady()) {
            //设置监听器
            mInterstitialAd.setAdInterstitialListener(interstitialListener);
            mInterstitialAd.showAd(context);
        }else {
            loadInset();
        }
    }


    public void  loadInset(){
        if (GMMediationAdSdk.configLoadSuccess()) {
            loadInteractionAd();
        } else {
            GMMediationAdSdk.registerConfigCallback(mSettingConfigCallbackinset);
        }
    }


    private GMSettingConfigCallback mSettingConfigCallbackinset = new GMSettingConfigCallback() {
        @Override
        public void configLoad() {
            LogUtils.showAdLog("load 首页插屏在config 回调中加载广告");
            loadInteractionAd();
        }
    };


    public void isReady(){
        if (insetIsLoadSuccess && mInterstitialAd != null && mInterstitialAd.isReady()) {

        }else {
            loadInset();
        }
    }

    private boolean isLoad;
    /**
     * 加载插屏广告
     */
    private  GMAdSlotInterstitial adSlotInterstitial;
    private      String ad_code;
    private void loadInteractionAd() {
        //Context 必须传activity
        /**
         * 注：每次加载插屏广告的时候需要新建一个TTInterstitialAd，否则可能会出现广告填充问题
         * （ 例如：mInterstitialAd = new TTInterstitialAd(this, adUnitId);）
         */
        if (CommonUtils.isDestory(context)){
            return;
        }
        if (isLoad){
            return;
        }
        isLoad=true;
         ad_code="";
        ad_code= Constant.INSTER;
        mInterstitialAd = new GMInterstitialAd(context, ad_code);

         adSlotInterstitial = new GMAdSlotInterstitial.Builder()
                .setGMAdSlotBaiduOption(GMAdOptionUtil.getGMAdSlotBaiduOption().build())
                .setGMAdSlotGDTOption(GMAdOptionUtil.getGMAdSlotGDTOption().build())
                .setImageAdSize(dpw, dph)
                .setVolume(0.5f)
                .setMuted(false)
                .build();

        //请求广告，调用插屏广告异步请求接口
        mInterstitialAd.loadAd(adSlotInterstitial, new GMInterstitialAdLoadCallback() {
            @Override
            public void onInterstitialLoadFail(AdError adError) {
                if (onAdShowCaback!=null){
                    onAdShowCaback.onError();
                }
                isTxLoadAdSuccess="0";
                isLoad=false;
                LogUtils.showAdLog("load首页插屏error"+ adError.code + ", errMsg: " + adError.message);
                insetIsLoadSuccess = false;
                loadAdCount++;
                if (loadAdCount<=3){
                    loadInset();
                }
                // 获取本次waterfall加载中，加载失败的adn错误信息。
                if (mInterstitialAd != null)
                    Log.d("ccc", "ad load 首页插屏infos: " + mInterstitialAd.getAdLoadInfoList());
            }

            @Override
            public void onInterstitialLoad() {
                isLoad=false;
                loadAdCount=0;
                LogUtils.showAdLog("load首页插屏success");
                insetIsLoadSuccess = true;
                VUiKit.postDelayed(100, new Runnable() {
                    @Override
                    public void run() {
                        boolean ready = mInterstitialAd.isReady();
                        if (!ready){
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onError();
                            }
                            loadAdCountCache++;
                            if (loadAdCountCache<=2){
                                loadInteractionAd();
                            }else {
                                isTxLoadAdSuccess="0";
                            }
                        }else {
                            if ("1".equals(isTxLoadAdSuccess)){
                                if (!CommonUtils.isDestory(context)){
                                    isTxLoadAdSuccess="5";
                                    mInterstitialAd.setAdInterstitialListener(interstitialListener);
                                    mInterstitialAd.showAd(context);
                                }
                            }
                        }
                    }
                });

                // 获取本次waterfall加载中，加载失败的adn错误信息。
                if (mInterstitialAd != null)
                    Log.d("ccc", "ad load首页插屏 infos: " + mInterstitialAd.getAdLoadInfoList());
            }
        });
    }

    GMInterstitialAdListener interstitialListener = new GMInterstitialAdListener() {
        /**
         * 广告展示
         */
        @Override
        public void onInterstitialShow() {
            loadAdCount=0;
            loadAdCountCache=0;
            isTxLoadAdSuccess="3";
            loadInset();
            insetIsShow=true;
            if (onAdShowCaback!=null){
                onAdShowCaback.onRewardedAdShow();
            }
            AppSettingUtils.showTxShow("chaping",ad_code);
        }

        /**
         * show失败回调。如果show时发现无可用广告（比如广告过期或者isReady=false），会触发该回调。
         * 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载。
         * @param adError showFail的具体原因
         */
        @Override
        public void onInterstitialShowFail(AdError adError) {
            if (onAdShowCaback!=null){
                onAdShowCaback.onError();
            }
            if ("1".equals(isTxLoadAdSuccess)||"5".equals(isTxLoadAdSuccess)){
                isTxLoadAdSuccess="2";
            }
            insetIsShow=false;
            // 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载
            loadInset();
        }

        @Override
        public void onInterstitialAdClick() {
            if (onAdShowCaback!=null){
                onAdShowCaback.onRewardClick();
            }
            AppSettingUtils.showTxClick("chaping",ad_code);
        }

        @Override
        public void onInterstitialClosed() {
            loadAdCount=0;
            if (onAdShowCaback!=null){
                onAdShowCaback.onRewardedAdClosed(false);
            }
        }
        /**
         * 当广告打开浮层时调用，如打开内置浏览器、内容展示浮层，一般发生在点击之后
         * 常常在onAdLeftApplication之前调用
         */
        @Override
        public void onAdOpened() {
        }

        /**
         * 此方法会在用户点击打开其他应用（例如 Google Play）时
         * 于 onAdOpened() 之后调用，从而在后台运行当前应用。
         */
        @Override
        public void onAdLeftApplication() {
        }
    };
    //=================end===================插屏=====================================================================================

    private OnInsetAdShowCaback onAdShowCaback;
    public interface OnInsetAdShowCaback{
        void onRewardedAdShow();
        void onError();
        void onRewardClick();
        void onRewardedAdClosed(boolean isVideoClick);
    }
}
