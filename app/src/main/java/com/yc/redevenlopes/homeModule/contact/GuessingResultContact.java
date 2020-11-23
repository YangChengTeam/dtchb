package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.GuessHistoryBeans;

public interface GuessingResultContact {
    interface View extends BaseView {

        void submitGuessNoSuccess(GuessHistoryBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
