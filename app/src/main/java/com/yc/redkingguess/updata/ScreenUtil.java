package com.yc.redkingguess.updata;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class ScreenUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }

    @SuppressLint("NewApi")
    public static int getHeight(Context paramContext) {
        if (Build.VERSION.SDK_INT >= 13) {
            Display localDisplay = ((WindowManager) paramContext.getSystemService(Application.WINDOW_SERVICE)).getDefaultDisplay();
            Point localPoint = new Point();
            localDisplay.getSize(localPoint);
            return localPoint.y;
        }
        return paramContext.getResources().getDisplayMetrics().heightPixels;
    }

    @SuppressLint("NewApi")
    public static int getWidth(Context paramContext) {
        if (Build.VERSION.SDK_INT >= 13) {
            Display localDisplay = ((WindowManager) paramContext.getSystemService(Application.WINDOW_SERVICE)).getDefaultDisplay();
            Point localPoint = new Point();
            localDisplay.getSize(localPoint);
            return localPoint.x;
        }
        return paramContext.getResources().getDisplayMetrics().widthPixels;
    }

    public static boolean isActivityDestory(Context context) {
        Activity activity = findActivity(context);
        return activity == null || activity.isFinishing() || (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed());
    }

    public static Activity findActivity(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        } else {
            return null;
        }
    }
}
