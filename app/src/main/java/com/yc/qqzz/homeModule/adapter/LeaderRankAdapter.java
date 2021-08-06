package com.yc.qqzz.homeModule.adapter;


import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.module.bean.LeaderRankInfo;

import java.util.List;


/**
 * Created by suns  on 2020/11/18 10:45.
 */
public class LeaderRankAdapter extends BaseQuickAdapter<LeaderRankInfo, BaseViewHolder> {
    public LeaderRankAdapter(@Nullable List<LeaderRankInfo> data) {
        super(R.layout.item_leader_rank_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LeaderRankInfo item) {
        int position = helper.getAdapterPosition();
        if (position == 0) {
            helper.setGone(R.id.iv_ranking_icon, true)
                    .setGone(R.id.tv_ranking_num, false);
            helper.setImageResource(R.id.iv_ranking_icon, R.mipmap.icon_pank1);
        } else if (position == 1) {
            helper.setGone(R.id.iv_ranking_icon, true)
                    .setGone(R.id.tv_ranking_num, false);
            helper.setImageResource(R.id.iv_ranking_icon, R.mipmap.icon_pank2);
        } else if (position == 2) {
            helper.setGone(R.id.iv_ranking_icon, true)
                    .setGone(R.id.tv_ranking_num, false);
            helper.setImageResource(R.id.iv_ranking_icon, R.mipmap.icon_pank3);
        } else {
            helper.setGone(R.id.iv_ranking_icon, false)
                    .setGone(R.id.tv_ranking_num, true);
        }
        helper.setText(R.id.tv_ranking_num, (position + 1) + "");
        ((TextView) helper.getView(R.id.tv_name)).setText(item.getNickname());
        ((TextView) helper.getView(R.id.tv_money)).setText(""+item.getMoney());
        Glide.with(mContext)
                .load(item.getFace())
                .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                .into(((ImageView) helper.getView(R.id.iv_avatar)));
    }
}
