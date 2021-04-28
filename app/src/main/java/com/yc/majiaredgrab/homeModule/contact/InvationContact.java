package com.yc.majiaredgrab.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.majiaredgrab.homeModule.module.bean.InvationsBeans;

public interface InvationContact {
    interface View extends BaseView {

        void getInvationDataSuccess(InvationsBeans data);

        void getInputCodeSuccess();

    }

    interface Presenter extends BasePresenter<View> {

    }
}
