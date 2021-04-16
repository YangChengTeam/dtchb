package com.yc.majiaredgrab.homeModule.adapter;

import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.homeModule.module.bean.VipTaskInfo;
import java.util.List;
import androidx.annotation.Nullable;

/**
 * Created by suns  on 2020/11/16 18:13.
 */
public class VipLevelTaskAdapter extends BaseQuickAdapter<VipTaskInfo, BaseViewHolder> {

    public VipLevelTaskAdapter(@Nullable List<VipTaskInfo> data) {
        super(R.layout.item_vip_level_task_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipTaskInfo item) {
        helper.setText(R.id.tv_vip_level, item.level + "级")
                .setText(R.id.tv_task_level_title, "升级到" + item.level + "级可领取")
                .setText(R.id.tv_money, item.money + "");

        TextView tvVipTaskState = helper.getView(R.id.tv_vip_task_state);
        int status = item.status;
        //0:表示不能领取 1:表示能领取 2:表示已领取
        if (status == 0) {
            tvVipTaskState.setText("未达成");
            helper.itemView.setBackgroundResource(R.mipmap.icon_upgrade_gray);
        } else if (status == 1) {
            tvVipTaskState.setText("领奖励");
            helper.itemView.setBackgroundResource(R.mipmap.icon_upgrade_orange);
        } else if (status == 2) {
            tvVipTaskState.setText("已达成");
            helper.itemView.setBackgroundResource(R.mipmap.icon_upgrade_gray);
        }
    }
}
