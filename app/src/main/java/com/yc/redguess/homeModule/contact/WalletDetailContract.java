package com.yc.redguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redguess.homeModule.module.bean.WalletDetailBeans;

import java.util.List;

/**
 * Created by suns  on 2020/11/18 09:18.
 */
public interface WalletDetailContract {
    interface View extends BaseView {
        void getWalletDetailsDataSuccess(List<WalletDetailBeans> data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
