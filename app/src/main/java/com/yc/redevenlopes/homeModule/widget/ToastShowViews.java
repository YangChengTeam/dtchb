package com.yc.redevenlopes.homeModule.widget;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.lq.lianjibusiness.base_libary.App.App;
import com.yc.redevenlopes.R;
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
    private static ToastShowViews toastUtilsViews;
    private static Toast toastThree;
    private static Timer timer;
    public static ToastShowViews getInstance() {
        if (toastUtilsViews == null) {
            synchronized (ToastShowViews.class) {
                if (toastUtilsViews == null) {
                    toastUtilsViews = new ToastShowViews();
                }
            }
        }
        return toastUtilsViews;
    }
   private Disposable s;

    @SuppressLint("CheckResult")
    public void showMyToast() {
        if (App.getInstance() == null) {
            return;
        }
           try {
               try {
                   toastThree = new Toast(App.getInstance());
                   View view=LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three, null);
                   toastThree.setView(view);
                   toastThree.setDuration(Toast.LENGTH_LONG);
                   toastThree.setGravity(Gravity.BOTTOM, 0, 280);
                   toastThree.show();
               }catch (Exception e){

               }

               List<String> datas=new ArrayList<>();
               for (int i = 0; i < 3; i++) {
                   datas.add("2");
               }
               Observable<String> listObservable = Observable.fromIterable(datas);
               Observable<Long> timeObservable = Observable.interval(5, TimeUnit.SECONDS);
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
                           toastThree.setView(view);
                           toastThree.setDuration(Toast.LENGTH_LONG);
                           toastThree.setGravity(Gravity.BOTTOM, 0, 280);
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

    public void cancleToast() {
      if (s!=null&&!s.isDisposed()){
          s.dispose();
      }
    }
}