package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.module.bean.LoadGameLoadApkBeans;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface LoadGameContract {
    interface View extends BaseView {


        void getCashdownApkSuccess(LoadGameLoadApkBeans data);

    }

    interface Presenter extends BasePresenter<View> {
    }
}
