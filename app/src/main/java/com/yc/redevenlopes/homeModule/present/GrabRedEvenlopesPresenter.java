package com.yc.redevenlopes.homeModule.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.GrabRedEvenlopesContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoBeans;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoMoneyBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class GrabRedEvenlopesPresenter extends RxPresenter<GrabRedEvenlopesContact.View> implements GrabRedEvenlopesContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public GrabRedEvenlopesPresenter(HomeApiModule apis) {
        this.apis = apis;
    }

    public void updtreasure(String group_id) {
        addSubscribe(apis.updtreasure(group_id)
                .compose(RxUtil.<HttpResult<UpQuanNumsBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpQuanNumsBeans>(this) {
                    @Override
                    public void onAnalysisNext(UpQuanNumsBeans data) {
                        mView.updtreasureSuccess(data);
                    }
                }));
    }

    public void getlookVideo(String imei, String group_id) {
        addSubscribe(apis.getlookVideo(imei,group_id)
                .compose(RxUtil.<HttpResult<LookVideoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<LookVideoBeans>(this) {
                    @Override
                    public void onAnalysisNext(LookVideoBeans data) {
                        mView.getlookVideoSuccess(data);
                    }
                }));
    }

    public void getlookVideoRedMoney(String imei, int group_id, String is_double, String info_id, String money) {
        addSubscribe(apis.getlookVideoRedMoney(imei,String.valueOf(group_id),is_double,info_id,money)
                .compose(RxUtil.<HttpResult<LookVideoMoneyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<LookVideoMoneyBeans>(this) {
                    @Override
                    public void onAnalysisNext(LookVideoMoneyBeans data) {
                        mView.getlookVideoRedMoneySuccess(data);
                    }
                }));
    }
}
