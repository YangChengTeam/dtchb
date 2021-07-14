package com.yc.qqzz.homeModule.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.TurnTableContact;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.TurnGoPrizeBeanszq;
import com.yc.qqzz.homeModule.module.bean.TurnTablePrizeInfoBeanszq;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class TurnTablePresenter extends RxPresenter<TurnTableContact.View> implements TurnTableContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public TurnTablePresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getPrizeInfoData(String group_id) {
        showWaiteDialog();
        addSubscribe(apis.getPrizeInfoData(group_id)
                .compose(RxUtil.<HttpResult<TurnTablePrizeInfoBeanszq>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TurnTablePrizeInfoBeanszq>(this) {
                    @Override
                    public void onAnalysisNext(TurnTablePrizeInfoBeanszq data) {
                        mView.getPrizeInfoDataSuccess(data);
                    }
                }));
    }

    public void getGoPrize(String group_id) {
        showWaiteDialog();
        addSubscribe(apis.getGoPrize(group_id)
                .compose(RxUtil.<HttpResult<TurnGoPrizeBeanszq>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TurnGoPrizeBeanszq>(this) {
                    @Override
                    public void onAnalysisNext(TurnGoPrizeBeanszq data) {
                        mView.getGoPrizeSuccess(data);
                    }
                }));
         }


}
