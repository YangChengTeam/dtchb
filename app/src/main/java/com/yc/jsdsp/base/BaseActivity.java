package com.yc.jsdsp.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lq.lianjibusiness.base_libary.App.Constants;
import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.lq.lianjibusiness.base_libary.ui.base.NetActivity;
import com.lq.lianjibusiness.base_libary.utils.PrefUtils;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;



import com.yc.jsdsp.R;
import com.yc.jsdsp.application.MyApplication;
import com.yc.jsdsp.di.component.ActivityMainComponent;
import com.yc.jsdsp.di.component.DaggerActivityMainComponent;
import com.yc.jsdsp.di.module.ActivityMainModule;


import javax.inject.Inject;

import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ccc on 2020/9/15.
 */

public abstract class BaseActivity<T extends BasePresenter> extends NetActivity implements BaseView {
    @Inject
    public T mPresenter;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        mUnBinder = ButterKnife.bind(this);
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
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

    public void setFullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(1280 | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public abstract void initEventAndData();

    public abstract void initInject();

    protected ActivityMainComponent getActivityComponent() {
        return DaggerActivityMainComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityMainModule(getActivityModule())
                .build();
    }

    protected ActivityMainModule getActivityModule() {
        return new ActivityMainModule(this);
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.onStop();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void mistakeLoadData() {
        mPresenter.getNetMistakeData();
    }

    @Override
    public synchronized void toLogin() {
        //如果需要到主页面的跳转,需要给userinfo给一个状态
       /* String token = PrefUtils.getString(Constants.SP_TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            PrefUtils.putString(Constants.SP_TOKEN, "");
            ActivityJumpUtils.ToOtherActivity(LoginStepOneActivity.class, this, null);
            ImExitUtils.exitIm();
        }*/
//        PrefUtils.putString(Constants.SP_TOKEN, "");
//        PrefUtils.putString(Constants.SP_ACCOUNT, "");
//        PrefUtils.putString(Constants.SP_REALNAME, "");
//        PrefUtils.putInt(Constants.SP_STATUS, -1);
//        PrefUtils.putString(Constants.SP_HEADPORTRAIT, "");
//        PrefUtils.putLong(Constants.SP_ID, -1);
//        PrefUtils.putLong(Constants.SP_STOREID, -1);

        // ActivityUtils.switchTo(this, LoginActivity.class);
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(PrefUtils.getString(Constants.SP_TOKEN, ""));
    }

    @Override
    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ToastUtil.showToast(msg);
        }
    }

    @Override
    public void onComplete() {

    }
}

