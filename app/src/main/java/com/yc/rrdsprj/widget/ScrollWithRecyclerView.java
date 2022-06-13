package com.yc.rrdsprj.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

public class ScrollWithRecyclerView extends RecyclerView {
    public ScrollWithRecyclerView(Context context) {
        super(context);
    }

    public ScrollWithRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWithRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }




}
