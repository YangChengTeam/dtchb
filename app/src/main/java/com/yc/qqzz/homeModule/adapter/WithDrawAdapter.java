package com.yc.qqzz.homeModule.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.module.bean.WithDrawBeans;
import java.util.List;

public class WithDrawAdapter extends BaseQuickAdapter<WithDrawBeans, BaseViewHolder> {
    public WithDrawAdapter( @Nullable List<WithDrawBeans> data) {
        super(R.layout.withdraw_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithDrawBeans item) {
         boolean isSelect=true;
         if (isSelect){
             ((LinearLayout) helper.getView(R.id.line_item)).setBackgroundResource(R.drawable.line_bg_yellow12);
             ((TextView) helper.getView(R.id.tv_moneys)).setTextColor(mContext.getResources().getColor(R.color.gray_FB9D3F));
             ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.VISIBLE);
         }else {
             ((LinearLayout) helper.getView(R.id.line_item)).setBackgroundResource(R.drawable.line_bg_black1);
             ((TextView) helper.getView(R.id.tv_moneys)).setTextColor(mContext.getResources().getColor(R.color.A1_000000));
             ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.GONE);
         }
    }
}
