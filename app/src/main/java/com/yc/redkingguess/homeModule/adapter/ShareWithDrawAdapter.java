package com.yc.redkingguess.homeModule.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redkingguess.R;
import com.yc.redkingguess.homeModule.module.bean.InvationsShareBeans;

import java.util.List;


public class ShareWithDrawAdapter  extends BaseQuickAdapter<InvationsShareBeans.InviteListBean, BaseViewHolder> {
    public ShareWithDrawAdapter(@Nullable List<InvationsShareBeans.InviteListBean> data) {
        super(R.layout.sharewithdraw_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvationsShareBeans.InviteListBean item) {
        RelativeLayout rela_item = helper.getView(R.id.rela_item);
        if (item.isSelect()){
            rela_item.setBackground(mContext.getResources().getDrawable(R.drawable.line_greenbg2_1aad19));
            ((TextView) helper.getView(R.id.tv_moneys)).setText(item.getCash_exchange()+"");
            ((TextView) helper.getView(R.id.tv_moneys)).setTextColor(mContext.getResources().getColor(R.color.green_1AAD19));
            ((TextView) helper.getView(R.id.tv_des)).setTextColor(mContext.getResources().getColor(R.color.green_1AAD19));
            ((TextView) helper.getView(R.id.tv_select)).setVisibility(View.VISIBLE);
        }else {
            ((TextView) helper.getView(R.id.tv_select)).setVisibility(View.GONE);
            rela_item.setBackground(mContext.getResources().getDrawable(R.drawable.line_graybg2_bababa));
            ((TextView) helper.getView(R.id.tv_moneys)).setText(item.getCash_exchange()+"");
            ((TextView) helper.getView(R.id.tv_moneys)).setTextColor(mContext.getResources().getColor(R.color.gray_BABABA));
            ((TextView) helper.getView(R.id.tv_des)).setTextColor(mContext.getResources().getColor(R.color.gray_BABABA));
        }
    }
}
