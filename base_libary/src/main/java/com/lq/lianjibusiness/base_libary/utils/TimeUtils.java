package com.lq.lianjibusiness.base_libary.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ccc on 2020/9/15.
 */

public class TimeUtils {
    //字符串转时间戳
    public static String getTime(String timeString,String type){ // "yyyy-MM-dd HH:mm:ss"   "HH:mm:ss"
        String timeStamp = "";
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date d;
        try{
            d = sdf.parse(timeString+"");
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }

    //时间戳转字符串
    public static String getStrTime(String timeStamp){
        String timeString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long  l = Long.parseLong(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }
    //时间戳获取小时
    public static int getHour(String times) {
        Calendar calendar = null;
      //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  d = new Date(Long.parseLong(times));
             calendar = Calendar.getInstance();
             calendar.setTime(d);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    //时间戳获取年
    public static int getYear(String times) {
        Calendar calendar = null;
        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  d = new Date(Long.parseLong(times));
        calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.YEAR);
    }

    //时间戳获取月
    public static int getMonth(String times) {
        Calendar calendar = null;
        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  d = new Date(Long.parseLong(times));
        calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.MONTH)+1;
    }
    //时间戳获取月
    public static int getMonth(Date  d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.MONTH)+1;
    }
    //时间戳获取月
    public static int getDays(String times) {
        Calendar calendar = null;
        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  d = new Date(Long.parseLong(times));
        calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    //时间戳获取星期
    public static int getWeek(String times) {
        Calendar calendar = null;
        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  d = new Date(Long.parseLong(times));
        calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //时间戳获取分钟
    public static int getMinute(String times) {
        Calendar calendar = null;
        //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  d = new Date(Long.parseLong(times));
        calendar = Calendar.getInstance();
        calendar.setTime(d);
        return calendar.get(Calendar.MINUTE);
    }
    //时间戳转字符串
    public static String getYearTime(String timeStamp){
        String timeString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long  l = Long.parseLong(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    //时间戳转字符串
    public static String getHourTime(String timeStamp){
        String timeString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        long  l = Long.parseLong(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }


    //时间戳转字符串
    public static String getStrTime(String timeStamp,String regex){
        String timeString = "";
        SimpleDateFormat sdf = new SimpleDateFormat(regex);
        long  l = Long.parseLong(timeStamp);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }


    //时间戳转字符串
    public static String getStrTime(Date date,String regex){
        return new SimpleDateFormat(regex).format(date);
    }


    public static String getTimeUnit(String type){
        //	期限类型（0：天，1：周，2：月，3：年）
        String unit = "";
        if("0".equals(type)){
            unit ="天";
        }else if("1".equals(type)){
            unit ="周";
        }else if("2".equals(type)){
            unit ="月";
        }else if("3".equals(type)){
            unit ="年";
        }
        return unit;
    }
    //初始化秒，如果时间为个位数,则在之前加0
    public static int inittimes(Long time){
        String s = String.valueOf(time);
        if (s.length()==1){
            return 1;
        }
        return 2;
    }
    //初始化秒，如果时间为个位数,则在之前加0
    public static int initInttimes(int time){
        String s = String.valueOf(time);
        if (s.length()==1){
            return 1;
        }
        return 2;
    }

    public static long getTimeChou(String type) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(type);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long timestamp = cal.getTimeInMillis();
        return timestamp;
    }


    //获取当前时间钱多少天时间
    public static Date getCurrTimeBefore(Date endDate) {
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_MONTH,-30);//往上推一天  30推三十天  365推一年
        dBefore = calendar.getTime();
        return dBefore;
    }

    public static long  compareDay(Calendar c1,Calendar c2) {
        long t1 = c1.getTimeInMillis();
        long t2 = c2.getTimeInMillis();
        long days = (t2 - t1)/(24 * 60 * 60 * 1000);
        return days;
    }

    public static long  compareMonth(Calendar c1,Calendar c2) {
        //转换为相对时间
        long t1 = c1.get(Calendar.MONTH);
        long t2 = c2.get(Calendar.MONTH);
        long months = t2 - t1;
        return months;
    }


    public static Date getTimeDate(String type) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = df.parse(type);
        return date;
    }

}
