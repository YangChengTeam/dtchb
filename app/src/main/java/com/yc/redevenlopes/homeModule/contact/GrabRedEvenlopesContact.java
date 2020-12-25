package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;
import com.yc.redevenlopes.homeModule.module.bean.GoToSignBeans;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoBeans;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoMoneyBeans;
import com.yc.redevenlopes.homeModule.module.bean.SeekBeans;
import com.yc.redevenlopes.homeModule.module.bean.SeekRedMoneyBean;
import com.yc.redevenlopes.homeModule.module.bean.SignInfoBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpFindRedBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;

import java.util.List;

public interface GrabRedEvenlopesContact {
    interface View extends BaseView {

        void updtreasureSuccess(UpQuanNumsBeans data);

        void getlookVideoSuccess(LookVideoBeans data);

        void getlookVideoRedMoneySuccess(LookVideoMoneyBeans data);

        void getSeekRedSuccess(SeekBeans data);

        void getSeekGetRedMoneySuccess(SeekRedMoneyBean data);

        void getUpFindRedSuccess(UpFindRedBeans data);

        void getSignInfoSuccess(SignInfoBeans data);

        void signSuccess(GoToSignBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
