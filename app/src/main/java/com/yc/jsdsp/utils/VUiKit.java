package com.yc.jsdsp.utils;


import android.os.Handler;
import android.os.Looper;

/**

 */
public class VUiKit {
    private static final Handler gUiHandler = new Handler(Looper.getMainLooper());

    public static void post(Runnable r) {
        gUiHandler.post(r);
    }

    public static void postDelayed(long delay, Runnable r) {
        gUiHandler.postDelayed(r, delay);
    }
}
