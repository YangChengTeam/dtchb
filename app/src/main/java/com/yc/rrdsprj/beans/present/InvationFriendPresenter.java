package com.yc.rrdsprj.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.rrdsprj.beans.contact.InvationFriendContract;
import com.yc.rrdsprj.beans.module.HomeApiModule;
import com.yc.rrdsprj.beans.module.beans.EmptyBeans;
import com.yc.rrdsprj.beans.module.beans.InvationFriendExchangeBeans;
import com.yc.rrdsprj.beans.module.beans.InvationPeopleListBeans;
import com.yc.rrdsprj.beans.module.beans.InvitationShareBeans;

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

    public void getExchangeadd(int id, String exchange_id, String wx_openid,String appVersionCode) {
        showWaiteDialog();
        addSubscribe(apiModule.getExchangeadd(String.valueOf(id),exchange_id,wx_openid,appVersionCode)
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

    public void getPeople(int id, int page, String pagesize) {
        showWaiteDialog();
        addSubscribe(apiModule.getPeople(String.valueOf(id),String.valueOf(page),pagesize)
                .compose(RxUtil.<HttpResult<List<InvationPeopleListBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<InvationPeopleListBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<InvationPeopleListBeans> data) {
                        mView.getPeopleSuccess(data);
                    }
                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }
}
