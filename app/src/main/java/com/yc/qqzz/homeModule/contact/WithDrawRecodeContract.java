package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.module.bean.CashRecordBeans;

import java.util.List;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface WithDrawRecodeContract {
    interface View extends BaseView {


        void getCashrecordSuccess(List<CashRecordBeans> data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
