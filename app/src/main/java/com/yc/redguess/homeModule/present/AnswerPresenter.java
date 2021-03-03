package com.yc.redguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redguess.homeModule.contact.AnswerContact;
import com.yc.redguess.homeModule.module.HomeApiModule;
import com.yc.redguess.homeModule.module.bean.AnswerBeans;
import com.yc.redguess.homeModule.module.bean.UpQuanNumsBeans;

import java.util.List;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class AnswerPresenter extends RxPresenter<AnswerContact.View> implements AnswerContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public AnswerPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getAnswerQuestionList(String groupId) {
        showWaiteDialog();
        addSubscribe(apis.getAnswerQuestionList(groupId)
                .compose(RxUtil.<HttpResult<List<AnswerBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<AnswerBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<AnswerBeans> data) {
                        mView.getAnswerQuestionListSuccess(data);
                    }
                }));
    }

    public void updtreasure(String group_id) {
        addSubscribe(apis.updtreasure(group_id)
                .compose(RxUtil.<HttpResult<UpQuanNumsBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpQuanNumsBeans>(this) {
                    @Override
                    public void onAnalysisNext(UpQuanNumsBeans data) {
                        mView.updtreasureSuccess(data);
                    }
                }));
    }
}
