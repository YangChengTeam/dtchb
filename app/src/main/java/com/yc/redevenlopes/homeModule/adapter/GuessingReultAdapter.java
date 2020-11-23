package com.yc.redevenlopes.homeModule.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.GuessHistoryBeans;
import java.util.List;

public class GuessingReultAdapter extends BaseQuickAdapter<GuessHistoryBeans.ListBean, BaseViewHolder> {
    public GuessingReultAdapter( @Nullable List<GuessHistoryBeans.ListBean> data) {
        super(R.layout.guessingresult_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GuessHistoryBeans.ListBean item) {
        ((TextView) helper.getView(R.id.tv_title)).setText(item.getGuessno()+"期");
        ((TextView) helper.getView(R.id.tv_guessNums)).setText(item.getNum()+"");
        ((TextView) helper.getView(R.id.tv_money)).setText(item.getMoney()+"");
        ((TextView) helper.getView(R.id.tv_name)).setText(item.getNickname()+"");
        Glide.with(mContext)
                .load(item.getFace())
                .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                .into((ImageView) helper.getView(R.id.iv_avatar));
    }
}
