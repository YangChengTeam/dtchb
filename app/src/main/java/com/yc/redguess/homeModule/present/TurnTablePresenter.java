package com.yc.redguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redguess.homeModule.contact.TurnTableContact;
import com.yc.redguess.homeModule.module.HomeApiModule;
import com.yc.redguess.homeModule.module.bean.TurnGetPrizeBeans;
import com.yc.redguess.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.redguess.homeModule.module.bean.TurnTablePrizeInfoBeans;
import com.yc.redguess.homeModule.module.bean.UpQuanNumsBeans;

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

    public void getTurn(String group_id, String prizeId) {

        addSubscribe(apis.getTurn(group_id,prizeId)
                .compose(RxUtil.<HttpResult<TurnGetPrizeBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TurnGetPrizeBeans>(this) {
                    @Override
                    public void onAnalysisNext(TurnGetPrizeBeans data) {
                     //   mView.getTurnSuccess(data);
                    }
                }));

    }
}
