package com.yc.majiaredgrab.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.majiaredgrab.homeModule.module.bean.AutoGetLuckyBeans;
import com.yc.majiaredgrab.homeModule.module.bean.SmokeBeans;
import com.yc.majiaredgrab.homeModule.module.bean.SmokeHbBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UpQuanNumsBeans;

public interface SmokeHbContact {
    interface View extends BaseView {

        void getLuckyRedSuccess(SmokeHbBeans data);

        void updtreasureSuccess(UpQuanNumsBeans data);

        void getLuckyMoneySuccess(SmokeBeans data);

        void getLuckyAutoRedSuccess(AutoGetLuckyBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
