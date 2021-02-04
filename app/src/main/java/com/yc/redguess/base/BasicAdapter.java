package com.yc.redguess.base;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by ccc on 2020/9/15.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {

    protected List<T> mList;
    protected Activity mActivity;

    public BasicAdapter(List<T> list, Activity activity) {
        this.mList = list;
        this.mActivity = activity;
    }

    public void setList(List<T> list) {
        this.mList = list;
    }

    public List<T> getList() {
        return mList;
    }


    @Override
    public int getCount() {

        if (null == mList) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseListViewHolder holder = null;
        if (convertView == null) {
            holder = getHolder();
            convertView = holder.getContentView();
        } else {
            holder = (BaseListViewHolder) convertView.getTag();
        }
        if (null != mList && mList.size() > 0) {
            holder.bindData(mList.get(position));
        }
        interpolator(holder ,position);
        return convertView;
    }

    public void interpolator(BaseListViewHolder holder , int position) {

    }

    public abstract BaseListViewHolder getHolder();


}
