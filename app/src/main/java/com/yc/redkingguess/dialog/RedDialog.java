package com.yc.redkingguess.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yc.redkingguess.R;

public class RedDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout layout;
    private Display display;
    public RedDialog(Context context) {
        this.context=context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }
    public View builder(int layoutID) {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                layoutID, null);

        // 获取自定义Dialog布局中的控件
        layout =  view.findViewById(R.id.ll_bg);


        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.center_dialog);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        dialog.setContentView(view);
        // 调整dialog背景大小
        layout.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.8), (int) (display
                .getHeight() * 0.9)));
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return view;
    }
    public void setDismiss(){
        dialog.dismiss();
    }
    public boolean getIsShow(){
        return dialog.isShowing();
    }
    public void  setOutCancle(boolean can){
        if (dialog!=null){
            dialog.setCanceledOnTouchOutside(can);
        }
    }
    public void setShow(){
        dialog.show();
    }

    public void setDismissListen(DialogInterface.OnDismissListener listen){
        dialog.setOnDismissListener(listen);
    }

}
