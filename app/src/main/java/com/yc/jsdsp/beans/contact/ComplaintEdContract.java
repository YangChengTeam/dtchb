package com.yc.jsdsp.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface ComplaintEdContract {
    interface View extends BaseView {


        void comPlaintSuccess();

    }

    interface Presenter extends BasePresenter<View> {
    }
}
