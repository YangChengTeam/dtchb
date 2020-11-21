package com.yc.redevenlopes.homeModule.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.lq.lianjibusiness.base_libary.utils.DensityUtils;
import com.yc.redevenlopes.utils.DisplayUtil;

/**
 * Created by ccc  on 2020/10/19
 */

public class BarChartView extends View {
	private Context context;
    //画笔
    private Paint mPaint;
    //标题大小
    private float titleSize;

    //视图宽度
    private int width;
    //视图高度
    private int height;
    //坐标原点位置
    private final int originX = dip2px(getContext(), 20);
    private  int originY ;
    //柱状图数据
    private List<Integer> data = new ArrayList<Integer>();
    private List<String> monthList = new ArrayList<String>();
    //柱状图数据颜色
    private int[] columnColors = new int[]{Color.parseColor("#fff5eb"),Color.parseColor("#fff5eb"),Color.parseColor("#fff5eb"),Color.parseColor("#fff5eb"),Color.parseColor("#fff5eb"),Color.parseColor("#fff5eb")};
	private int padding;
	private int paddingRight;
	private int maxNums=12000;
	private boolean onDraw = true;
 
    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        //创建画笔
		padding = DensityUtils.dp2px(getContext(), 10);
		paddingRight = DensityUtils.dp2px(getContext(), 40);

        mPaint = new Paint();
        //获取配置的属性值
        titleSize = sp2px(getContext(), 14);
    }
 
	public void setData(List<Integer> data) {
		this.data = data;
		aniProgress = new double[data.size()];
		for (int i = 0; i < data.size(); i++) {
			aniProgress[i] = 0;
		}
        ani = new HistogramAnimation();
        ani.setDuration(500);
	}
	
	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}

    public void setOnDraw(boolean onDraw) {
        this.onDraw = onDraw; 
    }


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w;
		height = h;
		originY=height-dip2px(getContext(), 20);
	}

	@Override
    public void onDraw(Canvas canvas) {
    	if (onDraw) {
			drawAxisX(canvas, mPaint);
			drawAxisScaleMarkValueX(canvas, mPaint);
            drawColumn(canvas, mPaint);
			drawLine(canvas, mPaint);
            drawText(canvas, mPaint);
		}
    }

    private void drawText(Canvas canvas, Paint mPaint) {
        if(data == null)
            return;
        float cellWidth = (width-padding*5-paddingRight)/data.size();
        float h=DisplayUtil.dip2px(context,5);
        if (aniProgress != null && aniProgress.length > 0) {
            for (int i = 0; i < aniProgress.length; i++) {
                mPaint.setColor(Color.parseColor("#999999"));
                float leftTopY =height- (float) (height*(aniProgress[i]/maxNums));
                float textWidth = mPaint.measureText(String.valueOf(data.get(i)));
                canvas.drawText(String.valueOf(data.get(i)),originX+cellWidth*i+padding*i+cellWidth/2-textWidth/2,  leftTopY-2*h,mPaint);
            }
        }
    }

    private void drawLine(Canvas canvas, Paint mPaint) {
		if(data == null)
			return;
		float cellWidth = (width-padding*5-paddingRight)/data.size();
		float h=DisplayUtil.dip2px(context,4);
		if (aniProgress != null && aniProgress.length > 0) {
			for (int i = 0; i < aniProgress.length; i++) {
				mPaint.setColor(Color.parseColor("#AB5B0F"));
				float leftTopY =height- (float) (height*(aniProgress[i]/maxNums));
				canvas.drawRect(
						originX+cellWidth*i+padding*i,
						leftTopY-h,
						originX+cellWidth*i+padding*i+cellWidth,
						leftTopY,
						mPaint);
			}
		}
	}

	/**
     * 绘制横坐标轴（X轴）
     */
    private void drawAxisX(Canvas canvas, Paint paint) {
        paint.setColor(Color.parseColor("#c0c0c0"));
        //设置画笔宽度
        paint.setStrokeWidth(3);
        //设置画笔抗锯齿
        paint.setAntiAlias(true);
        //画横轴(X)
        canvas.drawLine(20, originY, originX + width, originY, paint);
    }

 
    /**
     * 绘制柱状图
     *
     */
    private void drawColumn(Canvas canvas, Paint paint) {
        if(data == null)
            return;
        float cellWidth = (width-padding*5-paddingRight)/data.size();
        if (aniProgress != null && aniProgress.length > 0) {
	        for (int i = 0; i < aniProgress.length; i++) {
	            paint.setColor(columnColors[i]);
				float leftTopY =height- (float) (height*(aniProgress[i]/maxNums));
				canvas.drawRect(
	            		originX+cellWidth*i+padding*i,
	            		leftTopY,
						originX+cellWidth*i+padding*i+cellWidth,
	            		originY,
	            		mPaint);
	        }
        }
    }
    

 
    /**
     * 绘制横坐标轴刻度值(X轴)

     */
    private void drawAxisScaleMarkValueX(Canvas canvas, Paint paint) {
		float cellWidth = (width-padding*5-paddingRight)/data.size();
        //设置画笔绘制文字的属性
        paint.setColor(Color.parseColor("#999999"));
        paint.setTextSize(sp2px(getContext(), 12));
        paint.setFakeBoldText(true);
        for (int i = 0; i < monthList.size(); i++) {
            if (i==0){
                float textWidth = paint.measureText(monthList.get(i));
                canvas.drawText(monthList.get(i)+"",
                        originX + cellWidth * i+padding*i-textWidth/2,
                        originY + dip2px(getContext(), 15),
                        paint);
            }else {
                float textWidth = paint.measureText(monthList.get(i));
                canvas.drawText(monthList.get(i)+"",
                        originX + cellWidth * i+padding*i-textWidth/2-padding/2,
                        originY + dip2px(getContext(), 15),
                        paint);
            }
        }


    }
 

    
    int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	
	private double[] aniProgress;// 实现动画的值
	private HistogramAnimation ani;
	
	public void start() {
        this.startAnimation(ani);
    }
	
	/**
     * 集成animation的一个动画类
     */
    private class HistogramAnimation extends Animation {
        protected void applyTransformation(float interpolatedTime,
                Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f ) {
                for (int i = 0; i < aniProgress.length; i++) {
                    aniProgress[i] = (int) (data.get(i) * interpolatedTime);
                }
            } else {
                for (int i = 0; i < aniProgress.length; i++) {
                    aniProgress[i] = data.get(i);
                }
            }
            invalidate();
        }
    }


}