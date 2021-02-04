package com.yc.redguess.homeModule.adapter;

import android.content.Context;
import android.view.View;

public abstract class RollingTextAdapter {
    public abstract int getCount();

    public abstract View getView(Context context, View contentView, int position);
}
