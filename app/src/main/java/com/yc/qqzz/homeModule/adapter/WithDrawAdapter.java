package com.yc.qqzz.homeModule.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.bean.WithDrawHomeBeans;
import com.yc.qqzz.homeModule.module.bean.WithDrawBeans;
import java.util.List;

public class WithDrawAdapter extends BaseQuickAdapter<WithDrawHomeBeans.CashOutBean.OutamountBean, BaseViewHolder> {
    public WithDrawAdapter( @Nullable List<WithDrawHomeBeans.CashOutBean.OutamountBean> data) {
        super(R.layout.withdraw_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithDrawHomeBeans.CashOutBean.OutamountBean item) {
        ((TextView) helper.getView(R.id.tv_moneys)).setText(item.getMoney()+"元");
        if (helper.getAdapterPosition()==0){
            ((TextView) helper.getView(R.id.tv_status)).setText("新人红包");
            ((TextView) helper.getView(R.id.tv_status)).setVisibility(View.VISIBLE);
        }else {
            ((TextView) helper.getView(R.id.tv_status)).setVisibility(View.GONE);
        }
         if (item.isSelect()){
             ((RelativeLayout) helper.getView(R.id.line_item)).setBackgroundResource(R.drawable.line_bg_yellow12);
             ((TextView) helper.getView(R.id.tv_moneys)).setTextColor(mContext.getResources().getColor(R.color.gray_FB9D3F));
             ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.VISIBLE);
         }else {
             ((RelativeLayout) helper.getView(R.id.line_item)).setBackgroundResource(R.drawable.line_bg_black1);
             ((TextView) helper.getView(R.id.tv_moneys)).setTextColor(mContext.getResources().getColor(R.color.A1_000000));
             ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.GONE);
         }
    }
}
