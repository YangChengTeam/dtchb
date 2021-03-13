package com.yc.redguess.utils;

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

    //时间戳转字符串
    public static String getStrTime(String timeStamp){
        String timeString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        long  l = Long.parseLong(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    //时间戳转字符串
    public static String getStrTimessssss(long timeStamp){
        String timeString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd");
        timeString = sdf.format(new Date(timeStamp));//单位秒
        return timeString;
    }

    //时间戳转字符串
    public static String getStr(long timeStamp){
        String timeString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        timeString = sdf.format(new Date(timeStamp));//单位秒
        return timeString;
    }


    //时间戳转字符串
    public static String getStrTimeTwo(String timeStamp){
        String timeString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long  l = Long.parseLong(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    public static long getDayDiff(long diff) {
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24))
                / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                * (1000 * 60 * 60))
                / (1000 * 60);
        return days;
    }
    public static long getHourDiff(long diff) {
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24))
                / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                * (1000 * 60 * 60))
                / (1000 * 60);
        return hours;
    }
    public static long getMinDiff(long diff) {
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24))
                / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                * (1000 * 60 * 60))
                / (1000 * 60);
        return minutes;
    }

    public static long getSecondDiff(long diff) {
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24))
                / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                * (1000 * 60 * 60))
                / (1000 * 60);
        long second=(diff-days * (1000 * 60 * 60 * 24)- hours
                * (1000 * 60 * 60)-minutes*(1000 * 60 ))/(1000);
        return second;
    }

}