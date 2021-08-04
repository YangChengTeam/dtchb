package com.yc.qqzz.homeModule.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.module.bean.InvitationShareBeans;
import java.util.List;


public class ShareWithDrawAdapter extends BaseQuickAdapter<InvitationShareBeans.InviteListBean, BaseViewHolder> {
    public ShareWithDrawAdapter(@Nullable List<InvitationShareBeans.InviteListBean> data) {
        super(R.layout.sharewithdraw_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvitationShareBeans.InviteListBean item) {
        RelativeLayout rela_item = helper.getView(R.id.rela_item);
        if (item.isSelect()){
            rela_item.setBackground(mContext.getResources().getDrawable(R.drawable.line_greenbg2_1aad19));
           ((TextView) helper.getView(R.id.tv_moneys)).setText(item.getCash_exchange()+"");
            ((TextView) helper.getView(R.id.tv_moneys)).setTextColor(mContext.getResources().getColor(R.color.red_FE4C3E));
            ((TextView) helper.getView(R.id.tv_des)).setTextColor(mContext.getResources().getColor(R.color.red_FE4C3E));
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
