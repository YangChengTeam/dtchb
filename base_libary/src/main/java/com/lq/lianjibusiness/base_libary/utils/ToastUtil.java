package com.lq.lianjibusiness.base_libary.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lq.lianjibusiness.base_libary.App.App;
import com.lq.lianjibusiness.base_libary.R;

import java.lang.reflect.Field;


public class ToastUtil {
    private static Toast toast;
    private static Toast toastTwo;
    private static Toast toastThree;
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
                if (isShowToast()){
                    hook(toast);
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

    public static void showToastThree(String text) {
        if (App.getInstance() == null) {
            return;
        }
        try {
            toastThree = new Toast(App.getInstance());
            View view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_four, null);
            toastThree.setView(view);
            toastThree.setDuration(Toast.LENGTH_LONG);
            toastThree.setGravity(Gravity.CENTER, 0, 0);
            if (isShowToast()){
                hook(toastThree);
            }
            toastThree.show();
        }catch (Exception e){

        }

    }



    private static Field sFieldTN;
    private static Field sFieldTNHandler;

    static {
        try {
            sFieldTN = Toast.class.getDeclaredField("mTN");
            sFieldTN.setAccessible(true);
            sFieldTNHandler = sFieldTN.getType().getDeclaredField("mHandler");
            sFieldTNHandler.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void hook(Toast toast) {
        try {
            Object tn = sFieldTN.get(toast);
            Handler preHandler = (Handler) sFieldTNHandler.get(tn);
            sFieldTNHandler.set(tn, new SafelyHandlerWrapper(preHandler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SafelyHandlerWrapper extends Handler {
        private Handler impl;

        SafelyHandlerWrapper(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void handleMessage(Message msg) {
            impl.handleMessage(msg);
        }
    }
    public static boolean isShowToast(){
        if (Build.VERSION.SDK_INT==22||Build.VERSION.SDK_INT==25){
            return false;
        }else {
            return true;
        }
    }

}
