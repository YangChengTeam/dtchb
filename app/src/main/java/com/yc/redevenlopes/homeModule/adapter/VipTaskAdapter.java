package com.yc.redevenlopes.homeModule.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by suns  on 2020/11/16 18:13.
 */
public class VipTaskAdapter extends BaseQuickAdapter<VipTaskInfo, BaseViewHolder> {

    public VipTaskAdapter(@Nullable List<VipTaskInfo> data) {
        super(R.layout.item_vip_task_view,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipTaskInfo item) {

    }
}
