package com.yc.wxchb.beans.present;


import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.AnswerContact;
import com.yc.wxchb.beans.module.HomeApiModule;

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
