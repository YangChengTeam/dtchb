package com.lq.lianjibusiness.base_libary.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.lq.lianjibusiness.base_libary.App.App;
import com.lq.lianjibusiness.base_libary.R;


public class ToastUtil {
    private static Toast toast;
    private static Toast toastTwo;
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
