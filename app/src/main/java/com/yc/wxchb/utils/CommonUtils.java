package com.yc.wxchb.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.wxchb.application.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * 获取项目资源文件的内容
 */
public class CommonUtils {

    public static Drawable getDrawable(int id) {
        return MyApplication.getInstance().getResources().getDrawable(id);
    }

    public static String getString(int id) {
        return MyApplication.getInstance().getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return MyApplication.getInstance().getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return MyApplication.getInstance().getResources().getColor(id);
    }

    /**
     * 获取dp资源,会自动将dp单位转换成px
     *
     * @param id
     * @return
     */
    public static int getDimens(int id) {
        return MyApplication.getInstance().getResources().getDimensionPixelSize(id);
    }

    /**
     * 得到子view的父类对象移除自己 ,fragmentmanager 里的wrap方法会在外面包裹一层
     *
     * @param child
     */
    public static void removeSelfFromParent(View child) {
        ViewParent parent = child.getParent();
        if (parent != null && parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(child);
        }
    }

    public static String getAppPackage(Context context, String appName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                PackageInfo packageInfo = packageInfos.get(i);
                String names = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
                if (appName.equals(names)){
                    String packName = packageInfo.packageName;
                    return packName;
                }
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return "";
    }
    /**
     * 获取应用版本
     *
     * @param context
     * @return
     */
    public static int getCurrentVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return new PackageInfo().versionCode;
        }
    }


    /**
     * 获取应用版本名字
     *
     * @return
     */
    public static String getCurrentVersionName() {
        try {
            return MyApplication.getInstance().getPackageManager().getPackageInfo(
                    MyApplication.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return new PackageInfo().versionName;
        }
    }


    /**
     * 获取应用版本
     *
     * @param context
     * @return
     */
    public static String getDevice_udid(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        return deviceId == null ? "" : deviceId;
    }

    //设置头部沉浸
    public static void setSoakHead(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * wifi是否连接
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiNetworkInfo.isConnected();
    }


    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return new SimpleDateFormat("yyyy/MM/dd").format(curDate);
    }

    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

//    /*** oss  上传对象 key 定义规则  upload/yyyy/mm/dd/filename_md5.文件后缀
//     * @param pic*/
//    public static String getKey(String pic) {
//        return "upload/" + CommUtils.getCurrentDate() + "/_" + MD5Utils.getFileMD5(pic) + pic.substring(pic.lastIndexOf("."));
//    }

    /**
     * 设置文字前后大小不一致
     */
    public static void setTextAutoSize(TextView textView, String text) {
        SpannableString spannableString = new SpannableString(text);
        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(0.5f);
        //        spannableString.setSpan(sizeSpan01,1, text.indexOf("."), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        RelativeSizeSpan sizeSpan02 = new RelativeSizeSpan(0.5f);
        spannableString.setSpan(sizeSpan01, text.indexOf("."), text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableString.setSpan(sizeSpan02, 0, 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);

    }

    /**
     * 是否滚动到最底部
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        return recyclerView != null && recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
    }

    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        return visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 2 && state == RecyclerView.SCROLL_STATE_IDLE;
        //    LinearLayoutManager layoutManager = (LinearLayoutManager) mSwipeMenuRecyclerView.getLayoutManager();
        //    int totalItemCount = layoutManager.getItemCount();
        //    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        //    if (totalItemCount < (lastVisibleItem + Constant.VISIBLE_THRESHOLD)) {}

    }


    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(View view) {
   /*     ((InputMethodManager) getSystemServce(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);*/
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示键盘
     */
    public static void showKeyBoard(Context context) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * jsonarray to list
     */
    public static ArrayList<Object> jsonArrayToList(JSONArray jsonArray1) throws JSONException {
        ArrayList<Object> listdata = new ArrayList<Object>();
        if (jsonArray1 != null) {
            for (int i = 0; i < jsonArray1.length(); i++) {
                listdata.add(jsonArray1.get(i));
            }
        }
        return listdata;
    }


    /**
     * jsonarray to list
     */
    public static ArrayList<String> jsonArrayToList2(JSONArray jsonArray1) throws JSONException {
        ArrayList<String> listdata = new ArrayList<>();
        if (jsonArray1 != null) {
            for (int i = 0; i < jsonArray1.length(); i++) {
                Object o = jsonArray1.get(i);
                listdata.add(o.toString());
            }
        }
        return listdata;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取IMEI码
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}</p>
     *
     * @return IMEI码
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getDeviceId() : null;
    }


    public static String getDisplay(
            Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        display.getMetrics(metrics);
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            return dm.widthPixels + "*" + dm.heightPixels;
        } catch (Exception e) {
            return metrics.widthPixels + "*" + metrics.heightPixels;
        }
    }


    /**
     * 获取控件的高度
     */
    public static int getViewMeasuredHeight(View view) {
        calculateViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取控件的宽度
     */
    public static int getViewMeasuredWidth(View view) {
        calculateViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * 测量控件的尺寸
     */
    private static void calculateViewMeasure(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        view.measure(w, h);
    }


    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * @param context
     * @return 获取屏幕原始尺寸高度，包括虚拟功能键高度
     */
    public static int getTotalHeight(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }


    /**
     * @param context
     * @return 获取屏幕内容高度不包括虚拟按键
     */
    public static int getScreenHeight1(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    /**
     * 获取app当前的渠道号或application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData() {

        String channelNumber = null;
        try {
            PackageManager packageManager = MyApplication.getInstance().getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(MyApplication.getInstance().getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelNumber = applicationInfo.metaData.getString("UMENG_CHANNEL");
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelNumber;
    }
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 判断App是否安装
     *
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(final String packageName) {
        return !isSpace(packageName) && MyApplication.getInstance().getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 打开App
     *
     * @param packageName 包名
     */
    public static void launchApp(final String packageName) {
        if (isSpace(packageName)) return;
        MyApplication.getInstance().startActivity(MyApplication.getInstance().getPackageManager().getLaunchIntentForPackage(packageName));
    }

    /**
     * 改变电话号码格式
     *
     * @param phoneNum 手机号码
     */
    public static String changePhoneNum(String phoneNum) {
        StringBuilder sb = new StringBuilder();
        return sb.append(phoneNum.substring(0, 3))
                .append("  ")
                .append(phoneNum.substring(3, 7))
                .append("  ")
                .append(phoneNum.substring(7, 11))
                .toString();
    }

    /**
     * make true current connect service is wifi
     *
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 判断一个字符串是否为空
     */
    public static Boolean StringEntry(String s){
        if (s==null||"".equals(s)||s.length()==0){
            return true;
        }
        return false;
    }
    //工具类
    public static int dp2px(Context c, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context c, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    public static float dp2pxF(Context c, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static float sp2pxF(Context c, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    /**
     * 获取版本号(内部识别号)
     * @param context
     * @return
     */
    public static String getAppVersionCode(Context context)//
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取版本名(内部识别号)
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context)//
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
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
        String num = "[1][23456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    @SuppressLint("MissingPermission")
    public  static  String getUid(Context context) {
        String uid = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return uid;
    }

    public static boolean isDestory(Activity activity){
        if (activity==null||activity.isFinishing()||(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1&&activity.isDestroyed())){
            return true;
        }else {
            return false;
        }
    }

    public static int getMeasureWdith(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredWidth();
    }

    public static String date2FromNowTime(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long time = sdf.parse(date).getTime();
            long currentTimeMillis = System.currentTimeMillis();
            long differTime = currentTimeMillis - time;
            long minute = differTime / 1000 / 60;
            if (minute < 5) {
                return "刚刚";
            }
            if (minute < 60) {
                return minute + "分钟前";
            }
            long hour = minute / 60;
            if (hour < 24) {
                return hour + "小时前";
            }
            long day = hour / 24;
            if (day < 365) {
                return day + "天前";
            }
            long year = day / 365;
            return year + "年前";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static int dip2pxss(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2pxss(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static double change(double a){
        return a * Math.PI  / 180;
    }

    public static double changeAngle(double a){
        return a * 180 / Math.PI;
    }


    public static int getRandom(int min, int max){
        Random random = new Random();
        int i = random.nextInt(max) % (max - min + 1) + min;
        return i;
    }


    public static String getMacAddress(boolean isColon) {
        try {
            if (isColon) {
                return MacUtils.getMacAddress().toUpperCase();
            } else {
                return MacUtils.getMacAddress().toUpperCase().replace(":", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static <D> boolean isEmpty(List<D> list) {
        return list == null || list.isEmpty();
    }
    /**
     * 判断是否包含SIM卡
     *
     * @return 状态
     */
    public static boolean hasSimCard(Context context) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // 没有SIM卡
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        return result;
    }
    public static String readAssetsChannel(Context context) {
        String result2 = CommonUtils.getFromAssets(context,"gamechannel.json");
        return result2;
    }

    public static String getFromAssets(Context context,String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 判断是否处于开发者模式
     */

    public static boolean getIsDe(Context context){
        try {
            if (context!=null){
                boolean enableAdb = (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
                if(enableAdb){
                    return true;
                }else {
                    return false;
                }
            }
        }catch (Exception e){

        }
     return false;
    }
    /**
     * 是否开启了代理
     */
    private static boolean isWifiProxy(Context context) {
        try {
            if (context!= null){
                final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
                String proxyAddress;
                int proxyPort;
                if (IS_ICS_OR_LATER) {
                    proxyAddress = System.getProperty("http.proxyHost");
                    String portStr = System.getProperty("http.proxyPort");
                    proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
                } else {
                    proxyAddress = android.net.Proxy.getHost(context);
                    proxyPort = android.net.Proxy.getPort(context);
                }
                return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
            }
        }catch (Exception e){

        }
        return false;
    }

    public static boolean isProxyAndDe(Context context){
       /* if (context!=null&&context instanceof Activity&&!isDestory(((Activity) context))){
            if (isWifiProxy(context)||getIsDe(context)){
                return true;
            }else {
                return false;
            }
        }*/
        return false;
    }

}
