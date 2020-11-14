package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;


import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class MainPresenter extends RxPresenter<MainContact.View> implements MainContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public MainPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
