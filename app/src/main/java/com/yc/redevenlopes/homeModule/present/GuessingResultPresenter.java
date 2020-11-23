package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.GuessingResultContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.GuessHistoryBeans;
import com.yc.redevenlopes.homeModule.module.bean.PostGuessNoBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class GuessingResultPresenter extends RxPresenter<GuessingResultContact.View> implements GuessingResultContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public GuessingResultPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getGuessHistory(String info_id, int page, String pagesize) {
        showWaiteDialog();
        addSubscribe(apis.getGuessHistory(info_id,page,pagesize)
                .compose(RxUtil.<HttpResult<GuessHistoryBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<GuessHistoryBeans>(this) {
                    @Override
                    public void onAnalysisNext(GuessHistoryBeans data) {
                        mView.submitGuessNoSuccess(data);
                    }
                }));
    }
}
