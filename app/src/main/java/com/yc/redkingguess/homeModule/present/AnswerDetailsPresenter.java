package com.yc.redkingguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redkingguess.homeModule.contact.AnswerDetailsContact;
import com.yc.redkingguess.homeModule.module.HomeApiModule;
import com.yc.redkingguess.homeModule.module.bean.AnsPostRecordBeans;
import com.yc.redkingguess.homeModule.module.bean.AnswerQuestionListBeans;
import com.yc.redkingguess.homeModule.module.bean.UpQuanNumsBeans;

import java.util.List;

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


    public void getDetailsQuestionList(String groupId, String answerId) {
        addSubscribe(apis.getDetailsQuestionList(groupId,answerId)
                .compose(RxUtil.<HttpResult<List<AnswerQuestionListBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<AnswerQuestionListBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<AnswerQuestionListBeans> data) {
                        mView.getDetailsQuestionListSuccess(data);
                    }
                }));
    }

    public void postAnserRecord(String groupId, String answerId, String iserror) {
        addSubscribe(apis.postAnserRecord(groupId,answerId,iserror)
                .compose(RxUtil.<HttpResult<AnsPostRecordBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<AnsPostRecordBeans>(this) {
                    @Override
                    public void onAnalysisNext(AnsPostRecordBeans data) {
                        mView.postAnserRecordSuccess(data);
                    }
                }));
    }

    public void updtreasure(String group_id) {
        addSubscribe(apis.updtreasure(group_id)
                .compose(RxUtil.<HttpResult<UpQuanNumsBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpQuanNumsBeans>(this) {
                    @Override
                    public void onAnalysisNext(UpQuanNumsBeans data) {

                    }
                }));
    }
}
