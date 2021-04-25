package com.yc.majiaredgrab.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.majiaredgrab.homeModule.contact.InvationContact;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class InvationPresenter extends RxPresenter<InvationContact.View> implements InvationContact.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public InvationPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}
