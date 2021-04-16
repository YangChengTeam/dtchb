package com.yc.majiaredgrab.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.majiaredgrab.homeModule.contact.GuessingDetailsContact;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class GuessingDetailsPresenter extends RxPresenter<GuessingDetailsContact.View> implements GuessingDetailsContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public GuessingDetailsPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
