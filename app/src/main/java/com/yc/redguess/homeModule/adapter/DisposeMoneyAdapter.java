package com.yc.redguess.homeModule.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redguess.R;
import com.yc.redguess.homeModule.module.bean.TithDrawBeans;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by suns  on 2020/11/17 15:40.
 */
public class DisposeMoneyAdapter extends BaseQuickAdapter<TithDrawBeans.CashOutBean.OutamountBean, BaseViewHolder> {
    public TithDrawBeans.UserOtherBean userOtherBean;
    public DisposeMoneyAdapter(@Nullable List<TithDrawBeans.CashOutBean.OutamountBean> data) {
        super(R.layout.dispose_money_item_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TithDrawBeans.CashOutBean.OutamountBean item) {
        int position = helper.getAdapterPosition();
        ((TextView) helper.getView(R.id.tv_dispose_money)).setText(item.getMoney()+"");
        if (position == 0) {
            helper.setGone(R.id.tv_exclusive_tag, true)
                    .setGone(R.id.tv_dispose_progress, true)
                    .setGone(R.id.tv_dispose_level, true);
            ((TextView) helper.getView(R.id.tv_dispose_level)).setText(item.getOut_level()+"级可提现");
            ((TextView) helper.getView(R.id.tv_dispose_progress)).setText(item.getOther_num()+"/"+item.getNum());
        } else if (position == 1){
            helper.setGone(R.id.tv_dispose_progress, false)
                    .setGone(R.id.tv_dispose_level, false);
            ((TextView) helper.getView(R.id.tv_exclusive_tag)).setText("夺宝奖励");
            ((TextView) helper.getView(R.id.tv_exclusive_tag)).setVisibility(View.VISIBLE);
        } else if (position ==2){
            helper.setGone(R.id.tv_dispose_progress, false)
                    .setGone(R.id.tv_dispose_level, false);
            ((TextView) helper.getView(R.id.tv_exclusive_tag)).setText("签到奖励");
            ((TextView) helper.getView(R.id.tv_exclusive_tag)).setVisibility(View.VISIBLE);
        }else {
            helper.setGone(R.id.tv_exclusive_tag, false)
                    .setGone(R.id.tv_dispose_progress, false)
                    .setGone(R.id.tv_dispose_level, false);
        }

        if (item.isSelect()){
            helper.itemView.setSelected(true);
        }else {
            helper.itemView.setSelected(false);
        }
    }

    public void setCashInfo(TithDrawBeans.UserOtherBean user_other) {
         this.userOtherBean=user_other;
    }
}
