package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.GrabRedFgContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class GrabRedFgPresenter extends RxPresenter<GrabRedFgContract.View> implements GrabRedFgContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public GrabRedFgPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}
