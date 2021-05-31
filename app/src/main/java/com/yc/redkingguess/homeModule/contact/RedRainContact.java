package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.RedRainBeans;

public interface RedRainContact {
    interface View extends BaseView {

        void getRedRainMoneySuccess(RedRainBeans data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
