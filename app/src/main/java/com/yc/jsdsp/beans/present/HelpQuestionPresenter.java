package com.yc.jsdsp.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.jsdsp.beans.contact.HelpQuestionContact;
import com.yc.jsdsp.beans.module.HomeApiModule;
import com.yc.jsdsp.beans.module.beans.HelpQuestionBeans;

import java.util.List;

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

    public void getaboutplayList(int userid) {
        addSubscribe(apis.getaboutplayList(String.valueOf(userid))
                .compose(RxUtil.<HttpResult<List<HelpQuestionBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<HelpQuestionBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<HelpQuestionBeans> data) {
                        mView.getaboutplayListSuccess(data);
                    }
                }));
    }

}
