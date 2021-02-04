package com.yc.redguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redguess.homeModule.contact.RedCountContact;
import com.yc.redguess.homeModule.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class RedCountPresenter extends RxPresenter<RedCountContact.View> implements RedCountContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public RedCountPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
