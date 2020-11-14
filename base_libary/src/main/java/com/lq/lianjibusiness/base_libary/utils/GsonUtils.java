package com.lq.lianjibusiness.base_libary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    private static   Gson gson;

    public static Gson buildGson() {
        if(gson == null) {
            gson =new GsonBuilder()
                    .registerTypeAdapter(Integer.class,new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class,new IntegerDefault0Adapter())
                    .create();
        }
        return gson;
    }
}
