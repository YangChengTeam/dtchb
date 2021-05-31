package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.GuessBeans;
import com.yc.redkingguess.homeModule.module.bean.PostGuessNoBeans;
import com.yc.redkingguess.homeModule.module.bean.UpQuanNumsBeans;

public interface GuessingContact {
    interface View extends BaseView {

        void getGuessDataSuccess(GuessBeans data);

        void submitGuessNoSuccess(PostGuessNoBeans data);

        void getGuessDataError();

        void updtreasureSuccess(UpQuanNumsBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
