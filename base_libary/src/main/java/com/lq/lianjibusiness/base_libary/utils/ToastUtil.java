package com.lq.lianjibusiness.base_libary.utils;

import android.widget.Toast;

import com.lq.lianjibusiness.base_libary.App.App;


public class ToastUtil {
    private static Toast toast;

    public static void showToast(String text) {
        if (App.getInstance() == null) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
}
