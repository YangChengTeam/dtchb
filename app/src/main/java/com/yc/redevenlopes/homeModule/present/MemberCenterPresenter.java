package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.MemberCenterContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.personModule.PersonApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class MemberCenterPresenter extends RxPresenter<MemberCenterContact.View> implements MemberCenterContact.Presenter {

    private PersonApiModule apis;

    @Inject
    public MemberCenterPresenter(PersonApiModule apis) {
        this.apis = apis;
    }


}
