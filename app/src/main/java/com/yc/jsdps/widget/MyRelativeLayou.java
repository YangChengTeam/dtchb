package com.yc.jsdps.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MyRelativeLayou extends RelativeLayout {
    public MyRelativeLayou(Context context) {
        this(context,null);
    }

    public MyRelativeLayou(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRelativeLayou(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    //记录上次滑动后的坐标值
//    private int lastX;
//    private int lastY;
//
//    private int mx;
//    private int my;
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Boolean intercept=false;
//        //获取坐标点：
//        int x= (int) ev.getX();
//        int y= (int) ev.getY();
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                intercept=false;
//                Log.d("ccc","---ACTION_DOWN---------111");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d("ccc", "----000-----ACTION_DOWN: x："+x+"---y："+y+"---mx："+mx+"---my："+my);
//                int deletx=x-mx;
//                int delety=y-my;
//                Log.d("ccc", "-------111--ACTION_DOWN: "+deletx+"---"+delety);
//
//                if(Math.abs(deletx)>Math.abs(delety)){
//                    intercept=true;
//                    Log.d("ccc","---拦截了---------111");
//                }
//                else {
//                    intercept=false;
//                    Log.d("ccc","------没拦截------222");
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d("ccc","---ACTION_UP---------111");
//                intercept=false;
//                break;
//            default:
//                break;
//        }
//        //这里尤其重要，解决了拦截MOVE事件却没有拦截DOWN事件没有坐标的问题
//        lastX=x;
//        lastY=y;
//        mx=x;
//        my=y;
//        return intercept;
//    }


    private boolean isIntercept;
    private boolean isSolve;//是否完成了拦截判断，如果决定拦截，那么同系列事件就不能设置为不拦截
      private float mx;
     private float my;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mx = ev.getX();
                my = ev.getY();
                return false;//down的时候拦截后，就只能交给自己处理了

            case MotionEvent.ACTION_MOVE:
                if (isSolve){
                    isIntercept = (Math.abs(ev.getX() -mx) > Math.abs(ev.getY() -my));//如果是左右滑动，且水平角度小于30°，就拦截
                }else {
                    isIntercept=true;
                }
                return isIntercept;//如果是左右滑动，就拦截
        }
        return super.onInterceptTouchEvent(ev);
    }



    public void setCanScrollview(boolean b) {
         this.isSolve=b;
    }
}
