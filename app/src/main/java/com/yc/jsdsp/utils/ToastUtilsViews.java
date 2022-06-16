package com.yc.jsdsp.utils;

public class ToastUtilsViews {
//    private static Toast toast;
//    private static Toast toastTwo;
//    private static Toast toastThree;
//
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
//
//    private static void hook(Toast toast) {
//        try {
//            Object tn = sFieldTN.get(toast);
//            Handler preHandler = (Handler) sFieldTNHandler.get(tn);
//            sFieldTNHandler.set(tn, new SafelyHandlerWrapper(preHandler));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
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
//
//
//    public static void showToast(String msg) {
//
//    }
//
//
//
//    public static void showCenterToast(String type,String styContents) {
//        if (App.getInstance() == null) {
//            return;
//        }
//        try {
//            if (Build.VERSION.SDK_INT !=25) {
//                if (RomUtil.isVivo()&& Build.VERSION.SDK_INT ==22){
//
//                }else {
//                    toast = new Toast(App.getInstance());
//                    View view=LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view, null);
//                    TextView textView=view.findViewById(R.id.tv_contents);
//                    TextView tv_money=view.findViewById(R.id.tv_money);
//                    ImageView iv_type=view.findViewById(R.id.iv_type);
//                    ImageView iv_typetwo=view.findViewById(R.id.iv_typeTwo);
//                    if ("1".equals(type)){//夺宝券
//                        textView.setText("夺宝券+");
//                        tv_money.setText("1");
//                        iv_type.setVisibility(View.VISIBLE);
//                        iv_typetwo.setVisibility(View.GONE);
//                    }else {
//                        textView.setText("红包+");
//                        tv_money.setText(styContents+"元");
//                        iv_type.setVisibility(View.GONE);
//                        iv_typetwo.setVisibility(View.VISIBLE);
//                    }
//                    toast.setView(view);
//                    toast.setGravity(Gravity.BOTTOM, 0, 350);
//                    hook(toast);
//                    toast.show();
//                }
//            }
//        }catch (Exception e){
//
//        }
//    }
//
//    public static void showCenterToastTwo(String type,String styContents) {
//        if (App.getInstance() == null) {
//            return;
//        }
//        try {
//            if (Build.VERSION.SDK_INT !=25) {
//                if (RomUtil.isVivo()&& Build.VERSION.SDK_INT ==22){
//
//                }else {
//                    toastTwo = new Toast(App.getInstance());
//                    View view=LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view, null);
//                    TextView textView=view.findViewById(R.id.tv_contents);
//                    TextView tv_money=view.findViewById(R.id.tv_money);
//                    ImageView iv_type=view.findViewById(R.id.iv_type);
//                    ImageView iv_typetwo=view.findViewById(R.id.iv_typeTwo);
//                    if ("1".equals(type)){//夺宝券
//                        textView.setText("夺宝券+");
//                        tv_money.setText("1");
//                        iv_type.setVisibility(View.VISIBLE);
//                        iv_typetwo.setVisibility(View.GONE);
//                    }else {
//                        textView.setText("红包+");
//                        tv_money.setText(styContents+"元");
//                        iv_type.setVisibility(View.GONE);
//                        iv_typetwo.setVisibility(View.VISIBLE);
//                    }
//                    toastTwo.setView(view);
//                    toastTwo.setGravity(Gravity.CENTER, 0, 0);
//                    if (AppSettingUtils.isShowToast()){
//                        hook(toastTwo);
//                    }
//                    toastTwo.show();
//                }
//            }
//        }catch (Exception e){
//
//        }
//    }
//
//    public static void showCenterToastThree() {
//        if (App.getInstance() == null) {
//            return;
//        }
//        try {
//            toastThree = new Toast(App.getInstance());
//            View view=LayoutInflater.from(App.getInstance()).inflate(R.layout.toast_view_three, null);
//            toastThree.setView(view);
//            toastThree.setDuration(Toast.LENGTH_LONG);
//            toastThree.setGravity(Gravity.BOTTOM, 0, 280);
//            if (AppSettingUtils.isShowToast()){
//                hook(toastThree);
//            }
//            toastThree.show();
//        }catch (Exception e){
//
//        }
//    }

}
