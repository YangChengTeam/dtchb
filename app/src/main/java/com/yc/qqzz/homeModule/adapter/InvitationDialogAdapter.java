package com.yc.qqzz.homeModule.adapter;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.module.bean.InvitationShareBeans;

import java.util.List;

public class InvitationDialogAdapter extends BaseQuickAdapter<InvitationShareBeans, BaseViewHolder> {
    public InvitationDialogAdapter( @Nullable List<InvitationShareBeans> data) {
        super(R.layout.invitation_dialog_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvitationShareBeans item) {

    }
}
