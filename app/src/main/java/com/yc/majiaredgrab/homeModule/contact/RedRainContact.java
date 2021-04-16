package com.yc.majiaredgrab.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.majiaredgrab.homeModule.module.bean.RedRainBeans;

public interface RedRainContact {
    interface View extends BaseView {

        void getRedRainMoneySuccess(RedRainBeans data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
