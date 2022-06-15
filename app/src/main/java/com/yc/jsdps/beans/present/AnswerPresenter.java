package com.yc.jsdps.beans.present;


import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.jsdps.beans.contact.AnswerContact;
import com.yc.jsdps.beans.module.HomeApiModule;

import javax.inject.Inject;



/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class AnswerPresenter extends RxPresenter<AnswerContact.View> implements AnswerContact.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public AnswerPresenter(HomeApiModule apis) {
        this.apiModule = apis;
    }




}
