package com.yc.jsdps.beans.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;


public class CircleProgressView extends View {
    private Paint mBackPaint;
    private Paint mBackWhitePaint;
    private Paint mFrontPaint;
    private Paint mTextPaint;
    private float mStrokeWidth = 16;
    private float mHalfStrokeWidth = mStrokeWidth / 2;
    private float mRadius = 200;
    private RectF mRect;
    private int mProgress = 0;
    //目标值，想改多少就改多少
    private int mTargetProgress = 30000;
    private int mMax = 100;
    private int mWidth;
    private int mHeight;



    public CircleProgressView(Context context) {
        super(context);
        init();
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    //完成相关参数初始化
    private void init() {
        mBackPaint = new Paint();
        mBackPaint.setStrokeWidth(mStrokeWidth);
        mBackPaint.setColor(Color.parseColor("#FFFFFF"));
        mBackPaint.setAntiAlias(false);
        mBackPaint.setAlpha(50);
        mBackPaint.setStyle(Paint.Style.FILL);

        mBackWhitePaint = new Paint();
        mBackWhitePaint.setStrokeWidth(1);
        mBackWhitePaint.setColor(Color.parseColor("#5688FB"));
        mBackWhitePaint.setAntiAlias(false);
        mBackWhitePaint.setStyle(Paint.Style.FILL);

        mFrontPaint = new Paint();
        mFrontPaint.setStrokeWidth(mStrokeWidth);
        mFrontPaint.setColor(Color.parseColor("#FF9B26"));
        mFrontPaint.setAntiAlias(false);
        mFrontPaint.setStyle(Paint.Style.STROKE);


        mTextPaint = new Paint();
        mTextPaint.setColor(Color.GREEN);
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(48);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        iniTAnimotor();
    }


    //重写测量大小的onMeasure方法和绘制View的核心方法onDraw()
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
        mRadius=(Math.min(mWidth,mHeight)-30)/2;
        initRect();
    }
    private String contents;
    private  float angle;
    @Override
    protected void onDraw(Canvas canvas) {
        angle = mProgress;
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mBackPaint);
        canvas.drawArc(mRect, -90, angle, false, mFrontPaint);
    }

    public int getRealSize(int measureSpec) {
        int result = 1;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            //自己计算
            result = (int) (mRadius * 2 + mStrokeWidth);
        } else {
            result = size;
        }

        return result;
    }

    private void initRect() {
        if (mRect == null) {
            mRect = new RectF();
            float left = mWidth/2-mRadius+8;
            float top = mHeight/2 - mRadius+8;
            float right = mWidth/2 + mRadius-8;
            float bottom =  mHeight/2 + mRadius-8;
            mRect.set(left, top, right, bottom);
        }
    }


    public void startAnimotor(int mTargetProgress) {
           this.mTargetProgress=mTargetProgress;
           if (valueAnimator!=null){
               valueAnimator.setDuration(mTargetProgress);
               valueAnimator.start();
           }
    }

    public void stopAnimotor() {
        if (valueAnimator!=null){
            valueAnimator.pause();
        }
    }
    public void continueAnimotor(boolean isDialogs) {
        if (valueAnimator!=null){
            valueAnimator.resume();
        }
       if (!isDialogs){
        if (isRuns){

        }else {
            valueAnimator.setDuration(mTargetProgress);
            valueAnimator.start();
        }
      }
    }

    public ValueAnimator getAni(){
          return valueAnimator;
    }

   private boolean isRuns;
   private  ValueAnimator valueAnimator;
    private     long currentPlayTime;
    public void iniTAnimotor(){
        valueAnimator=new ValueAnimator();
        valueAnimator.setFloatValues(0,360);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                isRuns=true;
                float animatedValue = (float) animation.getAnimatedValue();
                mProgress= (int) animatedValue;
                currentPlayTime = valueAnimator.getCurrentPlayTime();
                if (animotorListen!=null){
                    animotorListen.times(currentPlayTime);
                }
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }


            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {

            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                     isRuns=false;
                     if (animotorListen!=null){
                         animotorListen.ends();
                     }
            }
        });
        valueAnimator.setDuration(mTargetProgress);
    }
    public AnimotorListen animotorListen;

    public void cancle() {
        if (valueAnimator!=null){
            valueAnimator.cancel();
            valueAnimator=null;
        }
    }

    public interface AnimotorListen{
           void ends();
           void times(long time);
    }
    public void setAnimotorListen(AnimotorListen listen){
        this.animotorListen=listen;
    }


}
