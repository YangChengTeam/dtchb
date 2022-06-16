package com.yc.jsdsp.beans.present;



import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.jsdsp.beans.contact.AnswersContract;
import com.yc.jsdsp.beans.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class AnswersPresenter extends RxPresenter<AnswersContract.View> implements AnswersContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public AnswersPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}