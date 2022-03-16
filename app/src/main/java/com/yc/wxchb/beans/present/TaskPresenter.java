package com.yc.wxchb.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.LoginContract;
import com.yc.wxchb.beans.contact.TaskContract;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.module.beans.EmptyBeans;
import com.yc.wxchb.beans.module.beans.HelpQuestionBeans;
import com.yc.wxchb.beans.module.beans.LimitedBeans;
import com.yc.wxchb.beans.module.beans.LimitedRedBeans;
import com.yc.wxchb.beans.module.beans.RedTaskBeans;
import com.yc.wxchb.beans.module.beans.TaskLineBean;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class TaskPresenter extends RxPresenter<TaskContract.View> implements TaskContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public TaskPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }
    public void getLimitedData(int userId) {
        addSubscribe(apiModule.getLimitedData(String.valueOf(userId))
                .compose(RxUtil.<HttpResult<List<LimitedBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<LimitedBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<LimitedBeans> data) {
                        mView.getLimitedDataSuccess(data);
                    }
                }));
    }

    public void getRedTaskData(int userId) {
        addSubscribe(apiModule.getRedTaskData(String.valueOf(userId))
                .compose(RxUtil.<HttpResult<RedTaskBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<RedTaskBeans>(this) {
                    @Override
                    public void onAnalysisNext(RedTaskBeans data) {
                        mView.getRedTaskDataSuccess(data);
                    }
                }));
    }

    public void getLimiteRed(int id, int limitedId) {
        addSubscribe(apiModule.getLimiteRed(String.valueOf(id),String.valueOf(limitedId))
                .compose(RxUtil.<HttpResult<LimitedRedBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<LimitedRedBeans>(this) {
                    @Override
                    public void onAnalysisNext(LimitedRedBeans data) {
                        mView.getLimiteRedSuccess(data);
                    }
                }));
    }

    public void getTaskLine(int id) {
        addSubscribe(apiModule.getTaskLine(String.valueOf(id))
                .compose(RxUtil.<HttpResult<TaskLineBean>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TaskLineBean>(this) {
                    @Override
                    public void onAnalysisNext(TaskLineBean data) {
                        mView.getTaskLineSuccess(data);
                    }
                }));
    }

    public void getTaskMoney(int id, int taskId) {
        addSubscribe(apiModule.getTaskMoney(String.valueOf(id),String.valueOf(taskId))
                .compose(RxUtil.<HttpResult<TaskLineBean>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TaskLineBean>(this) {
                    @Override
                    public void onAnalysisNext(TaskLineBean data) {
                        mView.getTaskMoneySuccess(data);
                    }
                }));
    }
}
