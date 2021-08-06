package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.bean.AnswerFanBeiBeans;
import com.yc.qqzz.homeModule.bean.AnswerFgBeans;
import com.yc.qqzz.homeModule.bean.AnswerFgQuestionBeans;
import com.yc.qqzz.homeModule.bean.GetHomeLineRedBeans;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface GrabRedFgContract {
    interface View extends BaseView {


        void getAnswerListSuccess(AnswerFgBeans data);

        void questionAddSuccess(AnswerFgQuestionBeans data);

        void gethbonlineSuccess(GetHomeLineRedBeans data);

        void getDoubleVideoSuccess(AnswerFanBeiBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
