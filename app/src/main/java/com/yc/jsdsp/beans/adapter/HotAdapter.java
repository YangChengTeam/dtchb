package com.yc.jsdsp.beans.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.jsdsp.R;
import com.yc.jsdsp.beans.module.beans.HotWithDrawBeans;


import java.util.List;

public class HotAdapter extends BaseQuickAdapter<HotWithDrawBeans.HuoliBean.ConfigJsonBean, BaseViewHolder> {
    public HotAdapter(@Nullable List<HotWithDrawBeans.HuoliBean.ConfigJsonBean> data) {
        super(R.layout.hot_items, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotWithDrawBeans.HuoliBean.ConfigJsonBean item) {
        ((TextView) helper.getView(R.id.tv_moneys)).setText(item.getMoney());
        if (item.getStatus()==1){
            if (item.isSelect()){
                ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.VISIBLE);
                ((RelativeLayout) helper.getView(R.id.rela_item)).setBackground(mContext.getDrawable(R.drawable.line_bg_green9));
            }else {
                ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.GONE);
                ((RelativeLayout) helper.getView(R.id.rela_item)).setBackground(mContext.getDrawable(R.drawable.tv_bg_gray13));
            }
        }else {
            ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.GONE);
            ((RelativeLayout) helper.getView(R.id.rela_item)).setBackground(mContext.getDrawable(R.drawable.tv_bg_gray14));
        }

    }
}
