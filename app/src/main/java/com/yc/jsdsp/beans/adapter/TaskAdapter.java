package com.yc.jsdsp.beans.adapter;


import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.jsdsp.R;
import com.yc.jsdsp.beans.module.beans.RedTaskBeans;

import java.util.List;

public class TaskAdapter extends BaseQuickAdapter<RedTaskBeans.HbOnlineTaskBean, BaseViewHolder> {
    public TaskAdapter(@Nullable List<RedTaskBeans.HbOnlineTaskBean> data) {
        super(R.layout.task_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedTaskBeans.HbOnlineTaskBean item) {
        ((TextView) helper.getView(R.id.tv_title)).setText("累计领取"+item.getNum()+"个倒计时红包");
        ((TextView) helper.getView(R.id.tv_des)).setText("开启倒计时红包，领取大额红包");
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
