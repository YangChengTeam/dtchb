package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.SmokeHbContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.AutoGetLuckyBeans;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoBeans;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoMoneyBeans;
import com.yc.redevenlopes.homeModule.module.bean.SmokeBeans;
import com.yc.redevenlopes.homeModule.module.bean.SmokeHbBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class SmokeHbPresenter extends RxPresenter<SmokeHbContact.View> implements SmokeHbContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public SmokeHbPresenter(HomeApiModule apis) {
        this.apis = apis;
    }

    public void getLuckyRed(String imei, int group_id) {
        addSubscribe(apis.getLuckyRed(imei,String.valueOf(group_id))
                .compose(RxUtil.<HttpResult<SmokeHbBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SmokeHbBeans>(this) {
                    @Override
                    public void onAnalysisNext(SmokeHbBeans data) {
                        mView.getLuckyRedSuccess(data);
                    }
                }));

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

    public void getLuckyMoney(String imei, int group_id, String is_double, String redId) {
        addSubscribe(apis.getLuckyMoney(imei,String.valueOf(group_id),is_double,redId)
                .compose(RxUtil.<HttpResult<SmokeBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SmokeBeans>(this) {
                    @Override
                    public void onAnalysisNext(SmokeBeans data) {
                        mView.getLuckyMoneySuccess(data);
                    }
                }));
    }

    public void getLuckyAutoRed(String imei, String group_id) {
        addSubscribe(apis.getLuckyAutoRed(imei,group_id)
                .compose(RxUtil.<HttpResult<AutoGetLuckyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<AutoGetLuckyBeans>(this) {
                    @Override
                    public void onAnalysisNext(AutoGetLuckyBeans data) {
                        mView.getLuckyAutoRedSuccess(data);
                    }
                }));
    }
}
