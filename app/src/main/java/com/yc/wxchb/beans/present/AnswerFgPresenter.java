package com.yc.wxchb.beans.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.AnswerFgContact;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.module.beans.AnswerFanBeiBeans;
import com.yc.wxchb.beans.module.beans.AnswerFgBeans;
import com.yc.wxchb.beans.module.beans.AnswerFgQuestionBeans;
import com.yc.wxchb.beans.module.beans.HotNumsInfoBeans;
import javax.inject.Inject;



/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class AnswerFgPresenter extends RxPresenter<AnswerFgContact.View> implements AnswerFgContact.Presenter {
    private HomeApiModule apiModule;
    @Inject
    public AnswerFgPresenter(HomeApiModule apis) {
        this.apiModule = apis;
    }

    public void getAnswerList(String userId, int page, int pagesize) {
        addSubscribe(apiModule.getAnswerList(userId,String.valueOf(page),String.valueOf(pagesize))
                .compose(RxUtil.<HttpResult<AnswerFgBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<AnswerFgBeans>(this) {
                    @Override
                    public void onAnalysisNext(AnswerFgBeans data) {
                        mView.getAnswerListSuccess(data);
                    }
                }));
    }

    public void questionAdd(String userId, int iserror) {
        addSubscribe(apiModule.questionAdd(userId,String.valueOf(iserror))
                .compose(RxUtil.<HttpResult<AnswerFgQuestionBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<AnswerFgQuestionBeans>(this) {
                    @Override
                    public void onAnalysisNext(AnswerFgQuestionBeans data) {
                        mView.questionAddSuccess(data);
                    }
                }));
    }

    public void getAnswerRed(int userId, int info_id,int is_double) {
        addSubscribe(apiModule.getAnswerRed(String.valueOf(userId), String.valueOf(info_id),String.valueOf(is_double))
                .compose(RxUtil.rxSchedulerHelper()).subscribeWith(new ResultSubscriber<AnswerFanBeiBeans>(this) {
                    @Override
                    public void onAnalysisNext(AnswerFanBeiBeans data) {
                        mView.getAnswerRed(data);
                    }
                }));
    }


    public void getHotInfo(String userId, String agentId,String stype) {
        addSubscribe(apiModule.getHotInfo(userId,String.valueOf(agentId),stype)
                .compose(RxUtil.<HttpResult<HotNumsInfoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HotNumsInfoBeans>(this) {
                    @Override
                    public void onAnalysisNext(HotNumsInfoBeans data) {
                        mView.getHotInfoSuccess(data);
                    }
                }));
    }
}
