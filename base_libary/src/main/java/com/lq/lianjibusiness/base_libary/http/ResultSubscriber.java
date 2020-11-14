package com.lq.lianjibusiness.base_libary.http;


import android.text.TextUtils;
import android.util.Log;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by ccc on 2020/9/15.
 * 描述：
 */
public abstract class ResultSubscriber<T> extends ResourceSubscriber<HttpResult<T>> {
    private BasePresenter mPresenter;

    public ResultSubscriber(BasePresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onNext(HttpResult<T> tHttpResult) {
        int status = tHttpResult.getCode();
        if (status==1) {
            mPresenter.closeDialog();
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
        mPresenter.showError(message,state);
    }


    public abstract void onAnalysisNext(T data);


    @Override
    public void onError(Throwable t) {
        Log.d("ccc", "---onError: "+ t.getMessage());
        t.printStackTrace();
        errorState("","");
        mPresenter.closeDialog();
    }

    @Override
    public void onComplete() {
        mPresenter.closeDialog();
        mPresenter.complete();
    }
}
