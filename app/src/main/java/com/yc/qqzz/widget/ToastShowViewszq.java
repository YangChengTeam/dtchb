package com.yc.qqzz.widget;

import android.annotation.SuppressLint;
import android.os.Build;
import android.widget.Toast;

import com.yc.qqzz.constants.Constant;
import com.yc.qqzz.utils.RomUtil;

import io.reactivex.disposables.Disposable;


public class ToastShowViewszq {

    private static Toast toastThree;
    private static Toast toastFour;
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





    public static void showMyToastTwo(String str,String type) {
        if ("2".equals(Constant.ISBANNER)){
            if ("2".equals(Constant.ISSHOTOAST)){
                if (RomUtil.isVivo()&& Build.VERSION.SDK_INT ==22){

                }else {
                    if (RomUtil.isVivo()&& Build.VERSION.SDK_INT ==25){

                    }else {
                        shows(str,type);
                    }
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    public static void shows(String str,String type){
//        try {
//            try {
//                Log.d("ccc", "--------0-----shows: ");
//                if (toastFour!=null){
//                    toastFour=null;
//                }
//                toastFour = new Toast(App.getInstance());
//                View view=null;
//                Log.d("ccc", "--------1----shows: ");
//                if ("1".equals(type)){
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three, null);//点击下载广告 解锁快速签到
//                }else if ("2".equals(type)){
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_two, null);//点击广告下载试玩 有概率获取升级卷
//                }else if ("3".equals(type)){
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_three, null);//下载广告试玩10秒 可提高红包金额
//                }else if ("4".equals(type)){
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_four, null);//点击下载视频游戏  加速升到3级
//                }else if ("5".equals(type)){
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_five, null);//点击广告下载完 ，马上就能提现了
//                }else if ("6".equals(type)){
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_six, null);//点击广告下载试玩 ，有机会直接升级
//                }else if ("7".equals(type)){
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_seven, null);//点击广告下载完 ，马上就能提现了
//                }else if ("8".equals(type)){
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_eight, null);//点击广告下载试玩 ，有机会直接升级   点击下载，试玩1分钟快速升级
//                }else if ("9".equals(type)){
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_nine, null);//点击广告下载试玩 ，有机会直接升级   点击下载，试玩1分钟快速升级
//                }else if ("12".equals(type)){
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_12, null);//观看视频，点击下载广告
//                }else {
//                    view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_nine, null);//点击广告下载试玩 ，有机会直接升级   点击下载，试玩1分钟快速升级
//                }
//                Log.d("ccc", "--------2----shows: ");
//                toastFour.setView(view);
//                toastFour.setDuration(Toast.LENGTH_LONG);
//                toastFour.setGravity(Gravity.BOTTOM, 0, 280);
//                Log.d("ccc", "-------3---shows: ");
//                hook(toastFour);
//                Log.d("ccc", "-------4---shows: ");
//                toastFour.show();
//            }catch (Exception e){
//
//            }
//            List<String> datas=new ArrayList<>();
//            for (int i = 0; i < 5; i++) {
//                datas.add("2");
//            }
//            Observable<String> listObservable = Observable.fromIterable(datas);
//            Observable<Long> timeObservable = Observable.interval(5 ,TimeUnit.SECONDS);
//            Observable.zip(listObservable, timeObservable, new BiFunction<String, Long, Object>() {
//                @Override
//                public Object apply(String s, Long aLong) throws Exception {
//                    return s;
//                }//AndroidSchedulers.mainThread()
//            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
//                @Override
//                public void onSubscribe(Disposable d) {
//                    s=d;
//                }
//
//                @Override
//                public void onNext(Object o) {
//                    try {
//                        if (toastThree!=null){
//                            toastThree=null;
//                        }
//                        toastThree = new Toast(App.getInstance());
//                        View view=null;
//                        if ("1".equals(type)){
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three, null);//点击下载广告 解锁快速签到
//                        }else if ("2".equals(type)){
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_two, null);//点击广告下载试玩 有概率获取升级卷
//                        }else if ("3".equals(type)){
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_three, null);//下载广告试玩10秒 可提高红包金额
//                        }else if ("4".equals(type)){
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_four, null);//点击下载视频游戏  加速升到3级
//                        }else if ("5".equals(type)){
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_five, null);//点击广告下载完 ，马上就能提现了
//                        }else if ("6".equals(type)){
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_six, null);//点击广告下载试玩 ，有机会直接升级
//                        }else if ("7".equals(type)){
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_seven, null);//点击广告下载完 ，马上就能提现了
//                        }else if ("8".equals(type)){
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_eight, null);//点击广告下载试玩 ，有机会直接升级   点击下载，试玩1分钟快速升级
//                        }else if ("9".equals(type)){
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_nine, null);//点击广告下载试玩 ，有机会直接升级   点击下载，试玩1分钟快速升级
//                        }else if ("12".equals(type)){
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_12, null);//观看视频，点击下载广告
//                        }else {
//                            view= LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three_nine, null);//点击广告下载试玩 ，有机会直接升级   点击下载，试玩1分钟快速升级
//                        }
//                        toastThree.setView(view);
//                        toastThree.setDuration(Toast.LENGTH_LONG);
//                        toastThree.setGravity(Gravity.BOTTOM, 0, 380);
//                        hook(toastThree);
//                        toastThree.show();
//                    }catch (Exception e){
//
//                    }
//                }
//
//                @Override
//                public void onError(Throwable e) {
//
//                }
//
//                @Override
//                public void onComplete() {
//
//                }
//            });
//        }catch (Exception e){
//
//        }
//    }

//    public static void cancleToastTwo() {
//        if ("2".equals(Constant.ISBANNER)){
//            if ("2".equals(Constant.ISSHOTOAST)){
//                if (s!=null&&!s.isDisposed()){
//                    s.dispose();
//                }
//            }
//        }
//    }

//
//    private static Field sFieldTN;
//    private static Field sFieldTNHandler;
//
//    static {
//        try {
//            sFieldTN = Toast.class.getDeclaredField("mTN");
//            sFieldTN.setAccessible(true);
//            sFieldTNHandler = sFieldTN.getType().getDeclaredField("mHandler");
//            sFieldTNHandler.setAccessible(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private static void hook(Toast toast) {
//        try {
//            Object tn = sFieldTN.get(toast);
//            Handler preHandler = (Handler) sFieldTNHandler.get(tn);
//            sFieldTNHandler.set(tn, new SafelyHandlerWrapper(preHandler));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private static class SafelyHandlerWrapper extends Handler {
//        private Handler impl;
//
//        SafelyHandlerWrapper(Handler impl) {
//            this.impl = impl;
//        }
//
//        @Override
//        public void dispatchMessage(Message msg) {
//            try {
//                super.dispatchMessage(msg);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            impl.handleMessage(msg);
//        }
//    }



    }
}