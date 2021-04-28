package com.yc.majiaredgrab.homeModule.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.homeModule.module.bean.InvationBeans;

import java.util.List;


public class InvationAdapter extends BaseQuickAdapter<InvationBeans, BaseViewHolder> {
    public InvationAdapter( @Nullable List<InvationBeans> data) {
        super(R.layout.invation_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvationBeans item) {
        ((TextView) helper.getView(R.id.tv_title)).setText("邀请"+item.getExchange_num()+"人");
        ((TextView) helper.getView(R.id.tv_quanDes)).setText(item.getLevel_upgrade()+"级升级卷");
        ((TextView) helper.getView(R.id.tv_withDrawDes)).setText("(或提现"+item.getCash_exchange()+"元)");
    }
}
