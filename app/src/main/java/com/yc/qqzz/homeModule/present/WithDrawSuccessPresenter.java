package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.WithDrawSuccessContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class WithDrawSuccessPresenter extends RxPresenter<WithDrawSuccessContract.View> implements WithDrawSuccessContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public WithDrawSuccessPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}
