package com.yc.qqzz.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by ccc on 2020/9/15.
 *  baseHolder基类
 */
public abstract class BaseHolder<T> {
    protected View contentView;
    protected Activity mActivity;

    public BaseHolder(Activity mActivity) {
        this.mActivity = mActivity;
        this.contentView = initView();

    }

    /**
     * 得到holder的布局
     *
     * @return view
     */
    public View getContentView() {
        return contentView;
    }

    /**
     * 初始化布局
     *
     * @return
     */
    public abstract View initView();

    /**
     * 初始化数据
     */
    public abstract void bindData(Object obj);

    /**
     * 初始化数据
     */
    public void bindData(T obj, int position) {

    }
}
