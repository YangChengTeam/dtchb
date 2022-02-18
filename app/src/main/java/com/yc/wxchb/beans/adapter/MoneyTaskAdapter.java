package com.yc.wxchb.beans.adapter;

import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wxchb.R;
import com.yc.wxchb.beans.module.beans.MoneyTaskBeans;

import java.util.List;

public class MoneyTaskAdapter extends BaseQuickAdapter<MoneyTaskBeans, BaseViewHolder> {
    public MoneyTaskAdapter(@Nullable List<MoneyTaskBeans> data) {
        super(R.layout.money_task_items, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MoneyTaskBeans item) {
        int totalNum = item.getNum();
        int finishNum = item.getFinish_num();
        helper.setText(R.id.tv_reward_title, item.getMoney()+"元提现任务")
                .setText(R.id.tv_reward_num, "(" + finishNum + "/" + totalNum + ")");
        ProgressBar progressBar = helper.getView(R.id.progressbar_reward);
        progressBar.setMax(totalNum * 10);
        progressBar.setProgress(finishNum * 10);
        TextView tvRewardState = helper.getView(R.id.tv_reward_state);
        int status = item.getStatus();
        if (status==0){//未完成
            ((LinearLayout) helper.getView(R.id.rela_re)).setBackground(mContext.getDrawable(R.drawable.line_bg_yellow8));
            tvRewardState.setText("去完成");
        }else {
            ((LinearLayout) helper.getView(R.id.rela_re)).setBackground(mContext.getDrawable(R.drawable.tv_bg_gray2));
            tvRewardState.setText("已完成");
        }
        helper.addOnClickListener(R.id.rela_re);
    }
}
