package com.yc.redkingguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redkingguess.homeModule.contact.WithdrawConstact;
import com.yc.redkingguess.homeModule.module.bean.CashBeans;
import com.yc.redkingguess.homeModule.module.bean.EmptyBeans;
import com.yc.redkingguess.homeModule.module.bean.SignWiths;
import com.yc.redkingguess.homeModule.module.bean.TithDrawBeans;
import com.yc.redkingguess.homeModule.module.bean.WeixinCashBeans;
import com.yc.redkingguess.homeModule.personModule.PersonApiModule;
import com.yc.redkingguess.utils.CacheDataUtils;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class WithdrawPresenter extends RxPresenter<WithdrawConstact.View> implements WithdrawConstact.Presenter {

    private PersonApiModule apis;

    @Inject
    public WithdrawPresenter(PersonApiModule apis) {
        this.apis = apis;
    }


    public void getWithDrawData(String groupId) {
        addSubscribe(apis.getWithDrawData(groupId)
                .compose(RxUtil.<HttpResult<TithDrawBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TithDrawBeans>(this) {
                    @Override
                    public void onAnalysisNext(TithDrawBeans data) {
                        mView.getWithDrawDataSuccess(data);
                    }
                }));
    }

    public void weixinCash(String groupId, String wx,String wx_openid,String name,String weixinImg) {
        addSubscribe(apis.weixinCash(groupId,wx,wx_openid,name,weixinImg)
                .compose(RxUtil.<HttpResult<CashBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<CashBeans>(this) {
                    @Override
                    public void onAnalysisNext(CashBeans data) {
                        mView.weixinBindCashSuccess(data);
                    }
                }));
    }

    public void cashMoney(String groupId, String wx, String cashMoney) {
        addSubscribe(apis.cashMoney(groupId,wx,cashMoney)
                .compose(RxUtil.<HttpResult<WeixinCashBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<WeixinCashBeans>(this) {
                    @Override
                    public void onAnalysisNext(WeixinCashBeans data) {
                        mView.cashMoneySuccess(data);
                    }
                }));
    }

    public void getRegUserLog(int id, String type) {
        showWaiteDialog();
        addSubscribe(apis.getRegUserLog(String.valueOf(id),type)
                .compose(RxUtil.<HttpResult<EmptyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<EmptyBeans>(this) {
                    @Override
                    public void onAnalysisNext(EmptyBeans data) {
                        CacheDataUtils.getInstance().setWithdraw();
                    }
                }));
    }


    public void getSignMoeny(String imei, int group_id) {
        showWaiteDialog();
        addSubscribe(apis.getSignMoenys(imei,String.valueOf(group_id))
                .compose(RxUtil.<HttpResult<SignWiths>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SignWiths>(this) {
                    @Override
                    public void onAnalysisNext(SignWiths data) {
                        mView.getSignMoenysSuccess(data);
                    }
                }));
    }

}
