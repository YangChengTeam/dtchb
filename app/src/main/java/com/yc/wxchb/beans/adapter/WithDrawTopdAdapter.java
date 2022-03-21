package com.yc.wxchb.beans.adapter;


import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wxchb.R;
import com.yc.wxchb.beans.module.beans.PayInfoBeans;
import com.yc.wxchb.utils.CountDownUtilsThree;
import com.yc.wxchb.utils.TimesUtils;

import java.util.List;


public class WithDrawTopdAdapter extends BaseQuickAdapter<PayInfoBeans.OutArrBean, BaseViewHolder> {
    public WithDrawTopdAdapter(@Nullable List<PayInfoBeans.OutArrBean> data) {
        super(R.layout.withdrawtop_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayInfoBeans.OutArrBean item) {
      ((TextView) helper.getView(R.id.tv_moneys)).setText(item.getOut_money()+"");
        CountDownUtilsThree  countDownUtilsFour = new CountDownUtilsThree();
        countDownUtilsFour.setOnCountDownListen(new CountDownUtilsThree.OnCountDownListen() {
            @Override
            public void count(long mMin, long mSecond) {
                String timesContents = TimesUtils.getTv(mMin) + ":" +  TimesUtils.getTv(mSecond);
                ((TextView) helper.getView(R.id.tv_times)).setText(timesContents+"");
                ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.VISIBLE);
            }

            @Override
            public void countFinsh() {
                ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.GONE);
            }
        });
        long other_time = item.getOther_time();
        countDownUtilsFour.setHours(TimesUtils.getMinDiff(other_time*1000),TimesUtils.getSecondDiff(other_time*1000));
        helper.addOnClickListener(R.id.line_status);
    }
}
