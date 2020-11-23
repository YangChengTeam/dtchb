package com.yc.redevenlopes.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    //时间戳获取小时
    public static int getHour(long times) {
        Calendar calendar = null;
        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  d = new Date(times);
        calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    //时间戳获取分钟
    public static int getMinute(long times) {
        Calendar calendar = null;
        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  d = new Date(times);
        calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.MINUTE);
    }
    //时间戳获取秒
    public static int getSecond(long times) {
        Calendar calendar = null;
        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  d = new Date(times);
        calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.MINUTE);
    }
}
