package com.yc.wxchb.utils;

import android.util.Log;

public class LogUtils {

    public static boolean isShow=true;

    public static void showAdLog(String msg){
        if (isShow){
            Log.d("ccc", "---广告log-----: "+msg);
        }
    }

    public static void showComlog(String msg){
        if (isShow){
            Log.d("ccc", "---日程log-----: "+msg);
        }
    }
}
