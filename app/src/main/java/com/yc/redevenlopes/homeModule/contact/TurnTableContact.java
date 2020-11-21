package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnTablePrizeInfoBeans;

public interface TurnTableContact {
    interface View extends BaseView {

        void getPrizeInfoDataSuccess(TurnTablePrizeInfoBeans data);

        void getGoPrizeSuccess(TurnGoPrizeBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
