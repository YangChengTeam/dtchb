package com.yc.redkingguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redkingguess.homeModule.contact.WithdrawLeadborderContact;
import com.yc.redkingguess.homeModule.personModule.PersonApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class WithdrawLeadborderPresenter extends RxPresenter<WithdrawLeadborderContact.View> implements WithdrawLeadborderContact.Presenter {

    private PersonApiModule apis;

    @Inject
    public WithdrawLeadborderPresenter(PersonApiModule apis) {
        this.apis = apis;
    }


}
