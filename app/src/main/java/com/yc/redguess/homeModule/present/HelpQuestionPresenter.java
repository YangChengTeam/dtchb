package com.yc.redguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redguess.homeModule.contact.HelpQuestionContact;
import com.yc.redguess.homeModule.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class HelpQuestionPresenter extends RxPresenter<HelpQuestionContact.View> implements HelpQuestionContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public HelpQuestionPresenter(HomeApiModule apis) {
        this.apis = apis;
    }



}
