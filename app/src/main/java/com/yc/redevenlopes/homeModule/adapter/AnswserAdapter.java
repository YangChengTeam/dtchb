package com.yc.redevenlopes.homeModule.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;


import java.util.List;

public class AnswserAdapter extends BaseQuickAdapter<AnswerBeans, BaseViewHolder> {
    public AnswserAdapter( @Nullable List<AnswerBeans> data) {
        super(R.layout.answer_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnswerBeans item) {

    }
}
