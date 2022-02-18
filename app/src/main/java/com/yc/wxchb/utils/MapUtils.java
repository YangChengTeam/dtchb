package com.yc.wxchb.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.lq.lianjibusiness.base_libary.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: huangcg
 * @date: 2018/9/6
 * @Version:
 */
public class MapUtils {

    /**
     *  高德地图
     * @param mContext
     * @param lat 纬度
     * @param lng 经度
     */
    public static void gaoDe(Context mContext, String lat, String lng,String adr){
        if (isAvilible(mContext, "com.autonavi.minimap")) {
            try{
                String url="amapuri://route/plan/?sid=BGVIS1&slat=&slon=&sname=&did=&dlat="+lat+"&dlon="+lng+"&dname="+adr+"&dev=0&t=0";
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                mContext.startActivity(intent);

            } catch (Exception e)
            {e.printStackTrace(); }
        }else{
            ToastUtil.showToast("您尚未安装高德地图");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            mContext.startActivity(intent);
        }
    }

    /**
     * 启动腾讯地图App进行导航
     * @param address 目的地
     * @param latStr 必填 纬度
     * @param lonStr 必填 经度
     */
    public static void gotoTengxun(Context context, String address, String latStr, String lonStr) {
        if (isAvilible(context, "com.tencent.map")) {
            double lat=0 ,lon=0;
            if (!TextUtils.isEmpty(latStr)){
                lat=Double.parseDouble(latStr);
            }
            if (!TextUtils.isEmpty(lonStr)){
                lon=Double.parseDouble(lonStr);
            }
            // 启动路径规划页面
            Intent naviIntent = new Intent("android.intent.action.VIEW", Uri.parse("qqmap://map/routeplan?type=drive&from=&fromcoord=&to="+ address + "&tocoord=" + lat + "," + lon + "&policy=0&referer=appName"));
            context.startActivity(naviIntent);
        }else {
            ToastUtil.showToast("您尚未安装腾讯地图");
            Uri uri = Uri.parse("market://details?id=com.tencent.map");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }


    /**
     * 启动百度App进行导航
     * @param address 目的地
     * @param latStr 必填 纬度
     * @param lonStr 必填 经度
     */
    public static void goToBaiduActivity(Context context, String address, String latStr, String lonStr) {
        if (isAvilible(context, "com.baidu.BaiduMap")) {
            double lat = 0, lon = 0;
            if (!TextUtils.isEmpty(latStr)) {
                lat = Double.parseDouble(latStr);
            }
            if (!TextUtils.isEmpty(lonStr)) {
                lon = Double.parseDouble(lonStr);
            }

            //启动路径规划页面
            String url="baidumap://map/direction?origin=我的位置&destination="+address+"&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
            Intent naviIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            context.startActivity(naviIntent);
        }else {
            ToastUtil.showToast("您尚未安装百度地图");
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }
    /* 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName){
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if(packageInfos != null){
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }


    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }
}
