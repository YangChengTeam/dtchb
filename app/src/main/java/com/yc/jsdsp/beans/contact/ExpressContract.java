package com.yc.jsdsp.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.jsdsp.beans.module.beans.ExpressBeans;

import java.util.List;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface ExpressContract {
    interface View extends BaseView {


        void getExpressDataSuccess(List<ExpressBeans> data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
