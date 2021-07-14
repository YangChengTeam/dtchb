package com.yc.qqzz.homeModule.adapter;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.module.bean.VipTaskInfo;

import java.util.List;

/**
 * Created by suns  on 2020/11/16 18:13.
 */
public class VipTaskAdapter extends BaseQuickAdapter<VipTaskInfo, BaseViewHolder> {

    public VipTaskAdapter(@Nullable List<VipTaskInfo> data) {
        super(R.layout.item_vip_task_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipTaskInfo item) {
        int totalNum = item.num;
        int finishNum = item.finish_num;
        helper.setText(R.id.tv_reward_title, item.title)
                .setText(R.id.tv_reward_num, "(" + finishNum + "/" + totalNum + ")");
        ProgressBar progressBar = helper.getView(R.id.progressbar_reward);
        progressBar.setMax(totalNum * 10);
        progressBar.setProgress(finishNum * 10);

       // setProgress(finishNum, progressBar);

        TextView tvRewardState = helper.getView(R.id.tv_reward_state);
        int status = item.status;
        if (status == 0) {//0代表未完成当前任务，1代表已完成，但未领取奖励2代表已领取奖励
            ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.GONE);
            tvRewardState.setBackgroundResource(R.drawable.tv_bg_gray1);
            tvRewardState.setText("去完成");
            tvRewardState.setTextColor(mContext.getResources().getColor(R.color.A1_999999));
        } else if (status == 1) {
//            if (((MyApplication) MyApplication.getInstance()).levels==1){
//                ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.VISIBLE);
//            }else {
//                int adapterPosition = helper.getAdapterPosition();
//                if (adapterPosition==0){
//                    String taskShou = CacheDataUtils.getInstance().getTaskShou();
//                    if (TextUtils.isEmpty(taskShou)){
//                        ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.GONE);
//                    }
//                }else {
//                    ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.GONE);
//                }
//            }
            ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.GONE);
            tvRewardState.setBackgroundResource(R.drawable.line_bg_yellow9);
            tvRewardState.setTextColor(mContext.getResources().getColor(R.color.white));
            tvRewardState.setText("领取");
        } else if (status == 2) {
            ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.GONE);
            tvRewardState.setBackground(null);
            tvRewardState.setText("已完成");
            tvRewardState.setBackgroundResource(R.drawable.line_bg_yellow9);
            tvRewardState.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        helper.addOnClickListener(R.id.rela_re);
        helper.addOnClickListener(R.id.tv_reward_state);
    }

    private void setProgress(int finishNum, ProgressBar progressBar) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, finishNum * 10);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            Log.d("ccc", "------------setProgress: '"+value);
            progressBar.setProgress(value);
        });
        valueAnimator.start();
    }
}