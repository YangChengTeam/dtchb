package com.lq.lianjibusiness.base_libary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lq.lianjibusiness.base_libary.App.App;
import com.lq.lianjibusiness.base_libary.App.Constants;


/**
 * 专门访问和设置SharePreference的工具类, 保存和配置一些设置信息
 *
 * @author ccc
 */
public class PrefUtils {

    private static final String SHARE_PREFS_NAME = Constants.SP_NAME;

    public static void putBoolean(String key, boolean value) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key,
                                     boolean defaultValue) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getBoolean(key, defaultValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getString(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putInt(key, value).commit();
    }


    public static void putLong(String key, long value) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        pref.edit().putLong(key, value).commit();
    }

    public static long getLong(String key, long defaultValue) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getLong(key, defaultValue);
    }


    public static int getInt(String key, int defaultValue) {
        SharedPreferences pref = App.getInstance().getSharedPreferences(SHARE_PREFS_NAME,
                Context.MODE_PRIVATE);

        return pref.getInt(key, defaultValue);
    }

}
