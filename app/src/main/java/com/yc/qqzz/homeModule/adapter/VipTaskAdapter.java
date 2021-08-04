package com.yc.qqzz.homeModule.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.bean.TaskUnlockBeans;
import java.util.List;

/**
 * Created by suns  on 2020/11/16 18:13.
 */
public class VipTaskAdapter extends BaseQuickAdapter<TaskUnlockBeans, BaseViewHolder> {

    public VipTaskAdapter(@Nullable List<TaskUnlockBeans> data) {
        super(R.layout.item_vip_task_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskUnlockBeans item) {
        int totalNum = item.getNum();
        int finishNum = item.getFinish_num();
        helper.setText(R.id.tv_reward_title, item.getTitle())
                .setText(R.id.tv_reward_num, "(" + finishNum + "/" + totalNum + ")");
        ProgressBar progressBar = helper.getView(R.id.progressbar_reward);
        progressBar.setMax(totalNum * 10);
        progressBar.setProgress(finishNum * 10);

       // setProgress(finishNum, progressBar);

        TextView tvRewardState = helper.getView(R.id.tv_reward_state);
        int status = item.getStatus();
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
}
