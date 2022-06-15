package com.yc.jsdps.beans.adapter;


import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.jsdps.R;
import com.yc.jsdps.beans.module.beans.LimitedBeans;

import java.util.List;

public class LimitedAdapter extends BaseQuickAdapter<LimitedBeans, BaseViewHolder> {
    public LimitedAdapter(@Nullable List<LimitedBeans> data) {
        super(R.layout.limited_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LimitedBeans item) {
       ((TextView) helper.getView(R.id.tv_title)).setText("观看视频必得"+item.getMoney()+"元提现机会");
        ((TextView) helper.getView(R.id.tv_des)).setText("累计观看"+item.getNum()+"个视频即可提现");
        if (item.getStatus()==0){//0:未完成 1:已完成未提现 2:已提现
            ((TextView) helper.getView(R.id.tv_sure)).setText("去观看");
            ((TextView) helper.getView(R.id.tv_sure)).setBackground(mContext.getDrawable(R.drawable.line_bg_yellow4));
        }else if (item.getStatus()==1){
            ((TextView) helper.getView(R.id.tv_sure)).setText("去瓜分");
            ((TextView) helper.getView(R.id.tv_sure)).setBackground(mContext.getDrawable(R.drawable.tv_red9));
        }
        ((ProgressBar) helper.getView(R.id.progressbar)).setMax(item.getNum()* 10);
        ((ProgressBar) helper.getView(R.id.progressbar)).setProgress(item.getFinish_num() * 10);
        ((TextView) helper.getView(R.id.tv_progressnums)).setText(item.getFinish_num()+"");
        ((TextView) helper.getView(R.id.tv_allNums)).setText("/"+item.getNum()+"");
        helper.addOnClickListener(R.id.line_status);
    }
}
