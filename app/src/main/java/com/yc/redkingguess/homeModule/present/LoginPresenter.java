package com.yc.redkingguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redkingguess.homeModule.contact.LoginContract;
import com.yc.redkingguess.homeModule.module.HomeApiModule;

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

//    public void login(int app_type, String wx_openid, String qq_openid,
//                      String age, String nickname, int sex, String face,String agent_id) {
//
//            String agentId = ((MyApplication) MyApplication.getInstance()).getAgentId();
//            if (TextUtils.isEmpty(agentId)) {
//                agentId = "1";
//            }
//            String oid = GoagalInfo.oaid;
//            String macAddress = MacUtils.getMacAddress();
//            String imei = DeviceUtils.getImei();
//            String imie2 = PhoneCommonUtils.getIMEI2();
//            if (TextUtils.isEmpty(imie2)){
//                imie2="";
//            }
//            String model = Build.BRAND+"_"+Build.MODEL+"_"+Build.VERSION.RELEASE;
//
//        addSubscribe(apiModule.login(app_type, wx_openid, qq_openid, age, nickname, sex, face,agent_id, imei,oid,macAddress,imie2,model)
//                .compose(RxUtil.rxSchedulerHelper()).subscribeWith(new ResultSubscriber<UserInfo>(this) {
//                    @Override
//                    public void onAnalysisNext(UserInfo data) {
//                        CacheDataUtils.getInstance().saveUserInfo(data);
//                        mView.showLoginSuccess(data);
//                    }
//                }));
//    }
}
