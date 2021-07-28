package com.yc.qqzz.homeModule.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.bean.HomeLineRedBeans;
import com.yc.qqzz.homeModule.contact.MainContact;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.HomeGetRedMoneyBeanszq;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class MainPresenter extends RxPresenter<MainContact.View> implements MainContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public MainPresenter(HomeApiModule apis) {
        this.apis = apis;
    }

    public void getLineRed(String imei, int group_id) {
        addSubscribe(apis.getLineRed(imei,String.valueOf(group_id))
                .compose(RxUtil.<HttpResult<HomeLineRedBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HomeLineRedBeans>(this) {
                    @Override
                    public void onAnalysisNext(HomeLineRedBeans data) {
                        mView.getLineRedSuccess(data);
                    }
                }));
    }




}
