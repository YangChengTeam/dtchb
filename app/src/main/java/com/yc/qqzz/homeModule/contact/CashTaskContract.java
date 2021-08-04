package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.bean.CashTaskBeans;
import com.yc.qqzz.homeModule.bean.EmptyBeans;
import com.yc.qqzz.homeModule.module.bean.DayCashTashBeans;

import java.util.List;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface CashTaskContract {
    interface View extends BaseView {


        void getCashdownSuccess(DayCashTashBeans data);

        void getDayhbSuccess(CashTaskBeans data);

        void getOutcashdownSuccess(EmptyBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
