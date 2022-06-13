package com.yc.rrdsprj.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.rrdsprj.beans.contact.ExpressContract;
import com.yc.rrdsprj.beans.module.HomeApiModule;
import com.yc.rrdsprj.beans.module.beans.ExpressBeans;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class ExpressPresenter extends RxPresenter<ExpressContract.View> implements ExpressContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public ExpressPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getExpressData(int id) {
        addSubscribe(apiModule.getExpressData(String.valueOf(id))
                .compose(RxUtil.<HttpResult<List<ExpressBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<ExpressBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<ExpressBeans> data) {
                        mView.getExpressDataSuccess(data);
                    }
                }));
    }
}
