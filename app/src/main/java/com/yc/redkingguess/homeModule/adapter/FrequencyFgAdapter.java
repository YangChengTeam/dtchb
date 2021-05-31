package com.yc.redkingguess.homeModule.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redkingguess.R;
import com.yc.redkingguess.homeModule.module.bean.FrequencyFgBeans;
import com.yc.redkingguess.utils.TimesUtils;

import java.util.List;

public class FrequencyFgAdapter  extends BaseQuickAdapter<FrequencyFgBeans, BaseViewHolder> {
    public FrequencyFgAdapter( @Nullable List<FrequencyFgBeans> data) {
        super(R.layout.frequency_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FrequencyFgBeans item) {
        ((TextView) helper.getView(R.id.tv_titles)).setText(item.getAdd_date()+item.getAdd_num()+"");
        String strTime = TimesUtils.getStrTime(String.valueOf(item.getStart_time()*1000));
        String endTime = TimesUtils.getStrTime(String.valueOf(item.getEnd_time()*1000));
        ((TextView) helper.getView(R.id.tv_times)).setText(strTime+"-"+endTime);
        ((TextView) helper.getView(R.id.prizeNums)).setText(item.getPrize_num()+"");
        helper.addOnClickListener(R.id.tv_details);
    }
}
