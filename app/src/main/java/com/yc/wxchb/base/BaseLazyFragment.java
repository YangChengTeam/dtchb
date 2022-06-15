package com.yc.wxchb.base;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lq.lianjibusiness.base_libary.App.Constants;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.lq.lianjibusiness.base_libary.ui.base.NetFragment;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.lq.lianjibusiness.base_libary.utils.PrefUtils;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.di.component.DaggerFragmentMainComponent;
import com.yc.wxchb.di.component.FragmentMainComponent;
import com.yc.wxchb.di.module.FragmentMainModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by ccc on 2020/9/15.
 */

public abstract class BaseLazyFragment<T extends RxPresenter> extends NetFragment implements BaseView {
    public final String TAG = this.getClass().getSimpleName();

    protected View mRootView;
    protected Activity mActivity;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;
    private Unbinder bind;

    @Inject
    public T mPresenter;

    protected FragmentMainComponent getFragmentComponent() {
        return DaggerFragmentMainComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .fragmentMainModule(getFragmentModule())
                .build();
    }

    protected FragmentMainModule getFragmentModule() {
        return new FragmentMainModule(this);
    }

    //--------------------system method callback------------------------//
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;

        mActivity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = super.onCreateView(inflater, container, savedInstanceState);
        }
        bind = ButterKnife.bind(this, mRootView);
        initInject();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {

            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public synchronized void toLogin() {
        /*if (SystemUtil.isLogin()) {
            ToastUtil.showToast("登陆已过期");
            PrefUtils.putString(Constants.SP_TOKEN, "");
            Log.e(TAG, "fragment");
            ActivityJumpUtils.ToOtherActivity(LoginStepOneActivity.class, mActivity, null);
            ImExitUtils.exitIm();
        }*/
//        PrefUtils.putString(Constants.SP_TOKEN, "");
//        PrefUtils.putString(Constants.SP_ACCOUNT, "");
//        PrefUtils.putString(Constants.SP_REALNAME, "");
//        PrefUtils.putInt(Constants.SP_STATUS, -1);
//        PrefUtils.putString(Constants.SP_HEADPORTRAIT, "");
//        PrefUtils.putLong(Constants.SP_ID, -1);
//        PrefUtils.putLong(Constants.SP_STOREID, -1);
       // ActivityUtils.switchTo(mActivity, LoginActivity.class);
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(PrefUtils.getString(Constants.SP_TOKEN, ""));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null) {
            bind.unbind();
        }
        mPresenter.detachView();
    }


    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst || mRootView == null) {
            return;
        }
        initLazyData();
        isFirst = false;
    }

    @Override
    public void showError(String msg,String status) {
//        DialogUtils.dialogTip(msg, mActivity);
        if(TextUtils.equals(status, "-9")){
            ToastUtil.showToast("网络开小差！");
        }else{
            if (!TextUtils.isEmpty(msg)){
                ToastUtil.showToast(msg);
            }
        }
    }

    @Override
    public void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)){
            ToastUtil.showToast(msg);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void mistakeLoadData() {
        mPresenter.getMistakeData();
    }

    protected abstract void initInject();

    /**
     * fragment被设置为不可见时调用
     */
    protected abstract void onInvisible();

    /**
     * 这里获取数据，刷新界面
     */
    protected abstract void initLazyData();

    @Override
    public void onComplete() {

    }
}
