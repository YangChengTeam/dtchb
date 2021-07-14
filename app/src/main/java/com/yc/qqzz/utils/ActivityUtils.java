package com.yc.qqzz.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


/**
 * @Description Activity工具管理
 */
public class ActivityUtils {

    /**
     * 带参数进行Activity跳转
     */
    public static void switchTo(Context context,
                                Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 带参数进行Activity跳转
     *
     * @param targetActivity 目标Activity
     */
    public static void switchTo(Activity context,
                                Class<? extends Fragment> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        switchTo(context, intent);
    }

    /**
     * 带参数进行Activity跳转
     *
     * @param context        context
     * @param targetActivity 目标Activity
     * @param bundle         参数
     */
    public static void switchTo(Context context,
                                Class<? extends Activity> targetActivity, Bundle bundle) {
        Intent intent = new Intent(context, targetActivity);
        intent.putExtras(bundle);
        switchTo(context, intent);
    }


    /**
     * 带参数进行Activity跳转
     *
     * @param activity       当前Activity
     * @param targetActivity 目标Activity
     * @param bundle         参数
     */
    public static void switchTo(Activity activity,
                                Class<? extends Activity> targetActivity, Bundle bundle) {
        Intent intent = new Intent(activity, targetActivity);
        intent.putExtras(bundle);
        switchTo(activity, intent);
    }

    /**
     * 带参数进行Activity跳转
     *
     * @param activity       当前Activity
     * @param targetActivity 目标Activity
     */
    public static void switchTo(Context activity,
                                Class<? extends Activity> targetActivity, String... params) {
        Intent intent = new Intent(activity, targetActivity);
        for (int i = 0; i < params.length; i += 2) {
            intent.putExtra(params[i], params[i + 1]);
        }
        switchTo(activity, intent);
    }

    /**
     * 跳转到某个Activity
     *
     * @param activity       当前Activity
     * @param targetActivity 目标Activity
     */
    public static void switchTo(Context activity,
                                Class<? extends Activity> targetActivity) {
        switchTo(activity, new Intent(activity, targetActivity));
    }

    /**
     * 根据给定的Intent进行Activity跳转
     *
     * @param activity 当前Activity
     * @param intent   目标Activity的Intent
     */
    public static void switchTo(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    /**
     * 根据给定的Intent进行Activity跳转
     *
     * @param activity 当前Activity
     */
    public static void switchToForResult(Activity activity,
                                         Class<? extends Activity> targetActivity, int requestCode) {
        Intent intent = new Intent(activity, targetActivity);
        switchToForResult(activity, intent, requestCode);
    }

    /**
     * 根据给定的Intent进行Activity跳转
     *
     * @param activity 当前Activity
     */
    public static void switchToForResult(Activity activity,
                                         Class<? extends Activity> targetActivity) {
        Intent intent = new Intent(activity, targetActivity);
        switchToForResult(activity, intent, 1);
    }

    /**
     * 根据给定的Intent进行Activity跳转
     *
     * @param activity       当前Activity
     * @param targetActivity
     */
    public static void switchToForResult(Activity activity,
                                         Class<? extends Activity> targetActivity, int requestCode,
                                         String... params) {
        Intent intent = new Intent(activity, targetActivity);
        for (int i = 0; i < params.length; i += 2) {
            intent.putExtra(params[i], params[i + 1]);
        }
        switchToForResult(activity, intent, requestCode);
    }

    /**
     * 根据给定的Intent进行Activity跳转
     *
     * @param activity
     * @param targetActivity
     * @param requestCode
     * @param bundle
     */
    public static void switchToForResult(Activity activity,
                                         Class<? extends Activity> targetActivity, int requestCode,
                                         Bundle bundle) {
        Intent intent = new Intent(activity, targetActivity);
        intent.putExtras(bundle);
        switchToForResult(activity, intent, requestCode);
    }

    /**
     * 根据给定的Intent进行Activity跳转
     *
     * @param activity 当前Activity
     * @param intent   目标Activity的Intent
     */
    public static void switchToForResult(Activity activity, Intent intent,
                                         int requestCode) {
        activity.startActivityForResult(intent, requestCode);
        // activity.overridePendingTransition(R.anim.in_from_right,
        // R.anim.out_from_left);
    }

//    /**
//     * 获取当前设置的电话号码
//     *
//     * @param context
//     * @return
//     */
//    public static String getNativePhoneNumber(Context context) {
//        TelephonyManager telephonyManager = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
//        }
//        String mobile = telephonyManager.getLine1Number();
//        if (!TextUtils.isEmpty(mobile) && mobile.startsWith("+86")) {
//            mobile = mobile.substring(3);
//        }
//        return mobile;
//    }
//
//    /**
//     * 拨号
//     *
//     * @param phoneNum 电话号码
//     * @param activity
//     */
//    public static void callPhoneNumber(String phoneNum, Activity activity, Context context) {
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
//                + phoneNum));
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//        activity.startActivity(intent);
//    }
}
