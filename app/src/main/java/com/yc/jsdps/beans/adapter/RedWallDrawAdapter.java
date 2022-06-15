package com.yc.jsdps.beans.adapter;


import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.jsdps.R;
import com.yc.jsdps.beans.module.beans.RedWallInfoBeans;

import java.util.List;


public class RedWallDrawAdapter extends BaseQuickAdapter<RedWallInfoBeans.CashGoldBean.ConfigJsonBean, BaseViewHolder> {
    public RedWallDrawAdapter(@Nullable List<RedWallInfoBeans.CashGoldBean.ConfigJsonBean> data) {
        super(R.layout.withdraw_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedWallInfoBeans.CashGoldBean.ConfigJsonBean item) {
        ((TextView) helper.getView(R.id.tv_moneys)).setText(item.getMoney());
        ((TextView) helper.getView(R.id.tv_dispose_progress)).setVisibility(View.GONE);
        if (item.isSelect()){
            ((RelativeLayout) helper.getView(R.id.line_item)).setBackgroundResource(R.drawable.line_bg_yellow12);
            ((TextView) helper.getView(R.id.tv_moneys)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.VISIBLE);
        }else {
            ((RelativeLayout) helper.getView(R.id.line_item)).setBackgroundResource(R.drawable.line_bg_black1);
            ((TextView) helper.getView(R.id.tv_moneys)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.GONE);
        }
    }
}
