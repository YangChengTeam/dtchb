package com.lq.lianjibusiness.base_libary.utils;

import android.os.Build;
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
        try {
            if (Build.VERSION.SDK_INT !=25) {
                if (toast == null) {
                    toast = Toast.makeText(App.getInstance(), text, Toast.LENGTH_LONG);
                } else {
                    toast.setText(text);
                }
                toast.show();
            }
        }catch (Exception e){

        }
    }

    public static void showToastTwo(String text) {
        if (App.getInstance() == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT !=25) {
                if (toastTwo == null) {
                    toastTwo = Toast.makeText(App.getInstance(), text, Toast.LENGTH_LONG);
                } else {
                    toastTwo.setText(text);
                }
                toastTwo.setGravity(Gravity.CENTER,0,0);
                toastTwo.show();
            }
        }catch (Exception e){

        }
    }

}
