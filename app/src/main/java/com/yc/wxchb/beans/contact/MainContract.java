package com.yc.wxchb.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.wxchb.beans.module.beans.OtherBeans;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface MainContract {
    interface View extends BaseView {


        void getOtherInfoSuccess(OtherBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
