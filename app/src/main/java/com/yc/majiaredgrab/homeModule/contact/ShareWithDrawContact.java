package com.yc.majiaredgrab.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.majiaredgrab.homeModule.module.bean.InvationsShareBeans;
import com.yc.majiaredgrab.homeModule.module.bean.ShareWithExChangeBeans;


public interface ShareWithDrawContact {
    interface View extends BaseView {
        void getExchangeInfoDataSuccess(InvationsShareBeans data);

        void exchangeSuccess(ShareWithExChangeBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}