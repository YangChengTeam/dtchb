package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.module.bean.TurnGoPrizeBeanszq;
import com.yc.qqzz.homeModule.module.bean.TurnTablePrizeInfoBeanszq;

public interface TurnTableContact {
    interface View extends BaseView {
        void getPrizeInfoDataSuccess(TurnTablePrizeInfoBeanszq data);

        void getGoPrizeSuccess(TurnGoPrizeBeanszq data);


    }

    interface Presenter extends BasePresenter<View> {

    }
}
