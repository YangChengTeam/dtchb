package com.yc.redguess.homeModule.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redguess.homeModule.contact.RedRainContact;
import com.yc.redguess.homeModule.module.HomeApiModule;
import com.yc.redguess.homeModule.module.bean.RedRainBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class RedRainPresenter extends RxPresenter<RedRainContact.View> implements RedRainContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public RedRainPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getRedRainMoney(String imei, String groupId, String info_id) {
        addSubscribe(apis.getRedRainMoney(imei,groupId,info_id)
                .compose(RxUtil.<HttpResult<RedRainBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<RedRainBeans>(this) {
                    @Override
                    public void onAnalysisNext(RedRainBeans data) {
                        if (mView!=null&&data!=null){
                            mView.getRedRainMoneySuccess(data);
                        }
                    }
                }));
    }
}
