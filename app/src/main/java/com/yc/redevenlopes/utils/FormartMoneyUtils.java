package com.yc.redevenlopes.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 作者：ZhangZhanRui
 * 创建日期：2017/6/2
 * 描述：格式化金额的工具类
 */

public class FormartMoneyUtils {

    public static String parseMoney(BigDecimal bd) {
        double doubleMoney = bd.doubleValue();
        String pattern = "###,###,###.00";
        if (doubleMoney < 1) {
            pattern = "0.##";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(bd);
    }

    public static String parseMoney(String money) {
        double doubleMoney = Double.valueOf(money);
        String pattern = "#########.00";
        if (doubleMoney < 1) {
            pattern = "0.##";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(doubleMoney);
    }

    public static String parseMoneyNoZero(BigDecimal bd) {
        String pattern = "###,###,###";
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(bd);
    }

    public static String parseMoney(double money) {
        String pattern = "#########.00";
        if (money < 1) {
            pattern = "0.##";
        } else if (money == 0) {
            return "0.00";
        }

        BigDecimal bd = new BigDecimal(money);
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(bd);
    }

    /**
     * 格式化手机号
     *
     * @param phoneNum 手机号
     * @return
     */
    public static String parsePhoneNum(String phoneNum) {
        char[] chars = phoneNum.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (i < 3) {
                sb.append(chars[i]);
                if (i == 2) {
                    sb.append("  ");
                }
            }
            if (2 < i && i < 7) {
                sb.append("*");
                if (i == 6) {
                    sb.append("  ");
                }
            }
            if (7 <= i) {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }
}
