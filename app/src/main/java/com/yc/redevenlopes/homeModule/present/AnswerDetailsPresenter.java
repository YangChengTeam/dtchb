package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.AnswerDetailsContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class AnswerDetailsPresenter extends RxPresenter<AnswerDetailsContact.View> implements AnswerDetailsContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public AnswerDetailsPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
