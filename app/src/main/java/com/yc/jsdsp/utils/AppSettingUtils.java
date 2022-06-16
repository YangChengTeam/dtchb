package com.yc.jsdsp.utils;

import android.content.Context;
import android.text.TextUtils;

import com.yc.jsdsp.constants.Constant;
import com.yc.jsdsp.utils.adgromore.log.AdLog;


public class AppSettingUtils {



    public static void showTxClick(String ad_positions,String adCode){
        if (TextUtils.isEmpty(ad_positions)){
            ad_positions="home";
        }
        AdLog.sendLog(Constant.IPCODE, 41234, "95", String.valueOf(CacheDataUtils.getInstance().getUserInfo().getId()), ad_positions,adCode, "click");
    }

    public static void showTxShow(String ad_positions,String adCode){
        if (TextUtils.isEmpty(ad_positions)){
            ad_positions="home";
        }
       AdLog.sendLog(Constant.IPCODE, 41234, "95", String.valueOf(CacheDataUtils.getInstance().getUserInfo().getId()), ad_positions, adCode, "show");
    }






    public static boolean commonYou(Context context){
        return false;
    }

    public static boolean commonYouTwo(Context context){
        return false;
    }



    public static boolean isShowToast(){
        return true;
    }

}
