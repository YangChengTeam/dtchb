package com.yc.wxchb.beans.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.EmptyContract;
import com.yc.wxchb.beans.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class EmptyPresenter extends RxPresenter<EmptyContract.View> implements EmptyContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public EmptyPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}