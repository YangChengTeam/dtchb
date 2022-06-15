package com.yc.wxchb.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.wxchb.beans.module.beans.EmptyBeans;
import com.yc.wxchb.beans.module.beans.RedWallInfoBeans;
import com.yc.wxchb.beans.module.beans.WallMoneyBeansTwo;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface RedWallContract {
    interface View extends BaseView {


        void getWallInfoSuccess(RedWallInfoBeans data);

        void wallCashSuccess(EmptyBeans data);

        void getWallMoneysSuccess(WallMoneyBeansTwo data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
