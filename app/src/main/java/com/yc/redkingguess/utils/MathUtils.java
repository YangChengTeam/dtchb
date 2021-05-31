package com.yc.redkingguess.utils;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/6/1.
 */

public class MathUtils {


    /**
     * 将long类型分值转换成floaty类型元值
     */
    public static String  toYuanLongDivide(long data ){
        BigDecimal divide = new BigDecimal(data).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        String i = divide.toPlainString();
        return i;
    }

    /**
     * 将long类型分值转换成floaty类型元值
     */
    public static Float  toYuanFloatDivide(long data ){
        BigDecimal divide = new BigDecimal(data).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP);
        float i = divide.floatValue();
        return i;
    }

    /**
     * 两个数之间乘法
     */
    public static String ToMultiply(String price ,String num){
        BigDecimal multiply = new BigDecimal(price).multiply(new BigDecimal(num)).setScale(2,BigDecimal.ROUND_UP);
        String index = multiply.toPlainString();
        return index;
    }

    /**
     * 两个数之间乘法
     */
    public static float ToMultiplys(String price ,String num){
        BigDecimal multiply = new BigDecimal(price).multiply(new BigDecimal(num)).setScale(2,BigDecimal.ROUND_UP);
        String index = multiply.toPlainString();
        float v = multiply.floatValue();
        return v;
    }

    /**
     * 将int类型分值转换成String类型元值
     */
    public static String toYuanInt(int data){
        return new BigDecimal(data).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toPlainString();
    }

    /**
     * 将int类型分值转换int类型元值
     */
    public static int intYuanInt(int data){
        return new BigDecimal(data).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).intValue();
    }


    /**
     * 两个数之间的除法
     */
    public static int BigDecimals(float one,int two){
        return new BigDecimal(one).divide(new BigDecimal(two), 2, BigDecimal.ROUND_UP).intValue();
    }
}
