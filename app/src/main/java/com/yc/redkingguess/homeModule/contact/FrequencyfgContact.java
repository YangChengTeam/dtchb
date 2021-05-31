package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.FrequencyFgBeans;

import java.util.List;

public interface FrequencyfgContact {
    interface View extends BaseView {

        void getSnatchNumsSuccess(List<FrequencyFgBeans> data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
