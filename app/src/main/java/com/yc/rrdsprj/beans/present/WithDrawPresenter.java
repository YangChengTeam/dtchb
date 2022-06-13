package com.yc.rrdsprj.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.rrdsprj.beans.contact.WithDrawContract;
import com.yc.rrdsprj.beans.module.HomeApiModule;
import com.yc.rrdsprj.beans.module.beans.FalseUserBeans;
import com.yc.rrdsprj.beans.module.beans.LotterBeans;
import com.yc.rrdsprj.beans.module.beans.LotterInfoBeans;
import com.yc.rrdsprj.beans.module.beans.PayInfoBeans;
import com.yc.rrdsprj.beans.module.beans.RedTaskBeans;
import com.yc.rrdsprj.beans.module.beans.WithDrawStatusBeans;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ccc  on 2020/11/19 16:21.
 */
public class WithDrawPresenter extends RxPresenter<WithDrawContract.View> implements WithDrawContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public WithDrawPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getPayInfo(int id) {
        addSubscribe(apiModule.getPayInfo(String.valueOf(id))
                .compose(RxUtil.<HttpResult<PayInfoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<PayInfoBeans>(this) {
                    @Override
                    public void onAnalysisNext(PayInfoBeans data) {
                        mView.getPayInfoSuccess(data);
                    }
                }));
    }

    public void getPanInfo(int id) {
        addSubscribe(apiModule.getPayInfo(String.valueOf(id))
                .compose(RxUtil.<HttpResult<PayInfoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<PayInfoBeans>(this) {
                    @Override
                    public void onAnalysisNext(PayInfoBeans data) {
                        mView.getPayInfoSuccess(data);
                    }
                }));
    }

    public void getlotterInfo(int id) {
        addSubscribe(apiModule.getlotterInfo(String.valueOf(id))
                .compose(RxUtil.<HttpResult<List<LotterInfoBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<LotterInfoBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<LotterInfoBeans> data) {
                        mView.getLimitedDataSuccess(data);
                    }
                }));
    }

    public void getlotter(int id, String appVersionCode) {
        addSubscribe(apiModule.getlotter(String.valueOf(id),appVersionCode)
                .compose(RxUtil.<HttpResult<LotterBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<LotterBeans>(this) {
                    @Override
                    public void onAnalysisNext(LotterBeans data) {
                        mView.getlotterSuccess(data);
                    }
                }));
    }

    public void weixinCash(String id, String wx, String wx_openid, String lotterMoneys, String appVersionCode) {
        addSubscribe(apiModule.weixinCash(String.valueOf(id),wx,wx_openid,lotterMoneys,appVersionCode)
                .compose(RxUtil.<HttpResult<WithDrawStatusBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<WithDrawStatusBeans>(this) {
                    @Override
                    public void onAnalysisNext(WithDrawStatusBeans data) {
                       mView.weixinCashSuccess(data);
                    }
                }));
    }


    public void getFalseuser(String pagesize) {
        addSubscribe(apiModule.getFalseuser(pagesize)
                .compose(RxUtil.<HttpResult<List<FalseUserBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<FalseUserBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<FalseUserBeans> data) {
                        mView.getFalseuserSuccess(data);
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
}
