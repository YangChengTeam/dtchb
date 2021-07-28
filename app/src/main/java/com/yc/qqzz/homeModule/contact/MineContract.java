package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.module.bean.OtherBeanszq;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface MineContract {
    interface View extends BaseView {
        void getOtherInfoSuccess(OtherBeanszq data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
