package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.WithdrawConstact;
import com.yc.redevenlopes.homeModule.contact.WithdrawRecordConstact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.personModule.PersonApiModule;

import javax.inject.Inject;

/**
 * Created  on 2020/10/14 18:02.
 */
public class WithdrawRecordPresenter extends RxPresenter<WithdrawRecordConstact.View> implements WithdrawRecordConstact.Presenter {

    private PersonApiModule apis;

    @Inject
    public WithdrawRecordPresenter(PersonApiModule apis) {
        this.apis = apis;
    }


}
