package com.yc.rrdsprj.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.rrdsprj.R;

import java.lang.reflect.Field;


public class ToastUtilsViewsTwo {
    private static Toast toast;
    private static Toast toastTwo;
    private static Toast toastThree;


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


    public static void showToast(String msg) {

    }



    public static void showCenterToastTwo(int huoli) {
        if (App.getInstance() == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT !=25) {
                if (RomUtil.isVivo()&& Build.VERSION.SDK_INT ==22){

                }else {
                    toastTwo = new Toast(App.getInstance());
                    View view=LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_two, null);
                    TextView textView=view.findViewById(R.id.tv_contents);
                    TextView tv_money=view.findViewById(R.id.tv_money);
                    ImageView iv_type=view.findViewById(R.id.iv_type);
                    textView.setText("活力值+");
                    tv_money.setText(huoli+"");
                    iv_type.setVisibility(View.VISIBLE);
                    toastTwo.setView(view);
                    toastTwo.setGravity(Gravity.BOTTOM, 0, 200);
                    toastTwo.setDuration(Toast.LENGTH_LONG);
                    if (AppSettingUtils.isShowToast()){
                        hook(toastTwo);
                    }
                    toastTwo.show();
                }
            }
        }catch (Exception e){

        }
    }


}
