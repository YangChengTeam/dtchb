package com.yc.redkingguess.homeModule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.yc.redkingguess.utils.DisplayUtil;

public class SignProgress extends View {
    private Paint bgPaint;
    private Paint ciclePaint;
    private Paint progressPaint;
    private float w;
    private float h;
    public SignProgress(Context context) {
        this(context,null);
    }

    public SignProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SignProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        bgPaint=new Paint();
        bgPaint.setColor(Color.parseColor("#FDE1B2"));
        bgPaint.setStrokeWidth(DisplayUtil.dip2px(context,1));

        ciclePaint=new Paint();
        ciclePaint.setColor(Color.parseColor("#ffffff"));
        ciclePaint.setStrokeWidth(DisplayUtil.dip2px(context,1));
        ciclePaint.setStyle(Paint.Style.FILL);

        progressPaint=new Paint();
        progressPaint.setColor(Color.parseColor("#FE7A25"));
        progressPaint.setStrokeWidth(DisplayUtil.dip2px(context,1));
        progressPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);

    }

    private void drawBg(Canvas canvas) {
       // canvas.drawCircle();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w=w;
        this.h=h;
    }
}
