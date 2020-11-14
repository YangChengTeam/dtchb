package com.lq.lianjibusiness.base_libary.ui.base;

/**
 * Created by ccc on 2020/9/15.
 * view的基类
 */

public interface BaseView {

    void showError(String msg,String status);

    void toLogin();

    void showWaiteDialog();

    void closeWaiteDialog();

    void showNetPage();

    void showServicesError();

    void showNetError();

    void showToast(String msg);

    void onComplete();
}
