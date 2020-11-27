package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.WithdrawConstact;
import com.yc.redevenlopes.homeModule.contact.WithdrawRecordConstact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.AnswerQuestionListBeans;
import com.yc.redevenlopes.homeModule.module.bean.WithDrawRecordBeans;
import com.yc.redevenlopes.homeModule.personModule.PersonApiModule;

import java.util.List;

import javax.inject.Inject;

/**
 * Created  on 2020/10/14 18:02.
 */
public class WithdrawRecordPresenter extends RxPresenter<WithdrawRecordConstact.View> implements WithdrawRecordConstact.Presenter {

    private PersonApiModule apis;

    @Inject
    public WithdrawRecordPresenter(PersonApiModule apis) {
        this.apis = apis;
    }


    public void getWithDrawRecord(String groupId, String page,String pagesize) {
        addSubscribe(apis.getDetailsQuestionList(groupId,page,pagesize)
                .compose(RxUtil.<HttpResult<List<WithDrawRecordBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<WithDrawRecordBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<WithDrawRecordBeans> data) {
                        mView.getWithDrawRecordSuccess(data);
                    }
                }));
    }
}
