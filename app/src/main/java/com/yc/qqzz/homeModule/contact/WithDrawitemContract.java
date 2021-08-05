package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.bean.WithDrawHomeBeans;
import com.yc.qqzz.homeModule.bean.WxCashBeans;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface WithDrawitemContract {
    interface View extends BaseView {
        void getPayinfoSuccess(WithDrawHomeBeans data);

        void getWeixinCashSuccess(WxCashBeans data);

    }

    interface Presenter extends BasePresenter<View> {
    }
}
