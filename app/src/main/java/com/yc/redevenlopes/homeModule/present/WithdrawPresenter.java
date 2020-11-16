package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.WithdrawConstact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.personModule.PersonApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class WithdrawPresenter extends RxPresenter<WithdrawConstact.View> implements WithdrawConstact.Presenter {

    private PersonApiModule apis;

    @Inject
    public WithdrawPresenter(PersonApiModule apis) {
        this.apis = apis;
    }


}
