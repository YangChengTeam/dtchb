package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.WithDrawitemContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class WithDrawitemPresenter extends RxPresenter<WithDrawitemContract.View> implements WithDrawitemContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public WithDrawitemPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}
