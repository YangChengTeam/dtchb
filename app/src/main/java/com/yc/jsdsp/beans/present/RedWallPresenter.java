package com.yc.jsdsp.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.jsdsp.beans.contact.RedWallContract;
import com.yc.jsdsp.beans.module.HomeApiModule;
import com.yc.jsdsp.beans.module.beans.EmptyBeans;
import com.yc.jsdsp.beans.module.beans.RedWallInfoBeans;
import com.yc.jsdsp.beans.module.beans.WallMoneyBeansTwo;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class RedWallPresenter extends RxPresenter<RedWallContract.View> implements RedWallContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public RedWallPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getWallInfo(int id) {
        showWaiteDialog();
        addSubscribe(apiModule.getWallInfo(String.valueOf(id))
                .compose(RxUtil.<HttpResult<RedWallInfoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<RedWallInfoBeans>(this) {
                    @Override
                    public void onAnalysisNext(RedWallInfoBeans data) {
                        mView.getWallInfoSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }

    public void wallCash(String userId, String wx_openid, String moneys, String appVersionCode) {
        addSubscribe(apiModule.wallCash(userId,wx_openid,moneys,appVersionCode)
                .compose(RxUtil.<HttpResult<EmptyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<EmptyBeans>(this) {
                    @Override
                    public void onAnalysisNext(EmptyBeans data) {
                        mView.wallCashSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }

    public void getWallMoneys(String adNetworkRitId) {
        addSubscribe(apiModule.getWallMoneys(adNetworkRitId)
                .compose(RxUtil.<HttpResult<WallMoneyBeansTwo>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<WallMoneyBeansTwo>(this) {
                    @Override
                    public void onAnalysisNext(WallMoneyBeansTwo data) {
                        mView.getWallMoneysSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }
}
