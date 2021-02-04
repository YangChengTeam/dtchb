package com.yc.redguess.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class CountDownUtilsThree {

    private Timer mTimer;
    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                if (onCountDownListen!=null){
                    onCountDownListen.count(mMin,mSecond);
                    if (mSecond == 0  && mMin == 0 ) {
                        mTimer.cancel();
                        onCountDownListen.countFinsh();
                    }
                }
            }
        }
    };

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
                mMin = 0;
                mSecond = 0;
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
           void count(long mMin, long mSecond);
           void countFinsh();
    }
    public void  setHours(long mMin,long mSecond){
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
