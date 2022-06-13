package com.yc.rrdsprj.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.rrdsprj.beans.module.beans.OtherBeans;
import com.yc.rrdsprj.beans.module.beans.TelBeans;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface MineContract {
    interface View extends BaseView {
        void getTelSuccess(TelBeans data);

        void getOtherInfoSuccess(OtherBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
