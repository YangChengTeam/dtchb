package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.LoginContract;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.utils.CacheDataUtils;

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
        addSubscribe(apiModule.login(app_type, wx_openid, qq_openid, age, nickname, sex, face,agent_id)
                .compose(RxUtil.rxSchedulerHelper()).subscribeWith(new ResultSubscriber<UserInfo>(this) {
                    @Override
                    public void onAnalysisNext(UserInfo data) {
                        CacheDataUtils.getInstance().saveUserInfo(data);
                        mView.showLoginSuccess();
                    }

                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
//                        ToastUtil.showToast(message);
                    }
                }));
    }
}
