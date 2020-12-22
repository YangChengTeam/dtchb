package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoBeans;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoMoneyBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;

import java.util.List;

public interface GrabRedEvenlopesContact {
    interface View extends BaseView {

        void updtreasureSuccess(UpQuanNumsBeans data);

        void getlookVideoSuccess(LookVideoBeans data);

        void getlookVideoRedMoneySuccess(LookVideoMoneyBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
