package com.yc.qqzz.utils;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;
import com.yc.qqzz.constants.SPUtils;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;


/**
 * Version: 1.0
 */
public class CacheDataUtils {
    private static CacheDataUtils instance;
    private Gson gson;
    private UserInfozq userInfo;
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

    public UserInfozq getUserInfo() {
        if (userInfo==null||userInfo.getId()==0){
            String jsonStr = MMKV.defaultMMKV().getString(SPUtils.USER_INFO, "");
            try {
                Gson gson = getGson();
                userInfo = gson.fromJson(jsonStr, UserInfozq.class);
            } catch (Exception e) {
            }
            return userInfo;
        }else {
            return userInfo;
        }
    }

    public void saveUserInfo(UserInfozq userInfozq) {
        Gson gson = getGson();
        MMKV.defaultMMKV().putString(SPUtils.USER_INFO, gson.toJson(userInfozq));
    }



    public boolean isLogin() {
        UserInfozq userInfozq = getUserInfo();
        return userInfozq != null && userInfozq.getId() != 0&&!TextUtils.isEmpty(userInfozq.getImei());
    }


    //现在红包
    public void setHbZaiXian() {
        MMKV.defaultMMKV().putString("zaixian", "1");
    }
    //现在红包
    public String getHbZaiXian() {
        String agree = MMKV.defaultMMKV().getString("zaixian", "");
        return agree;
    }




    //手气红包
    public void setShouqiVideo() {
        MMKV.defaultMMKV().putString("shouqiVideo", "1");
    }
    //手气红包
    public String getShouqiVideo() {
        String agree = MMKV.defaultMMKV().getString("shouqiVideo", "");
        return agree;
    }



    //新手引导
    public void setNewGu(String news) {
        MMKV.defaultMMKV().putString("news", news);
    }
    //新手引导
    public String getNewGu() {
        String agree = MMKV.defaultMMKV().getString("news", "");
        return agree;
    }



    //登录更新时间
    public void setLoginTimes(String news) {
        MMKV.defaultMMKV().putString("logintimes", news);
    }


    //设置积分墙渠道转盘第一次点击看视频
    public void setTuraFirst(String news) {
        MMKV.defaultMMKV().putString("turnfirst", news);
    }
    //设置积分墙渠道转盘第一次点击看视频
    public String getTuraFirst() {
        String agree = MMKV.defaultMMKV().getString("turnfirst", "");
        return agree;
    }

    //用户协议
    public void setLevel(String str) {
        MMKV.defaultMMKV().putString("level", str);
    }




    //播放广告的次数
    public void setVideoNums(int videoNums) {
        MMKV.defaultMMKV().putInt("videoNums", videoNums);
    }

    //播放广告的次数
    public int getVideoNums() {
        int agree = MMKV.defaultMMKV().getInt("videoNums", 0);
        return agree;
    }

    //正在播放哪个广告
    public void setVideoType(String videoType) {
        MMKV.defaultMMKV().putString("videoType", videoType);
    }

    //正在播放哪个广告
    public String getVideoType() {//1 头条   2腾讯
        String videoType = MMKV.defaultMMKV().getString("videoType", "");
        return videoType;
    }

    //新手引导会员页面
    public String getYindao() {//
        String videoType = MMKV.defaultMMKV().getString("memberyindao", "");
        return videoType;
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
}
