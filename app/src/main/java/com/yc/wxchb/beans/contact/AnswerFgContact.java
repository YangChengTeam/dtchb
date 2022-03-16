package com.yc.wxchb.beans.contact;


import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.wxchb.beans.module.beans.AnswerFanBeiBeans;
import com.yc.wxchb.beans.module.beans.AnswerFgBeans;
import com.yc.wxchb.beans.module.beans.AnswerFgQuestionBeans;
import com.yc.wxchb.beans.module.beans.HotNumsInfoBeans;
import com.yc.wxchb.beans.module.beans.QuestionRightBeans;

public interface AnswerFgContact {
    interface View extends BaseView {
        void getAnswerListSuccess(AnswerFgBeans data);

        void questionAddSuccess(AnswerFgQuestionBeans data);

        void getHotInfoSuccess(HotNumsInfoBeans data);

        void getAnswerRed(AnswerFanBeiBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
