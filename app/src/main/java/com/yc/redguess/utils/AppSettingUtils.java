package com.yc.redguess.utils;

import android.text.TextUtils;

import com.yc.redguess.application.MyApplication;

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
}
