package com.yc.majiaredgrab.homeModule.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.homeModule.module.bean.RedDetailsBeans;
import com.yc.majiaredgrab.utils.TimesUtils;
import java.util.List;

public class RobRedEvenlopesAdapter extends BaseQuickAdapter<RedDetailsBeans.ListBean, BaseViewHolder> {
    private int total;
    private int listSize;
    public RobRedEvenlopesAdapter( @Nullable List<RedDetailsBeans.ListBean> data) {
        super(R.layout.robredevenlopes_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedDetailsBeans.ListBean item) {
        if (total>0&&listSize==total){
            if (helper.getPosition()==0){
                ((TextView) helper.getView(R.id.tv_best)).setVisibility(View.VISIBLE);
            }else {
                ((TextView) helper.getView(R.id.tv_best)).setVisibility(View.GONE);
            }
        }else {
            ((TextView) helper.getView(R.id.tv_best)).setVisibility(View.GONE);
        }
        Glide.with(mContext)
                .load(item.getUser_info().getFace())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                .into((ImageView) helper.getView(R.id.iv_top));
        ((TextView) helper.getView(R.id.tv_name)).setText(item.getUser_info().getNickname());
        String  time = String.valueOf(item.getAdd_time()*1000);
        ((TextView) helper.getView(R.id.tv_times)).setText(TimesUtils.getStrTime(time));
        ((TextView) helper.getView(R.id.tv_money)).setText(item.getMoney()+"元");
    }

    public void setTatol(int total,int listSize) {
      this.total=total;
      this.listSize=listSize;
    }
}
