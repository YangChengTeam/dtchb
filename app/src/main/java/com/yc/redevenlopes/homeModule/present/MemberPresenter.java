package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.MemberConstact;
import com.yc.redevenlopes.homeModule.module.bean.RedReceiveInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfoWrapper;
import com.yc.redevenlopes.homeModule.personModule.PersonApiModule;

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

}
