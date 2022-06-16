package com.yc.jsdsp.beans.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.jsdsp.R;
import com.yc.jsdsp.beans.module.beans.InvationPeopleListBeans;

import java.util.List;


public class InvitationDialogAdapter extends BaseQuickAdapter<InvationPeopleListBeans, BaseViewHolder> {
    public InvitationDialogAdapter(@Nullable List<InvationPeopleListBeans> data) {
        super(R.layout.invitation_dialog_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvationPeopleListBeans item) {
        if (item.getBe_invite_user()!=null){
            ((TextView) helper.getView(R.id.tv_name)).setText(item.getBe_invite_user().getNickname());
            ((TextView) helper.getView(R.id.tv_id)).setText("ID："+item.getBe_invite_user().getId());
        }
        ((TextView) helper.getView(R.id.tv_sort)).setText(String.valueOf(helper.getPosition()+1));
        ((TextView) helper.getView(R.id.tv_times)).setText("ID："+item.getAdd_time());
        if (item.getIs_meet()==1){//有效
            ((TextView) helper.getView(R.id.tv_status)).setText("有效");
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.yellow_FF1849));
        }else {
            ((TextView) helper.getView(R.id.tv_status)).setTextColor(mContext.getResources().getColor(R.color.gray_999999));
            ((TextView) helper.getView(R.id.tv_status)).setText("无效");
        }
    }
}
