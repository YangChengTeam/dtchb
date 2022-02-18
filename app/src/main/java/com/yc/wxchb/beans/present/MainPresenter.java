package com.yc.wxchb.beans.present;


import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.MainContract;
import com.yc.wxchb.beans.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public MainPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}
