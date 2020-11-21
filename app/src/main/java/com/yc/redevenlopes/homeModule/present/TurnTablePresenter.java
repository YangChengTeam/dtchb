package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.TurnTableContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnTablePrizeInfoBeans;
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
                .compose(RxUtil.<HttpResult<TurnTablePrizeInfoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TurnTablePrizeInfoBeans>(this) {
                    @Override
                    public void onAnalysisNext(TurnTablePrizeInfoBeans data) {
                        mView.getPrizeInfoDataSuccess(data);
                    }
                }));
    }

    public void getGoPrize(String group_id) {
        showWaiteDialog();
        addSubscribe(apis.getGoPrize(group_id)
                .compose(RxUtil.<HttpResult<TurnGoPrizeBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TurnGoPrizeBeans>(this) {
                    @Override
                    public void onAnalysisNext(TurnGoPrizeBeans data) {
                        mView.getGoPrizeSuccess(data);
                    }
                }));
    }
}
