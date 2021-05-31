package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.GoToSignBeans;
import com.yc.redkingguess.homeModule.module.bean.LookVideoBeans;
import com.yc.redkingguess.homeModule.module.bean.LookVideoMoneyBeans;
import com.yc.redkingguess.homeModule.module.bean.SeekBeans;
import com.yc.redkingguess.homeModule.module.bean.SeekRedMoneyBean;
import com.yc.redkingguess.homeModule.module.bean.SignInfoBeans;
import com.yc.redkingguess.homeModule.module.bean.TaskUnLockResBeans;
import com.yc.redkingguess.homeModule.module.bean.UpFindRedBeans;
import com.yc.redkingguess.homeModule.module.bean.UpQuanNumsBeans;

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

        void getUnlockTaskSuccess(TaskUnLockResBeans data);

        void getUnlockTaskReeorState();

    }

    interface Presenter extends BasePresenter<View> {

    }
}
