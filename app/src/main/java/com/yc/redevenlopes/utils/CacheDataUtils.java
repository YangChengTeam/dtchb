package com.yc.redevenlopes.utils;


import com.google.gson.Gson;


/**
 * Version: 1.0
 */
public class CacheDataUtils {
    private static CacheDataUtils instance;
    private Gson gson;

    public static CacheDataUtils getInstance() {
        if (instance == null) {
            synchronized (CacheDataUtils.class) {
                if (instance == null) {
                    instance = new CacheDataUtils();
                }
            }
        }
        return instance;
    }

    public Gson getGson() {
        if (gson == null) {
            synchronized (CacheDataUtils.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }


}
