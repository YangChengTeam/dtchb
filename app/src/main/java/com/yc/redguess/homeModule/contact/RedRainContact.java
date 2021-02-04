package com.yc.redguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redguess.homeModule.module.bean.RedRainBeans;

public interface RedRainContact {
    interface View extends BaseView {

        void getRedRainMoneySuccess(RedRainBeans data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
