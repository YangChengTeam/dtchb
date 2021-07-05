package com.yc.majiaredgrab.utils;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;
import com.yc.majiaredgrab.constants.SPUtils;
import com.yc.majiaredgrab.homeModule.module.bean.UserInfo;
import com.yc.majiaredgrab.homeModule.module.bean.WithDraw5Beans;


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



    public WithDraw5Beans getWithDrawBeans() {
        String jsonStr = MMKV.defaultMMKV().getString(SPUtils.WITH_INFO, "");
        WithDraw5Beans withDraw5Beans = null;
        try {
            Gson gson = getGson();
            withDraw5Beans = gson.fromJson(jsonStr, WithDraw5Beans.class);
        } catch (Exception e) {
        }
        return withDraw5Beans;
    }

    public void setWithDrawBeans(WithDraw5Beans withDrawBeans) {
        Gson gson = getGson();
        MMKV.defaultMMKV().putString(SPUtils.WITH_INFO, gson.toJson(withDrawBeans));
    }

    public boolean isLogin() {
        UserInfo userInfo = getUserInfo();
        return userInfo != null && userInfo.getId() != 0&&!TextUtils.isEmpty(userInfo.getImei());
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

    //现在红包
    public void setHbZaiXian() {
        MMKV.defaultMMKV().putString("zaixian", "1");
    }
    //现在红包
    public String getHbZaiXian() {
        String agree = MMKV.defaultMMKV().getString("zaixian", "");
        return agree;
    }


    //提现页面
    public void setWithdraw() {
        MMKV.defaultMMKV().putString("withdraw", "1");
    }
    //提现页面
    public String getWithdraw() {
        String agree = MMKV.defaultMMKV().getString("withdraw", "");
        return agree;
    }

    //抢红包页面
    public void setQhb() {
        MMKV.defaultMMKV().putString("qhb", "1");
    }
    //抢红包页面
    public String getQhb() {
        String agree = MMKV.defaultMMKV().getString("qhb", "");
        return agree;
    }

    //会员页面
    public void setMember() {
        MMKV.defaultMMKV().putString("member", "1");
    }
    //会员页面
    public String getMember() {
        String agree = MMKV.defaultMMKV().getString("member", "");
        return agree;
    }


    //答题视频
    public void setAnswerVideo() {
        MMKV.defaultMMKV().putString("answerVideo", "1");
    }
    //答题视频
    public String getAnswerVideo() {
        String agree = MMKV.defaultMMKV().getString("answerVideo", "");
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



    //音效
    public void setSol(String sol) {
        MMKV.defaultMMKV().putString("sol", sol);
    }
    //用户协议
    public String getSol() {
        String agree = MMKV.defaultMMKV().getString("sol", "");
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


    //新手引导hognb
    public void setNewGuHongbao(String news) {
        MMKV.defaultMMKV().putString("newshongbao", news);
    }
    //新手引导
    public String getNewGuHongbao() {
        String agree = MMKV.defaultMMKV().getString("newshongbao", "");
        return agree;
    }

    //新手引导sign
    public void setSignYinDao(String news) {
        MMKV.defaultMMKV().putString("signyindao", news);
    }
    //新手引导
    public String getSignYinDao() {
        String agree = MMKV.defaultMMKV().getString("signyindao", "");
        return agree;
    }


    //登录更新时间
    public void setLoginTimes(String news) {
        MMKV.defaultMMKV().putString("logintimes", news);
    }
    //登录更新时间
    public String getLoginTimes() {
        String agree = MMKV.defaultMMKV().getString("logintimes", "");
        return agree;
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



    //任务小手
    public void setTaskShou(String shou) {
        MMKV.defaultMMKV().putString("taskshou", shou);
    }
    //任务小手
    public String getTaskShou() {
        String agree = MMKV.defaultMMKV().getString("taskshou", "");
        return agree;
    }



    //任务在线红包
    public void setTaskRed(String shou) {
        MMKV.defaultMMKV().putString("taskred", shou);
    }
    //任务在线红包
    public String getTaskRed() {
        String agree = MMKV.defaultMMKV().getString("taskred", "");
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


    //轮播顺序有没有改变
    public void setVideoSort(String videoType) {
        MMKV.defaultMMKV().putString("videoSort", videoType);
    }

    //轮播顺序有没有改变
    public String getVideoSort() {//1 头条   2腾讯
        String videoType = MMKV.defaultMMKV().getString("videoSort", "");
        return videoType;
    }


    //新手引导会员页面
    public void setYindao(String videoType) {
        MMKV.defaultMMKV().putString("memberyindao", videoType);
    }

    //新手引导会员页面
    public String getYindao() {//
        String videoType = MMKV.defaultMMKV().getString("memberyindao", "");
        return videoType;
    }


    //签到日历时间戳
    public void setSignCalendar(String times) {
        MMKV.defaultMMKV().putString("signTimes", times);
    }

    //签到日历提醒时间戳
    public String getSignCalendar() {//1 头条   2腾讯
        String videoType = MMKV.defaultMMKV().getString("signTimes", "");
        return videoType;
    }
    //会员页面滑动引导
    public void setMemberScroll(String memberscroll) {
        MMKV.defaultMMKV().putString("memberscroll", memberscroll);
    }

    //会员页面滑动引导
    public String getMemberScroll() {
        String videoType = MMKV.defaultMMKV().getString("memberscroll", "");
        return videoType;
    }


    //分享页面滑动引导
    public void setShareScroll(String memberscroll) {
        MMKV.defaultMMKV().putString("sharescroll", memberscroll);
    }

    //分享页面滑动引导
    public String getShareScroll() {
        String videoType = MMKV.defaultMMKV().getString("sharescroll", "");
        return videoType;
    }
}
