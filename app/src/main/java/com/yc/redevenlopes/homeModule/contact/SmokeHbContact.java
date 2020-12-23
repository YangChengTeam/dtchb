package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;
import com.yc.redevenlopes.homeModule.module.bean.SmokeBeans;
import com.yc.redevenlopes.homeModule.module.bean.SmokeHbBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;

import java.util.List;

public interface SmokeHbContact {
    interface View extends BaseView {

        void getLuckyRedSuccess(SmokeHbBeans data);

        void updtreasureSuccess(UpQuanNumsBeans data);

        void getLuckyMoneySuccess(SmokeBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
