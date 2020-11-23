package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.SnatchDetailsBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchPostBeans;

public interface SnatchTreasureContact {
    interface View extends BaseView {

        void getSnatchinfoDetailsSuccess(SnatchDetailsBeans data);

        void getSnatchPostSuccess(SnatchPostBeans data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
