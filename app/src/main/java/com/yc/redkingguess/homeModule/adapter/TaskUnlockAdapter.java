package com.yc.redkingguess.homeModule.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redkingguess.R;
import com.yc.redkingguess.homeModule.module.bean.TaskUnlock;
import java.util.List;

public class TaskUnlockAdapter extends BaseQuickAdapter<TaskUnlock, BaseViewHolder> {
    public TaskUnlockAdapter( @Nullable List<TaskUnlock> data) {
        super(R.layout.taskunlock_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskUnlock item) {
        int num = item.getNum();
        int finish_num = item.getFinish_num();
        helper.addOnClickListener(R.id.tv_lookVideoUnlockingOne);
        if (!TextUtils.isEmpty(item.getTitle())){
            ((TextView) helper.getView(R.id.tv_title)).setText(item.getTitle()+"");
        }
        if (helper.getPosition()==0){
            helper.setImageResource(R.id.iv_icons, R.drawable.icon_video_mem);
            if (!TextUtils.isEmpty(item.getExcerpt())){
                ((TextView) helper.getView(R.id.tv_des)).setText(item.getExcerpt());
            }else {
                ((TextView) helper.getView(R.id.tv_des)).setText("观看广告视频完成一次任务");
            }
        }else {
            if (!TextUtils.isEmpty(item.getExcerpt())){
                ((TextView) helper.getView(R.id.tv_des)).setText(item.getExcerpt());
            }else {
                ((TextView) helper.getView(R.id.tv_des)).setText("观看广告视频完成一次任务，马上解锁3级任务");
            }
            helper.setImageResource(R.id.iv_icons, R.drawable.icon_upgrade_mem);
        }
        if (finish_num<num){//未完成
            ((TextView) helper.getView(R.id.tv_lookVideoUnlockingOne)).setText("去完成");
            ((TextView) helper.getView(R.id.tv_lookVideoUnlockingOne)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_yellow11));
        }else {//完成
            ((TextView) helper.getView(R.id.tv_lookVideoUnlockingOne)).setText("已完成");
            ((TextView) helper.getView(R.id.tv_lookVideoUnlockingOne)).setBackground(mContext.getResources().getDrawable(R.drawable.tv_bg_gray11));
        }
    }
}
