package com.yc.redguess.homeModule.adapter;



import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yc.redguess.R;
import com.yc.redguess.homeModule.widget.garlly.AdapterMeasureHelper;


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
    }

    @Override
    public int getItemCount() {
        return mList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_big;

        public ViewHolder(final View view) {
            super(view);
            iv_big = (ImageView) view.findViewById(R.id.iv_tops);
        }
    }
}
