package com.yc.wxchb.utils;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;
import com.yc.wxchb.application.SPUtils;
import com.yc.wxchb.beans.module.beans.UserInfo;


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
        MMKV.defaultMMKV().putString("withdraws", "1");
    }

    public String getWithDraw() {
        String prizes = MMKV.defaultMMKV().getString("withdraws", "");
        return prizes;
    }
    //????????????
    public void setHbZaiXian() {
        MMKV.defaultMMKV().putString("zaixian", "1");
    }
    //????????????
    public String getHbZaiXian() {
        String agree = MMKV.defaultMMKV().getString("zaixian", "");
        return agree;
    }




    //????????????
    public void setShouqiVideo() {
        MMKV.defaultMMKV().putString("shouqiVideo", "1");
    }
    //????????????
    public String getShouqiVideo() {
        String agree = MMKV.defaultMMKV().getString("shouqiVideo", "");
        return agree;
    }



    //????????????
    public void setNewGu(String news) {
        MMKV.defaultMMKV().putString("news", news);
    }
    //????????????
    public String getNewGu() {
        String agree = MMKV.defaultMMKV().getString("news", "");
        return agree;
    }

    //????????????
    public void setZaixian(String lineRed) {
        MMKV.defaultMMKV().putString("lineRed", lineRed);
    }
    //????????????
    public String getZaixian() {
        String agree = MMKV.defaultMMKV().getString("lineRed", "");
        return agree;
    }

    //??????????????????
    public void setLoginTimes(String news) {
        MMKV.defaultMMKV().putString("logintimes", news);
    }


    //???????????????????????????????????????????????????
    public void setTuraFirst(String news) {
        MMKV.defaultMMKV().putString("turnfirst", news);
    }
    //???????????????????????????????????????????????????
    public String getTuraFirst() {
        String agree = MMKV.defaultMMKV().getString("turnfirst", "");
        return agree;
    }

    //????????????
    public void setLevel(String str) {
        MMKV.defaultMMKV().putString("level", str);
    }






    //?????????????????????
    public int getInsetNums() {
        int agree = MMKV.defaultMMKV().getInt("insetNums", 0);
        return agree;
    }

    //????????????????????????
    public void setInsetType(String videoType) {
        MMKV.defaultMMKV().putString("insetType", videoType);
    }

    //????????????????????????

    public String getInsetType() {//1 ??????   2??????
        String videoType = MMKV.defaultMMKV().getString("insetType", "");
        return videoType;
    }

    //?????????????????????
    public void setInsetNums(int videoNums) {
        MMKV.defaultMMKV().putInt("insetNums", videoNums);
    }
    //?????????????????????
    public void setVideoNums(int videoNums) {
        MMKV.defaultMMKV().putInt("videoNums", videoNums);
    }
    //?????????????????????
    public int getVideoNums() {
        int agree = MMKV.defaultMMKV().getInt("videoNums", 0);
        return agree;
    }

    //????????????????????????
    public void setVideoType(String videoType) {
        MMKV.defaultMMKV().putString("videoType", videoType);
    }

    //????????????????????????
    public String getVideoType() {//1 ??????   2??????
        String videoType = MMKV.defaultMMKV().getString("videoType", "");
        return videoType;
    }


    //????????????????????????
    public String getTaskYindaos() {//
        String videoType = MMKV.defaultMMKV().getString("taskyindoa", "");
        return videoType;
    }

    //????????????????????????
    public String setTaskYindaos() {//
        String videoType = MMKV.defaultMMKV().getString("taskyindoa", "");
        return videoType;
    }

    //??????
    public void setSol(String sol) {
        MMKV.defaultMMKV().putString("sol", sol);
    }
    //????????????
    public String getSol() {
        String agree = MMKV.defaultMMKV().getString("sol", "");
        return agree;
    }
    public String getLevel() {
        String agree = MMKV.defaultMMKV().getString("level", "");
        return agree;
    }


    //????????????hognb
    public void setNewGuHongbao(String news) {
        MMKV.defaultMMKV().putString("newshongbao", news);
    }
    //????????????
    public String getNewGuHongbao() {
        String agree = MMKV.defaultMMKV().getString("newshongbao", "");
        return agree;
    }
    //????????????
    public String getAgreement() {
        String agree = MMKV.defaultMMKV().getString("agree", "");
        return agree;
    }

    //????????????
    public void setAgreement() {
        MMKV.defaultMMKV().putString("agree", "1");
    }



    //????????????spot????????????
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


    //????????????
    public String getAnsSoul() {
        String anssoul = MMKV.defaultMMKV().getString("anssoul", "");
        return anssoul;
    }

    //????????????
    public void setAnsSoul(String anSo) {
        MMKV.defaultMMKV().putString("anssoul", anSo);
    }



    public String getTaskRedGu() {
        String anssoul = MMKV.defaultMMKV().getString("taskredgu", "");
        return anssoul;
    }

    //????????????
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


    //??????????????????????????????ad
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


    //??????????????????????????????ad
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


    //??????????????????????????????ad
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
