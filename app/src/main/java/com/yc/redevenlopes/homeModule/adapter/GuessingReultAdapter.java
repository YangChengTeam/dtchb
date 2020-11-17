package com.yc.redevenlopes.homeModule.adapter;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.GuessingReultBeans;


import java.util.List;

public class GuessingReultAdapter extends BaseQuickAdapter<GuessingReultBeans, BaseViewHolder> {
    public GuessingReultAdapter( @Nullable List<GuessingReultBeans> data) {
        super(R.layout.guessingresult_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GuessingReultBeans item) {

    }
}
