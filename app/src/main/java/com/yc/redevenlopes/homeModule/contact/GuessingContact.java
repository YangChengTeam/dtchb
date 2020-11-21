package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.GuessBeans;

public interface GuessingContact {
    interface View extends BaseView {

        void getGuessDataSuccess(GuessBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
