package com.lq.lianjibusiness.base_libary.http;


import android.text.TextUtils;
import android.util.Log;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * 作者：zzr
 * 创建日期：2018/3/29
 * 描述：
 */
public abstract class ResultRefreshSubscriber<T> extends ResourceSubscriber<HttpResult<T>>{

    public ResultRefreshSubscriber() {

    }

    @Override
    public void onNext(HttpResult<T> tHttpResult) {
        int status = tHttpResult.getCode();
        if (status==1) {
            onAnalysisNext(tHttpResult.getData());
        } else if (status==0) {
            if (!TextUtils.isEmpty(tHttpResult.getMsg())){
                ToastUtil.showToast(tHttpResult.getMsg());
            }
            errorState(tHttpResult.getMsg(), tHttpResult.getCode()+"");
        } else {
            errorState(tHttpResult.getMsg(), tHttpResult.getCode()+"");
        }
    }

    public void errorState(String message, String state) {

    }


    public abstract void onAnalysisNext(T data);


    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
        errorState("","");

    }

    @Override
    public void onComplete() {

    }
}
