package com.yc.redkingguess.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by ccc on 2020/9/15.
 * listviewAdpter的基类
 */
public abstract class BaseListViewHolder<T> {
    protected View contentView;
    protected Activity mActivity;

    public BaseListViewHolder(Activity mActivity) {
        this.mActivity = mActivity;
        contentView = initView();
        contentView.setTag(this);
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
     * 绑定数据
     *
     * @param obj
     */
    public abstract void bindData(T obj);
}
