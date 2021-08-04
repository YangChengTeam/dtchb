package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.bean.EmptyBeans;
import com.yc.qqzz.homeModule.bean.InvationFriendExchangeBeans;
import com.yc.qqzz.homeModule.bean.InvationsShareBeans;
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

    public void getShareList(int id) {
        showWaiteDialog();
        addSubscribe(apiModule.getShareList(String.valueOf(id))
                .compose(RxUtil.<HttpResult<InvitationShareBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<InvitationShareBeans>(this) {
                    @Override
                    public void onAnalysisNext(InvitationShareBeans data) {
                        mView.getShareListSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }



    public void getInvitationCode(int id,  String code) {
        showWaiteDialog();
        addSubscribe(apiModule.getInvitationCode(String.valueOf(id),code)
                .compose(RxUtil.<HttpResult<EmptyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<EmptyBeans>(this) {
                    @Override
                    public void onAnalysisNext(EmptyBeans data) {
                        mView.getInvitationCodeSuccess(data);
                    }
                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }

    public void getExchangeadd(int id, String exchange_id, String wx_openid) {
        showWaiteDialog();
        addSubscribe(apiModule.getExchangeadd(String.valueOf(id),exchange_id,wx_openid)
                .compose(RxUtil.<HttpResult<InvationFriendExchangeBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<InvationFriendExchangeBeans>(this) {
                    @Override
                    public void onAnalysisNext(InvationFriendExchangeBeans data) {
                        mView.getExchangeaddSuccess(data);
                    }
                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }
}
