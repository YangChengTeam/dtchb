package com.yc.majiaredgrab.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.majiaredgrab.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.majiaredgrab.homeModule.module.bean.TurnTablePrizeInfoBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UpQuanNumsBeans;

public interface TurnTableContact {
    interface View extends BaseView {

        void getPrizeInfoDataSuccess(TurnTablePrizeInfoBeans data);

        void getGoPrizeSuccess(TurnGoPrizeBeans data);

        void updtreasureSuccess(UpQuanNumsBeans data);

       // void getTurnSuccess(TurnGetPrizeBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
