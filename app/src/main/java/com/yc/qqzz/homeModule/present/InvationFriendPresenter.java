package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.InvationFriendContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.InvitationCodeBeans;
import com.yc.qqzz.homeModule.module.bean.InvitationInfoBeans;
import com.yc.qqzz.homeModule.module.bean.InvitationShareBeans;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class InvationFriendPresenter extends RxPresenter<InvationFriendContract.View> implements InvationFriendContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public InvationFriendPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getShareList(int id, String mobile, String page, String pagesize) {
        showWaiteDialog();
        addSubscribe(apiModule.getShareList(String.valueOf(id), mobile,page,pagesize)
                .compose(RxUtil.<HttpResult<List<InvitationShareBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<InvitationShareBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<InvitationShareBeans> data) {
                        mView.getShareListSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }

    public void getInvitationInfo(int id, String mobile) {
        showWaiteDialog();
        addSubscribe(apiModule.getInvitationInfo(String.valueOf(id), mobile)
                .compose(RxUtil.<HttpResult<InvitationInfoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<InvitationInfoBeans>(this) {
                    @Override
                    public void onAnalysisNext(InvitationInfoBeans data) {
                        mView.getInvitationInfoSuccess(data);
                    }
                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }

    public void getInvitationCode(int id, String mobile, String code) {
        showWaiteDialog();
        addSubscribe(apiModule.getInvitationCode(String.valueOf(id), mobile,code)
                .compose(RxUtil.<HttpResult<InvitationCodeBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<InvitationCodeBeans>(this) {
                    @Override
                    public void onAnalysisNext(InvitationCodeBeans data) {
                        mView.getInvitationCodeSuccess(data);
                    }
                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }
}
