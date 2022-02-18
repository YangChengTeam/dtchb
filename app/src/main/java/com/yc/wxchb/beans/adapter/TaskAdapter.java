package com.yc.wxchb.beans.adapter;


import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wxchb.R;
import com.yc.wxchb.beans.module.beans.TaskBeans;

import java.util.List;

public class TaskAdapter extends BaseQuickAdapter<TaskBeans, BaseViewHolder> {
    public TaskAdapter(@Nullable List<TaskBeans> data) {
        super(R.layout.task_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskBeans item) {
      ((TextView) helper.getView(R.id.tv_title)).setText("");
    }
}
