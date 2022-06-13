package com.yc.rrdsprj.utils;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;
import com.yc.rrdsprj.application.SPUtils;
import com.yc.rrdsprj.beans.module.beans.UserInfo;


import java.util.ArrayList;
import java.util.List;


/**
 * Version: 1.0
 */
public class CacheDataUtils {
    private static CacheDataUtils instance;
    private Gson gson;
    private UserInfo userInfo;
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

    public Gson getGson()  {
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
        if (userInfo==null||userInfo.getId()==0){
            String jsonStr = MMKV.defaultMMKV().getString(SPUtils.USER_INFO, "");
            try {
                Gson gson = getGson();
                userInfo = gson.fromJson(jsonStr, UserInfo.class);
            } catch (Exception e) {
            }
            return userInfo;
        }else {
            return userInfo;
        }
    }

    public void saveUserInfo(UserInfo userInfozq) {
        Gson gson = getGson();
        MMKV.defaultMMKV().putString(SPUtils.USER_INFO, gson.toJson(userInfozq));
    }

    public void cleanUserInfo() {
        MMKV.defaultMMKV().putString(SPUtils.USER_INFO, "");
    }

    public boolean isLogin() {
        UserInfo userInfozq = getUserInfo();
        return userInfozq != null && userInfozq.getId() != 0&&!TextUtils.isEmpty(userInfozq.getImei());
    }

    public void setWithDraw() {
        MMKV.defaultMMKV().putString("withdrawsssss", "1");
    }

    public String getWithDraw() {
        String prizes = MMKV.defaultMMKV().getString("withdrawsssss", "");
        return prizes;
    }


    public void setWithDrawHome() {
        MMKV.defaultMMKV().putString("withdraws", "1");
    }

    public String getWithDrawHome() {
        String prizes = MMKV.defaultMMKV().getString("withdraws", "");
        return prizes;
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

    //在线红包
    public void setZaixian(String lineRed) {
        MMKV.defaultMMKV().putString("lineRed", lineRed);
    }
    //新手引导
    public String getZaixian() {
        String agree = MMKV.defaultMMKV().getString("lineRed", "");
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
    public int getInsetNums() {
        int agree = MMKV.defaultMMKV().getInt("insetNums", 0);
        return agree;
    }

    //正在播放哪个广告
    public void setInsetType(String videoType) {
        MMKV.defaultMMKV().putString("insetType", videoType);
    }

    //正在播放哪个广告

    public String getInsetType() {//1 头条   2腾讯
        String videoType = MMKV.defaultMMKV().getString("insetType", "");
        return videoType;
    }

    //播放广告的次数
    public void setInsetNums(int videoNums) {
        MMKV.defaultMMKV().putInt("insetNums", videoNums);
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


    //新手引导任务页面
    public String getTaskYindaos() {//
        String videoType = MMKV.defaultMMKV().getString("taskyindoa", "");
        return videoType;
    }

    //新手引导任务页面
    public String setTaskYindaos() {//
        String videoType = MMKV.defaultMMKV().getString("taskyindoa", "");
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
    public String getLevel() {
        String agree = MMKV.defaultMMKV().getString("level", "");
        return agree;
    }


    //新手引导hognb
    public void setNewGuHongbao(String news) {
        MMKV.defaultMMKV().putString("newshongbao", news);
    }
    //新手引导
    public String getNewGuHongbao() {
        String agree = MMKV.defaultMMKV().getString("newshongbao", "");
        return agree;
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



    //存储所有spot历史搜索
    public List<String> getLoadPack() {
        String loadPackList = MMKV.defaultMMKV().getString("package", "");
        Gson gson = getGson();
        List<String> list = gson.fromJson(loadPackList, new TypeToken<List<String>>() {
        }.getType());
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setLoadPack(List<String> loadPackList) {
        if (loadPackList == null) {
            loadPackList = new ArrayList<>();
        }
        Gson gson = getGson();
        String stCity = gson.toJson(loadPackList);
        MMKV.defaultMMKV().putString("package", stCity);

    }


    //用户协议
    public String getAnsSoul() {
        String anssoul = MMKV.defaultMMKV().getString("anssoul", "");
        return anssoul;
    }

    //用户协议
    public void setAnsSoul(String anSo) {
        MMKV.defaultMMKV().putString("anssoul", anSo);
    }



    public String getTaskRedGu() {
        String anssoul = MMKV.defaultMMKV().getString("taskredgu", "");
        return anssoul;
    }

    //用户协议
    public void setTaskRedGu(String anSo) {
        MMKV.defaultMMKV().putString("taskredgu", anSo);
    }



    public String getCashTips() {
        String anssoul = MMKV.defaultMMKV().getString("cashtips", "");
        return anssoul;
    }

    public void setCashTips(String anSo) {
        MMKV.defaultMMKV().putString("cashtips", anSo);
    }

    public String getCashTipsTwo() {
        String anssoul = MMKV.defaultMMKV().getString("cashtipstwo", "");
        return anssoul;
    }

    public void setCashTipsTwo(String anSo) {
        MMKV.defaultMMKV().putString("cashtipstwo", anSo);
    }

    public String getHomeYindao() {
        String anssoul = MMKV.defaultMMKV().getString("homeyindao", "");
        return anssoul;
    }

    public void setHomeYindao(String homeyindao) {
        MMKV.defaultMMKV().putString("homeyindao", homeyindao);
    }


    public String getFirstChaping() {
        String anssoul = MMKV.defaultMMKV().getString("chapings", "");
        return anssoul;
    }

    public void setFirstChaping(String chapings) {
        MMKV.defaultMMKV().putString("chapings", chapings);
    }

    public void setTaskMemyindao(String taskmemyindao) {
        MMKV.defaultMMKV().putString("taskmemyindao", taskmemyindao);
    }

    public String getTaskMemyindao() {
        String anssoul = MMKV.defaultMMKV().getString("taskmemyindao", "");
        return anssoul;
    }

    public void setTasktips(String taskmemyindao) {
        MMKV.defaultMMKV().putString("tasktips", taskmemyindao);
    }

    public String getTasktips() {
        String anssoul = MMKV.defaultMMKV().getString("tasktips", "");
        return anssoul;
    }


    //存储所有激励视频顺序ad
    public List<String> getAdType() {
        String cityHistory = MMKV.defaultMMKV().getString("adlist", "");
        Gson gson = getGson();
        List<String> list = gson.fromJson(cityHistory, new TypeToken<List<String>>() {
        }.getType());
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setAdType(List<String> citySearchList) {
        if (citySearchList == null) {
            citySearchList = new ArrayList<>();
        }
        Gson gson = getGson();
        String stCity = gson.toJson(citySearchList);
        MMKV.defaultMMKV().putString("adlist", stCity);
    }


    //存储所有激励视频顺序ad
    public List<String> getDownAdType() {
        String cityHistory = MMKV.defaultMMKV().getString("downadlist", "");
        Gson gson = getGson();
        List<String> list = gson.fromJson(cityHistory, new TypeToken<List<String>>() {
        }.getType());
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setDownAdType(List<String> citySearchList) {
        if (citySearchList == null) {
            citySearchList = new ArrayList<>();
        }
        Gson gson = getGson();
        String stCity = gson.toJson(citySearchList);
        MMKV.defaultMMKV().putString("downadlist", stCity);
    }


    //存储所有激励视频顺序ad
    public List<String> getAdInsetType() {
        String cityHistory = MMKV.defaultMMKV().getString("adInset", "");
        Gson gson = getGson();
        List<String> list = gson.fromJson(cityHistory, new TypeToken<List<String>>() {
        }.getType());
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setAdInsetType(List<String> citySearchList) {
        if (citySearchList == null) {
            citySearchList = new ArrayList<>();
        }
        Gson gson = getGson();
        String stCity = gson.toJson(citySearchList);
        MMKV.defaultMMKV().putString("adInset", stCity);
    }
}
