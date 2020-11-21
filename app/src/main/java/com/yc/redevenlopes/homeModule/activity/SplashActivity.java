package com.yc.redevenlopes.homeModule.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.App.App;
import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultRefreshSubscriber;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.SimpleActivity;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.application.MyApplication;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.SplashBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;



/**
 * Created by suns  on 2020/11/18 17:48.
 */
public class SplashActivity extends SimpleActivity {


    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_progress)
    TextView tvProgress;


    private static final int REQUEST_CODE = 1000;

    private String[] request_permissons = new String[]{
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

        applyPermissions();

        initLog();
        initData();

    }

    private void initData() {

        ValueAnimator objectAnimator = ObjectAnimator.ofInt(1, 100);
        objectAnimator.addUpdateListener(animation -> {

            int animatedFraction = (int) animation.getAnimatedValue();
            progressbar.setProgress(animatedFraction);
            tvProgress.setText(String.format(getString(R.string.percent), animatedFraction));

            if (animatedFraction == 100) {
                toMain();
            }

        });
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());

        objectAnimator.start();

    }


    private void initLog(){
        apis=new HomeApiModule();
        mDisposables = new CompositeDisposable();
        String sv = android.os.Build.MODEL.contains(android.os.Build.BRAND) ? android.os.Build.MODEL + " " + android
                .os.Build.VERSION.RELEASE : Build.BRAND + " " + android
                .os.Build.MODEL + " " + android.os.Build.VERSION.RELEASE;
        String uid = CommonUtils.getUid(App.getInstance());
        String versionCode = CommonUtils.getAppVersionCode(App.getInstance());
        String versionName = CommonUtils.getAppVersionName(App.getInstance());
        MyApplication app = (MyApplication)App.getInstance();
        mDisposables.add(apis.initLog(uid, app.getAgentId(), versionCode, versionName, sv).compose(RxUtil.<HttpResult<SplashBeans>>rxSchedulerHelper()).subscribeWith(new ResultRefreshSubscriber<SplashBeans>() {
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
            List<String> denyPermissions = checkPermission(request_permissons);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            List<String> denyPermissions = checkPermission(permissions);
            if (denyPermissions.size() > 0) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            } else {
                initData();
            }
        }
    }
}
