package com.yc.redguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redguess.homeModule.module.bean.GuessHistoryBeans;

public interface GuessingResultContact {
    interface View extends BaseView {

        void submitGuessNoSuccess(GuessHistoryBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
