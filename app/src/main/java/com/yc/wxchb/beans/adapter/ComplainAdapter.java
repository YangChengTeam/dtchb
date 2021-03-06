package com.yc.wxchb.beans.adapter;


import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wxchb.R;
import com.yc.wxchb.beans.module.beans.ComplainBeans;

import java.util.List;


public class ComplainAdapter extends BaseQuickAdapter<ComplainBeans, BaseViewHolder> {
    public ComplainAdapter(@Nullable List<ComplainBeans> data) {
        super(R.layout.complain_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComplainBeans item) {
      ((TextView) helper.getView(R.id.tv_name)).setText(item.getTitle());
    }
}
