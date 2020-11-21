package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.AnswerContact;
import com.yc.redevenlopes.homeModule.contact.GuessingContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.GuessBeans;
import com.yc.redevenlopes.homeModule.module.bean.RedDetailsBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class GuessingPresenter extends RxPresenter<GuessingContact.View> implements GuessingContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public GuessingPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getGuessData(String group_id) {
        showWaiteDialog();
        addSubscribe(apis.getGuessData(group_id)
                .compose(RxUtil.<HttpResult<GuessBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<GuessBeans>(this) {
                    @Override
                    public void onAnalysisNext(GuessBeans data) {
                        mView.getGuessDataSuccess(data);
                    }
                }));
    }
}
