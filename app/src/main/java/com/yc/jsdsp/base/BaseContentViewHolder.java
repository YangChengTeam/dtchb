package com.yc.jsdsp.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ccc on 2020/9/15.
 */

public abstract class BaseContentViewHolder<T> {

    public Activity mActivity;
    private final View contentView;
    public Unbinder bind;

    public BaseContentViewHolder(Activity mActivity) {
        this.mActivity = mActivity;
        contentView = initView(mActivity.getLayoutInflater());
        if (contentView != null) {
            bind = ButterKnife.bind(this, contentView);
        }
    }

    public abstract View initView(LayoutInflater layoutInflater);

    public abstract void bindData(T t);

    public void onDestroy() {
        mActivity = null;
        if (bind != null) {
            bind.unbind();
        }
    }

    public View getContentView() {
        return contentView;
    }
}
