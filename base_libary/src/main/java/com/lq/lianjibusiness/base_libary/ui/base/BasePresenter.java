package com.lq.lianjibusiness.base_libary.ui.base;

/**
 * Created by ccc on 2020/9/15.
 * Presenter基类
 */
public interface BasePresenter<T extends BaseView>{

    void attachView(T view);

    void detachView();

    void onStart();

    void onStop();

    void showNetPage();

    void showError(String tip,String state);

    void getNetMistakeData();

    void toLogin();

    void showServiceError();

    void showNetError();

    void closeDialog();

    void showWaiteDialog();

    void complete();
}
