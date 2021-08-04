package com.yc.qqzz.homeModule.adapter;


import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.bean.UpgradeTaskitemBeans;
import java.util.List;

public class UpgradeTaskitemAdapter extends BaseQuickAdapter<UpgradeTaskitemBeans.TaskArrBean, BaseViewHolder> {
    public UpgradeTaskitemAdapter(@Nullable List<UpgradeTaskitemBeans.TaskArrBean> data) {
        super(R.layout.upgradetask_item_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpgradeTaskitemBeans.TaskArrBean item) {
        ((TextView) helper.getView(R.id.tv_title)).setText(item.getTitle());
        ((TextView) helper.getView(R.id.tv_des)).setText(item.getExcerpt());
        helper.addOnClickListener(R.id.tv_lookVideoUnlockingOne);
        if (item.getFinish_num()<item.getNum()){//
            ((TextView) helper.getView(R.id.tv_lookVideoUnlockingOne)).setBackgroundResource(R.drawable.line_bg_red9);
            ((TextView) helper.getView(R.id.tv_lookVideoUnlockingOne)).setTextColor(mContext.getResources().getColor(R.color.white));
            ((TextView) helper.getView(R.id.tv_lookVideoUnlockingOne)).setText("去完成");
        }else {
            ((TextView) helper.getView(R.id.tv_lookVideoUnlockingOne)).setBackgroundResource(R.drawable.tv_bg_gray5);
            ((TextView) helper.getView(R.id.tv_lookVideoUnlockingOne)).setTextColor(mContext.getResources().getColor(R.color.A1_999999));
            ((TextView) helper.getView(R.id.tv_lookVideoUnlockingOne)).setText("已完成");
        }
    }
}
