package com.yc.rrdsprj.beans.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.rrdsprj.beans.contact.ComplaintContract;
import com.yc.rrdsprj.beans.module.HomeApiModule;
import com.yc.rrdsprj.beans.module.beans.ComplainBeans;


import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class ComplaintPresenter extends RxPresenter<ComplaintContract.View> implements ComplaintContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public ComplaintPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getComplainList(String userId) {
        addSubscribe(apiModule.getComplainList(userId)
                .compose(RxUtil.<HttpResult<List<ComplainBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<ComplainBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<ComplainBeans> data) {
                        mView.getComplainListSuccess(data);
                    }
                }));
    }
}
