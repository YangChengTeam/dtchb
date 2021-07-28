package com.yc.qqzz.homeModule.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.utils.CountDownUtilsThree;

import java.util.List;

public class LineRedAdapter  extends BaseQuickAdapter<String, BaseViewHolder> {

    public LineRedAdapter( @Nullable List<String> data) {
        super(R.layout.linered_adapter_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if ("2".equals(item)){
            ((ImageView) helper.getView(R.id.iv_bg)).setImageResource(R.drawable.mr_redbox);
            ((TextView) helper.getView(R.id.tv_status)).setText("拆开");
            ((TextView) helper.getView(R.id.tv_status)).setVisibility(View.VISIBLE);
        }else {
            ((ImageView) helper.getView(R.id.iv_bg)).setImageResource(R.drawable.mr_redbox2);
            ((TextView) helper.getView(R.id.tv_status)).setVisibility(View.GONE);
        }
    }
}
