package com.yc.jsdsp.utils.ad;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsLoadManager;
import com.kwad.sdk.api.KsRewardVideoAd;
import com.kwad.sdk.api.KsScene;
import com.kwad.sdk.api.KsVideoPlayConfig;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.ads.rewardvideo.ServerSideVerificationOptions;
import com.qq.e.comm.compliance.DownloadConfirmCallBack;
import com.qq.e.comm.compliance.DownloadConfirmListener;
import com.qq.e.comm.util.AdError;
import com.yc.jsdsp.beans.module.beans.UserInfo;
import com.yc.jsdsp.constants.Constant;
import com.yc.jsdsp.utils.AppSettingUtils;
import com.yc.jsdsp.utils.CacheDataUtils;
import com.yc.jsdsp.utils.CommonUtils;
import com.yc.jsdsp.utils.DeleteFileUtil;
import com.yc.jsdsp.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GromoreAdShow {
    private Activity mContext;
    private static GromoreAdShow instance;
    public static GromoreAdShow getInstance() {
        if (instance == null) {
            synchronized (GromoreAdShow.class) {
                if (instance == null) {
                    instance = new GromoreAdShow();
                }
            }
        }
        return instance;
    }
   private String userId;
    private  int ad_pb;
    private List<String> adType;
    private List<String> adTypes;
    public void setContextsInit(Context contexts){
       this.mContext = (Activity) contexts;
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        if (userInfo!=null){
            userId=userInfo.getId()+"";
            ad_pb= userInfo.getAd_pb();
        }
        adType = CacheDataUtils.getInstance().getAdType();
        adTypes = CacheDataUtils.getInstance().getDownAdType();
        loadRewardVideoAd();
        loadTx();
    }
    private int index;//
    private int downIndex;//
    private String indexType;//现在播放的是哪个 1 穿山甲  2腾讯  3快手
    private boolean loadSuccess;
    private boolean isComplete;
    private boolean isVideoClick;
    private String ad_positions;
    private int type;
    private String absolutePath2;
    private String isTxLoadAdSuccess="0";//0 默认状态  1：开始播放  2：拉去广告失败  3：拉去广告成功


    public void showjiliAd(Context context,int types,String positions,OnAdShowCaback onAdShowCaback){
        if (CommonUtils.isProxyAndDe(context)){
            ToastUtil.showToast("出现未知错误，请稍后再试");
            return;
        }
        isVideoClick=false;
        this.type=types;
        this.mContext = (Activity) context;
        if (TextUtils.isEmpty(userId)){
            UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
            if (userInfo!=null){
                userId=userInfo.getId()+"";
            }
        }
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        if (onAdShowCaback!=null){
            this.onAdShowCaback=onAdShowCaback;
        }
        if (!TextUtils.isEmpty(positions)){
            ad_positions=positions;
        }
        if (ad_pb==1){
            ToastUtil.showToast("您涉嫌不合规操作，账号已被封禁，请联系客服申诉");
            return;
        }
        isComplete=false;
        isVideoClick=false;
        isTxLoadAdSuccess="1";

        if (type==1){
            if (adType==null||adType.size()==0){
                adType = CacheDataUtils.getInstance().getAdType();
                if (adType==null){
                    adType=new ArrayList<>();
                }
                if (adType.size()==0){
                    adType.add("1");
                    adType.add("2");
                    adType.add("3");
                }
            }
            if (index<adType.size()){
                indexType = adType.get(index);
                if (adType.size()>0){
                    if ("1".equals(indexType)){//播放穿山甲
                        LogUtils.showAdLog("---------穿山甲-----激励视频:");
                        showCSJRewardVideoAd();
                    }else if ("2".equals(indexType)){//播放腾讯
                        LogUtils.showAdLog("----------腾讯-----激励视频:");
                        showTx();
                    }else if ("3".equals(indexType)){//播放快手
                        LogUtils.showAdLog("--------快手-----激励视频:");
                        loadKSRewardVideoAd();
                    }else {
                        showCSJRewardVideoAd();
                    }
                    index++;
                }else {
                    showCSJRewardVideoAd();
                }
            }else {
                index=0;
                if (adType==null||adType.size()==0){
                    adType = CacheDataUtils.getInstance().getAdType();
                    if (adType==null){
                        adType=new ArrayList<>();
                    }
                    if (adType.size()==0){
                        adType.add("1");
                        adType.add("2");
                        adType.add("3");
                    }
                }
                indexType = adType.get(index);
                if ("1".equals(indexType)){//播放穿山甲
                    LogUtils.showAdLog("---------穿山甲-----激励视频:");
                    showCSJRewardVideoAd();
                }else if ("2".equals(indexType)){//播放腾讯
                    LogUtils.showAdLog("----------腾讯-----激励视频:");
                    showTx();
                }else if ("3".equals(indexType)){//播放快手
                    LogUtils.showAdLog("--------快手-----激励视频:");
                    loadKSRewardVideoAd();
                }else {
                    showCSJRewardVideoAd();
                }
            }
        }else {
            try {
                absolutePath2 = mContext.getExternalFilesDir("").getAbsolutePath()+"/ksadsdk/Download";
            }catch (Exception e){
                absolutePath2="";
            }
            if (adTypes==null||adTypes.size()==0){
                adTypes = CacheDataUtils.getInstance().getDownAdType();
                if (adTypes==null){
                    adTypes=new ArrayList<>();
                }
                if (adTypes.size()==0){
                    adTypes.add("1");
                    adTypes.add("2");
                    adTypes.add("3");
                }
            }
            if (downIndex<adTypes.size()){
                indexType = adTypes.get(downIndex);
                if ("1".equals(indexType)){//播放穿山甲
                    LogUtils.showAdLog("---------穿山甲-----激励视频:");
                    showCSJRewardVideoAd();
                }else if ("2".equals(indexType)){//播放腾讯
                    LogUtils.showAdLog("----------腾讯-----激励视频:");
                    showTx();
                }else if ("3".equals(indexType)){//播放快手
                    LogUtils.showAdLog("--------快手-----激励视频:");
                    loadKSRewardVideoAd();
                }else {
                    showCSJRewardVideoAd();
                }
                downIndex++;
            }else {
                downIndex=0;
                indexType = adTypes.get(downIndex);
                if ("1".equals(indexType)){//播放穿山甲
                    showCSJRewardVideoAd();
                }else if ("2".equals(indexType)){//播放腾讯
                    showTx();
                }else if ("3".equals(indexType)){//播放快手
                    loadKSRewardVideoAd();
                }else {
                    showCSJRewardVideoAd();
                }
            }
        }
    }


    public void setIndex(int types){
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        if ("1".equals(indexType)){//播放穿山甲
            if (types==1){
                if (this.type==1){
                    if (adType.contains("3")){
                        loadKSRewardVideoAd();//播快手
                    }else if (adType.contains("2")){
                        showTx();//播腾讯
                    }else {
                        loadKSRewardVideoAd();//播快手
                    }
                }else{
                    if (adTypes.contains("3")){
                        loadKSRewardVideoAd();//播快手
                    }else if (adTypes.contains("2")){
                        showTx();//播腾讯
                    }else {
                        loadKSRewardVideoAd();//播快手
                    }
                }
            }else if (types==2){
                loadKSRewardVideoAd();//播快手
            }else {
                ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
            }
        }else if ("2".equals(indexType)){//播放腾讯
            if (types==2){
                showCSJRewardVideoAd();//播穿山甲
            }else if (types==1){
                loadKSRewardVideoAd();//播快手
            }else {
                ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
            }
        }else if ("3".equals(indexType)){//播放快手
            if (types==3){
                showCSJRewardVideoAd();//播穿山甲
            }else if (types==1){
                showTx();//播快手
            }else {
                ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
            }
        }
    }



//==============start========穿山甲激励视频=======================================================================================
   public void showCSJRewardVideoAd() {
       csjStatus=0;
       isLoad=false;
       LogUtils.showAdLog("------2---穿山甲-----激励视频:");
       if (CommonUtils.isDestory(mContext)){
           return;
       }
       LogUtils.showAdLog("------2---穿山甲-----激励视频:"+(mttRewardVideoAd==null)+"---"+(mContext==null));
       if (mttRewardVideoAd != null && mContext != null) {
           LogUtils.showAdLog("------2---穿山甲-----激励视频:"+(mttRewardVideoAd==null)+"---"+(mContext==null)+"--");
        //step6:在获取到广告后展示
         try {
            mttRewardVideoAd.showRewardVideoAd((Activity) mContext);
         }catch (Exception e){
            setIndex(1);
         }
     }else {
           setIndex(1);
           loadRewardVideoAd();
     }
   }
    private TTRewardVideoAd mttRewardVideoAd;
    private int csjCount;
    private int showIndex;//  0没有播放  1穿山甲  2 腾讯  快手
    /**
     * 加载激励视频广告
     */
    private String fileNames;
    private String loadAppPackage;
    private String loadAppName;
    private int csjStatus;//0不是下载视频或未下载  1已经下载   2已经安装
    private boolean isLoad;//0没有下载过  1下载过
    /**
     * 加载激励视频广告
     */
    private void loadRewardVideoAd() {
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        //个性化模板广告需要传入期望广告view的宽、高，单位dp，
        String codes= Constant.RVIDEO;
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codes)
                .setSupportDeepLink(true)
                //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                .setExpressViewAcceptedSize(500, 500)
                .setUserID(userId)//用户id,必传参数
                .setUserData(userId)
                .setMediaExtra("media_extra") //附加参数，可选
                .setOrientation(TTAdConstant.VERTICAL) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                .build();
        TTAdManagerHolder.get().createAdNative(mContext).loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtils.showAdLog("-----原生---穿山甲激励视频"+message+"---"+code);
                mttRewardVideoAd=null;
                if (csjCount<2){
                    csjCount++;
                    loadRewardVideoAd();
                }
            }

            //视频广告加载后的视频文件资源缓存到本地的回调
            @Override
            public void onRewardVideoCached() {
                csjCount=0;
            }

            @Override
            public void onRewardVideoCached(TTRewardVideoAd ttRewardVideoAd) {
                csjCount=0;
            }

            //视频广告素材加载到，如title,视频url等，不包括视频文件
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                mttRewardVideoAd = ad;
                //mttRewardVideoAd.setShowDownLoadBar(false);
                csjCount=0;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        showIndex=1;
                        AppSettingUtils.showTxShow("ad_jili", Constant.RVIDEO);
                        isTxLoadAdSuccess="3";
                        LogUtils.showAdLog("--------穿山甲激励视频:show");
                    }

                    @Override
                    public void onAdVideoBarClick() { ////广告的下载bar点击回调
                        isVideoClick=true;
                        Log.d("ccc", "---穿山甲--激励视频----onAdVideoBarClick: ");
                        AppSettingUtils.showTxClick(ad_positions, Constant.RVIDEO);
                    }

                    @Override
                    public void onAdClose() { //视频广告关闭回调
                        mttRewardVideoAd=null;
                        showIndex=0;
                        if (type==1||type==3){
                            mttRewardVideoAd=null;
                            if (csjCount<2){
                                csjCount++;
                                loadRewardVideoAd();
                            }
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onRewardedAdClosed(isVideoClick,true);
                            }
                        }else {
                            isVideoClick=false;
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onRewardedAdClosed(true,true);
                            }
                            if (isLoad&&csjStatus==2){
                                if (!TextUtils.isEmpty(loadAppName)){
                                    loadAppPackage = CommonUtils.getAppPackage(mContext, loadAppName);
                                    if (onAdShowCaback!=null){
                                        onAdShowCaback.onFinshTask(loadAppPackage,loadAppName,"1");
                                        Log.d("ccc", "-----穿山甲----onDownloadActive: "+loadAppPackage+"----"+loadAppName);
                                    }
                                }else {
                                    if (onAdShowCaback!=null){
                                        onAdShowCaback.onNoTask();
                                    }
                                }
                            }else {
                                if (onAdShowCaback!=null){
                                    onAdShowCaback.onNoTask();
                                }
                            }
                            mttRewardVideoAd=null;
                            if (csjCount<2){
                                csjCount++;
                                loadRewardVideoAd();
                            }
                        }
                    }

                    @Override
                    public void onVideoComplete() {   //视频广告播放完毕回调
                        if (onAdShowCaback!=null){
                            onAdShowCaback.onVideoComplete();
                        }
                    }

                    @Override
                    public void onVideoError() {
                        Log.d("ccc", "------播放错误-----------onVideoError: ");
                        if ("1".equals(isTxLoadAdSuccess)){
                            setIndex(1);
                        }
                    }

                    @Override
                    public void onRewardVerify(boolean b, int i, String s, int i1, String s1) {
                        if (onAdShowCaback!=null){
                            onAdShowCaback.onVideoComplete();
                        }
                    }

                    @Override
                    public void onRewardArrived(boolean b, int i, Bundle bundle) {

                    }

                    @Override
                    public void onSkippedVideo() {  //跳过

                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        Log.d("ccc", "---穿山甲------onIdle: "+"----");
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        if (type==2){
                            if (!TextUtils.isEmpty(fileName)){
                                fileNames=fileName;
                            }
                            if (!TextUtils.isEmpty(appName)){
                                loadAppName=appName;
                            }
                            csjStatus=1;
                            isLoad=true;
                            Log.d("ccc", "-----穿山甲----onDownloadActive: "+fileName+"----"+appName+"-----:"+totalBytes+"----:"+currBytes);
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("ccc", "----穿山甲-----onDownloadPaused: "+fileName+"----"+appName+"-----:"+totalBytes+"----:"+currBytes);
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("ccc", "----穿山甲-----onDownloadFailed: "+fileName+"----"+appName+"-----:"+totalBytes+"----:"+currBytes);
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Log.d("ccc", "----穿山甲-----onDownloadFinished: "+fileName+"----"+appName+"-----:"+totalBytes+"----:");
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        if (type==2){
                            if (!TextUtils.isEmpty(fileName)){
                                fileNames=fileName;
                            }
                            if (!TextUtils.isEmpty(appName)){
                                loadAppName=appName;
                            }
                            csjStatus=2;
                            Log.d("ccc", "-----穿山甲----onInstalled: "+fileName+"----"+appName);
                        }
                    }
                });
            }
        });
    }
//==============end========穿山甲激励视频=======================================================================================


//==============start========腾讯激励视频=======================================================================================
private String txApkUrl;
    public void showTx(){
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(isTxLoadAdSuccess)){
                //暂无可用激励视频广告，请等待缓存加载或者重新刷新
                setIndex(2);
            }
        }else {

            if (!mRewardVideoAD.hasShown()){
                if (mRewardVideoAD.isValid()){
                    if (CommonUtils.isDestory(mContext)){
                        return;
                    }
                    mRewardVideoAD
                            .showAD(mContext);
                    // 展示广告
                }else {//已过期
                    loadTxTwo();
                    if ("1".equals(isTxLoadAdSuccess)){
                        //暂无可用激励视频广告，请等待缓存加载或者重新刷新
                        setIndex(2);
                    }
                }
            }else {//已展示过
                loadTxTwo();
                if ("1".equals(isTxLoadAdSuccess)){
                    //暂无可用激励视频广告，请等待缓存加载或者重新刷新
                    setIndex(2);
                }
            }
        }
    }
    public void loadTxTwo(){
        mIsLoaded=false;
        loadTx();
    }
    private int txStatus;
    private RewardVideoAD mRewardVideoAD;
    private boolean mIsLoaded;
    public void loadTx(){
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        txApkUrl="";
        String codes=Constant.TXRVIDEO;
        if (type==3){
            codes=Constant.HOTXJRVIDEO;
        }else {
            codes=Constant.TXRVIDEO;
        }
        Log.d("ccc", "---------------loadTx: "+codes);
        mRewardVideoAD=new RewardVideoAD(mContext, codes, new RewardVideoADListener() {
            @Override
            public void onADLoad() {
                mIsLoaded = true;
            }

            @Override
            public void onVideoCached() {

            }

            @Override
            public void onADShow() {
                Map<String, Object> extraInfo = mRewardVideoAD.getExtraInfo();
                Log.d("ccc", "----腾讯---extraInfo--onADShow: "+extraInfo+"---"+extraInfo.toString());
                
                
                
                showIndex=2;
                if (type==1||type==3){
                    AppSettingUtils.showTxShow("ad_jili", Constant.TXRVIDEO);
                    isTxLoadAdSuccess="3";
                    if (onAdShowCaback!=null){
                        onAdShowCaback.onRewardedAdShow();
                    }
                    LogUtils.showAdLog("--------腾讯激励视频:show--->" +mRewardVideoAD.getApkInfoUrl());
                }else {
                    AppSettingUtils.showTxShow("ad_jili", Constant.TXRVIDEO);
                    isTxLoadAdSuccess="3";
                    if (onAdShowCaback!=null){
                        onAdShowCaback.onRewardedAdShow();
                    }
                    String apkInfoUrl = mRewardVideoAD.getApkInfoUrl();
                    txStatus=0;
                    if (!TextUtils.isEmpty(apkInfoUrl)){
                        txApkUrl=mRewardVideoAD.getApkInfoUrl();
                        String appPack = parseApk(txApkUrl);
                        boolean installApp = CommonUtils.isInstallApp(appPack);
                        if (installApp){
                            txStatus=1;
                        }else {
                            txStatus=2;
                        }
                    }
                }
            }

            @Override
            public void onADExpose() {

            }

            @Override
            public void onReward(Map<String, Object> map) {
                if (onAdShowCaback!=null){
                    onAdShowCaback.onVideoComplete();
                }
            }

            @Override
            public void onADClick() {
                isVideoClick=true;
                LogUtils.showAdLog("--------腾讯激励视频:onADClick--->" +mRewardVideoAD.getApkInfoUrl());
                AppSettingUtils.showTxClick("ad_jili", Constant.TXRVIDEO);
            }

            @Override
            public void onVideoComplete() {
                if (onAdShowCaback!=null){
                    onAdShowCaback.onVideoComplete();
                }
            }

            @Override
            public void onADClose() {
                showIndex=0;
                if (type==1||type==3){
                    if (mRewardVideoAD.hasShown()){
                        loadTxTwo();
                    }
                    if (onAdShowCaback!=null){
                        onAdShowCaback.onRewardedAdClosed(isVideoClick,true);
                    }
                }else {
                    isVideoClick=false;
                    if (txStatus==2){
                        if (!TextUtils.isEmpty(txApkUrl)){
                            String appPack = parseApk(txApkUrl);
                            if (!TextUtils.isEmpty(appPack)){
                                boolean installApp = CommonUtils.isInstallApp(appPack);
                                if (installApp){
                                    if (onAdShowCaback!=null){
                                        PackageInfo info = null;
                                        PackageManager pm = mContext.getPackageManager();
                                        try {
                                            info = pm.getPackageInfo(appPack,PackageManager.GET_ACTIVITIES);
                                        } catch (PackageManager.NameNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        if (info!=null){
                                            String appName = info.applicationInfo.loadLabel(pm).toString();
                                            onAdShowCaback.onFinshTask(appPack,appName,"2");
                                        }
                                    }
                                }else {
                                    if (onAdShowCaback!=null){
                                        onAdShowCaback.onNoTask();
                                    }
                                }
                            }else {
                                if (onAdShowCaback!=null){
                                    onAdShowCaback.onNoTask();
                                }
                            }
                        }else {
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onNoTask();
                            }
                        }
                    }else {
                        if (onAdShowCaback!=null){
                            onAdShowCaback.onNoTask();
                        }
                    }
                    if (mRewardVideoAD.hasShown()){
                        loadTxTwo();
                    }
                    if (onAdShowCaback!=null){
                        onAdShowCaback.onRewardedAdClosed(true,true);
                    }
                }

            }

            @Override
            public void onError(AdError adError) {
                LogUtils.showAdLog("---tx-----onError-----激励视频:"+adError.getErrorMsg());
                if ("1".equals(isTxLoadAdSuccess)){
                    if (CommonUtils.isDestory(mContext)){
                        return;
                    }
                    //失败了播放腾讯的
                    setIndex(2);
                }
            }
        });
        ServerSideVerificationOptions.Builder builder=new ServerSideVerificationOptions.Builder();
        builder.setUserId(userId);
        builder.setCustomData("APPscustomdata");
        ServerSideVerificationOptions options =builder.build();
        mRewardVideoAD.setServerSideVerificationOptions(options);
        mRewardVideoAD.setDownloadConfirmListener(new DownloadConfirmListener() {
            @Override
            public void onDownloadConfirm(Activity activity, int i, String s, DownloadConfirmCallBack downloadConfirmCallBack) {
                LogUtils.showAdLog("--------腾讯激励视频:onADClick--->" +mRewardVideoAD.getApkInfoUrl());
            }
        });

        // 设置播放时静音状态
        // mRewardVideoAD.setVolumeOn(volumeOn);
        // 拉取广告
        mRewardVideoAD.loadAD();
        // 展示广告
    }

    public String parseApk(String txApkUrl){
        String appPack ="";
        int s = txApkUrl.indexOf("pkgName=");
        String substring = txApkUrl.substring(s+8, txApkUrl.length());
        Log.d("ccc", "-----腾讯2-----------------txApkUrl: "+substring);
        if (!TextUtils.isEmpty(substring)){
            if (substring.contains("&")){
                int i = substring.indexOf("&");
                appPack = substring.substring(0, i);
                Log.d("ccc", "-----腾讯3-----------------txApkUrl: "+appPack);
            }else {
                appPack=substring;
                Log.d("ccc", "-----腾讯4-----------------txApkUrl: "+appPack);
            }
        }
        if (CommonUtils.isInstallApp(appPack)){
            return appPack;
        }else {
            return "";
        }
    }
    //=================end===================腾讯激励视频=====================================================================================

    //=================start===================快手激励视频=====================================================================================
    private KsRewardVideoAd mRewardVideoAd;
    private void loadKSRewardVideoAd() {
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        try {
            if (!TextUtils.isEmpty(absolutePath2)){
                DeleteFileUtil.deleteDirectory(absolutePath2);
            }
        }catch (Exception e){
            Log.d("ccc", "---快手000------onPageDismiss: "+e.getMessage());
        }

        String codes=Constant.KSRVIDEO;
        if (type==3){
            codes=Constant.HOKSJRVIDEO;
        }else {
            codes=Constant.KSRVIDEO;
        }
        Log.d("ccc", "--codes--------loadKSRewardVideoAd: "+codes);
        if (TextUtils.isEmpty(codes)){
            return;
        }
        long aLong = Long.parseLong(codes);
        KsScene.Builder builder = new KsScene.Builder(aLong)
                .screenOrientation(1);
        // 激励视频服务端回调的参数设置
        Map<String, String> rewardCallbackExtraData = new HashMap<>();
// 开发者系统中的⽤户id，会在请求客户的回调url中带上
        rewardCallbackExtraData.put("thirdUserId", userId);
// 开发者⾃定义的附加参数，会在请求客户的回调url中带上
        builder.rewardCallbackExtraData(rewardCallbackExtraData);
        KsScene scene = builder.build();
        KsLoadManager loadManager = KsAdSDK.getLoadManager();
        if (loadManager!=null&&scene!=null){
            loadManager.loadRewardVideoAd(scene, new KsLoadManager.RewardVideoAdListener() {
                @Override
                public void onError(int code, String msg) {
                    LogUtils.showAdLog("---ks-----激励视频-----:"+msg);
                    if ("1".equals(isTxLoadAdSuccess)){
                        setIndex(3);
                    }
                }

                @Override
                public void onRequestResult(int adNumber) {

                }

                @Override
                public void onRewardVideoAdLoad(  List<KsRewardVideoAd> adList) {
                    LogUtils.showAdLog("---ks-----激励视频-----onRewardVideoAdLoad:");
                    if (CommonUtils.isDestory(mContext)){
                        return;
                    }
                    if (adList != null && adList.size() > 0) {
                        LogUtils.showAdLog("---ks--2---激励视频-----onRewardVideoAdLoad:");
                        mRewardVideoAd = adList.get(0);
                        if ("1".equals(isTxLoadAdSuccess)){
                            showRewardVideoAd(null);//竖屏
                        }
                    } else {
                        LogUtils.showAdLog("---ks-3--激励视频-----onRewardVideoAdLoad:");
                        if ("1".equals(isTxLoadAdSuccess)){
                            //暂无可用激励视频广告，请等待缓存加载或者重新刷新
                            setIndex(3);
                        }
                        //没有广告
                    }
                }
            });
        }
    }
    private void showRewardVideoAd(KsVideoPlayConfig videoPlayConfig) {
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        if (mRewardVideoAd != null && mRewardVideoAd.isAdEnable()) {
            mRewardVideoAd
                    .setRewardAdInteractionListener(new KsRewardVideoAd.RewardAdInteractionListener() {
                        @Override
                        public void onAdClicked() {
                            isVideoClick=true;
                            AppSettingUtils.showTxClick("ad_jili", Constant.KSRVIDEO);
                        }

                        @Override
                        public void onPageDismiss() {
                            isTxLoadAdSuccess="0";
                            showIndex=0;
                            if (type==1||type==3){
                                if (onAdShowCaback!=null){
                                    onAdShowCaback.onRewardedAdClosed(isVideoClick,true);
                                }
                            }else {
                                isVideoClick=false;
                                String save = DeleteFileUtil.isSave(absolutePath2);
                                if (!TextUtils.isEmpty(save)){
                                    String s=absolutePath2+"/"+save;
                                    try {
                                        PackageManager pm = mContext.getPackageManager();
                                        PackageInfo info = pm.getPackageArchiveInfo(s, PackageManager.GET_ACTIVITIES);
                                        if(info != null){
                                            ApplicationInfo appInfo = info.applicationInfo;
                                            String appName = pm.getApplicationLabel(appInfo).toString();
                                            String packageName = appInfo.packageName;  //获取安装包名称
                                            if (onAdShowCaback!=null){
                                                onAdShowCaback.onFinshTask(packageName,appName,"3");
                                            }
                                        }else {
                                            if (onAdShowCaback!=null){
                                                onAdShowCaback.onNoTask();
                                            }
                                        }
                                    }catch (Exception e){
                                        if (onAdShowCaback!=null){
                                            onAdShowCaback.onNoTask();
                                        }
                                    }
                                }else {
                                    if (onAdShowCaback!=null){
                                        onAdShowCaback.onNoTask();
                                    }
                                }
                                if (onAdShowCaback!=null){
                                    onAdShowCaback.onRewardedAdClosed(true,true);
                                }
                            }
                        }

                        @Override
                        public void onVideoPlayError(int code, int extra) {

                        }

                        @Override
                        public void onVideoPlayEnd() {
                        }

                        @Override
                        public void onVideoSkipToEnd(long l) {

                        }

                        @Override
                        public void onVideoPlayStart() {
                            showIndex=3;
                            isTxLoadAdSuccess="3";
                            AppSettingUtils.showTxShow("ad_jili", Constant.KSRVIDEO);
                            LogUtils.showAdLog("--------快手激励视频:show");
                        }

                        @Override
                        public void onRewardVerify() {
                            if (onAdShowCaback!=null){
                                onAdShowCaback.onVideoComplete();
                            }
                        }

                        @Override
                        public void onRewardStepVerify(int i, int i1) {

                        }
                    });

            if (CommonUtils.isDestory(mContext)){
                return;
            }
            mRewardVideoAd.showRewardVideoAd((Activity) mContext, videoPlayConfig);
        } else {
            LogUtils.showAdLog("---ks-5--激励视频-----onRewardVideoAdLoad:");
            if ("1".equals(isTxLoadAdSuccess)){
                //暂无可用激励视频广告，请等待缓存加载或者重新刷新
                setIndex(3);
            }
        }
    }

    //=================end===================快手激励视频=====================================================================================


    private OnAdShowCaback onAdShowCaback;
    public interface OnAdShowCaback{
        void onRewardedAdShow();
        void onRewardedAdShowFail();
        void onRewardClick();
        void onVideoComplete();
        void setVideoCallBacks();
        void onRewardedAdClosed(boolean isVideoClick, boolean isCompeter);
        void onFinshTask(String appPackage, String appName, String type);
        void onNoTask();
    }


}
