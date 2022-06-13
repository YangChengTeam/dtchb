package com.yc.rrdsprj.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.yc.rrdsprj.R;
import com.yc.rrdsprj.utils.CommonUtils;

import java.util.ArrayList;

public class NineLuckPan extends View {
    public String type;//1 每日升级  2 每日提现
    private Context mContent;
    private Paint mPaint;
    private  Paint paintThree;
    private ArrayList<RectF> mRects;//存储矩形的集合
    private float mStrokWidth = 5;//矩形的描边宽度
    private int[] mItemColor = {Color.parseColor("#ffffff"), Color.parseColor("#ffffff")};//矩形的颜色
    private int mRectSize;//矩形的宽和高（矩形为正方形）
    private boolean mClickStartFlag = false;//是否点击中间矩形的标记
    private int mRepeatCount = 3;//转的圈数
    private int mLuckNum = 3;//最终中奖位置
    private int mPosition = -1;//抽奖块的位置
    private int mStartLuckPosition = 0;//开始抽奖的位置
    private int[] mImgs = {R.drawable.prize_bg3, R.drawable.luckpan1,R.drawable.prize_bg3, R.drawable.luckpan1, R.drawable.luckpan1, R.drawable.prize_bg3, R.drawable.luckpan1, R.drawable.luckpan1, R.drawable.luckpan_go};
    private String[] mLuckStr = {"升1级", "2.0元", "升2级", "3.0元", "升3级", "4.0元", "升5级", "1.0元"};
    private OnLuckPanListener onLuckPanListener;
    private int spaceWidth = 15;

    private PaintFlagsDrawFilter pfd;
    private Paint mImgPaint;

    public OnLuckPanListener getOnLuckPanListener() {
        return onLuckPanListener;
    }

    public void setOnLuckPanListener(OnLuckPanListener onLuckPanListener) {
        this.onLuckPanListener = onLuckPanListener;
    }

    public NineLuckPan(Context context) {
        this(context, null);
    }

    public NineLuckPan(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineLuckPan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public int getmLuckNum() {
        return mLuckNum;
    }

    public void setmLuckNum(int mLuckNum) {
        this.mLuckNum = mLuckNum;
    }

    public int[] getmImgs() {
        return mImgs;
    }

    public void setmImgs(int[] mImgs) {
        this.mImgs = mImgs;
        invalidate();
    }

    public String[] getmLuckStr() {
        return mLuckStr;
    }

    public void setmLuckStr(String[] mLuckStr) {
        this.mLuckStr = mLuckStr;
        invalidate();
    }

    /**
     * 初始化数据
     */
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStrokWidth);

        paintThree = new Paint();
        paintThree.setStyle(Paint.Style.STROKE);
        paintThree.setColor(Color.parseColor("#FF1849"));
        paintThree.setStrokeWidth(8);

        mRects = new ArrayList<>();

        mImgPaint = new Paint();
        pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mImgPaint.setFilterBitmap(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //mRectSize = Math.min(w, h) / 3;//获取矩形的宽和高
        mRectSize = (Math.min(w, h) - 60) / 3;
        mRects.clear();//当控件大小改变的时候清空数据
        initRect();//重新加载矩形数据
    }

    /**
     * 加载矩形数据
     */
    private void initRect() {
        //加载前三个矩形
        for (int x = 0; x < 3; x++) {
            float left = x * mRectSize;
            float top = spaceWidth;
            float right = (x + 1) * mRectSize;
            float bottom = mRectSize + spaceWidth;
            RectF rectF = new RectF(left + (spaceWidth * (x + 1)), top, right + (spaceWidth * (x + 1)), bottom);
            mRects.add(rectF);
        }
        //加载第四个
        mRects.add(new RectF(getWidth() - mRectSize - spaceWidth, mRectSize + spaceWidth * 2, getWidth() - spaceWidth, mRectSize * 2 + spaceWidth * 2));
        //加载第五~七个
        for (int y = 3; y > 0; y--) {
            float left = getWidth() - (4 - y) * mRectSize - ((4 - y) * spaceWidth);
            float top = mRectSize * 2 + spaceWidth * 3;
            float right = (y - 3) * mRectSize + getWidth() - (4 - y) * spaceWidth;
            float bottom = mRectSize * 3 + spaceWidth * 3;
            RectF rectF = new RectF(left, top, right, bottom);
            mRects.add(rectF);
        }
        //加载第八个
        mRects.add(new RectF(0 + spaceWidth, mRectSize + spaceWidth * 2, mRectSize + spaceWidth, mRectSize * 2 + spaceWidth * 2));
        //加载第九个
        mRects.add(new RectF(mRectSize + spaceWidth * 2, mRectSize + spaceWidth * 2, mRectSize * 2 + spaceWidth * 2, mRectSize * 2 + spaceWidth * 2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mRects.get(8).contains(event.getX(), event.getY())) {
                mClickStartFlag = true;
            } else {
                mClickStartFlag = false;
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mClickStartFlag) {
                if (mRects.get(8).contains(event.getX(), event.getY())) {
                    onLuckPanListener.onLuckStart();
                    //startAnim();//判断只有手指落下和抬起都在中间的矩形内才开始抽奖
                }
                mClickStartFlag = false;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRects(canvas);
        drawImages(canvas);
    }


    /**
     * 画图片
     *
     * @param canvas
     */
    private void drawImages(Canvas canvas) {
        canvas.setDrawFilter(pfd);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#E62601"));
        paint.setAntiAlias(true);
        paint.setTextSize(CommonUtils.sp2px(getContext(),10));



        Paint painttwo = new Paint();
        painttwo.setStyle(Paint.Style.FILL);
        painttwo.setColor(Color.parseColor("#FFFFFF"));
        painttwo.setAntiAlias(true);
        painttwo.setTextSize(CommonUtils.sp2px(getContext(),10));
        int tempLeft = (mRectSize - CommonUtils.dp2px(getContext(),35)) / 2;
        int tempLefttwo = (mRectSize - CommonUtils.dp2px(getContext(),47)) / 2;
        for (int x = 0; x < mRects.size(); x++) {
            RectF rectF = mRects.get(x);
            float left = rectF.left;
            float top = rectF.top + 10;
            float bottom = rectF.bottom;
            float toptwo = rectF.top +50;
            if (x != 8) {
                String text = mLuckStr[x];
                if (TextUtils.isEmpty(text)||"0".equals(text)||"0.00".equals(text)||"0.0".equals(text)){
                    text="???";
                }
                Rect tRect = new Rect();
                paint.getTextBounds(text, 0, text.length(), tRect);
                float temp = (mRectSize - tRect.width()) / 2 + left;
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), mImgs[x]), left + tempLefttwo, toptwo, null);
                canvas.drawText(text, temp+1, bottom -mRectSize/2+tRect.height(), painttwo);
            } else {
                canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), mImgs[x]), mRectSize, mRectSize, false), rectF.left, rectF.top, null);
            }
        }
    }

    /**
     * 画矩形
     *
     * @param canvas
     */
    private void drawRects(Canvas canvas) {
        for (int x = 0; x < mRects.size(); x++) {
            RectF rectF = mRects.get(x);
            if (x == 8) {
                mPaint.setColor(Color.TRANSPARENT);
                canvas.drawRect(rectF, mPaint);
            } else {
                mPaint.setColor(mItemColor[x % 2]);
                if (mPosition == x) {
                    mPaint.setColor(getResources().getColor(R.color.red_F1DBCA));
                    canvas.drawRoundRect(rectF, 20, 20, paintThree);
                }
                canvas.drawRoundRect(rectF, 20, 20, mPaint);
            }
        }
    }

    public void setPosition(int position) {
        mPosition = position;
        invalidate();
    }

    /**
     * 开始动画
     */
    public void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mStartLuckPosition, mRepeatCount * 8 + mLuckNum).setDuration(4000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int position = (int) animation.getAnimatedValue();
                setPosition(position % 8);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mStartLuckPosition = mLuckNum;
                if (onLuckPanListener != null) {
                    onLuckPanListener.onAnimEnd(mPosition, mLuckStr[mPosition]);
                }
            }
        });
        valueAnimator.start();
        //ScaleAnimation animation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,1, 0.5f);
    }

    public void setType(String type) {
         this.type=type;
    }

    public interface OnLuckPanListener {
        void onLuckStart();

        void onAnimEnd(int position, String msg);
    }
}
