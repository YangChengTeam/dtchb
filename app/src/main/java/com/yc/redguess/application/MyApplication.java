package com.yc.redguess.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.google.gson.Gson;
import com.kk.share.UMShareImpl;
import com.lq.lianjibusiness.base_libary.App.App;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.redguess.di.component.AppComponent;
import com.yc.redguess.di.component.DaggerAppComponent;
import com.yc.redguess.di.module.AppModule;
import com.yc.redguess.homeModule.module.bean.ChannelInfo;
import com.yc.redguess.utils.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import androidx.multidex.MultiDex;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;



/**
 * Created by ccc on 2020/9/15.
 */

public class MyApplication extends App {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //bugly异常上报
        CrashReport.initCrashReport(getApplicationContext(), "39ecb2d279", true);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.

        //  Glide.get(getApplicationContext()).setMemoryCategory(MemoryCategory.LOW);
        MMKV.initialize(this);
        //切换至商业版服务
        //  HeConfig.switchToBizService();
        // 渠道  主要是获取agentId
        Observable.just("").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> init());


    }

    private void init() {
        initChannel();
        initUM();
        adVideo();
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
            ChannelInfo channelInfo = new Gson().fromJson(result, ChannelInfo.class);
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
    }
    private AdPlatformSDK.InitCallback initCallback;

    public void setInitCallback(AdPlatformSDK.InitCallback initCallback) {
        this.initCallback = initCallback;
    }
    public  void  adVideo(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.init(this, "1", new AdPlatformSDK.InitCallback() {
            @Override
            public void onAdInitSuccess() {
                if (initCallback != null) {
                    initCallback.onAdInitSuccess();
                }
            }

            @Override
            public void onAdInitFailure() {
                if (initCallback != null) {
                    initCallback.onAdInitFailure();
                }
            }
        });
    }

    private void initUM() {
        // 选用合适的页面采集模式，这里以LEGACY_MANUAL为例
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_MANUAL);
        // 支持在子进程中统计自定义事件
        UMConfigure.setProcessEvent(true);

        PlatformConfig.setQQZone("101931689", "Edkrj0yNUcusdoGa");
        PlatformConfig.setQQFileProvider("com.yc.gamebox.controller.providers.ImageFileProvider");

        UMConfigure.init(this, "5fbdc603690bda19c78aa8d5", agentId, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMConfigure.setLogEnabled(true);
        UMShareAPI.get(getApplicationContext()).setShareConfig(config);


        //初始化友盟SDK
        UMShareAPI.get(this); //初始化sdk
        UMShareImpl.Builder builder = new UMShareImpl.Builder();
        builder.setWeixin("wxcbd79b049fcdefb3", "879a7c9b951b24b74ebf312f65489c73")
                .build(this);
    }


    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }

}
