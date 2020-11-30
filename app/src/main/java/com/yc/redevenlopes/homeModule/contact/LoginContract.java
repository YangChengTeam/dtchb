package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface LoginContract {
    interface View extends BaseView {
        void showLoginSuccess(UserInfo data);

    }

    interface Presenter extends BasePresenter<View> {
    }
}
