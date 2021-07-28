package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.bean.HomeLineRedBeans;

public interface MainContact {
    interface View extends BaseView {


        void getLineRedSuccess(HomeLineRedBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
