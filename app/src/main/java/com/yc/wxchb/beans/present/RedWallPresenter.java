package com.yc.wxchb.beans.present;


import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.RedWallContract;
import com.yc.wxchb.beans.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class RedWallPresenter extends RxPresenter<RedWallContract.View> implements RedWallContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public RedWallPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}
