package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.SnatchDetailsBeans;
import com.yc.redkingguess.homeModule.module.bean.SnatchPostBeans;

public interface SnatchTreasureContact {
    interface View extends BaseView {

        void getSnatchinfoDetailsSuccess(SnatchDetailsBeans data);

        void getSnatchPostSuccess(SnatchPostBeans data);

        void getSnatchinfoDetailsError();

    }

    interface Presenter extends BasePresenter<View> {

    }
}
