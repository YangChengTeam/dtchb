package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.RedDetailsBeans;

public interface RodRedEvenlopesContact {
    interface View extends BaseView {

        void getRedEvenlopesDetailsSuccess(RedDetailsBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
