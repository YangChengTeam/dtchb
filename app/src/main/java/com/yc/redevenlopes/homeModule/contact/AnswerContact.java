package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;

import java.util.List;

public interface AnswerContact {
    interface View extends BaseView {

        void getAnswerQuestionListSuccess(List<AnswerBeans> data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
