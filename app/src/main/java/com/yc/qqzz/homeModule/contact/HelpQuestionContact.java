package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.module.bean.HelpQuestionBeans;

import java.util.List;

public interface HelpQuestionContact {
    interface View extends BaseView {

        void getaboutplayListSuccess(List<HelpQuestionBeans> data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
