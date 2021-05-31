package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.CashBeans;
import com.yc.redkingguess.homeModule.module.bean.SignWiths;
import com.yc.redkingguess.homeModule.module.bean.TithDrawBeans;
import com.yc.redkingguess.homeModule.module.bean.WeixinCashBeans;

public interface WithdrawConstact {
    interface View extends BaseView {

        void getWithDrawDataSuccess(TithDrawBeans data);

        void weixinBindCashSuccess(CashBeans data);

        void cashMoneySuccess(WeixinCashBeans data);

        void getSignMoenysSuccess(SignWiths signWiths);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
