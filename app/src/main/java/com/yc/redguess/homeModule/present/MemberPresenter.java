package com.yc.redguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redguess.homeModule.contact.MemberConstact;
import com.yc.redguess.homeModule.module.bean.EmptyBeans;
import com.yc.redguess.homeModule.module.bean.RedReceiveInfo;
import com.yc.redguess.homeModule.module.bean.TaskUnLockResBeans;
import com.yc.redguess.homeModule.module.bean.VipTaskInfo;
import com.yc.redguess.homeModule.module.bean.VipTaskInfoWrapper;
import com.yc.redguess.homeModule.personModule.PersonApiModule;
import com.yc.redguess.utils.CacheDataUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class MemberPresenter extends RxPresenter<MemberConstact.View> implements MemberConstact.Presenter {

    private PersonApiModule apis;

    @Inject
    public MemberPresenter(PersonApiModule apis) {
        this.apis = apis;
    }


    public void upgradeRewardInfos(int groupId) {
        showWaiteDialog();
        addSubscribe(apis.upgradeRewardInfos(groupId).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<VipTaskInfo>>(this) {
                    @Override
                    public void onAnalysisNext(List<VipTaskInfo> data) {
                        mView.showUpgradeInfos(data);
                    }
                }));
    }

    public void getUserTaskInfo(int groupId) {
        addSubscribe(apis.getUserTaskInfo(groupId).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<VipTaskInfoWrapper>(this) {
                    @Override
                    public void onAnalysisNext(VipTaskInfoWrapper data) {
                        mView.showVipTaskInfo(data);
                    }
                }));
    }

    public void getReceiveInfo(int groupId, int task_id) {
        addSubscribe(apis.getReceiveInfo(groupId, task_id).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<RedReceiveInfo>(this) {
                    @Override
                    public void onAnalysisNext(RedReceiveInfo data) {
                        mView.showReceiveSuccess(data);
                    }
                }));
    }

    public void getUpgradeRewardInfos(int groupId, int grade_id, int position) {
        addSubscribe(apis.getUpgradeRewardInfos(groupId, grade_id).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<VipTaskInfo>>(this) {
                    @Override
                    public void onAnalysisNext(List<VipTaskInfo> data) {
                        mView.showUpdateRewardSuccess(data, position);
                    }
                }));
    }

    public void getRegUserLog(int id, String type) {
        showWaiteDialog();
        addSubscribe(apis.getRegUserLog(String.valueOf(id),type)
                .compose(RxUtil.<HttpResult<EmptyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<EmptyBeans>(this) {
                    @Override
                    public void onAnalysisNext(EmptyBeans data) {
                        CacheDataUtils.getInstance().setMember();
                    }
                }));
    }

    public void getUnlockTask(String imei, int group_id, int unLockTaskId) {
        showWaiteDialog();
        addSubscribe(apis.getUnlockTask(imei,String.valueOf(group_id),String.valueOf(unLockTaskId))
                .compose(RxUtil.<HttpResult<TaskUnLockResBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TaskUnLockResBeans>(this) {
                    @Override
                    public void onAnalysisNext(TaskUnLockResBeans data) {
                        mView.getUnlockTaskSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        mView.getUnlockTaskReeorState();
                    }
                }));
    }
}
