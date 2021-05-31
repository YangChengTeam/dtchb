package com.yc.redkingguess.homeModule.adapter;


import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redkingguess.R;
import com.yc.redkingguess.homeModule.module.bean.AnswerBeans;


import java.util.List;

public class AnswserAdapter extends BaseQuickAdapter<AnswerBeans, BaseViewHolder> {
    public AnswserAdapter( @Nullable List<AnswerBeans> data) {
        super(R.layout.answer_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnswerBeans item) {
        if (item.getIs_continue()==0){
            ((ImageView) helper.getView(R.id.iv_red)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_receive_red_envelope));
            ((LinearLayout) helper.getView(R.id.line_item)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_red3));
            ((TextView) helper.getView(R.id.tv_status)).setText("已领取");
        }else {
            ((TextView) helper.getView(R.id.tv_status)).setText("未领取");
        }
        if (!TextUtils.isEmpty(item.getMoney())){
            if ("0.10".equals(item.getMoney())||"0.20".equals(item.getMoney())){
                ((TextView) helper.getView(R.id.tv_price)).setText("简单");
            }else if ("0.30".equals(item.getMoney())){
                ((TextView) helper.getView(R.id.tv_price)).setText("中等");
            }else  if ("0.50".equals(item.getMoney())||"1.00".equals(item.getMoney())){
                ((TextView) helper.getView(R.id.tv_price)).setText("困难");
            }
       }
    }
}
