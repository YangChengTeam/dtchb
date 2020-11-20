package com.yc.redevenlopes.application;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.google.gson.Gson;
import com.lq.lianjibusiness.base_libary.App.App;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdConfigInfo;
import com.yc.redevenlopes.di.component.AppComponent;
import com.yc.redevenlopes.di.component.DaggerAppComponent;
import com.yc.redevenlopes.di.module.AppModule;
import com.yc.redevenlopes.homeModule.module.bean.ChannelInfo;
import com.yc.redevenlopes.utils.FileUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import androidx.multidex.MultiDex;


/**
 * Created by ccc on 2020/9/15.
 */

public class MyApplication extends App {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //bugly异常上报
        CrashReport.initCrashReport(getApplicationContext(), "511db8dc12", true);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.

        //  Glide.get(getApplicationContext()).setMemoryCategory(MemoryCategory.LOW);
        MMKV.initialize(this);
        //切换至商业版服务
        //  HeConfig.switchToBizService();
        // 渠道  主要是获取agentId
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
        AdConfigInfo adConfigInfo = new AdConfigInfo();
        adConfigInfo.setAppId("5120314");
        adConfigInfo.setAppName("抢红包");
        adConfigInfo.setSplash("887403902");
        adConfigInfo.setOpen(true);
        adPlatformSDK.setAdConfigInfo(adConfigInfo);

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
        UMConfigure.init(this, "5f9157a94d7bf81a2ea8ed4c", agentId, UMConfigure.DEVICE_TYPE_PHONE, "");
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMConfigure.setLogEnabled(true);
        UMShareAPI.get(getApplicationContext()).setShareConfig(config);
    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }

}
