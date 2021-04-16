package com.yc.majiaredgrab.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.majiaredgrab.homeModule.module.bean.GoToSignBeans;
import com.yc.majiaredgrab.homeModule.module.bean.LookVideoBeans;
import com.yc.majiaredgrab.homeModule.module.bean.LookVideoMoneyBeans;
import com.yc.majiaredgrab.homeModule.module.bean.SeekBeans;
import com.yc.majiaredgrab.homeModule.module.bean.SeekRedMoneyBean;
import com.yc.majiaredgrab.homeModule.module.bean.SignInfoBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UpFindRedBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UpQuanNumsBeans;

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
