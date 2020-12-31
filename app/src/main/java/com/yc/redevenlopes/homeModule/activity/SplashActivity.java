package com.yc.redevenlopes.homeModule.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.lq.lianjibusiness.base_libary.App.App;
import com.lq.lianjibusiness.base_libary.App.GoagalInfo;
import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultRefreshSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.SimpleActivity;
import com.lq.lianjibusiness.base_libary.utils.DeviceUtils;
import com.umeng.analytics.MobclickAgent;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.application.MyApplication;
import com.yc.redevenlopes.dialog.YonghuxieyiDialog;
import com.yc.redevenlopes.homeModule.fragment.UserPolicyFragment;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.SplashBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.updata.DownloadManager;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.PermissionHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by suns  on 2020/11/18 17:48.
 */
public class SplashActivity extends SimpleActivity {

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.frame_item)
    FrameLayout frameItem;
    LinearLayout lineView;
    private PermissionHelper mPermissionHelper;
    private boolean isLogin;
    private static final int REQUEST_CODE = 1000;
    private boolean isAdClick;
    private String[] request_permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    public CompositeDisposable mDisposables;
    public HomeApiModule apis;

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {
        lineView=findViewById(R.id.line_view);
        initPermissions();
        apis = new HomeApiModule();
        mDisposables = new CompositeDisposable();
        initLog();
    }


    private void initPermissions() {
        mPermissionHelper = new PermissionHelper();
        mPermissionHelper.checkAndRequestPermission(this, new PermissionHelper.OnRequestPermissionsCallback() {
            @Override
            public void onRequestPermissionSuccess() {
                DownloadManager.init(new WeakReference<>(SplashActivity.this));
                initData();
            }

            @Override
            public void onRequestPermissionError() {
                initData();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    private ValueAnimator objectAnimator;

    private void initData() {
        objectAnimator = ObjectAnimator.ofInt(1, 100);
        objectAnimator.addUpdateListener(animation -> {
            if (progressbar != null) {
                int animatedFraction = (int) animation.getAnimatedValue();
                progressbar.setProgress(animatedFraction);
                tvProgress.setText(String.format(getString(R.string.percent), animatedFraction));
//                if (animatedFraction == 100) {
//                    if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getAgreement())){
//                        toMain();
//                    }
//                }
            }

        });
        objectAnimator.setDuration(1500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());

        if (apis == null) {
            apis = new HomeApiModule();
        }

        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }

        String agentId = ((MyApplication) MyApplication.getInstance()).getAgentId();
        if (TextUtils.isEmpty(agentId)) {
            agentId = "";
        }
        String oid="";
        if (Build.VERSION.SDK_INT >= 29) {
            oid = GoagalInfo.oaid;
        }
        //  objectAnimator.start();
        mDisposables.add(apis.login(1, null, null, null, null, 2, null, agentId, DeviceUtils.getImei(),oid).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultRefreshSubscriber<UserInfo>() {
                    @Override
                    public void onAnalysisNext(UserInfo data) {
                        showSplash(data.getId()+"");
                        CacheDataUtils.getInstance().saveUserInfo(data);
                        Log.d("ccc", "---1----------onComplete: "+CacheDataUtils.getInstance().getAgreement());
                        if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getAgreement())) {

                        } else {
                            showAgreementDialog();
                        }
                    }
                }));

    }

//    private void showAgreementDialog() {
//
//        UserPolicyFragment userPolicyFragment = new UserPolicyFragment();
//        userPolicyFragment.setUserPolicyOncliciListen(new UserPolicyFragment.UserPolicyOncliciListen() {
//            @Override
//            public void know() {
//                CacheDataUtils.getInstance().setSol("1");
//                CacheDataUtils.getInstance().setAgreement();
//                toMain();
//            }
//        });
//        userPolicyFragment.show(getSupportFragmentManager(), "");
//    }
  private  YonghuxieyiDialog dialog;
    private void showAgreementDialog(){
         dialog=new YonghuxieyiDialog(this);
        View view = dialog.builder(R.layout.agreement_dialog);
        TextView tv_agree=view.findViewById(R.id.tv_sure);
        TextView tv_agreeContents=view.findViewById(R.id.tv_contents);
        String str = "欢迎使用无限抢红包！我们非常重视您的隐私和个人信息保护，在您使用无限抢红包前，请认真阅读《用户隐私协议》,您同意并接受全部条款后方可使用无限抢红包。";
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(str);
        final int start = str.indexOf("《");//第一个出现的位置
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent1 = new Intent(SplashActivity.this, WebViewActivity.class);
                intent1.putExtra("url", "http://m.k1u.com/hongbao/yinsi.html");
                intent1.putExtra("title", "用户隐私协议");
                startActivity(intent1);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.A1_4EB1FF));       //设置文件颜色
                // 去掉下划线
                ds.setUnderlineText(false);
            }

        }, start, start + 8, 0);

        tv_agreeContents.setMovementMethod(LinkMovementMethod.getInstance());
        tv_agreeContents.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      CacheDataUtils.getInstance().setSol("1");
                     CacheDataUtils.getInstance().setAgreement();
                     toMain();
            }
        });
        if (!CommonUtils.isDestory(SplashActivity.this)) {
            dialog.setShow();
        }
    }



    private void initLog() {

        String sv = Build.MODEL.contains(Build.BRAND) ? Build.MODEL + " " + Build.VERSION.RELEASE : Build.BRAND + " " + Build.MODEL + " " + Build.VERSION.RELEASE;
        String imei;
        if (CacheDataUtils.getInstance().isLogin()) {
            imei = CacheDataUtils.getInstance().getUserInfo().getImei();
        } else {
            imei = DeviceUtils.getImei();
        }
        String versionCode = CommonUtils.getAppVersionCode(App.getInstance());
        String versionName = CommonUtils.getAppVersionName(App.getInstance());
        MyApplication app = (MyApplication) App.getInstance();
        mDisposables.add(apis.initLog(imei, app.getAgentId(), versionCode, versionName, sv).compose(RxUtil.<HttpResult<SplashBeans>>rxSchedulerHelper()).subscribeWith(new ResultRefreshSubscriber<SplashBeans>() {
            @Override
            public void onAnalysisNext(SplashBeans data) {


            }

            @Override
            public void errorState(String message, String state) {
                super.errorState(message, state);
            }
        }));
    }


    private void toMain() {
        if (dialog!=null){
            dialog.setDismiss();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void applyPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> denyPermissions = checkPermission(request_permissions);
            if (denyPermissions.size() == 0) {
                initData();
            } else {
                ActivityCompat.requestPermissions(this, denyPermissions.toArray(new String[]{}), REQUEST_CODE);
            }

        } else {
            initData();
        }
    }

    private List<String> checkPermission(String[] permissons) {
        if (permissons == null) return null;
        List<String> denyPermissions = new ArrayList<>();
        for (String permission : permissons) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(permission);
            }
        }
        return denyPermissions;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isAdClick) {
            isAdClick = false;
            toMain();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (objectAnimator != null) {
            objectAnimator.cancel();
            objectAnimator = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(this, requestCode);
    }

    private void showSplash(String id) {
        AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(id);
        adPlatformSDK.showSplashVerticalAd(this, "ad_kaiping", new AdCallback() {
            @Override
            public void onDismissed() {
                Log.d("ccc", "---0----------onComplete: "+CacheDataUtils.getInstance().getAgreement());
                if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getAgreement())) {
                    toMain();
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("ccc", "---1----------onComplete: "+CacheDataUtils.getInstance().getAgreement());
                if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getAgreement())) {
                    toMain();
                }
            }

            @Override
            public void onComplete() {
                Log.d("ccc", "-------------onComplete: "+id);
            }

            @Override
            public void onPresent() {
                if (lineView!=null){
                    lineView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onClick() {
                isAdClick = true;
            }

            @Override
            public void onLoaded() {
                Log.d("ccc", "-------------onLoaded: "+id);
                if (lineView!=null){
                    lineView.setVisibility(View.VISIBLE);
                }
            }
        }, frameItem);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
