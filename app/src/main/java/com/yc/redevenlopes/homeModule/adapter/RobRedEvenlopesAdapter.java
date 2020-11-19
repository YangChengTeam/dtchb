package com.yc.redevenlopes.homeModule.adapter;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.RobRedEvenlopesBeans;
import java.util.List;

public class RobRedEvenlopesAdapter extends BaseQuickAdapter<RobRedEvenlopesBeans, BaseViewHolder> {
    public RobRedEvenlopesAdapter( @Nullable List<RobRedEvenlopesBeans> data) {
        super(R.layout.robredevenlopes_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RobRedEvenlopesBeans item) {

    }
}
