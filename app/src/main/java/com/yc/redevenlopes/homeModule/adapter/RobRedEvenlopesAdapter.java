package com.yc.redevenlopes.homeModule.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.RedDetailsBeans;
import com.yc.redevenlopes.homeModule.module.bean.RobRedEvenlopesBeans;
import java.util.List;

public class RobRedEvenlopesAdapter extends BaseQuickAdapter<RedDetailsBeans.ListBean, BaseViewHolder> {
    public RobRedEvenlopesAdapter( @Nullable List<RedDetailsBeans.ListBean> data) {
        super(R.layout.robredevenlopes_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedDetailsBeans.ListBean item) {
        Glide.with(mContext)
                .load(item.getUser_info().getFace())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                .into((ImageView) helper.getView(R.id.iv_top));
        ((TextView) helper.getView(R.id.tv_name)).setText(item.getUser_info().getNickname());
        ((TextView) helper.getView(R.id.tv_times)).setText(item.getAdd_time());
        ((TextView) helper.getView(R.id.tv_money)).setText(item.getMoney()+"元");
    }
}
