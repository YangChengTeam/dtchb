package com.yc.redguess.homeModule.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redguess.R;
import com.yc.redguess.homeModule.module.bean.TaskUnlock;
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
            ((TextView) helper.getView(R.id.tv_des)).setText("看精彩视频，领今日加速权限");
        }else {
            ((TextView) helper.getView(R.id.tv_des)).setText("看视频下载游戏，解锁提前升级");
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
