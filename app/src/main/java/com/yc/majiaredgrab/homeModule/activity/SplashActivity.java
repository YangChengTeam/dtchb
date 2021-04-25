package com.yc.majiaredgrab.homeModule.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageInfo;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.lq.lianjibusiness.base_libary.App.App;
import com.lq.lianjibusiness.base_libary.App.GoagalInfo;
import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultRefreshSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.SimpleActivity;
import com.lq.lianjibusiness.base_libary.utils.DeviceUtils;
import com.lq.lianjibusiness.base_libary.utils.PhoneCommonUtils;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdConfigInfo;
import com.yc.adplatform.ad.core.AdError;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.application.MyApplication;
import com.yc.majiaredgrab.constants.Constant;
import com.yc.majiaredgrab.dialog.UpdateDialog;
import com.yc.majiaredgrab.dialog.YonghuxieyiDialog;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;
import com.yc.majiaredgrab.homeModule.module.bean.AdCodeBeans;
import com.yc.majiaredgrab.homeModule.module.bean.SplashBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UpgradeInfo;
import com.yc.majiaredgrab.homeModule.module.bean.UserInfo;
import com.yc.majiaredgrab.updata.DownloadManager;
import com.yc.majiaredgrab.utils.CacheDataUtils;
import com.yc.majiaredgrab.utils.CommonUtils;
import com.yc.majiaredgrab.utils.MacUtils;
import com.yc.majiaredgrab.utils.PermissionHelper;
import com.yc.majiaredgrab.utils.UpDataVersion;

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

    @BindView(R.id.frame_item)
    FrameLayout frameItem;
    LinearLayout lineView;
    private PermissionHelper mPermissionHelper;
    private boolean isLogin;
    private static final int REQUEST_CODE = 1000;
    private boolean isAdClick;
    private String loginTypes;
    private String[] request_permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public CompositeDisposable mDisposables;
    public HomeApiModule apis;

    private boolean isFirst;
    private String weixinloginType;
    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {
        lineView = findViewById(R.id.line_view);
        apis = new HomeApiModule();
        mDisposables = new CompositeDisposable();
        initPermissions();
        initLog();
    }


    private void initPermissions() {
        mPermissionHelper = new PermissionHelper();
        mPermissionHelper.checkAndRequestPermission(this, new PermissionHelper.OnRequestPermissionsCallback() {
            @Override
            public void onRequestPermissionSuccess() {
                DownloadManager.init(new WeakReference<>(SplashActivity.this));
                getAdCode();
            }

            @Override
            public void onRequestPermissionError() {
                getAdCode();
            }
        });
    }

    public void getAdCode(){
        String oid = GoagalInfo.oaid;
        String macAddress = MacUtils.getMacAddress();
        String imei = DeviceUtils.getImei();
        String imie2 = PhoneCommonUtils.getIMEI2();
        if (TextUtils.isEmpty(imie2)){
            imie2="";
        }
        mDisposables.add(apis.getAdCode(imei,oid,macAddress,imie2).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultRefreshSubscriber<AdCodeBeans>() {
                    @Override
                    public void onAnalysisNext(AdCodeBeans data) {

                        if (data!=null){
                            String share_img = data.getShare_img();
                            if (!TextUtils.isEmpty(share_img)){
                                Constant.SHAREURL=share_img ;
                            }
                        }

                        if (data!=null&&!TextUtils.isEmpty(data.getAd_jili())){
                            if (!TextUtils.isEmpty(data.getAd_jili())) {
                                Constant.RVIDEO=data.getAd_jili();
                            }
                            if (!TextUtils.isEmpty(data.getAd_banner())) {
                                Constant.BANNER=data.getAd_banner();
                            }
                            if (!TextUtils.isEmpty(data.getAd_kaiping())) {
                                Constant.SPLASH=data.getAd_kaiping();
                            }
                            if (!TextUtils.isEmpty(data.getAd_express())) {
                                Constant.EXPRESS=data.getAd_express();
                            }
                            if (!TextUtils.isEmpty(data.getAd_insert())) {
                                Constant.INSTER=data.getAd_insert();
                            }
                            if (!TextUtils.isEmpty(data.getServer_ip())) {
                                Constant.IPCODE=data.getServer_ip();
                            }
                            if (!TextUtils.isEmpty(data.getAd_tx_jili())) {
                                Constant.TXRVIDEO=data.getAd_tx_jili();
                            }
                            loginTypes = data.getAgent_login();
                            if (!TextUtils.isEmpty(data.getAgent_ads())){
                                ((MyApplication) MyApplication.getInstance()).setLoginType(data.getAgent_ads());
                            }
                        }
                        if (data!=null&&data.getAd_config()!=null){
                            AdCodeBeans.AdConfigBean ad_config = data.getAd_config();
                            if (ad_config.getJili()!=null){
                                AdCodeBeans.AdConfigBean.JiliBean.CsjBean csj = ad_config.getJili().getCsj();
                                AdCodeBeans.AdConfigBean.JiliBean.TxBean tx = ad_config.getJili().getTx();
                                if (csj!=null){
                                    ((MyApplication) MyApplication.getInstance()).ttNums=csj.getNum();
                                    ((MyApplication) MyApplication.getInstance()).ttSort=csj.getSort();
                                }
                                if (tx!=null){
                                    ((MyApplication) MyApplication.getInstance()).txNums=tx.getNum();
                                    ((MyApplication) MyApplication.getInstance()).txSort=tx.getSort();
                                }

                                if (csj!=null&&tx!=null){
                                    if ("1".equals(((MyApplication) MyApplication.getInstance()).ttSort)){
                                        CacheDataUtils.getInstance().setVideoType("1");
                                     }else if ("1".equals(((MyApplication) MyApplication.getInstance()).txSort)){
                                        CacheDataUtils.getInstance().setVideoType("2");
                                    }
                                }
                            }
                        }
                        initAdCode();
                    }

                    @Override
                    public void errorState(String message, String state) {
                        initAdCode();
                    }
                }));
    }

    private AdPlatformSDK.InitCallback initCallback;
    public void setInitCallback(AdPlatformSDK.InitCallback initCallback) {
        this.initCallback = initCallback;
    }
    private void initAdCode(){
      //  Log.d("ccc", "-------------激励视频: "+Constant.RVIDEO+"---ip:"+Constant.IPCODE+"---开屏:"+Constant.SPLASH+"---信息流："+Constant.EXPRESS+"----banner图"+Constant.BANNER+"----插屏："+Constant.INSTER);
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(MyApplication.getInstance());
        AdConfigInfo adConfigInfo = new AdConfigInfo();
        adConfigInfo.setAppId("5160313");
        adConfigInfo.setAppName(getResources().getString(R.string.app_name));
        adConfigInfo.setRewardVideoVertical(Constant.RVIDEO);
        adConfigInfo.setIp(Constant.IPCODE);
        adConfigInfo.setSplash(Constant.SPLASH);
        adConfigInfo.setExpress(Constant.EXPRESS);
        adConfigInfo.setBanner(Constant.BANNER);
        adConfigInfo.setInster(Constant.INSTER);
        adConfigInfo.setOpen(true);
        adPlatformSDK.setAdConfigInfo(adConfigInfo);
        adPlatformSDK.init(MyApplication.getInstance(), "48", new AdPlatformSDK.InitCallback() {
            @Override
            public void onAdInitSuccess()  {
                initVersion();
                if (initCallback != null) {
                    initCallback.onAdInitSuccess();
                }
            }

            @Override
            public void onAdInitFailure() {
                initAdCodeTwo();
                if (initCallback != null) {
                    initCallback.onAdInitFailure();
                }
            }
        });
    }

    private void initAdCodeTwo(){
      //  Log.d("ccc", "-------initAdCodeTwo------激励视频: "+Constant.RVIDEO+"---ip:"+Constant.IPCODE+"---开屏:"+Constant.SPLASH+"---信息流："+Constant.EXPRESS+"----banner图"+Constant.BANNER+"----插屏："+Constant.INSTER);
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(MyApplication.getInstance());
        AdConfigInfo adConfigInfo = new AdConfigInfo();
        adConfigInfo.setAppId("5160313");
        adConfigInfo.setAppName(getResources().getString(R.string.app_name));
        adConfigInfo.setRewardVideoVertical(Constant.RVIDEO);
        adConfigInfo.setIp(Constant.IPCODE);
        adConfigInfo.setSplash(Constant.SPLASH);
        adConfigInfo.setExpress(Constant.EXPRESS);
        adConfigInfo.setBanner(Constant.BANNER);
        adConfigInfo.setInster(Constant.INSTER);
        adConfigInfo.setOpen(true);
        adPlatformSDK.setAdConfigInfo(adConfigInfo);
        adPlatformSDK.init(MyApplication.getInstance(), "48", new AdPlatformSDK.InitCallback() {
            @Override
            public void onAdInitSuccess()  {
                initVersion();
                if (initCallback != null) {
                    initCallback.onAdInitSuccess();
                }
            }

            @Override
            public void onAdInitFailure() {
                initVersion();
                if (initCallback != null) {
                    initCallback.onAdInitFailure();
                }
            }
        });
    }

    private void  initVersion(){
        mDisposables.add(apis.upVersion(((MyApplication) MyApplication.getInstance()).getAgentId()).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultRefreshSubscriber<UpDataVersion>() {
                    @Override
                    public void onAnalysisNext(UpDataVersion data) {
                        UpgradeInfo upgradeInfo = new UpgradeInfo();
                        upgradeInfo.setDesc(data.getUpdate_content());
                        upgradeInfo.setDownUrl(data.getDownload_url());
                        upgradeInfo.setVersion(data.getVersion_name());
                        upgradeInfo.setVersionCode(data.getVersion_code());
                        upgradeInfo.setForce_update(data.getForce_update());
                        try {
                            PackageInfo info = getPackageManager().getPackageInfo(SplashActivity.this.getPackageName(), PackageManager.GET_ACTIVITIES);
                            if (upgradeInfo != null && upgradeInfo.getVersionCode() > info.versionCode) {
                                if (!TextUtils.isEmpty(data.getDownload_url())) {
                                    UpdateDialog dialog = new UpdateDialog(SplashActivity.this);
                                    dialog.setInfo(upgradeInfo);
                                    dialog.show();
                                }else {
                                    initData();
                                }
                            }else {
                                initData();
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
        if (!isFirst) {
            isFirst = true;

            if (apis == null) {
                apis = new HomeApiModule();
            }

            if (mDisposables == null) {
                mDisposables = new CompositeDisposable();
            }

            youkeLogin();

//            String agentId = ((MyApplication) MyApplication.getInstance()).getAgentId();
//            if (!TextUtils.isEmpty(loginTypes)){
//                if (loginTypes.contains(",")){
//                    String[] split = loginTypes.split(",");
//                    String types="";
//                    for (int i = 0; i < split.length; i++) {
//                        if (agentId.equals(split[i])){
//                            types="1";
//                        }
//                    }
//                    if (!TextUtils.isEmpty(types)){
//                        youkeLogin();
//                    }else {
//                        weixinLogin();
//                    }
//                }else {
//                    if (loginTypes.equals(agentId)){
//                        youkeLogin();
//                    }else {
//                        weixinLogin();
//                    }
//                }
//            }else {
//                weixinLogin();
//            }



//            if ("1".equals(Constant.ISWXLOGIN)){//直接游客登录  2微信登录
//                if (CacheDataUtils.getInstance().isLogin()){
//                    showSplash(CacheDataUtils.getInstance().getUserInfo().getId()+"");
//                }else {
//                    String agentId = ((MyApplication) MyApplication.getInstance()).getAgentId();
//                    if (TextUtils.isEmpty(agentId)) {
//                        agentId = "";
//                    }
//                    String oid = GoagalInfo.oaid;
//                    String macAddress = MacUtils.getMacAddress();
//                    String imei = DeviceUtils.getImei();
//                    String imie2 = PhoneCommonUtils.getIMEI2();
//                    if (TextUtils.isEmpty(imie2)){
//                        imie2="";
//                    }
//                    String model = Build.BRAND+"_"+Build.MODEL+"_"+Build.VERSION.RELEASE;
//                    mDisposables.add(apis.login(1, null, null, null, null, 2, null, agentId, imei,oid,macAddress,imie2,model).compose(RxUtil.rxSchedulerHelper())
//                            .subscribeWith(new ResultRefreshSubscriber<UserInfo>() {
//                                @Override
//                                public void onAnalysisNext(UserInfo data) {
//                                    CacheDataUtils.getInstance().saveUserInfo(data);
//                                    showSplash(data.getId()+"");
//                                }
//
//                                @Override
//                                public void errorState(String message, String state) {
//                                    if (CacheDataUtils.getInstance().isLogin()){
//                                        toMain();
//                                    }else {
//                                        ToastUtil.showToast("登录失败");
//                                    }
//                                }
//                            }));
//                }
//            }else {
//                if (CacheDataUtils.getInstance().isLogin()){
//                    showSplash(CacheDataUtils.getInstance().getUserInfo().getId()+"");
//                }else {
//                    Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
        }
    }
    //微信登录
    private void weixinLogin(){
                if (CacheDataUtils.getInstance().isLogin()){
                    showSplash(CacheDataUtils.getInstance().getUserInfo().getId()+"");
                }else {
                    toLogin();
               }
    }
    //游客登录
    private void youkeLogin(){
                if (CacheDataUtils.getInstance().isLogin()){
                    showSplash(CacheDataUtils.getInstance().getUserInfo().getId()+"");
                }else {
                    String agentId = ((MyApplication) MyApplication.getInstance()).getAgentId();
                    if (TextUtils.isEmpty(agentId)) {
                        agentId = "";
                    }
                    String oid = GoagalInfo.oaid;
                    String macAddress = MacUtils.getMacAddress();
                    String imei = DeviceUtils.getImei();
                    String imie2 = PhoneCommonUtils.getIMEI2();
                    if (TextUtils.isEmpty(imie2)){
                        imie2="";
                    }
                    String model = Build.BRAND+"_"+Build.MODEL+"_"+Build.VERSION.RELEASE;
                    mDisposables.add(apis.login(1, null, null, null, null, 2, null, agentId, imei,oid,macAddress,imie2,model).compose(RxUtil.rxSchedulerHelper())
                            .subscribeWith(new ResultRefreshSubscriber<UserInfo>() {
                                @Override
                                public void onAnalysisNext(UserInfo data) {
                                    CacheDataUtils.getInstance().saveUserInfo(data);
                                    showSplash(data.getId()+"");
                                }

                                @Override
                                public void errorState(String message, String state) {
                                    if (CacheDataUtils.getInstance().isLogin()){
                                        toMain();
                                    }else {
                                        ToastUtil.showToast("登录失败");
                                    }
                                }
                            }));
                }
    }

    private  YonghuxieyiDialog dialog;
    private void showAgreementDialog(){
        MobclickAgent.onEvent(SplashActivity.this, "xiejujue");//参数二为当前统计的事件ID
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
                MobclickAgent.onEvent(SplashActivity.this, "xieyi");//参数二为当前统计的事件ID
                      CacheDataUtils.getInstance().setSol("1");
                     CacheDataUtils.getInstance().setAgreement();

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
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
        AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(SplashActivity.this);
        if (!TextUtils.isEmpty(id)){
            adPlatformSDK.setUserId(id);
        }
        adPlatformSDK.showSplashVerticalAd(SplashActivity.this, "ad_kaiping", new AdCallback() {
            @Override
            public void onDismissed() {
                toMain();
            }

            @Override
            public void onNoAd(AdError adError) {
                toMain();
            }

            @Override
            public void onComplete() {

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
