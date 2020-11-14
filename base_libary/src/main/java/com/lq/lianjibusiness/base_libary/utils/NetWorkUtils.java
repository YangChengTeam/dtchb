package com.lq.lianjibusiness.base_libary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;


import androidx.annotation.RequiresApi;

import com.lq.lianjibusiness.base_libary.App.App;


/**
 * Created by ccc on 2020/9/15.
 */

public class NetWorkUtils {

    public static boolean isNetworkConnected() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return checkState_23orNew();
        } else {
            return checkState_23();
        }
    }

    public static boolean checkState_23() {
        ConnectivityManager connMgr = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        Boolean isMobileConn = networkInfo.isConnected();
        if (isWifiConn || isMobileConn) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean checkState_23orNew() {
        ConnectivityManager connMgr = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks;
        networks = connMgr.getAllNetworks();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < networks.length; i++) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
            if (networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }
}
