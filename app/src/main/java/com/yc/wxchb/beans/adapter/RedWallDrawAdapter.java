package com.yc.wxchb.beans.adapter;


import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wxchb.R;
import com.yc.wxchb.beans.module.beans.HotWithDrawBeans;

import java.util.List;


public class RedWallDrawAdapter extends BaseQuickAdapter<HotWithDrawBeans.HuoliBean.ConfigJsonBean, BaseViewHolder> {
    public RedWallDrawAdapter(@Nullable List<HotWithDrawBeans.HuoliBean.ConfigJsonBean> data) {
        super(R.layout.withdraw_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotWithDrawBeans.HuoliBean.ConfigJsonBean item) {
        ((TextView) helper.getView(R.id.tv_moneys)).setText(item.getMoney());
        ((TextView) helper.getView(R.id.tv_dispose_progress)).setVisibility(View.GONE);
        if (item.getStatus()==1){
            if (item.isSelect()){
              ((RelativeLayout) helper.getView(R.id.line_item)).setBackgroundResource(R.drawable.line_bg_yellow12);
              ((TextView) helper.getView(R.id.tv_moneys)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
              ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.VISIBLE);
            }else {
              ((RelativeLayout) helper.getView(R.id.line_item)).setBackgroundResource(R.drawable.line_bg_black1);
              ((TextView) helper.getView(R.id.tv_moneys)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
              ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.GONE);
            }
        }else {
            ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.GONE);
            ((RelativeLayout) helper.getView(R.id.line_item)).setBackground(mContext.getDrawable(R.drawable.tv_bg_gray14));
        }
    }
}
