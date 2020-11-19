package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.FrequencyfgContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class FrequencyfgPresenter extends RxPresenter<FrequencyfgContact.View> implements FrequencyfgContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public FrequencyfgPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
