package com.yc.redguess.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.redguess.R;

public class ToastUtilsViews {
    private static Toast toast;
    private static Toast toastTwo;
    private static Toast toastThree;
    public static void showCenterToast(String type,String styContents) {
        if (App.getInstance() == null) {
            return;
        }
        try {
            toast = new Toast(App.getInstance());
            View view=LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view, null);
            TextView textView=view.findViewById(R.id.tv_contents);
            TextView tv_money=view.findViewById(R.id.tv_money);
            ImageView iv_type=view.findViewById(R.id.iv_type);
            ImageView iv_typetwo=view.findViewById(R.id.iv_typeTwo);
            if ("1".equals(type)){//夺宝券
                textView.setText("夺宝券+");
                tv_money.setText("1");
                iv_type.setVisibility(View.VISIBLE);
                iv_typetwo.setVisibility(View.GONE);
            }else {
                textView.setText("红包+");
                tv_money.setText(styContents+"元");
                iv_type.setVisibility(View.GONE);
                iv_typetwo.setVisibility(View.VISIBLE);
            }
            toast.setView(view);
            toast.setGravity(Gravity.BOTTOM, 0, 350);
            toast.show();
        }catch (Exception e){

        }
    }

    public static void showCenterToastTwo(String type,String styContents) {
        if (App.getInstance() == null) {
            return;
        }
        try {
            toastTwo = new Toast(App.getInstance());
            View view=LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view, null);
            TextView textView=view.findViewById(R.id.tv_contents);
            TextView tv_money=view.findViewById(R.id.tv_money);
            ImageView iv_type=view.findViewById(R.id.iv_type);
            ImageView iv_typetwo=view.findViewById(R.id.iv_typeTwo);
            if ("1".equals(type)){//夺宝券
                textView.setText("夺宝券+");
                tv_money.setText("1");
                iv_type.setVisibility(View.VISIBLE);
                iv_typetwo.setVisibility(View.GONE);
            }else {
                textView.setText("红包+");
                tv_money.setText(styContents+"元");
                iv_type.setVisibility(View.GONE);
                iv_typetwo.setVisibility(View.VISIBLE);
            }
            toastTwo.setView(view);
            toastTwo.setGravity(Gravity.CENTER, 0, 0);
            toastTwo.show();
        }catch (Exception e){

        }
    }

    public static void showCenterToastThree() {
        if (App.getInstance() == null) {
            return;
        }
        try {
            toastThree = new Toast(App.getInstance());
            View view=LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three, null);
            toastThree.setView(view);
            toastThree.setDuration(Toast.LENGTH_LONG);
            toastThree.setGravity(Gravity.BOTTOM, 0, 280);
            toastThree.show();
        }catch (Exception e){

        }
    }

}
