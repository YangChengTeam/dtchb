package com.yc.redevenlopes.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：ccc
 * 创建日期：2017/5/12
 * 描述：输入内容规则检查工具类
 */

public class CheckRulesUtils {

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


    public static boolean isLoginPwd(String pwd) {
        String rex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (TextUtils.isEmpty(pwd))
            return false;
        else
            return pwd.matches(rex);
    }

    /**
     * 验证设置的登录密码是否符合规则
     *
     * @param pwd
     * @return
     */
    public static boolean isRightLoginPwd(String pwd) {
        //登录密码是6~18位的数字与字母的组合
        //密码正则：[A-Za-z0-9~!@#$%^&*.]{6,18}
        char c = 'a';
        for (int i = 0; i < pwd.length(); i++) {
            c = pwd.charAt(i);
            if (!((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 验证输入的密码是否符合格式
     *
     * @param pwd
     * @return
     */
    public static boolean checkPwd(String pwd) {
        //密码正则：[A-Za-z0-9~!@#$%^&*.]{6,18}
        Pattern pattern = Pattern.compile("[A-Za-z0-9]{6,20}");
        Matcher matcher = pattern.matcher(pwd);
        return matcher.matches();
    }


    /**
     * 校验姓名是否合格 2到14位汉字，可包含“ · ”
     *
     * @param name 姓名
     * @return
     */
    public static boolean isValidName(String name) {
        if (name.length() < 2 || name.length() > 14) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$");
        Matcher isName = pattern.matcher(name);
        if (!isName.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 校验邮箱
     *
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
//        String regEx1 = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        String regEx1 = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    /**
     * 判断字符串是否全是数字
     *
     * @param str
     * @return
     */
    public static boolean isAllNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否全是字母
     *
     * @param str
     * @return
     */
    public static boolean isAllLetter(String str) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher isLetter = pattern.matcher(str);
        if (!isLetter.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否全是字母
     *
     * @param str
     * @return
     */
    public static boolean isName(String str) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z\\u4e00-\\u9fa5]$");
        Matcher isLetter = pattern.matcher(str);
        if (!isLetter.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 过滤用户通讯录姓名
     *
     * @param name 通讯录姓名
     * @return filter_name 过滤后的姓名
     */
    public static String filterName(String name) {
        if (TextUtils.isEmpty(name)) {
            return "";
        } else {
            char[] chars = name.toCharArray();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < chars.length; i++) {
                if (isName(chars[i] + "")) {
                    sb.append(chars[i]);
                }
            }
            if (TextUtils.isEmpty(sb.toString())) {
                return "";
            } else {
                return sb.toString();
            }
        }
    }

    /**
     * 通讯录号码过滤
     *
     * @param phoneNum
     * @return
     */
    public static String filterPhoneNum(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            return "";
        } else {
            char[] chars = phoneNum.toCharArray();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < chars.length; i++) {
                if (isAllNum(chars[i] + "")) {
                    sb.append(chars[i]);
                }
            }
            if (TextUtils.isEmpty(sb.toString())) {
                return "";
            } else {
                return sb.toString();
            }
        }
    }

    /**
     * 格式化手机号码为 135 2323 5656
     *
     * @param phone
     * @return
     */
    public static String formartPhoneNum(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "";
        }
        String phoneStr = phone.substring(0, 3) + " " + phone.substring(3, 7) + " " + phone.substring(7);
        return phoneStr;
    }

    public static boolean checkQQ(String qq) {
        String regex = "[1-9][0-9]{4,14}";//第一位1-9之间的数字，第二位0-9之间的数字，数字范围4-14个之间
        //String regex2 = "[1-9]\\d{4,14}";//此句也可以
        return qq.matches(regex);
    }

    /**
     * 隐藏身份证号码中间8位
     *
     * @param idCard
     * @return
     */
    public static String hideIdCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return "";
        }
        String idCardStr = idCard.substring(0, 6) + "********" + idCard.substring(14);
        return idCardStr;
    }

    /**
     * 在客服电话中间加-
     *
     * @param phone
     * @return
     */
    public static String formartPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "";
        }
        String head = phone.substring(0, 3);
        String mid = phone.substring(3, 7);
        String wear = phone.substring(7);
        return head + "-" + mid + "-" + wear;
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" "))
                    return "";
                else
                    return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止edittext输入空格及特殊字符
     *
     * @param editText
     */
    public static void setEditTextNotInPutSpaceAndSpaChat(EditText editText) {
        setEditTextInhibitInputSpace(editText);
        setEditTextInhibitInputSpeChat(editText);
    }

    public static String formatTime(long time, String rex) {
        SimpleDateFormat format = new SimpleDateFormat(rex);
        return format.format(time);
    }
}
