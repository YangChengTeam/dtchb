package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.SnatchTreasureDetailssBeans;

public interface SnatchTreasureDeatilsContact {
    interface View extends BaseView {

        void getSnatchDetailssSuccess(SnatchTreasureDetailssBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
