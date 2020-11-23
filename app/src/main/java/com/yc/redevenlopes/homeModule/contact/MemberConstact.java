package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfoWrapper;

public interface MemberConstact {
    interface View extends BaseView {

        void showVipTaskInfo(VipTaskInfoWrapper data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
