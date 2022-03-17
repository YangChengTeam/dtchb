package com.yc.wxchb.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.wxchb.beans.module.beans.FalseUserBeans;
import com.yc.wxchb.beans.module.beans.LotterBeans;
import com.yc.wxchb.beans.module.beans.LotterInfoBeans;
import com.yc.wxchb.beans.module.beans.PayInfoBeans;
import com.yc.wxchb.beans.module.beans.RedTaskBeans;
import com.yc.wxchb.beans.module.beans.WithDrawStatusBeans;

import java.util.List;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface WithDrawContract {
    interface View extends BaseView {

        void getPayInfoSuccess(PayInfoBeans data);

        void getLimitedDataSuccess(List<LotterInfoBeans> data);

        void getlotterSuccess(LotterBeans data);

        void weixinCashSuccess(WithDrawStatusBeans data);

        void getFalseuserSuccess(List<FalseUserBeans> data);

        void getRedTaskDataSuccess(RedTaskBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
