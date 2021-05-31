package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.EmptyBeans;
import com.yc.redkingguess.homeModule.module.bean.RedDetailsBeans;

public interface RodRedEvenlopesContact {
    interface View extends BaseView {

        void getRedEvenlopesDetailsSuccess(RedDetailsBeans data);

        void getRegUserLogSuccess(EmptyBeans data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}