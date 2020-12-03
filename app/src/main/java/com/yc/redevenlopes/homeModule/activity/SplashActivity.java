package com.yc.redevenlopes.homeModule.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.lq.lianjibusiness.base_libary.App.App;
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

    private void initData() {
        ValueAnimator objectAnimator = ObjectAnimator.ofInt(1, 100);
        objectAnimator.addUpdateListener(animation -> {
            if (progressbar != null) {
                int animatedFraction = (int) animation.getAnimatedValue();
                progressbar.setProgress(animatedFraction);
                tvProgress.setText(String.format(getString(R.string.percent), animatedFraction));
                if (animatedFraction == 100) {
                    if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getAgreement())){
                        toMain();
                    }
                }
            }

        });
        objectAnimator.setDuration(2000);
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

        mDisposables.add(apis.login(1, null, null, null, null, 2, null, agentId, DeviceUtils.getImei()).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultRefreshSubscriber<UserInfo>() {
                    @Override
                    public void onAnalysisNext(UserInfo data) {
                       // showSplash();
                        CacheDataUtils.getInstance().saveUserInfo(data);
                        if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getAgreement())) {

                        } else {
                            showAgreementDialog();
                        }
                         objectAnimator.start();
                    }
                }));

    }

    private void showAgreementDialog() {
        UserPolicyFragment userPolicyFragment = new UserPolicyFragment();
        userPolicyFragment.setUserPolicyOncliciListen(new UserPolicyFragment.UserPolicyOncliciListen() {
            @Override
            public void know() {
                CacheDataUtils.getInstance().setSol("1");
                CacheDataUtils.getInstance().setAgreement();
                toMain();
            }
        });
        userPolicyFragment.show(getSupportFragmentManager(), "");
    }


    private void initLog() {

        String sv = Build.MODEL.contains(Build.BRAND) ? Build.MODEL + " " + Build.VERSION.RELEASE : Build.BRAND + " " + Build.MODEL + " " + Build.VERSION.RELEASE;
        String imei;
        if (CacheDataUtils.getInstance().isLogin()){
            imei=CacheDataUtils.getInstance().getUserInfo().getImei();
        }else {
            imei=DeviceUtils.getImei();
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
//        if (isAdClick) {
//            isAdClick = false;
//            toMain();
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(this, requestCode);
    }

//    private void showSplash() {
//        AdPlatformSDK.getInstance(this).showSplashVerticalAd(this, "ad_kaiping",new AdCallback() {
//            @Override
//            public void onDismissed() {
//                if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getAgreement())) {
//                    toMain();
//                }
//            }
//
//            @Override
//            public void onNoAd(AdError adError) {
//                if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getAgreement())) {
//                    toMain();
//                }
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//
//            @Override
//            public void onPresent() {
//            }
//
//            @Override
//            public void onClick() {
//                isAdClick = true;
//            }
//        }, frameItem);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
