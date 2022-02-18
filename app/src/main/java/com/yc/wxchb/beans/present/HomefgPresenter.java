package com.yc.wxchb.beans.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.HomefgContract;
import com.yc.wxchb.beans.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class HomefgPresenter extends RxPresenter<HomefgContract.View> implements HomefgContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public HomefgPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}
