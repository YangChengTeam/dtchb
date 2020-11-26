package com.yc.redevenlopes.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class CountDownUtils {
    private Timer mTimer;
    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                onCountDownListen.count(mHour,mMin,mSecond);
                if (mSecond == 0  && mHour == 0 && mMin == 0 ) {
                    mTimer.cancel();
                    onCountDownListen.countFinsh();
                }
            }
        }
    };

    private long mHour = 11;//小时,
    private long mMin = 56;//分钟,
    private long mSecond = 32;//秒
    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour= 0;
                    mMin = 0;
                    mSecond = 0;
                }
            }
        }
    }
    TimerTask mTimerTask;
    public void startRun() {
        mTimer=new Timer();
        mTimerTask= new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,0,1000);
    }
    public OnCountDownListen onCountDownListen;
    public void setOnCountDownListen(OnCountDownListen onCountDownListen){
           this.onCountDownListen=onCountDownListen;
    }
    public interface OnCountDownListen{
           void count(long mHour,long mMin,long mSecond);
           void countFinsh();
    }
    public void  setHours(long mHour,long mMin,long mSecond){
        this.mHour=mHour;
        this.mMin=mMin;
        this.mSecond=mSecond;
        startRun();
    }
    public void  clean(){
        if (mTimer!=null){
            mTimer.cancel();
            mTimer=null;
        }
        if (mTimerTask!=null){
            mTimerTask.cancel();
            mTimerTask=null;
        }
    }

}
