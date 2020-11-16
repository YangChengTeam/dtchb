package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureContact;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureDeatilsContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class SnatchTreasureDetailsPresenter extends RxPresenter<SnatchTreasureDeatilsContact.View> implements SnatchTreasureDeatilsContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public SnatchTreasureDetailsPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
