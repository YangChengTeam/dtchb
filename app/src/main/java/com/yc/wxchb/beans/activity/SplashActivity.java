package com.yc.wxchb.beans.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.lq.lianjibusiness.base_libary.http.ResultRefreshSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.SimpleActivity;
import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.module.beans.UpgradeInfozq;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.dialog.UpdateDialog;
import com.yc.wxchb.updata.DownloadManager;
import com.yc.wxchb.utils.PermissionHelper;
import com.yc.wxchb.utils.UpDataVersion;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by suns  on 2020/11/18 17:48.
 */
public class SplashActivity extends SimpleActivity {

    @BindView(R.id.frame_item)
    FrameLayout frameItem;
    LinearLayout lineView;

    private PermissionHelper mPermissionHelper;
    public CompositeDisposable mDisposables;
    public HomeApiModule apis;

    private boolean isFirst;
    private String weixinloginType;
    @Override
    public int getLayout() {
        return R.layout.activity_splashzq;
    }

    @Override
    protected void initEventAndData() {
        lineView = findViewById(R.id.line_view);
        apis = new HomeApiModule();
        mDisposables = new CompositeDisposable();
        initPermissions();
    }

    private void initPermissions() {
        mPermissionHelper = new PermissionHelper();
        mPermissionHelper.checkAndRequestPermission(this, new PermissionHelper.OnRequestPermissionsCallback() {
            @Override
            public void onRequestPermissionSuccess() {
                DownloadManager.init(new WeakReference<>(SplashActivity.this));
                initVersion();
            }

            @Override
            public void onRequestPermissionError() {
                initVersion();
            }
        });
    }

    private UpdateDialog dialog;
    private void  initVersion(){
        mDisposables.add(apis.upVersion(((MyApplication) MyApplication.getInstance()).getAgentId()).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultRefreshSubscriber<UpDataVersion>() {
                    @Override
                    public void onAnalysisNext(UpDataVersion data) {
                        UpgradeInfozq upgradeInfo = new UpgradeInfozq();
                        upgradeInfo.setDownUrl(data.getDown_url());
                        upgradeInfo.setPageName(data.getPackage_name());
                        try {
                            PackageInfo info = getPackageManager().getPackageInfo(SplashActivity.this.getPackageName(), PackageManager.GET_ACTIVITIES);
                                    String currPackName=   SplashActivity.this.getPackageName();
                                    String packge_name = data.getPackage_name();
                                    if (!TextUtils.isEmpty(packge_name)&&!TextUtils.isEmpty(currPackName)&&!packge_name.equals(currPackName)){
                                        if (isInstalled(packge_name)){//安装了应用
                                            String relaAPPName = info.applicationInfo.loadLabel(getPackageManager()).toString();
                                            appUpDataJump(relaAPPName,data.getIcon_url(),data.getApp_name(),packge_name,true);
                                        }else {//未安装
                                            dialog = new UpdateDialog(SplashActivity.this);
                                            dialog.setInfo(upgradeInfo);
                                            dialog.show();
                                        }
                                    }else {
                                        dialog = new UpdateDialog(SplashActivity.this);
                                        dialog.setInfo(upgradeInfo);
                                        dialog.show();
                                    }
                        } catch (PackageManager.NameNotFoundException e) {
                            initData();
                        }
                    }

                    @Override
                    public void errorState(String message, String state) {
                        initData();
                    }
                }));
    }

    private SnatchDialog updataAppJump;
    public void appUpDataJump(String relaAPPName, String icon, String app_name,String packge_name,boolean isForUpData){
        updataAppJump = new SnatchDialog(SplashActivity.this);
        View builder = updataAppJump.builder(R.layout.updataappjump_dialog);
        ImageView iv_currIcon = builder.findViewById(R.id.iv_currIcon);
        ImageView iv_junpIcon = builder.findViewById(R.id.iv_jumpLogo);
        TextView tv_currAppName = builder.findViewById(R.id.iv_currName);
        TextView tv_jumpAppName = builder.findViewById(R.id.iv_jumpName);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        if (!TextUtils.isEmpty(relaAPPName)){
            tv_currAppName.setText(relaAPPName);
        }
        if (!TextUtils.isEmpty(app_name)){
            tv_jumpAppName.setText(app_name);
        }
        iv_currIcon.setImageResource(R.drawable.ic_launcher);
        if (!TextUtils.isEmpty(icon)){
            Glide.with(this).load(icon).into(iv_junpIcon);
        }

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getPackageManager().getLaunchIntentForPackage(packge_name);
                if (intent != null) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        updataAppJump.setOutCancle(false);
        updataAppJump.setShow();
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    /**
     * 检查包是否存在
     * @param packname
     * @return
     */
    private boolean isInstalled(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }
    private ValueAnimator objectAnimator;

    private void initData() {
        if (!isFirst) {
            isFirst = true;

            if (apis == null) {
                apis = new HomeApiModule();
            }

            if (mDisposables == null) {
                mDisposables = new CompositeDisposable();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        if (updataAppJump!=null){
            updataAppJump.setDismiss();
        }
        if (dialog!=null){
            dialog.dismiss();
        }
        super.onDestroy();
        if (objectAnimator != null) {
            objectAnimator.cancel();
            objectAnimator = null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
