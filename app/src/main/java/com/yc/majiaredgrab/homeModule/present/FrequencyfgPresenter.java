package com.yc.majiaredgrab.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.majiaredgrab.homeModule.contact.FrequencyfgContact;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;
import com.yc.majiaredgrab.homeModule.module.bean.FrequencyFgBeans;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class FrequencyfgPresenter extends RxPresenter<FrequencyfgContact.View> implements FrequencyfgContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public FrequencyfgPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getSnatchNums(String group_id, int page, String pagesize) {
        addSubscribe(apis.getSnatchNums(group_id,page,pagesize)
                .compose(RxUtil.<HttpResult<List<FrequencyFgBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<FrequencyFgBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<FrequencyFgBeans> data) {
                        mView.getSnatchNumsSuccess(data);
                    }
                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));

    }

    public void getNearSnatchNums(String pagesize) {
        addSubscribe(apis.getNearSnatchNums(pagesize)
                .compose(RxUtil.<HttpResult<List<FrequencyFgBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<FrequencyFgBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<FrequencyFgBeans> data) {
                        mView.getSnatchNumsSuccess(data);
                    }
                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));
    }
}
