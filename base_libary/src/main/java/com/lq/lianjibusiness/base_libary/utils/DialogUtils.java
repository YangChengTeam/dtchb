package com.lq.lianjibusiness.base_libary.utils;

import android.app.Activity;
import android.view.View;

import com.lq.lianjibusiness.AlertDialog;


/**
 * Created by ccc on 2020/9/15.
 */

public class DialogUtils {

    private DialogUtils() {
    }

    public static void dialogTip(String tip, Activity mActivity) {
        dialogBuilder(tip, mActivity, null, null, "提示", "取消", "确定", false);
    }

    public static void dialogTipPositive(String tip, Activity mActivity, View.OnClickListener listener) {
        dialogBuilder(tip, mActivity, null, listener, "提示", "取消", "确定", false);
    }

    public static void dialogTipCancel(String tip, Activity mActivity, View.OnClickListener enter) {
        dialogBuilder(tip, mActivity, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, enter, "提示", "取消", "确定", false);
    }

    public static void dialogTipCancel(String tip,String titles,String cancelText, String enterText, Activity mActivity, View.OnClickListener enter, View.OnClickListener cancel) {
        dialogBuilder(tip, mActivity, cancel, enter, titles, cancelText, enterText, false);
    }

    /**
     * @param msg        提示问题
     * @param mActivity  activity
     * @param cancel     是否需要取消按钮
     * @param positive   确定按钮的回调可传入空
     * @param tip        提示标题
     * @param cancelText 取消的文字
     * @param enterText  确定的文字
     * @param isLeft     是否左对齐
     */
    public static void dialogBuilder(String msg, Activity mActivity, View.OnClickListener cancel, View.OnClickListener positive, String tip, String cancelText, String enterText, boolean isLeft) {
        if (mActivity != null && !mActivity.isFinishing()) {
            AlertDialog dialog = new AlertDialog(mActivity)
                    .builder()
                    .setTitle(tip)
                    .setMsg(msg)
                    .setCancelable(false);
            if (cancel != null) {
                dialog.setNegativeButton(cancelText, cancel);
            }
            if (positive == null) {
                dialog.setPositiveButton(enterText, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            } else {
                dialog.setPositiveButton(enterText, positive);
            }
            if (isLeft) {
                dialog.needLeftTip(true);
            }
            dialog.show();
        }
    }


}
