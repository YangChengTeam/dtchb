package com.yc.jsdps.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    public String EncoderByMd5(String str) {
        MessageDigest md5 = null;

        byte[] m = null;

        try {
            md5 = MessageDigest.getInstance("MD5");

            try {
                md5.update(str.getBytes("UTF-8"));

            } catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block

                e.printStackTrace();

            }

            m = md5.digest();// MD5 的计算结果是一个 128 位的长整数，

        } catch (NoSuchAlgorithmException e) {
// TODO Auto-generated catch block

            e.printStackTrace();

        }

        return getString(m);

    }

    private static String getString(byte[] tmp) {
        String s = null;

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',

                'A', 'B', 'C', 'D', 'E', 'F'};// 用来将字节转换成16进制表示的字符

        char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16进制需要 32 个字符

        int k = 0;// 表示转换结果中对应的字符位置

        for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节转换成 16进制字符的转换

            byte byte0 = tmp[i];// 取第 i 个字节

            str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,>>>为逻辑右移，将符号位一起右移

            str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换

        }

        s = new String(str);// 换后的结果转换为字符串

        return s;

    }



    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }


}
