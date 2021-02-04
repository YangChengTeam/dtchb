package com.yc.redguess.homeModule.widget.selectrecyclerview;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yc.redguess.R;


/**
 * Created by yushuangping on 2018/8/23.
 */

public class DescHolder extends RecyclerView.ViewHolder {
    public TextView descView;
    public TextView title;
    public DescHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        title=itemView.findViewById(R.id.tv_title);
        descView = (TextView) itemView.findViewById(R.id.tv_desc);
    }
}
