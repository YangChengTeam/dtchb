package com.yc.jsdsp.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.jsdsp.beans.module.beans.EmptyBeans;
import com.yc.jsdsp.beans.module.beans.MoneyTaskBeans;

import java.util.List;


/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface MoneyTaskContract {
    interface View extends BaseView {


        void getMoneyTaskSuccess(List<MoneyTaskBeans> data);

        void getMoneyTaskTxSuccess(EmptyBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
