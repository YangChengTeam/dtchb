package com.yc.redevenlopes.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/15.
 */

public class TimesUtils {
    //时间戳转字符串
    public static String getMonthTime(String timeStamp){
        String timeString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        long  l = Long.parseLong(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }


}
