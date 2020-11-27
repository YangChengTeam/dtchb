package com.yc.redevenlopes.homeModule.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.WithDrawRecordBeans;
import java.util.List;
import androidx.annotation.Nullable;

/**
 * Created by suns  on 2020/11/17 16:40.
 */
public class DisposeRecordAdapter extends BaseQuickAdapter<WithDrawRecordBeans, BaseViewHolder> {
    public DisposeRecordAdapter(@Nullable List<WithDrawRecordBeans> data) {
        super(R.layout.item_dispose_record_view,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithDrawRecordBeans item) {
        if (item.getOutstatus()==2){//
            ((TextView) helper.getView(R.id.tv_dispose_state_title)).setText("审核中");
        }
        ((TextView) helper.getView(R.id.tv_money)).setText("-"+item.getAmount());
        ((TextView) helper.getView(R.id.tv_times)).setText(item.getAdd_time());
    }
}
