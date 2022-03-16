package com.yc.wxchb.beans.adapter;

import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.wxchb.R;
import com.yc.wxchb.beans.module.beans.InvitationShareBeans;

import java.util.List;


public class ShareWithDrawAdapter extends BaseQuickAdapter<InvitationShareBeans.InviteListBean, BaseViewHolder> {
    public ShareWithDrawAdapter(@Nullable List<InvitationShareBeans.InviteListBean> data) {
        super(R.layout.sharewithdraw_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvitationShareBeans.InviteListBean item) {
        LinearLayout rela_item = helper.getView(R.id.rela_item);
        if (item.isSelect()){
            rela_item.setBackground(mContext.getResources().getDrawable(R.drawable.tv_red5));
           ((TextView) helper.getView(R.id.tv_invitation_moneys)).setText(item.getCash_exchange()+"");
            ((TextView) helper.getView(R.id.tv_invitation_moneys)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
           // ((TextView) helper.getView(R.id.tv_invitation_moneys)).setTextColor(mContext.getResources().getColor(R.color.red_FE4C3E));
           // ((TextView) helper.getView(R.id.tv_des)).setTextColor(mContext.getResources().getColor(R.color.red_FE4C3E));
        }else {
            ((TextView) helper.getView(R.id.tv_invitation_moneys)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            rela_item.setBackground(mContext.getResources().getDrawable(R.drawable.tv_red7));
            ((TextView) helper.getView(R.id.tv_invitation_moneys)).setText(item.getCash_exchange()+"");
         //   ((TextView) helper.getView(R.id.tv_invitation_moneys)).setTextColor(mContext.getResources().getColor(R.color.gray_BABABA));
           // ((TextView) helper.getView(R.id.tv_des)).setTextColor(mContext.getResources().getColor(R.color.gray_BABABA));
        }
    }
}
