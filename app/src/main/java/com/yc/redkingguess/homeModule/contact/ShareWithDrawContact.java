package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.CashBeans;
import com.yc.redkingguess.homeModule.module.bean.InvationsShareBeans;
import com.yc.redkingguess.homeModule.module.bean.ShareWithExChangeBeans;


public interface ShareWithDrawContact {
    interface View extends BaseView {
        void getExchangeInfoDataSuccess(InvationsShareBeans data);

        void exchangeSuccess(ShareWithExChangeBeans data);

        void weixinBindCashSuccess(CashBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
