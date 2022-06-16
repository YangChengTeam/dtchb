package com.yc.jsdsp.beans.contact;


import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.jsdsp.beans.module.beans.AnswerFanBeiBeans;
import com.yc.jsdsp.beans.module.beans.AnswerFgBeans;
import com.yc.jsdsp.beans.module.beans.AnswerFgQuestionBeans;

public interface AnswerFgContact {
    interface View extends BaseView {
        void getAnswerListSuccess(AnswerFgBeans data);

        void questionAddSuccess(AnswerFgQuestionBeans data);


        void getAnswerRed(AnswerFanBeiBeans data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
