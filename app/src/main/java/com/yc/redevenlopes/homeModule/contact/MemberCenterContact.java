package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;

public interface MemberCenterContact {
    interface View extends BaseView {

        void getOtherInfoSuccess(OtherBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
