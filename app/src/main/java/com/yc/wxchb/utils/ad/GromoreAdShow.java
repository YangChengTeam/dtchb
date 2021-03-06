package com.yc.wxchb.utils.ad;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import com.qq.e.comm.util.VideoAdValidity;
import com.yc.wxchb.application.Constant;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.utils.AppSettingUtils;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.DeleteFileUtil;
import com.yc.wxchb.utils.LogUtils;

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
    public void setContextsInit(Context contexts){
       this.mContext = (Activity) contexts;
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        if (userInfo!=null){
            userId=userInfo.getId()+"";
            ad_pb= userInfo.getAd_pb();
        }
        loadRewardVideoAd();
        loadTx();
    }
    private int index;//
    private int downIndex;//
    private String indexType;//???????????????????????? 1 ?????????  2??????  3??????
    private boolean loadSuccess;
    private boolean isComplete;
    private boolean isVideoClick;
    private String ad_positions;
    private int type;
    private String absolutePath2;
    private String isTxLoadAdSuccess="0";//0 ????????????  1???????????????  2?????????????????????  3?????????????????????


    public void showjiliAd(Context context,int types,String positions,OnAdShowCaback onAdShowCaback){
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
            ToastUtil.showToast("?????????????????????????????????????????????????????????????????????");
            return;
        }
        isComplete=false;
        isVideoClick=false;
        isTxLoadAdSuccess="1";

        if (type==1){
            List<String> adType = CacheDataUtils.getInstance().getAdType();
            if (adType.size()==0){
                adType.add("1");
                adType.add("2");
                adType.add("3");
            }
            if (index<adType.size()){
                indexType = adType.get(index);
                if (adType.size()>0){
                    if ("1".equals(indexType)){//???????????????
                        LogUtils.showAdLog("---------?????????-----????????????:");
                        showCSJRewardVideoAd();
                    }else if ("2".equals(indexType)){//????????????
                        LogUtils.showAdLog("----------??????-----????????????:");
                        showTx();
                    }else if ("3".equals(indexType)){//????????????
                        LogUtils.showAdLog("--------??????-----????????????:");
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
                indexType = adType.get(index);
                if ("1".equals(indexType)){//???????????????
                    LogUtils.showAdLog("---------?????????-----????????????:");
                    showCSJRewardVideoAd();
                }else if ("2".equals(indexType)){//????????????
                    LogUtils.showAdLog("----------??????-----????????????:");
                    showTx();
                }else if ("3".equals(indexType)){//????????????
                    LogUtils.showAdLog("--------??????-----????????????:");
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
            List<String> adTypes = CacheDataUtils.getInstance().getDownAdType();
            if (adTypes.size()==0){
                adTypes.add("1");
                adTypes.add("2");
                adTypes.add("3");
            }
            if (downIndex<adTypes.size()){
                indexType = adTypes.get(downIndex);
                if ("1".equals(indexType)){//???????????????
                    LogUtils.showAdLog("---------?????????-----????????????:");
                    showCSJRewardVideoAd();
                }else if ("2".equals(indexType)){//????????????
                    LogUtils.showAdLog("----------??????-----????????????:");
                    showTx();
                }else if ("3".equals(indexType)){//????????????
                    LogUtils.showAdLog("--------??????-----????????????:");
                    loadKSRewardVideoAd();
                }else {
                    showCSJRewardVideoAd();
                }
                downIndex++;
            }else {
                downIndex=0;
                indexType = adTypes.get(downIndex);
                if ("1".equals(indexType)){//???????????????
                    showCSJRewardVideoAd();
                }else if ("2".equals(indexType)){//????????????
                    showTx();
                }else if ("3".equals(indexType)){//????????????
                    loadKSRewardVideoAd();
                }else {
                    showCSJRewardVideoAd();
                }
            }
        }
    }


    public void setIndex(int type){
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        if ("1".equals(indexType)){//???????????????
            if (type==1){
                showTx();//?????????
            }else if (type==2){
                loadKSRewardVideoAd();//?????????
            }else {
                ToastUtil.showToast("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????,??????????????????APP???");
            }
        }else if ("2".equals(indexType)){//????????????
            if (type==2){
                showCSJRewardVideoAd();//????????????
            }else if (type==1){
                loadKSRewardVideoAd();//?????????
            }else {
                ToastUtil.showToast("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????,??????????????????APP???");
            }
        }else if ("3".equals(indexType)){//????????????
            if (type==3){
                showCSJRewardVideoAd();//????????????
            }else if (type==1){
                showTx();//?????????
            }else {
                ToastUtil.showToast("??????????????????????????????????????????????????????????????????????????????????????????????????????????????????,??????????????????APP???");
            }
        }
    }



//==============start========?????????????????????=======================================================================================
   public void showCSJRewardVideoAd() {
       csjStatus=0;
       isLoad=false;
       LogUtils.showAdLog("------2---?????????-----????????????:");
       if (CommonUtils.isDestory(mContext)){
           return;
       }
       LogUtils.showAdLog("------2---?????????-----????????????:"+(mttRewardVideoAd==null)+"---"+(mContext==null));
       if (mttRewardVideoAd != null && mContext != null) {
           LogUtils.showAdLog("------2---?????????-----????????????:"+(mttRewardVideoAd==null)+"---"+(mContext==null)+"--");
        //step6:???????????????????????????
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
    private int showIndex;//  0????????????  1?????????  2 ??????  ??????
    /**
     * ????????????????????????
     */
    private String fileNames;
    private String loadAppPackage;
    private String loadAppName;
    private int csjStatus;//0??????????????????????????????  1????????????   2????????????
    private boolean isLoad;//0???????????????  1?????????
    /**
     * ????????????????????????
     */
    private void loadRewardVideoAd() {
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        //?????????????????????????????????????????????view?????????????????????dp???
        String codes= Constant.RVIDEO;
        if (type==3){
            codes=Constant.HOTCSJRVIDEO;
        }else {
            codes=Constant.RVIDEO;
        }
        Log.d("ccc", "--codes--------loadRewardVideoAd: "+codes);
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(codes)
                .setSupportDeepLink(true)
                //????????????????????????????????????????????????????????????,??????dp,?????????????????????????????????????????????0??????
                .setExpressViewAcceptedSize(500, 500)
                .setUserID(userId)//??????id,????????????
                .setUserData(userId)
                .setMediaExtra("media_extra") //?????????????????????
                .setOrientation(TTAdConstant.VERTICAL) //?????????????????????????????????????????????TTAdConstant.HORIZONTAL ??? TTAdConstant.VERTICAL
                .build();
        TTAdManagerHolder.get().createAdNative(mContext).loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                mttRewardVideoAd=null;
                if (csjCount<2){
                    csjCount++;
                    loadRewardVideoAd();
                }
            }

            //??????????????????????????????????????????????????????????????????
            @Override
            public void onRewardVideoCached() {
                csjCount=0;
            }

            @Override
            public void onRewardVideoCached(TTRewardVideoAd ttRewardVideoAd) {
                csjCount=0;
            }

            //?????????????????????????????????title,??????url???????????????????????????
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
                        LogUtils.showAdLog("--------?????????????????????:show");
                    }

                    @Override
                    public void onAdVideoBarClick() { ////???????????????bar????????????
                        isVideoClick=true;
                        Log.d("ccc", "---?????????--????????????----onAdVideoBarClick: ");
                        AppSettingUtils.showTxClick(ad_positions, Constant.RVIDEO);
                    }

                    @Override
                    public void onAdClose() { //????????????????????????
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
                                        Log.d("ccc", "-----?????????----onDownloadActive: "+loadAppPackage+"----"+loadAppName);
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
                    public void onVideoComplete() {   //??????????????????????????????
                        if (onAdShowCaback!=null){
                            onAdShowCaback.onVideoComplete();
                        }
                    }

                    @Override
                    public void onVideoError() {
                        Log.d("ccc", "------????????????-----------onVideoError: ");
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
                    public void onSkippedVideo() {  //??????

                    }
                });
                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        Log.d("ccc", "---?????????------onIdle: "+"----");
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
                            Log.d("ccc", "-----?????????----onDownloadActive: "+fileName+"----"+appName+"-----:"+totalBytes+"----:"+currBytes);
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("ccc", "----?????????-----onDownloadPaused: "+fileName+"----"+appName+"-----:"+totalBytes+"----:"+currBytes);
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("ccc", "----?????????-----onDownloadFailed: "+fileName+"----"+appName+"-----:"+totalBytes+"----:"+currBytes);
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Log.d("ccc", "----?????????-----onDownloadFinished: "+fileName+"----"+appName+"-----:"+totalBytes+"----:");
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
                            Log.d("ccc", "-----?????????----onInstalled: "+fileName+"----"+appName);
                        }
                    }
                });
            }
        });
    }
//==============end========?????????????????????=======================================================================================


//==============start========??????????????????=======================================================================================
private String txApkUrl;
    public void showTx(){
        if (CommonUtils.isDestory(mContext)){
            return;
        }
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("????????????????????????");
            loadTxTwo();
            if ("1".equals(isTxLoadAdSuccess)){
                //????????????????????????????????????????????????????????????????????????
                setIndex(2);
            }
        }else {
            VideoAdValidity validity = mRewardVideoAD.checkValidity();
            switch (validity) {
                case SHOWED:
                case OVERDUE:
                    loadTxTwo();
                    if ("1".equals(isTxLoadAdSuccess)){
                        //????????????????????????????????????????????????????????????????????????
                        setIndex(2);
                    }
                    return;
                // ????????????????????????????????????????????????????????????????????????????????????
                case NONE_CACHE:
                    //  showToast("??????????????????????????????");
//            return;
                case VALID:
                    // ????????????????????????????????????????????????????????????????????????????????????
                    if (CommonUtils.isDestory(mContext)){
                        return;
                    }
                    mRewardVideoAD
                            .showAD(mContext);
                    // ????????????
                    break;
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
        Log.d("ccc", "--codes--------loadTx: "+codes);
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
                showIndex=2;
                if (type==1||type==3){
                    AppSettingUtils.showTxShow("ad_jili", Constant.TXRVIDEO);
                    isTxLoadAdSuccess="3";
                    if (onAdShowCaback!=null){
                        onAdShowCaback.onRewardedAdShow();
                    }
                    LogUtils.showAdLog("--------??????????????????:show--->" +mRewardVideoAD.getApkInfoUrl());
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
                LogUtils.showAdLog("--------??????????????????:onADClick--->" +mRewardVideoAD.getApkInfoUrl());
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
                LogUtils.showAdLog("---tx-----onError-----????????????:"+adError.getErrorMsg());
                if ("1".equals(isTxLoadAdSuccess)){
                    if (CommonUtils.isDestory(mContext)){
                        return;
                    }
                    loadTxTwo();
                    //????????????????????????
                    setIndex(2);
                }
            }
        });
        ServerSideVerificationOptions.Builder builder=new ServerSideVerificationOptions.Builder();
        builder.setUserId(userId);
        ServerSideVerificationOptions options =builder.build();
        mRewardVideoAD.setServerSideVerificationOptions(options);
        mRewardVideoAD.setDownloadConfirmListener(new DownloadConfirmListener() {
            @Override
            public void onDownloadConfirm(Activity activity, int i, String s, DownloadConfirmCallBack downloadConfirmCallBack) {
                LogUtils.showAdLog("--------??????????????????:onADClick--->" +mRewardVideoAD.getApkInfoUrl());
            }
        });

        // ???????????????????????????
        // mRewardVideoAD.setVolumeOn(volumeOn);
        // ????????????
        mRewardVideoAD.loadAD();
        // ????????????
    }

    public String parseApk(String txApkUrl){
        String appPack ="";
        int s = txApkUrl.indexOf("pkgName=");
        String substring = txApkUrl.substring(s+8, txApkUrl.length());
        Log.d("ccc", "-----??????2-----------------txApkUrl: "+substring);
        if (!TextUtils.isEmpty(substring)){
            if (substring.contains("&")){
                int i = substring.indexOf("&");
                appPack = substring.substring(0, i);
                Log.d("ccc", "-----??????3-----------------txApkUrl: "+appPack);
            }else {
                appPack=substring;
                Log.d("ccc", "-----??????4-----------------txApkUrl: "+appPack);
            }
        }
        if (CommonUtils.isInstallApp(appPack)){
            return appPack;
        }else {
            return "";
        }
    }
    //=================end===================??????????????????=====================================================================================

    //=================start===================??????????????????=====================================================================================
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
            Log.d("ccc", "---??????000------onPageDismiss: "+e.getMessage());
        }

        String codes=Constant.KSRVIDEO;
        if (type==3){
            codes=Constant.HOKSJRVIDEO;
        }else {
            codes=Constant.KSRVIDEO;
        }
        Log.d("ccc", "--codes--------loadKSRewardVideoAd: "+codes);
        long aLong = Long.parseLong(codes);
        KsScene.Builder builder = new KsScene.Builder(aLong)
                .screenOrientation(1);
        // ??????????????????????????????????????????
        Map<String, String> rewardCallbackExtraData = new HashMap<>();
// ???????????????????????????id??????????????????????????????url?????????
        rewardCallbackExtraData.put("thirdUserId", userId);
// ???????????????????????????????????????????????????????????????url?????????
        builder.rewardCallbackExtraData(rewardCallbackExtraData);
        KsScene scene = builder.build();
        KsLoadManager loadManager = KsAdSDK.getLoadManager();
        if (loadManager!=null&&scene!=null){
            loadManager.loadRewardVideoAd(scene, new KsLoadManager.RewardVideoAdListener() {
                @Override
                public void onError(int code, String msg) {
                    LogUtils.showAdLog("---ks-----????????????-----:"+msg);
                    if ("1".equals(isTxLoadAdSuccess)){
                        setIndex(3);
                    }
                }

                @Override
                public void onRequestResult(int adNumber) {

                }

                @Override
                public void onRewardVideoAdLoad(  List<KsRewardVideoAd> adList) {
                    LogUtils.showAdLog("---ks-----????????????-----onRewardVideoAdLoad:");
                    if (CommonUtils.isDestory(mContext)){
                        return;
                    }
                    if (adList != null && adList.size() > 0) {
                        LogUtils.showAdLog("---ks--2---????????????-----onRewardVideoAdLoad:");
                        mRewardVideoAd = adList.get(0);
                        if ("1".equals(isTxLoadAdSuccess)){
                            showRewardVideoAd(null);//??????
                        }
                    } else {
                        LogUtils.showAdLog("---ks-3--????????????-----onRewardVideoAdLoad:");
                        if ("1".equals(isTxLoadAdSuccess)){
                            //????????????????????????????????????????????????????????????????????????
                            setIndex(3);
                        }
                        //????????????
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
                                            String packageName = appInfo.packageName;  //?????????????????????
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
                            LogUtils.showAdLog("--------??????????????????:show");
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
            LogUtils.showAdLog("---ks-5--????????????-----onRewardVideoAdLoad:");
            if ("1".equals(isTxLoadAdSuccess)){
                //????????????????????????????????????????????????????????????????????????
                setIndex(3);
            }
        }
    }

    //=================end===================??????????????????=====================================================================================


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
