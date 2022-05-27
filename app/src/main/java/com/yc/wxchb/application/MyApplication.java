package com.yc.wxchb.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import android.util.Log;

import com.alex.voice.SPlayer;
import com.bytedance.applog.AppLog;
import com.bytedance.applog.InitConfig;
import com.bytedance.applog.util.UriConstants;
import com.bytedance.hume.readapk.HumeSDK;
import com.google.gson.Gson;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.SdkConfig;
import com.lq.lianjibusiness.base_libary.App.App;
import com.qq.e.comm.managers.GDTAdSdk;
import com.qq.e.comm.util.AdError;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;
import com.umeng.commonsdk.UMConfigure;
import com.yc.wxchb.beans.activity.SplashActivity;
import com.yc.wxchb.beans.module.beans.ChannelInfozq;
import com.yc.wxchb.constants.Constant;
import com.yc.wxchb.di.component.AppComponent;
import com.yc.wxchb.di.component.DaggerAppComponent;
import com.yc.wxchb.di.module.AppModule;
import com.yc.wxchb.utils.CardUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.FileUtil;
import com.yc.wxchb.utils.ad.InitAdCallback;
import com.yc.wxchb.utils.ad.TTAdManagerHolder;
import com.yc.wxchb.utils.adgromore.GMAdManagerHolder;
import com.yc.wxchb.utils.video.DPHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;



/**
 * Created by ccc on 2020/9/15.
 */

public class MyApplication extends App {
    public String cash;//余额
    public int answerNums;//答题
    public int hb_Nums;//倒计时红包总数
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //bugly异常上报
        CrashReport.initCrashReport(getApplicationContext(), "1c63f8442a", true);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.

        //  Glide.get(getApplicationContext()).setMemoryCategory(MemoryCategory.LOW);
        MMKV.initialize(this);
        Observable.just("").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> init());

    }

    private void init() {
        initChannel();
        CardUtils.init();
        SPlayer.init(this);
        initUM();

        GDTAdSdk.init(this,"1200469358");
        // 通过调用此方法初始化 SDK。如果需要在多个进程拉取广告，每个进程都需要初始化 SDK。

        KsAdSDK.init(this, new SdkConfig.Builder()
                .appId("786400008")
                .appName("答题拆红包")
                .showNotification(true) // 是否展示下载通知栏
                .debug(false) // 是否开启sdk 调试⽇志 可选
                .build());

        GMAdManagerHolder.init(this);

        TTAdManagerHolder.init(this, "5273285", Constant.APPNAME, false, new InitAdCallback() {
            @Override
            public void onSuccess() {
                initDp();
            }

            @Override
            public void onFailure(AdError adError) {
                initDp();
            }
        });
    }

    public void initDp(){

        /* 初始化开始 */
        final InitConfig config = new InitConfig("359297", agentId); // appid和渠道，appid须保证与广告后台申请记录一致，渠道可自定义，如有多个马甲包建议设置渠道号唯一标识一个马甲包。
        //上报域名可根据业务情况自己设置上报域名，国内版本只支持上报到DEFAULT，海外GDRP版本只支持SINGAPORE、AMERICA
        /* 国内: DEFAULT */
        config.setUriConfig (UriConstants.DEFAULT);
        config.setEnablePlay(true); // 是否开启游戏模式，游戏APP建议设置为 true
        config.setAbEnable(true); // 是否开启A/B Test功能
        config.setAutoStart(true);
        AppLog.init(this, config);
        /* 初始化结束 */
              /* 自定义 “用户公共属性”（可选，初始化后调用, key相同会覆盖）
              关于自定义 “用户公共属性” 请注意：1. 上报机制是随着每一次日志发送进行提交，默认的日志发送频率是1分钟，所以如果在一分钟内连续修改自定义用户公共属性，，按照日志发送前的最后一次修改为准， 2. 不推荐高频次修改，如每秒修改一次 */
        Map headerMap = new HashMap();
        headerMap.put("level",8);
        headerMap.put("gender","female");
        AppLog.setHeaderInfo((HashMap)headerMap);


        DPHolder.getInstance().init(this);
    }

    public String getAgentId() {
        return agentId;
    }

    public String agentId = "1";

    private void initChannel() {
        ApplicationInfo appinfo = getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zf = null;
        try {
            zf = new ZipFile(sourceDir);
            ZipEntry ze = zf.getEntry("META-INF/gamechannel.json");
            InputStream in = zf.getInputStream(ze);
            String result = FileUtil.readString(in);
            ChannelInfozq channelInfo = new Gson().fromJson(result, ChannelInfozq.class);
            agentId = channelInfo.agent_id;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zf != null) {
                try {
                    zf.close();
                } catch (IOException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            }
        }

        //
//        //读取V2签名的渠道
        try {
            String channel = CommonUtils.readAssetsChannel(this);
            if (!TextUtils.isEmpty(channel)) {
                ChannelInfozq channelInfo = new Gson().fromJson(channel, ChannelInfozq.class);
                agentId = channelInfo.agent_id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //获取渠道名称 context为宿主的context
        String channel = HumeSDK.getChannel(this);
        if (!TextUtils.isEmpty(channel)) {
            agentId=channel;
        }
    }


    private void initUM() {

        UMConfigure.preInit(this,"622ffedd317aa87760998865",agentId);
    }




    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }

}
