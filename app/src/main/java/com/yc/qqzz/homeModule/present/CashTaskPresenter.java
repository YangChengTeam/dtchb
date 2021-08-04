package com.yc.qqzz.homeModule.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.bean.CashTaskBeans;
import com.yc.qqzz.homeModule.bean.EmptyBeans;
import com.yc.qqzz.homeModule.contact.CashTaskContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.DayCashTashBeans;
import com.yc.qqzz.homeModule.module.bean.DayUpgradeDayCashFinshBeans;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class CashTaskPresenter extends RxPresenter<CashTaskContract.View> implements CashTaskContract.Presenter {

    private HomeApiModule apis;

    @Inject
    public CashTaskPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void cashdown(String imei, int group_id) {
            showWaiteDialog();
            addSubscribe(apis.getCashdown(imei,String.valueOf(group_id))
                    .compose(RxUtil.<HttpResult<DayCashTashBeans>>rxSchedulerHelper())
                    .subscribeWith(new ResultSubscriber<DayCashTashBeans>(this) {
                        @Override
                        public void onAnalysisNext(DayCashTashBeans data) {
                            mView.getCashdownSuccess(data);
                        }
                    }));
    }

    public void getDayhb(String imei, int group_id, String info_id) {
        showWaiteDialog();
        addSubscribe(apis.getDayhb(imei,String.valueOf(group_id),info_id)
                .compose(RxUtil.<HttpResult<CashTaskBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<CashTaskBeans>(this) {
                    @Override
                    public void onAnalysisNext(CashTaskBeans data) {
                        mView.getDayhbSuccess(data);
                    }
                }));
    }

    public void getOutcashdown(String imei, int group_id, int taskId, String wx_openid) {
        showWaiteDialog();
        addSubscribe(apis.getOutcashdown(imei,String.valueOf(group_id),String.valueOf(taskId),wx_openid)
                .compose(RxUtil.<HttpResult<EmptyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<EmptyBeans>(this) {
                    @Override
                    public void onAnalysisNext(EmptyBeans data) {
                        mView.getOutcashdownSuccess(data);
                    }
                }));
    }
}
