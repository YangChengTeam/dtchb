package com.yc.redevenlopes.homeModule.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.App.GoagalInfo;
import com.lq.lianjibusiness.base_libary.http.ResultRefreshSubscriber;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.SimpleActivity;
import com.lq.lianjibusiness.base_libary.utils.DeviceUtils;
import com.lq.lianjibusiness.base_libary.utils.PhoneCommonUtils;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.application.MyApplication;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.constants.Constant;
import com.yc.redevenlopes.dialog.UpdateDialog;
import com.yc.redevenlopes.homeModule.contact.LoginContract;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.AdCodeBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpgradeInfo;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.present.LoginPresenter;
import com.yc.redevenlopes.listener.ThirdLoginListener;
import com.yc.redevenlopes.service.event.Event;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.MacUtils;
import com.yc.redevenlopes.utils.SoundPoolUtils;
import com.yc.redevenlopes.utils.UpDataVersion;
import com.yc.redevenlopes.utils.UserLoginManager;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by suns  on 2020/11/19 08:44.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.tv_tourist_login)
    TextView tvTouristLogin;
    @BindView(R.id.ll_qq_login)
    LinearLayout llQqLogin;
    @BindView(R.id.ll_wx_login)
    LinearLayout llWxLogin;
    private UserLoginManager userLoginManager;
    private int appType = 1;
    public CompositeDisposable mDisposables;
    public HomeApiModule apis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initEventAndData() {
        apis = new HomeApiModule();
        mDisposables = new CompositeDisposable();
        tvTouristLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        userLoginManager = UserLoginManager.getInstance();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    @OnClick({R.id.tv_user_policy, R.id.ll_wx_login, R.id.ll_qq_login, R.id.tv_tourist_login})
    public void onClick(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        switch (view.getId()) {
            case R.id.tv_user_policy:
                WebViewActivity.startWebViewJump(this,"http://m.k1u.com/hongbao/yinsi.html","隐私协议");
                break;
            case R.id.ll_wx_login:
                appType = 2;
                wxLogin();
                break;
            case R.id.ll_qq_login:

                break;
            case R.id.tv_tourist_login:
                appType = 1;
                login(null, null, null, null, 2, null);
                break;
        }
    }


    private void login(String wx_openid, String qq_openid,
                       String age, String nickname, int sex, String face) {
        logins(appType, wx_openid, qq_openid, age, nickname, sex, face, ((MyApplication) MyApplication.getInstance()).getAgentId());
       // mPresenter.login(appType, wx_openid, qq_openid, age, nickname, sex, face, ((MyApplication) MyApplication.getInstance()).getAgentId());
    }


    public void showLoginSuccess() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
//        String faces="";
//        if (!TextUtils.isEmpty(data.getFace())){
//            faces=data.getFace();
//        }
//        EventBus.getDefault().post(new Event.LoginEvent(faces));
//        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // QQ授权回调需要配置这里
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    private void wxLogin() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new MyAuthLoginListener());
    }
    public class MyAuthLoginListener implements UMAuthListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            showWaiteDialog();
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            closeWaiteDialog();
            String unionid = map.get("unionid");
            String wx_openid = map.get("openid");
            String  name = map.get("name");
            String  profile_image_url = map.get("profile_image_url");
            if (!TextUtils.isEmpty(wx_openid)) {
                logins(appType, wx_openid, "", "", name, 2, profile_image_url, ((MyApplication) MyApplication.getInstance()).getAgentId());
               // mPresenter.login(appType, wx_openid, "", "", name, 2, profile_image_url, ((MyApplication) MyApplication.getInstance()).getAgentId());
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            closeWaiteDialog();
            ToastUtil.showToast("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            closeWaiteDialog();
            ToastUtil.showToast("授权取消");
        }
    }

    public void logins(int app_type, String wx_openid, String qq_openid,
                       String age, String nickname, int sex, String face,String agent_id){
        String agentId = ((MyApplication) MyApplication.getInstance()).getAgentId();
        if (TextUtils.isEmpty(agentId)) {
            agentId = "1";
        }
        String oid = GoagalInfo.oaid;
        String macAddress = MacUtils.getMacAddress();
        String imei = DeviceUtils.getImei();
        String imie2 = PhoneCommonUtils.getIMEI2();
        if (TextUtils.isEmpty(imie2)){
            imie2="";
        }
        String model = Build.BRAND+"_"+Build.MODEL+"_"+Build.VERSION.RELEASE;

        mDisposables.add(apis.login(app_type, wx_openid, qq_openid, age, nickname, sex, face,agent_id, imei,oid,macAddress,imie2,model)
                .compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultRefreshSubscriber<UserInfo>() {
                    @Override
                    public void onAnalysisNext(UserInfo data) {
                        CacheDataUtils.getInstance().saveUserInfo(data);
                        showLoginSuccess();
                    }

                    @Override
                    public void errorState(String message, String state) {

                    }
                }));
    }

}
