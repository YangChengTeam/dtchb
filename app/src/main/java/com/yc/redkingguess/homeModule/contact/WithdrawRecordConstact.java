package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.WithDrawRecordBeans;

import java.util.List;

public interface WithdrawRecordConstact {
    interface View extends BaseView {

        void getWithDrawRecordSuccess(List<WithDrawRecordBeans> data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
