package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.AnsPostRecordBeans;
import com.yc.redkingguess.homeModule.module.bean.AnswerBeans;
import com.yc.redkingguess.homeModule.module.bean.UpQuanNumsBeans;

import java.util.List;

public interface AnswerContact {
    interface View extends BaseView {

        void getAnswerQuestionListSuccess(List<AnswerBeans> data);

        void updtreasureSuccess(UpQuanNumsBeans data);

        void postAnserRecordSuccess(AnsPostRecordBeans data);

        void postAnserRecordError();

    }

    interface Presenter extends BasePresenter<View> {

    }
}
