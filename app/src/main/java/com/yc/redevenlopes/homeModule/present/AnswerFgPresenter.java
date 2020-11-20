package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.AnswerContact;
import com.yc.redevenlopes.homeModule.contact.AnswerFgContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class AnswerFgPresenter extends RxPresenter<AnswerFgContact.View> implements AnswerFgContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public AnswerFgPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
