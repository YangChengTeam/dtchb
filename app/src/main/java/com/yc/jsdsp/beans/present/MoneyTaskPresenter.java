package com.yc.jsdsp.beans.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.jsdsp.beans.contact.MoneyTaskContract;
import com.yc.jsdsp.beans.module.HomeApiModule;
import com.yc.jsdsp.beans.module.beans.EmptyBeans;
import com.yc.jsdsp.beans.module.beans.MoneyTaskBeans;


import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class MoneyTaskPresenter extends RxPresenter<MoneyTaskContract.View> implements MoneyTaskContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public MoneyTaskPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getMoneyTask(String userId) {
            addSubscribe(apiModule.getMoneyTask(String.valueOf(userId))
                    .compose(RxUtil.<HttpResult<List<MoneyTaskBeans>>>rxSchedulerHelper())
                    .subscribeWith(new ResultSubscriber<List<MoneyTaskBeans>>(this) {
                        @Override
                        public void onAnalysisNext(List<MoneyTaskBeans> data) {
                            mView.getMoneyTaskSuccess(data);
                        }
                    }));

    }

    public void getMoneyTaskTx(String userId, int txid, String wx_openid, String appVersionCode) {
        addSubscribe(apiModule.getMoneyTaskTx(String.valueOf(userId),String.valueOf(txid),wx_openid,appVersionCode)
                .compose(RxUtil.<HttpResult<EmptyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<EmptyBeans>(this) {
                    @Override
                    public void onAnalysisNext(EmptyBeans data) {
                        mView.getMoneyTaskTxSuccess(data);
                    }
                }));
    }
}
