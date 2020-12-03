package com.yc.redevenlopes.utils;


import android.net.rtp.RtpStream;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;
import com.yc.redevenlopes.constants.SPUtils;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;


/**
 * Version: 1.0
 */
public class CacheDataUtils {
    private static CacheDataUtils instance;
    private Gson gson;

    public static CacheDataUtils getInstance() {
        if (instance == null) {
            synchronized (CacheDataUtils.class) {
                if (instance == null) {
                    instance = new CacheDataUtils();
                }
            }
        }
        return instance;
    }

    public Gson getGson() {
        if (gson == null) {
            synchronized (CacheDataUtils.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    public UserInfo getUserInfo() {
        String jsonStr = MMKV.defaultMMKV().getString(SPUtils.USER_INFO, "");
        UserInfo userInfo = null;
        try {
            Gson gson = getGson();
            userInfo = gson.fromJson(jsonStr, UserInfo.class);
        } catch (Exception e) {
        }
        return userInfo;
    }

    public void saveUserInfo(UserInfo userInfo) {
        Gson gson = getGson();
        MMKV.defaultMMKV().putString(SPUtils.USER_INFO, gson.toJson(userInfo));
    }

    public boolean isLogin() {
        UserInfo userInfo = getUserInfo();
        return userInfo != null && userInfo.getId() != 0;
    }

    public int getUid() {
        UserInfo userInfo = getUserInfo();
        if (isLogin()) {
            return userInfo.getId();
        }
        return 0;
    }
    //用户协议
    public String getAgreement() {
        String agree = MMKV.defaultMMKV().getString("agree", "");
        return agree;
    }

    //用户协议
    public void setAgreement() {
        MMKV.defaultMMKV().putString("agree", "1");
    }
    //用户协议
    public String getLevel() {
        String agree = MMKV.defaultMMKV().getString("level", "");
        return agree;
    }

    //音效
    public void setSol(String sol) {
        MMKV.defaultMMKV().putString("sol", sol);
    }
    //用户协议
    public String getSol() {
        String agree = MMKV.defaultMMKV().getString("sol", "");
        return agree;
    }


    //用户协议
    public void setLevel(String str) {
        MMKV.defaultMMKV().putString("level", str);
    }
}
