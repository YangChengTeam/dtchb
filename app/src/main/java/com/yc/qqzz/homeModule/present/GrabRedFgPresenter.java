package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.bean.AnswerFanBeiBeans;
import com.yc.qqzz.homeModule.bean.AnswerFgBeans;
import com.yc.qqzz.homeModule.bean.AnswerFgQuestionBeans;
import com.yc.qqzz.homeModule.bean.GetHomeLineRedBeans;
import com.yc.qqzz.homeModule.contact.GrabRedFgContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.OtherBeanszq;
import com.yc.qqzz.utils.CacheDataUtils;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class GrabRedFgPresenter extends RxPresenter<GrabRedFgContract.View> implements GrabRedFgContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public GrabRedFgPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getAnswerList(String imei, int group_id, int page, int pagesize) {
        addSubscribe(apiModule.getAnswerList(imei,String.valueOf(group_id),String.valueOf(page),String.valueOf(pagesize))
                .compose(RxUtil.<HttpResult<AnswerFgBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<AnswerFgBeans>(this) {
                    @Override
                    public void onAnalysisNext(AnswerFgBeans data) {
                        mView.getAnswerListSuccess(data);
                    }
                }));
    }

    public void questionAdd(String imei, int group_id, int iserror, int continue_num) {
        addSubscribe(apiModule.questionAdd(imei,String.valueOf(group_id),String.valueOf(iserror),String.valueOf(continue_num))
                .compose(RxUtil.<HttpResult<AnswerFgQuestionBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<AnswerFgQuestionBeans>(this) {
                    @Override
                    public void onAnalysisNext(AnswerFgQuestionBeans data) {
                        mView.questionAddSuccess(data);
                    }
                }));
    }
    public void gethbonline(String imei, int group_id) {
        addSubscribe(apiModule.gethbonline(imei, String.valueOf(group_id))
                .compose(RxUtil.rxSchedulerHelper()).subscribeWith(new ResultSubscriber<GetHomeLineRedBeans>(this) {
                    @Override
                    public void onAnalysisNext(GetHomeLineRedBeans data) {
                        mView.gethbonlineSuccess(data);
                    }
                }));
    }
    public void getDoubleVideo(int userId, int info_id) {
        addSubscribe(apiModule.getDoubleVideo(String.valueOf(userId), String.valueOf(info_id))
                .compose(RxUtil.rxSchedulerHelper()).subscribeWith(new ResultSubscriber<AnswerFanBeiBeans>(this) {
                    @Override
                    public void onAnalysisNext(AnswerFanBeiBeans data) {
                        mView.getDoubleVideoSuccess(data);
                    }
                }));
    }



}
