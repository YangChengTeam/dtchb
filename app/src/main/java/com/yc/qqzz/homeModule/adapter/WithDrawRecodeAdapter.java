package com.yc.qqzz.homeModule.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.bean.WithDrawRecodeBeans;

import java.util.List;

public class WithDrawRecodeAdapter extends BaseQuickAdapter<WithDrawRecodeBeans, BaseViewHolder> {
    public WithDrawRecodeAdapter( @Nullable List<WithDrawRecodeBeans> data) {
        super(R.layout.withdrawrecode_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithDrawRecodeBeans item) {

    }
}
