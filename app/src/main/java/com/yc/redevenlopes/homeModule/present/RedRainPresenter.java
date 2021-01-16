package com.yc.redevenlopes.homeModule.present;


import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.RedRainContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class RedRainPresenter extends RxPresenter<RedRainContact.View> implements RedRainContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public RedRainPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
