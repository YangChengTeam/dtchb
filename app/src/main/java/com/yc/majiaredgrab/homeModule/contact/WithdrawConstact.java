package com.yc.majiaredgrab.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.majiaredgrab.homeModule.module.bean.CashBeans;
import com.yc.majiaredgrab.homeModule.module.bean.TithDrawBeans;
import com.yc.majiaredgrab.homeModule.module.bean.WeixinCashBeans;

public interface WithdrawConstact {
    interface View extends BaseView {

        void getWithDrawDataSuccess(TithDrawBeans data);

        void weixinBindCashSuccess(CashBeans data);

        void cashMoneySuccess(WeixinCashBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
