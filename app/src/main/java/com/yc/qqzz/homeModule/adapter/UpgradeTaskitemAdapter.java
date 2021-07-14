package com.yc.qqzz.homeModule.adapter;


import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.bean.UpgradeTaskitemBeans;
import java.util.List;

public class UpgradeTaskitemAdapter extends BaseQuickAdapter<UpgradeTaskitemBeans, BaseViewHolder> {
    public UpgradeTaskitemAdapter(@Nullable List<UpgradeTaskitemBeans> data) {
        super(R.layout.upgradetask_item_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UpgradeTaskitemBeans item) {
         boolean isSelect=true;
         if (isSelect){
             ((TextView) helper.getView(R.id.tv_sure)).setBackgroundResource(R.drawable.tv_bg_gray5);
             ((TextView) helper.getView(R.id.tv_sure)).setTextColor(mContext.getResources().getColor(R.color.A1_999999));
         }else {
             ((TextView) helper.getView(R.id.tv_sure)).setBackgroundResource(R.drawable.line_bg_red9);
             ((TextView) helper.getView(R.id.tv_sure)).setTextColor(mContext.getResources().getColor(R.color.A1_999999));
         }
    }
}
