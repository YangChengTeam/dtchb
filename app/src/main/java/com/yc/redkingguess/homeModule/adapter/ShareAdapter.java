package com.yc.redkingguess.homeModule.adapter;



import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.yc.redkingguess.R;
import com.yc.redkingguess.constants.Constant;
import com.yc.redkingguess.homeModule.widget.garlly.AdapterMeasureHelper;


public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder>{

    private int[] mList;
    private Context mContext;

    private AdapterMeasureHelper mCardAdapterHelper = new AdapterMeasureHelper();

    public ShareAdapter(Context ctx, int[] mList) {
        this.mList = mList;
        this.mContext = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_item, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        // Glide.with(mContext).load(R.drawable.bg_reward).into(holder.iv_big);
        holder.iv_big.setImageResource(mList[position]);
        if (!TextUtils.isEmpty(Constant.SHAREURL)){
            Glide.with(mContext)
                    .load(Constant.SHAREURL)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(1)))
                    .into(holder.ivShare);
        }else {
            holder.ivShare.setImageResource(R.drawable.sharesss);
        }
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_big,ivShare;

        public ViewHolder(final View view) {
            super(view);
            iv_big = (ImageView) view.findViewById(R.id.iv_tops);
            ivShare=view.findViewById(R.id.iv_erss);
        }
    }
}
