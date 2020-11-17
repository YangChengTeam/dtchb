package com.yc.redevenlopes.homeModule.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.DisposeInfo;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by suns  on 2020/11/17 15:40.
 */
public class DisposeMoneyAdapter extends BaseQuickAdapter<DisposeInfo, BaseViewHolder> {
    public DisposeMoneyAdapter(@Nullable List<DisposeInfo> data) {
        super(R.layout.dispose_money_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DisposeInfo item) {
        int position = helper.getAdapterPosition();
        if (position == 0) {
            helper.setGone(R.id.tv_exclusive_tag, true)
                    .setGone(R.id.tv_dispose_progress, true)
                    .setGone(R.id.tv_dispose_level, true);
            helper.itemView.setSelected(true);
        } else {
            helper.setGone(R.id.tv_exclusive_tag, false)
                    .setGone(R.id.tv_dispose_progress, false)
                    .setGone(R.id.tv_dispose_level, false);
            helper.itemView.setSelected(false);
        }
    }
}
