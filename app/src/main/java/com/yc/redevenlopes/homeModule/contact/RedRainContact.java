package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;
import com.yc.redevenlopes.homeModule.module.bean.RedRainBeans;

import java.util.List;

public interface RedRainContact {
    interface View extends BaseView {

        void getRedRainMoneySuccess(RedRainBeans data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
