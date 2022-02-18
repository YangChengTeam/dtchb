package com.yc.wxchb.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.yc.wxchb.application.Constant;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.utils.adgromore.log.AdLog;


public class AppSettingUtils {



    public static void showTxClick(String ad_positions,String adCode){
        if (TextUtils.isEmpty(ad_positions)){
            ad_positions="home";
        }
        AdLog.sendLog(Constant.IPCODE, 41234, "74", String.valueOf(CacheDataUtils.getInstance().getUserInfo().getId()), ad_positions,adCode, "click");
    }

    public static void showTxShow(String ad_positions,String adCode){
        if (TextUtils.isEmpty(ad_positions)){
            ad_positions="home";
        }
       AdLog.sendLog(Constant.IPCODE, 41234, "74", String.valueOf(CacheDataUtils.getInstance().getUserInfo().getId()), ad_positions, adCode, "show");
    }






    public static boolean commonYou(Context context){
        if (Constant.IS_OPEN==1){//1:打开 0:关闭
            if ( Constant.DIQU_PIBI==0){//0:未屏蔽 1:已屏蔽
                if (CommonUtils.isInstallApp("com.ss.android.lark")){
                    MobclickAgent.onEvent(context, "feishu_ins", "1");//参数二为当前统计的事件ID
                    return false;
                }else {
                    if (Constant.LEVEL_STATE==0){//0:关闭 1:打开
                        return true;
                    }else {
                        return true;
                    }
                }
            }else {
                MobclickAgent.onEvent(context, "diyu", "1");//参数二为当前统计的事件ID
                return false;
            }
        }else {
            return false;
        }
    }



    public static boolean isShowToast(){
        return true;
    }

}
