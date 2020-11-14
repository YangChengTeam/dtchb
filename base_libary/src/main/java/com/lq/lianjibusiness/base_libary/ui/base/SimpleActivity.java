package com.lq.lianjibusiness.base_libary.ui.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lq.lianjibusiness.base_libary.R;
import com.lq.lianjibusiness.base_libary.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ccc on 2020/9/15.
 * 无MVP的activity基类
 */

public abstract class SimpleActivity extends SupportActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initDialog();
        setTranslucentStatus();
        initEventAndData();

    }
    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));
        } else if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
    }

    private KProgressHUD show;
    private boolean isNeedDialog;

    private void initDialog() {
        isNeedDialog = isNeedDialog();
        if (isNeedDialog) {
            show = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true);
        }
    }
    public boolean isNeedDialog() {
        return true;
    }
    public void showWaiteDialog() {
        try {
            if (this.isFinishing())
                return;
            if (show != null && !show.isShowing()) {
                show.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void closeWaiteDialog() {
        try {
            if (show != null && show.isShowing()) {
                show.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();

    public void setStateBar(View top) {
        int i = StatusBarUtil.StatusBarLightMode(this);
        if(i == 0) {
            top.setBackgroundResource(R.drawable.top_view_bg);
        }
    }

}
