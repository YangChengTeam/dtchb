package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.bean.TaskBeans;
import com.yc.qqzz.homeModule.contact.TaskFgContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.DayCashTashBeans;
import com.yc.qqzz.homeModule.module.bean.TaskFgPrizeBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class TaskFgPresenter extends RxPresenter<TaskFgContract.View> implements TaskFgContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public TaskFgPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getTaskinfo(String imei, int group_id) {
            showWaiteDialog();
            addSubscribe(apiModule.getTaskinfo(imei,String.valueOf(group_id))
                    .compose(RxUtil.<HttpResult<TaskBeans>>rxSchedulerHelper())
                    .subscribeWith(new ResultSubscriber<TaskBeans>(this) {
                        @Override
                        public void onAnalysisNext(TaskBeans data) {
                            mView.getTaskinfoSuccess(data);
                        }
                    }));

    }

    public void getLevelprize(String imei, int group_id, String taskIds) {
        showWaiteDialog();
        addSubscribe(apiModule.getLevelprize(imei,String.valueOf(group_id),taskIds)
                .compose(RxUtil.<HttpResult<TaskFgPrizeBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TaskFgPrizeBeans>(this) {
                    @Override
                    public void onAnalysisNext(TaskFgPrizeBeans data) {
                        mView.getLevelprizeSuccess(data);
                    }
                }));
    }
}
