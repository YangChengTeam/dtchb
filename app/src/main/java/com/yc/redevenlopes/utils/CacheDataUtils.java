package com.yc.redevenlopes.utils;


import android.net.rtp.RtpStream;

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
}
