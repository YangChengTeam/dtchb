package com.yc.redguess.homeModule.widget;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.redguess.R;
import com.yc.redguess.utils.ToastUtilsViews;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;


public class ToastShowViews {

    private static Toast toastThree;
   private static  Disposable s;

    @SuppressLint("CheckResult")
    public static void showMyToast() {
//           try {
//               try {
//                   toastThree = new Toast(App.getInstance());
//                   View view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three, null);
//                   toastThree.setView(view);
//                   toastThree.setDuration(Toast.LENGTH_LONG);
//                   toastThree.setGravity(Gravity.BOTTOM, 0, 280);
//                   toastThree.show();
//               }catch (Exception e){
//
//               }
//
//               List<String> datas=new ArrayList<>();
//               for (int i = 0; i < 3; i++) {
//                   datas.add("2");
//               }
//               Observable<String> listObservable = Observable.fromIterable(datas);
//               Observable<Long> timeObservable = Observable.interval(5, TimeUnit.SECONDS);
//               Observable.zip(listObservable, timeObservable, new BiFunction<String, Long, Object>() {
//                   @Override
//                   public Object apply(String s, Long aLong) throws Exception {
//                       return s;
//                   }
//               }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
//                   @Override
//                   public void onSubscribe(Disposable d) {
//                       s=d;
//                   }
//
//                   @Override
//                   public void onNext(Object o) {
//                       try {
//                           toastThree = new Toast(App.getInstance());
//                           View view=LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three, null);
//                           toastThree.setView(view);
//                           toastThree.setDuration(Toast.LENGTH_LONG);
//                           toastThree.setGravity(Gravity.BOTTOM, 0, 280);
//                           toastThree.show();
//                       }catch (Exception e){
//
//                       }
//                   }
//
//                   @Override
//                   public void onError(Throwable e) {
//
//                   }
//
//                   @Override
//                   public void onComplete() {
//
//                   }
//               });
//           }catch (Exception e){
//
//           }
    }

    public static void cancleToast() {
//      if (s!=null&&!s.isDisposed()){
//          s.dispose();
//      }
    }




    @SuppressLint("CheckResult")
    public static void showMyToastTwo(String str) {
           try {
               try {
                   toastThree = new Toast(App.getInstance());
                   View view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three, null);
                   TextView tv_str=view.findViewById(R.id.tv_des);
                   if (!TextUtils.isEmpty(str)){
                       tv_str.setText(str);
                   }
                   toastThree.setView(view);
                   toastThree.setDuration(Toast.LENGTH_LONG);
                   toastThree.setGravity(Gravity.BOTTOM, 0, 280);
                   toastThree.show();
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
                   }
               }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
                   @Override
                   public void onSubscribe(Disposable d) {
                       s=d;
                   }

                   @Override
                   public void onNext(Object o) {
                       try {
                           toastThree = new Toast(App.getInstance());
                           View view=LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three, null);
                           TextView tv_str=view.findViewById(R.id.tv_des);
                           if (!TextUtils.isEmpty(str)){
                               tv_str.setText(str);
                           }
                           toastThree.setView(view);
                           toastThree.setDuration(Toast.LENGTH_LONG);
                           toastThree.setGravity(Gravity.BOTTOM, 0, 380);
                           hook(toastThree);
                           toastThree.show();
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
      if (s!=null&&!s.isDisposed()){
          s.dispose();
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