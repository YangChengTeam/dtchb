package com.yc.redevenlopes.updata;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;


import java.io.File;
import java.text.DecimalFormat;

public class DownloadUtils {
    public static boolean isPackageInstalled(Context context, String packageName) {
        return getPackageInfo(context, packageName) != null;
    }

    public static PackageInfo getPackageInfo(Context context, String packageName) {
        if (ScreenUtil.isActivityDestory(context))
            return null;

        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return packageInfo;
    }

    public static int getVersionCode(Context context, String packageName) {
        PackageInfo packageInfo = getPackageInfo(context, packageName);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    private static Uri getUriFromFile(Context context, File file) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = getUriFromFileForN(context, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    private static Uri getUriFromFileForN(Context context, File file) {
        Uri fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".DownloadProvider", file);
        return fileUri;
    }

    public static void installApp(Context context, File file) {
        Uri apkUri = getUriFromFile(context, file);
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        context.startActivity(installIntent);
    }

    public static String getHrSize(long size) {
        String hrSize = "";
        double m = size / 1024.0;
        DecimalFormat dec = new DecimalFormat("0.00");
        if (m > 1024 * 1024) {
            m = m / (1024 * 1024);
            hrSize = dec.format(m).concat(" GB");
        } else if (m > 1024.0) {
            m = m / 1024.0;
            hrSize = dec.format(m).concat(" MB");
        } else {
            hrSize = dec.format(m).concat(" KB");
        }
        return hrSize;
    }



    public static PackageInfo getPackageInfoByFile(Context context, File file) {
        if (ScreenUtil.isActivityDestory(context)) return null;
        PackageInfo packageInfo = null;
        try {
            PackageManager pm = context.getPackageManager();
            packageInfo = pm.getPackageArchiveInfo(file.getAbsolutePath(), 0);
        } catch (Exception e) {

        }
        return packageInfo;
    }

    public static int getVersionCodeByFile(Context context, File file) {
        int versionCode = 0;
        PackageInfo info = getPackageInfoByFile(context, file);
        if (info != null) {
            versionCode = info.versionCode;
        }
        return versionCode;
    }

    public static String getPackageNameByFile(Context context, File file) {
        String packeName = "";
        PackageInfo info = getPackageInfoByFile(context, file);
        if (info != null && info.packageName != null) {
            packeName = info.packageName;
        }
        return packeName;
    }




    public static void uninstallApp(Context context, String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String getStatusString(int status) {
        switch (status) {
            case DownloadStatus.DOWNLOADED:
                return "安装";
            case DownloadStatus
                    .DOWNLOADING:
                return "暂停";
            case DownloadStatus
                    .UNDOWNLOAD:
                return "下载";
            case DownloadStatus
                    .INSTALLED:
                return "打开";
            case DownloadStatus
                    .Error:
                return "错误";
            case DownloadStatus
                    .Stop:
                return "继续";
            case DownloadStatus
                    .WAITING:
                return "等待中";
            default:
                return "下载";
        }
    }

    public static DownloadStatusColor getStatusColor(int status) {
        DownloadStatusColor statusColor = new DownloadStatusColor();
        return statusColor;
    }


    public static boolean isWifi(Context context) {
        if (ScreenUtil.isActivityDestory(context))
            return true;

        boolean flag = true;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                flag = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
            }
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                return false;
            }
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                flag = false;
            }
        }
        return flag;
    }




}
