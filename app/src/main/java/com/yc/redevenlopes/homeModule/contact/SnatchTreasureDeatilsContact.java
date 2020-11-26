package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.SnatchTreasureDetailssBeans;

public interface SnatchTreasureDeatilsContact {
    interface View extends BaseView {

        void getSnatchDetailssSuccess(SnatchTreasureDetailssBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
