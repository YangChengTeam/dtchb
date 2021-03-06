package com.yc.wxchb.beans.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.App.GoagalInfo;
import com.lq.lianjibusiness.base_libary.http.ResultRefreshSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.utils.DeviceUtils;
import com.lq.lianjibusiness.base_libary.utils.PhoneCommonUtils;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.contact.LoginContract;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.beans.present.LoginPresenter;
import com.yc.wxchb.service.event.Event;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.MacUtils;
import com.yc.wxchb.utils.SoundPoolUtils;

import com.yc.wxchb.R;
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
    @BindView(R.id.ll_wx_login)
    LinearLayout llWxLogin;
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
        return R.layout.activity_loginzq;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        apis = new HomeApiModule();
        mDisposables = new CompositeDisposable();
        tvTouristLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    @OnClick({R.id.tv_user_policy, R.id.ll_wx_login, R.id.tv_tourist_login})
    public void onClick(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        switch (view.getId()) {
            case R.id.tv_user_policy:
               // WebViewActivity.startWebViewJump(this,"http://m.k1u.com/gdgw/dtchb.html","????????????");
                break;
            case R.id.ll_wx_login:
                appType = 2;
                wxLogin();
                break;

            case R.id.tv_tourist_login:
                appType = 1;
                login(null, null, null, null, 2, null);
                break;
        }
    }


    private void login(String wx_openid, String qq_openid,
                       String age, String nickname, int sex, String face) {
        logins(appType, wx_openid, qq_openid, age, nickname, sex, face, ((MyApplication) MyApplication.getInstance()).getAgentId(),"");
    }


    public void showLoginSuccess() {
         String faces="";
         if (CacheDataUtils.getInstance().isLogin()){
             faces= CacheDataUtils.getInstance().getUserInfo().getFace();
         }
        if (!TextUtils.isEmpty(faces)){
            EventBus.getDefault().post(new Event.LoginEvent(faces));
        }
        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
//        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // QQ??????????????????????????????
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    private void wxLogin() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN,null);
        UMShareAPI.get(this).release();
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
                logins(appType, wx_openid, "", "", name, 2, profile_image_url, ((MyApplication) MyApplication.getInstance()).getAgentId(),unionid);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            closeWaiteDialog();
            Log.d("ccc", "----onError: "+throwable.getMessage()+"--code:"+throwable.getLocalizedMessage());
            ToastUtil.showToast("????????????"+"----"+throwable.getMessage()+"--code:"+throwable.getLocalizedMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            closeWaiteDialog();
            ToastUtil.showToast("????????????");
        }
    }

    public void logins(int app_type, String wx_openid, String qq_openid,
                       String age, String nickname, int sex, String face,String agent_id,String unionid){
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

        boolean b = CommonUtils.hasSimCard(this);
        String isIc="";
        if (b){
            isIc="1";
        }else {
            isIc="0";
        }
        Log.d("ccc", "---------logins: "+isIc);
        mDisposables.add(apis.login(app_type, wx_openid, qq_openid, age, nickname, sex, face,agent_id, imei,oid,macAddress,imie2,model,unionid,isIc)
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
