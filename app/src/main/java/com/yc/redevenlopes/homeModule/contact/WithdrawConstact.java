package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.CashBeans;
import com.yc.redevenlopes.homeModule.module.bean.TithDrawBeans;
import com.yc.redevenlopes.homeModule.module.bean.WeixinCashBeans;

public interface WithdrawConstact {
    interface View extends BaseView {

        void getWithDrawDataSuccess(TithDrawBeans data);

        void weixinBindCashSuccess(CashBeans data);

        void cashMoneySuccess(WeixinCashBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
