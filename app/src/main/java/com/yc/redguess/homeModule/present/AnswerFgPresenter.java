package com.yc.redguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redguess.homeModule.contact.AnswerFgContact;
import com.yc.redguess.homeModule.module.HomeApiModule;

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
