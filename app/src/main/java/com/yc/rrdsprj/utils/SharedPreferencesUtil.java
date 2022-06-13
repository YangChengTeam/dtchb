package com.yc.rrdsprj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wlh on 2016/7/25.
 */
public class SharedPreferencesUtil {

    public static final String ADDRESS = "address";

    public static void putAddress(Context context,String id,String name,String phone,String address){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ADDRESS,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear().apply();
        edit.putString("id",id);
        edit.putString("name",name);
        edit.putString("phone",phone);
        edit.putString("address",address);
        edit.apply();
    }
    public static void delectAddress(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ADDRESS,Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    public static String getAddress(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ADDRESS,Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", null);
    }
    public static String getName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ADDRESS,Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", null);
    }
    public static String getPhone(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ADDRESS,Context.MODE_PRIVATE);
        return sharedPreferences.getString("phone", null);
    }
    public static String getDetailsAddress(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(ADDRESS,Context.MODE_PRIVATE);
        return sharedPreferences.getString("address", null);
    }

}
