package com.lq.lianjibusiness.base_libary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.lq.lianjibusiness.base_libary.App.App;
import com.lq.lianjibusiness.base_libary.App.GoagalInfo;
import com.orhanobut.logger.Logger;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by suns  on 2020/11/19 15:05.
 */
public class DeviceUtils {

    public static String getImei() {

        String imei = null;
        try {
            imei = PhoneCommonUtils.getImeiOrMeid(true);
        }catch (Exception e){

        }
        if (TextUtils.isEmpty(imei)) {
            imei =getUid();
        }
        if (TextUtils.isEmpty(imei)) {
            imei =GoagalInfo.oaid;
        }
        return imei;
    }

    @SuppressLint("MissingPermission")
    public  static  String getUid() {
        String uid = Settings.Secure.getString(App.getInstance().getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return uid;
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
