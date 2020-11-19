package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.WalletDetailContract;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/18 09:17.
 */
public class WalletDetailPresenter extends RxPresenter<WalletDetailContract.View> implements WalletDetailContract.Presenter {

    private HomeApiModule apis;

    @Inject
    public WalletDetailPresenter(HomeApiModule apis) {
        this.apis = apis;
    }
}
