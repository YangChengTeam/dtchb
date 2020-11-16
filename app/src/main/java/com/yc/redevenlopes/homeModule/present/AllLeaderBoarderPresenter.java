package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.AllLeaderBoarderContact;
import com.yc.redevenlopes.homeModule.contact.AnswerContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class AllLeaderBoarderPresenter extends RxPresenter<AllLeaderBoarderContact.View> implements AllLeaderBoarderContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public AllLeaderBoarderPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
