package com.yc.redguess.homeModule.widget.selectrecyclerview;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yc.redguess.R;


/**
 * Created by yushuangping on 2018/8/23.
 */

public class HeaderHolder extends RecyclerView.ViewHolder {
    public TextView titleView;
    public ImageView openView;
    public LinearLayout line_item;
    public HeaderHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        titleView = (TextView) itemView.findViewById(R.id.tv_title);
        openView = (ImageView) itemView.findViewById(R.id.tv_open);
        line_item=itemView.findViewById(R.id.line_item);
    }
}
