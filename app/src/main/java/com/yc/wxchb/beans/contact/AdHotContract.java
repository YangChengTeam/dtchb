package com.yc.wxchb.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.wxchb.beans.module.beans.HotIndexBeans;
import com.yc.wxchb.beans.module.beans.HotTaskBeans;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface AdHotContract {
    interface View extends BaseView {


        void getHotInfoIndexSuccess(HotIndexBeans data);

        void gethotTaskSuccess(HotTaskBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
