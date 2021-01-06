package com.yc.redevenlopes.homeModule.present;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.lq.lianjibusiness.base_libary.utils.DeviceUtils;
import com.yc.redevenlopes.application.MyApplication;
import com.yc.redevenlopes.homeModule.contact.LoginContract;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.MacUtils;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public LoginPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void login(int app_type, String wx_openid, String qq_openid,
                      String age, String nickname, int sex, String face,String agent_id) {
        if (app_type == 1) {
            showWaiteDialog();
        }
        String oid="";
        if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getUserInfo().getOaid())){
            oid=CacheDataUtils.getInstance().getUserInfo().getOaid();
        }
        String mac="";
        try {
            mac = MacUtils.getMacAddress();
        }catch (Exception e){

        }
        if (TextUtils.isEmpty(mac)){
            try {
                WifiManager manager = (WifiManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = manager.getConnectionInfo();
                mac = info.getMacAddress();
            }catch (Exception e){

            }
        }
        addSubscribe(apiModule.login(app_type, wx_openid, qq_openid, age, nickname, sex, face,agent_id, CacheDataUtils.getInstance().getUserInfo().getImei(),oid,mac,CacheDataUtils.getInstance().getUserInfo().getImei2(),CacheDataUtils.getInstance().getUserInfo().getPhone_brand())
                .compose(RxUtil.rxSchedulerHelper()).subscribeWith(new ResultSubscriber<UserInfo>(this) {
                    @Override
                    public void onAnalysisNext(UserInfo data) {
                        CacheDataUtils.getInstance().saveUserInfo(data);
                        mView.showLoginSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
//                        ToastUtil.showToast(message);
                    }
                }));
    }
}
