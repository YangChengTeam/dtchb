package com.yc.redevenlopes.homeModule.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.DisposeRecordInfo;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by suns  on 2020/11/17 16:40.
 */
public class DisposeRecordAdapter extends BaseQuickAdapter<DisposeRecordInfo, BaseViewHolder> {
    public DisposeRecordAdapter(@Nullable List<DisposeRecordInfo> data) {
        super(R.layout.item_dispose_record_view,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DisposeRecordInfo item) {

    }
}
