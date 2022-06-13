package com.yc.rrdsprj.beans.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.rrdsprj.R;
import com.yc.rrdsprj.beans.module.beans.CashRecordBeans;
import com.yc.rrdsprj.utils.CacheDataUtils;

import java.util.List;


public class WithDrawRecodeAdapter extends BaseQuickAdapter<CashRecordBeans, BaseViewHolder> {
    public WithDrawRecodeAdapter(@Nullable List<CashRecordBeans> data) {
        super(R.layout.withdrawrecode_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CashRecordBeans item) {
        if (item.getOutstatus()==1){
            ((TextView) helper.getView(R.id.tv_status)).setText("提现成功");
        }else if (item.getOutstatus()==2){
            ((TextView) helper.getView(R.id.tv_status)).setText("审核中");
        }
        ((TextView) helper.getView(R.id.tv_times)).setText(item.getAdd_time()+"");
        ((TextView) helper.getView(R.id.tv_moneys)).setText("￥"+item.getAmount());
        ((TextView) helper.getView(R.id.tv_name)).setText(CacheDataUtils.getInstance().getUserInfo().getNickname()+": ("+CacheDataUtils.getInstance().getUserInfo().getId()+")");
    }
}
