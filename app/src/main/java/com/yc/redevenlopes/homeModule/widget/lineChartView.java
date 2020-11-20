package com.yc.redevenlopes.homeModule.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lq.lianjibusiness.base_libary.utils.DensityUtils;

public class lineChartView extends View {
    private Paint linePaint;
    private Paint rectsPaint;
    private Paint rectsPaintTwo;
    private int width;
    private int height;
    public lineChartView(Context context) {
        this(context,null);
    }

    public lineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public lineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(DensityUtils.dp2px(getContext(), 1));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.parseColor("#999999"));

        rectsPaint = new Paint();
        rectsPaint.setAntiAlias(true);
        rectsPaint.setStrokeWidth(DensityUtils.dp2px(getContext(), 1));
        rectsPaint.setStyle(Paint.Style.STROKE);
        rectsPaint.setColor(Color.parseColor("#fff5eb"));

        rectsPaintTwo = new Paint();
        rectsPaintTwo.setAntiAlias(true);
        rectsPaintTwo.setStrokeWidth(DensityUtils.dp2px(getContext(), 3));
        rectsPaintTwo.setStyle(Paint.Style.STROKE);
        rectsPaintTwo.setColor(Color.parseColor("#FB9E3E"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
    }
}
