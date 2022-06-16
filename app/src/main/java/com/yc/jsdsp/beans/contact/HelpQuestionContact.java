package com.yc.jsdsp.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.jsdsp.beans.module.beans.HelpQuestionBeans;

import java.util.List;


public interface HelpQuestionContact {
    interface View extends BaseView {

        void getaboutplayListSuccess(List<HelpQuestionBeans> data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
