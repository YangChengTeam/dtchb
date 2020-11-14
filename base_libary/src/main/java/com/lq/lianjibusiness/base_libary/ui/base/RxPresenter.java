package com.lq.lianjibusiness.base_libary.ui.base;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by ccc on 2020/9/15.
 */

public class RxPresenter<T extends BaseView> implements BasePresenter<T> {
    public final String TAG = this.getClass().getSimpleName();
    public T mView;

    public CompositeDisposable mDisposables;
    //public RetrofitHelper mRetrofitHelper;

    @Override
    public void attachView(T view) {
        this.mView = view;
        //mRetrofitHelper = RetrofitHelper.getInstance();
    }

    @Override
    public void detachView() {
        unSubscribe();
        mView = null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void showNetPage() {
        if (mView != null) {
            mView.showNetPage();
        }
    }

    @Override
    public void showError(String tip,String status) {
        if (mView != null) {
            mView.showError(tip,status);
        }
    }

    @Override
    public void getNetMistakeData() {
        getMistakeData();
    }

    public void getMistakeData() {

    }

    public void unSubscribe() {
        if (mDisposables != null) {
            mDisposables.dispose();
        }
    }

    protected void addSubscribe(Disposable disposable) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(disposable);
    }

    @Override
    public void toLogin() {
        if (mView != null) {
            mView.toLogin();
        }
    }

    @Override
    public void showServiceError() {
        if (mView != null) {
            mView.showServicesError();
        }
    }

    @Override
    public void showNetError() {
        if (mView != null) {
            mView.showNetError();
        }
    }

    @Override
    public void closeDialog() {
        if (mView != null) {
            mView.closeWaiteDialog();
        }
    }

    @Override
    public void showWaiteDialog() {
        if (mView != null) {
            mView.showWaiteDialog();
        }
    }

    @Override
    public void complete() {
        if (mView != null) {
            mView.onComplete();
        }
    }
}
