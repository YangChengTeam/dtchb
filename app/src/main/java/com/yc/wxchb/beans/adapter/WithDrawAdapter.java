package com.yc.wxchb.beans.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wxchb.R;
import com.yc.wxchb.beans.module.beans.PayInfoBeans;

import java.util.List;

public class WithDrawAdapter extends BaseQuickAdapter<PayInfoBeans.CashOutBean.OutamountBean, BaseViewHolder> {
    public WithDrawAdapter(@Nullable List<PayInfoBeans.CashOutBean.OutamountBean> data) {
        super(R.layout.withdraw_items, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayInfoBeans.CashOutBean.OutamountBean item) {
        ((TextView) helper.getView(R.id.tv_moneys)).setText(item.getMoney());
        ((TextView) helper.getView(R.id.tv_status)).setText(item.getOther_num()+"/"+item.getNum());

        if (item.isSelect()){
            ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.VISIBLE);
            ((RelativeLayout) helper.getView(R.id.rela_item)).setBackground(mContext.getDrawable(R.drawable.line_bg_green9));
            ((TextView) helper.getView(R.id.tv_moneys)).setTextColor(mContext.getResources().getColor(R.color.green_02BD33));
            ((TextView) helper.getView(R.id.tv1)).setTextColor(mContext.getResources().getColor(R.color.green_02BD33));
        }else {
            ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.GONE);
            ((RelativeLayout) helper.getView(R.id.rela_item)).setBackground(mContext.getDrawable(R.drawable.tv_bg_gray18));
            ((TextView) helper.getView(R.id.tv_moneys)).setTextColor(mContext.getResources().getColor(R.color.A1_000000));
            ((TextView) helper.getView(R.id.tv1)).setTextColor(mContext.getResources().getColor(R.color.A1_000000));
        }

    }
}
