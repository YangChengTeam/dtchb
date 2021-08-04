package com.yc.qqzz.homeModule.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.bean.CashTaskBeans;
import com.yc.qqzz.homeModule.module.bean.DayCashTashBeans;

import java.util.List;

public class CashTaskAdapter extends BaseQuickAdapter<DayCashTashBeans.DownListBean, BaseViewHolder> {
    public CashTaskAdapter( @Nullable List<DayCashTashBeans.DownListBean> data) {
        super(R.layout.cashtask_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DayCashTashBeans.DownListBean item) {
        Glide.with(mContext).load(item.getIcon_url()).into((ImageView) helper.getView(R.id.iv_icon));
        ((TextView) helper.getView(R.id.tv_title)).setText(item.getApp_name());
        ((TextView) helper.getView(R.id.tv_des)).setText(item.getDescribe());
        ((TextView) helper.getView(R.id.tv_moneys)).setText(item.getMoney()+"元");
        helper.addOnClickListener(R.id.line_down);
        if (item.getStatus()==0){//未下载
            ((TextView) helper.getView(R.id.tv_status)).setText("下载提现");
            ((TextView) helper.getView(R.id.tv_status)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_yellow27));
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.white));
        }else if (item.getStatus()==1){//已下载未提现
            ((TextView) helper.getView(R.id.tv_status)).setText("去提现");
            ((TextView) helper.getView(R.id.tv_status)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_red11));
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.yellow_FDE803));
        }else if (item.getStatus()==2){//已提现
            ((TextView) helper.getView(R.id.tv_status)).setText("已完成");
            ((TextView) helper.getView(R.id.tv_status)).setBackground(mContext.getResources().getDrawable(R.drawable.tv_bg_gray5));
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.white));
        }

    }
}
