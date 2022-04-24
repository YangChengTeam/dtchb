package com.yc.wxchb.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.lq.lianjibusiness.base_libary.App.App;
import com.tachikoma.core.component.IFactory;
import com.yc.wxchb.R;
import com.yc.wxchb.constants.Constant;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


public class ToastShowViews {
    private static Toast toastFour;
    private static  Disposable s;


    public static void showMyToastTwo() {
        if (Constant.APPTYPE==2){
            if (Constant.DIQU_PIBI==0){
                if ("2".equals(Constant.ISSHOTOAST)){
                    if (RomUtil.isVivo()&& Build.VERSION.SDK_INT ==22){

                    }else {
                        if (RomUtil.isVivo()&& Build.VERSION.SDK_INT ==25){

                        }else {
                            shows();
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    public static void shows(){
        try {
            try {
                if (toastFour!=null){
                    toastFour=null;
                }
                toastFour = new Toast(App.getInstance());
                View view=null;
                view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three, null);//点击下载广告 解锁快速签到
                toastFour.setView(view);
                toastFour.setDuration(Toast.LENGTH_LONG);
                toastFour.setGravity(Gravity.BOTTOM, 0, 360);
                hook(toastFour);
                toastFour.show();
            }catch (Exception e){

            }
            List<String> datas=new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                datas.add("2");
            }
            Observable<String> listObservable = Observable.fromIterable(datas);
            Observable<Long> timeObservable = Observable.interval(5 ,TimeUnit.SECONDS);
            Observable.zip(listObservable, timeObservable, new BiFunction<String, Long, Object>() {
                @Override
                public Object apply(String s, Long aLong) throws Exception {
                    return s;
                }//AndroidSchedulers.mainThread()
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
                @Override
                public void onSubscribe(Disposable d) {
                    s=d;
                }

                @Override
                public void onNext(Object o) {
                    try {
                        if (toastFour!=null){
                            toastFour.show();
                        }
                    }catch (Exception e){

                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }catch (Exception e){

        }
    }

    public static void cancleToastTwo() {
        if (Constant.APPTYPE==2){
            if (Constant.DIQU_PIBI==0){
                if ("2".equals(Constant.ISSHOTOAST)){
                    if (s!=null&&!s.isDisposed()){
                        s.dispose();
                    }
                }
            }
        }
    }


    private static Field sFieldTN;
    private static Field sFieldTNHandler;

    static {
        try {
            sFieldTN = Toast.class.getDeclaredField("mTN");
            sFieldTN.setAccessible(true);
            sFieldTNHandler = sFieldTN.getType().getDeclaredField("mHandler");
            sFieldTNHandler.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void hook(Toast toast) {
        try {
            Object tn = sFieldTN.get(toast);
            Handler preHandler = (Handler) sFieldTNHandler.get(tn);
            sFieldTNHandler.set(tn, new SafelyHandlerWrapper(preHandler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SafelyHandlerWrapper extends Handler {
        private Handler impl;

        SafelyHandlerWrapper(Handler impl) {
            this.impl = impl;
        }

        @Override
        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void handleMessage(Message msg) {
            impl.handleMessage(msg);
        }
    }




}