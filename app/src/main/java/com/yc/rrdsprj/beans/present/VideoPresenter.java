package com.yc.rrdsprj.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.rrdsprj.beans.contact.VideoContract;
import com.yc.rrdsprj.beans.module.HomeApiModule;
import com.yc.rrdsprj.beans.module.beans.HotNumsInfoBeans;
import com.yc.rrdsprj.beans.module.beans.RedTaskBeans;
import com.yc.rrdsprj.beans.module.beans.TaskLineBean;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class VideoPresenter extends RxPresenter<VideoContract.View> implements VideoContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public VideoPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
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
    public void getHotInfo(String userId, String agentId) {
        addSubscribe(apiModule.getHotInfo(userId,String.valueOf(agentId),"2")
                .compose(RxUtil.<HttpResult<HotNumsInfoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HotNumsInfoBeans>(this) {
                    @Override
                    public void onAnalysisNext(HotNumsInfoBeans data) {
                        mView.getHotInfoSuccess(data);
                    }
                }));
    }

}
