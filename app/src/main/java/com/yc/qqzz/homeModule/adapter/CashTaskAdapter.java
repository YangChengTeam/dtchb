package com.yc.qqzz.homeModule.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.bean.CashTaskBeans;

import java.util.List;

public class CashTaskAdapter extends BaseQuickAdapter<CashTaskBeans, BaseViewHolder> {
    public CashTaskAdapter( @Nullable List<CashTaskBeans> data) {
        super(R.layout.cashtask_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CashTaskBeans item) {

    }
}
