package com.yc.redkingguess.homeModule.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redkingguess.R;
import com.yc.redkingguess.homeModule.module.bean.InvationsShareBeans;

import java.util.List;


public class ShareExchangeAdapter extends BaseQuickAdapter<InvationsShareBeans.InviteListBean, BaseViewHolder> {
    public ShareExchangeAdapter(@Nullable List<InvationsShareBeans.InviteListBean> data) {
        super(R.layout.shareexchange_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvationsShareBeans.InviteListBean item) {
        RelativeLayout rela_item = helper.getView(R.id.rela_item);
        ((TextView) helper.getView(R.id.tv_levels)).setText(item.getLevel_upgrade()+"级");
        if (item.isSelect()){
            rela_item.setBackground(mContext.getResources().getDrawable(R.drawable.line_yellowbg_f99f5e));
            ((TextView) helper.getView(R.id.tv_select)).setVisibility(View.VISIBLE);
            ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade4);
            ((View) helper.getView(R.id.views)).setBackground(mContext.getResources().getDrawable(R.drawable.line_yellowbg3_f99f5e));
        }else {
            rela_item.setBackground(mContext.getResources().getDrawable(R.drawable.line_graybg2_bababa));
            ((TextView) helper.getView(R.id.tv_select)).setVisibility(View.GONE);
            ((View) helper.getView(R.id.views)).setBackground(mContext.getResources().getDrawable(R.drawable.line_greenbg3_1aad19));
        }
        if (item.getLevel_upgrade()==4){
            if (item.isSelect()){
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade4);
            }else {
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade4_geay);
            }
        }else if (item.getLevel_upgrade()==6){
            if (item.isSelect()){
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade6);
            }else {
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade6_geay);
            }
        }else if (item.getLevel_upgrade()==10){
            if (item.isSelect()){
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade10);
            }else {
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade10_geay);
            }
        }else if (item.getLevel_upgrade()==15){
            if (item.isSelect()){
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade15);
            }else {
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade15_geay);
            }
        }else if (item.getLevel_upgrade()==17){
            if (item.isSelect()){
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade17);
            }else {
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade17_geay);
            }
        }else if (item.getLevel_upgrade()==18){
            if (item.isSelect()){
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade18);
            }else {
                ((ImageView) helper.getView(R.id.iv_select_level)).setImageResource(R.drawable.icon_grade18_geay);
            }
        }

    }
}
