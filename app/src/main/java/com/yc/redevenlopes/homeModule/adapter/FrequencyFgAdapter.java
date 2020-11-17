package com.yc.redevenlopes.homeModule.adapter;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.FrequencyFgBeans;
import java.util.List;

public class FrequencyFgAdapter  extends BaseQuickAdapter<FrequencyFgBeans, BaseViewHolder> {
    public FrequencyFgAdapter( @Nullable List<FrequencyFgBeans> data) {
        super(R.layout.frequency_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FrequencyFgBeans item) {

    }
}
