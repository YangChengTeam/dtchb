package com.yc.jsdps.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author:huangcg
 * @data: 2017/11/30
 * @Version:
 */
public class StringUtils {
    public static boolean isPhone(final String phone) {
        return checkCellphone(phone) || isTelPhone(phone);
    }
    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        return Pattern.compile(regex).matcher(cellphone).matches() ;
    }

    /**
     * 电话号码验证
     * @author ：shijing
     * 2016年12月5日下午4:34:21
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isTelPhone(final String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 文字局部变色
     * @param start
     * @param end
     * @param text
     */
    public static void setPartColor(int start, int end, String text,TextView tv,int color) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    电信号段:133/153/180/181/189/177；
    联通号段:130/131/132/155/156/185/186/145/176；
    移动号段：134/135/136/137/138/139/150/151/152/157/158/159/182/183/184/187/188/147/178。
    总结起来就是第一位必定为1，第二位必定为3或5或8或7或4，其他位置的可以为0-9
    */
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
    /**
     * 判断一个字符串是否为空
     */
    public static Boolean StringEmpty(String s){
        if (s==null||"".equals(s)||s.length()==0){
            return true;
        }
        return false;
    }


}
