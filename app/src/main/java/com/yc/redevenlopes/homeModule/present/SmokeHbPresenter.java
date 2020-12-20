package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.SmokeHbContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class SmokeHbPresenter extends RxPresenter<SmokeHbContact.View> implements SmokeHbContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public SmokeHbPresenter(HomeApiModule apis) {
        this.apis = apis;
    }

}
