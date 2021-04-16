package com.yc.majiaredgrab.homeModule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.yc.majiaredgrab.R;

public class PrizeTextView extends View {
    private Context context;
    private Paint textPaint;
    private Paint textPaintTwo;
    private int w;
    private int h;
    private String prize;
    private String contents;
    public PrizeTextView(Context context) {
        this(context,null);
    }

    public PrizeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PrizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    private void init() {
        textPaint=new Paint();
        textPaint.setColor(context.getResources().getColor(R.color.A1_333333));
        textPaint.setStrokeWidth(2);
        textPaint.setTextSize(13);

        textPaintTwo=new Paint();
        textPaint.setColor(context.getResources().getColor(R.color.A1_FB9E3E));
        textPaint.setStrokeWidth(2);
        textPaint.setTextSize(13);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w=w;
        this.h=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cavasText(canvas);

    }

    private void cavasText(Canvas canvas) {
        SpannableStringBuilder builder = new SpannableStringBuilder(contents);
//ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
       // textView.setText(builder);


    }

    private void  setPrizeNums(String prize){
           this.prize=prize;
    }
    private void  setContents(String contents){
        this.contents=contents;
    }
}
