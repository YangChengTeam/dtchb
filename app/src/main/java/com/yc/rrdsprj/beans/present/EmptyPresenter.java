package com.yc.rrdsprj.beans.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.rrdsprj.beans.contact.EmptyContract;
import com.yc.rrdsprj.beans.module.HomeApiModule;

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
