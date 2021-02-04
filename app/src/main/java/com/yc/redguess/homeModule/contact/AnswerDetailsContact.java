package com.yc.redguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redguess.homeModule.module.bean.AnsPostRecordBeans;
import com.yc.redguess.homeModule.module.bean.AnswerQuestionListBeans;

import java.util.List;

public interface AnswerDetailsContact {
    interface View extends BaseView {

        void getDetailsQuestionListSuccess(List<AnswerQuestionListBeans> data);

        void postAnserRecordSuccess(AnsPostRecordBeans data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
