package com.lq.lianjibusiness.base_libary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.lq.lianjibusiness.base_libary.App.App;
import com.lq.lianjibusiness.base_libary.App.GoagalInfo;


import java.lang.reflect.Method;

/**
 * Created by suns  on 2020/11/19 15:05.
 */
public class DeviceUtils {

    public static String getImei() {
        TelephonyManager tm = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        int simState = tm.getSimState();

        boolean hasSimCard = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
            case TelephonyManager.SIM_STATE_UNKNOWN:
                hasSimCard = false; // 没有SIM卡
                break;
        }
        String imei = null;
        try {
            imei = tm.getDeviceId(); 
        }catch (Exception e){
            
        }

        

//        if (hasSimCard) {
//            String subId = tm.getSubscriberId();
//            //判断是否为电信手机
//            if (!TextUtils.isEmpty(subId) && subId.startsWith("46003")) {
//                try {
//                    Method method = tm.getClass().getMethod("getDeviceId", int.class);
//
//                    //暂无用到
//                    //String imei1 = (String) method.invoke(tm, 0);
//
//                    imei = (String) method.invoke(tm, 1);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        if (TextUtils.isEmpty(imei)) {
            imei = GoagalInfo.oaid;
        }
        return imei;
    }

    /**
     * 获得当前android系统的版本号
     */
    public static int getAndroidSDKVersion() {
        //android 4.4  对应版本号19
        //android 4.3  对应版本号18
        //android 4.2.2  对应版本号17
        int version = 0;
        try {
            version = Build.VERSION.SDK_INT;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName() {
        try {
            PackageManager packageManager = App.getInstance().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    App.getInstance().getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号 整型
     */
    public static int getVersionCode() {
        int versionCode = -1;
        try {
            PackageManager manager = App.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(App.getInstance().getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getAndroidID() {
        String id = Settings.Secure.getString(
                App.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        return id == null ? "" : id;
    }
}