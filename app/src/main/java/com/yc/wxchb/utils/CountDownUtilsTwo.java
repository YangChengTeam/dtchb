package com.yc.wxchb.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class CountDownUtilsTwo {
    private Timer mTimer;
    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                if (onCountDownListen!=null){
                    onCountDownListen.count(mHour,mMin,mSecond);
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
        mSecond++;
        if (mSecond ==60) {
            mMin++;
            mSecond = 0;
            if (mMin ==60) {
                mMin = 0;
                mHour++;
                if (mHour ==24) {
                    // 倒计时结束
                    mHour=0;
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

    public Timer  getTimes(){
        return mTimer;
    }
    public OnCountDownListen onCountDownListen;
    public void setOnCountDownListen(OnCountDownListen onCountDownListen){
           this.onCountDownListen=onCountDownListen;
    }
    public interface OnCountDownListen{
           void count(long mHour, long mMin, long mSecond);
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
