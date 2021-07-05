package com.yc.majiaredgrab.homeModule.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.homeModule.module.bean.WalletDetailBeans;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by suns  on 2020/11/17 16:40.
 */
public class WalletDetailAdapter extends BaseQuickAdapter<WalletDetailBeans, BaseViewHolder> {
    public WalletDetailAdapter(@Nullable List<WalletDetailBeans> data) {
        super(R.layout.item_dispose_record_view,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletDetailBeans item) {
        ((TextView) helper.getView(R.id.tv_dispose_state_title)).setText(item.getDescribe());
        ((TextView) helper.getView(R.id.tv_times)).setText(item.getAdd_time());
        if (item.getStype()==0){
            ((TextView) helper.getView(R.id.tv_money)).setText("+"+item.getMoney());
        }else {
            ((TextView) helper.getView(R.id.tv_money)).setText("-"+item.getMoney());
        }

    }
}
