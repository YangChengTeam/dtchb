package com.yc.qqzz.homeModule.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.bean.WithDrawRecodeBeans;
import com.yc.qqzz.homeModule.module.bean.CashRecordBeans;

import java.util.List;

public class WithDrawRecodeAdapter extends BaseQuickAdapter<CashRecordBeans, BaseViewHolder> {
    public WithDrawRecodeAdapter( @Nullable List<CashRecordBeans> data) {
        super(R.layout.withdrawrecode_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CashRecordBeans item) {
        ((TextView) helper.getView(R.id.tv_status)).setText("-"+item.getAmount());
        ((TextView) helper.getView(R.id.tv_times)).setText("-"+item.getAdd_time());
    }
}
