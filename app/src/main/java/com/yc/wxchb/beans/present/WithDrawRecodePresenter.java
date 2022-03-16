package com.yc.wxchb.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.WithDrawRecodeContract;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.module.beans.CashRecordBeans;


import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class WithDrawRecodePresenter extends RxPresenter<WithDrawRecodeContract.View> implements WithDrawRecodeContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public WithDrawRecodePresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getCashrecord(int id,String page, String pagesize) {
        addSubscribe(apiModule.getCashrecord(String.valueOf(id),String.valueOf(page),String.valueOf(pagesize))
                .compose(RxUtil.<HttpResult<List<CashRecordBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<CashRecordBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<CashRecordBeans> data) {
                        mView.getCashrecordSuccess(data);
                    }
                }));

    }
}
