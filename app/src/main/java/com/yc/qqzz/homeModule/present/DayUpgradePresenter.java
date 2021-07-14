package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.DayUpgradeContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class DayUpgradePresenter extends RxPresenter<DayUpgradeContract.View> implements DayUpgradeContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public DayUpgradePresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}
