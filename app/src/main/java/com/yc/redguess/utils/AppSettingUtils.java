package com.yc.redguess.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.yc.adplatform.log.AdLog;
import com.yc.redguess.application.MyApplication;
import com.yc.redguess.constants.Constant;

public class AppSettingUtils {

    /**判断是否积分墙渠道
     */
    public static boolean isIntegralWall() {
        String agentId = ((MyApplication) MyApplication.getInstance()).getAgentId();
        String loginTypes = ((MyApplication) MyApplication.getInstance()).getLoginType();
        if (!TextUtils.isEmpty(loginTypes)){
            if (loginTypes.contains(",")){
                String[] split = loginTypes.split(",");
                String types="";
                for (int i = 0; i < split.length; i++) {
                    if (agentId.equals(split[i])){
                        types="1";
                    }
                }
                if (!TextUtils.isEmpty(types)){
                    return false;
                }else {
                    return true;
                }
            }else {
                if (loginTypes.equals(agentId)){
                    return false;
                }else {
                    return true;
                }
            }
        }else {
           return false;
        }
    }

    public static void showTxClick(String ad_positions){
        AdLog.sendLog(Constant.IPCODE, 41234, "38", String.valueOf(CacheDataUtils.getInstance().getUserInfo().getId()), ad_positions, Constant.TXRVIDEO, "click");
    }

    public static void showTxShow(String ad_positions){
        AdLog.sendLog(Constant.IPCODE, 41234, "38", String.valueOf(CacheDataUtils.getInstance().getUserInfo().getId()), ad_positions, Constant.TXRVIDEO, "show");
    }


    public static String getVideoType(){
       String videpTypes = getType();
      // Log.d("ccc", "---------getVideoType: "+videpTypes);
        return videpTypes;
    }

    public static String getVideoTypeTwo(){
        String videoType = CacheDataUtils.getInstance().getVideoType();//1 头条   2腾讯
        if (TextUtils.isEmpty(videoType)){
            videoType="1";
        }
        return videoType;
    }


    private static String getType(){
        String videoType = CacheDataUtils.getInstance().getVideoType();//1 头条   2腾讯
        int videoNums = CacheDataUtils.getInstance().getVideoNums();
        String ttNumss= ((MyApplication) MyApplication.getInstance()).ttNums;
        if (TextUtils.isEmpty(videoType)) {
            CacheDataUtils.getInstance().setVideoType("1");
            CacheDataUtils.getInstance().setVideoNums(0);
            videoType="1";
            videoNums=1;
        }
        if (TextUtils.isEmpty(ttNumss)){
            return "1";
        }
        if (TextUtils.isEmpty(((MyApplication) MyApplication.getInstance()).txNums)||"0".equals(((MyApplication) MyApplication.getInstance()).txNums)){
            return "1";
        }
        if ("0".equals(((MyApplication) MyApplication.getInstance()).ttNums)){
            return "2";
        }
        try {
           // Log.d("ccc", "----1----videoType: "+videoType+"----ttNums:"+((MyApplication) MyApplication.getInstance()).ttNums+"---:ttNums"+((MyApplication) MyApplication.getInstance()).txNums+"----videoNums:"+videoNums);
            if ("1".equals(videoType)){//头条
                if (videoNums<Integer.parseInt(((MyApplication) MyApplication.getInstance()).ttNums)){
                    CacheDataUtils.getInstance().setVideoNums(videoNums+1);
                    return "1";
                }else {
                    CacheDataUtils.getInstance().setVideoNums(1);
                    CacheDataUtils.getInstance().setVideoType("2");
                    return "2";
                }
            }else if ("2".equals(videoType)){
                if (videoNums<Integer.parseInt(((MyApplication) MyApplication.getInstance()).txNums)){
                    CacheDataUtils.getInstance().setVideoNums(videoNums+1);
                    return "2";
                }else {
                    CacheDataUtils.getInstance().setVideoNums(1);
                    CacheDataUtils.getInstance().setVideoType("1");
                    return "1";
                }
            }else {
                CacheDataUtils.getInstance().setVideoType("1");
                CacheDataUtils.getInstance().setVideoNums(1);
                return "1";
            }
        }catch (Exception e){
            CacheDataUtils.getInstance().setVideoType("1");
            CacheDataUtils.getInstance().setVideoNums(1);
            return "1";
        }
    }


    public static boolean isShowToast(){
        if (Build.VERSION.SDK_INT==22||Build.VERSION.SDK_INT==25){
            return false;
        }else {
            return true;
        }
    }

}
