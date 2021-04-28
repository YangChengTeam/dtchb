package com.yc.majiaredgrab.homeModule.present;



import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;
import com.yc.majiaredgrab.homeModule.module.bean.CashBeans;
import com.yc.majiaredgrab.homeModule.module.bean.InvationsShareBeans;
import com.yc.majiaredgrab.homeModule.module.bean.ShareWithExChangeBeans;


import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class ShareWithDrawPresenter extends RxPresenter<ShareWithDrawContact.View> implements ShareWithDrawContact.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public ShareWithDrawPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getExchangeInfoData(int id, String code) {
            showWaiteDialog();
            addSubscribe(apiModule.getExchangeInfoData(String.valueOf(id),code)
                    .compose(RxUtil.<HttpResult<InvationsShareBeans>>rxSchedulerHelper())
                    .subscribeWith(new ResultSubscriber<InvationsShareBeans>(this) {
                        @Override
                        public void onAnalysisNext(InvationsShareBeans data) {
                            mView.getExchangeInfoDataSuccess(data);
                        }
                    }));
    }

    public void exchange(int id, String exchangeId, String exchange_type) {
        showWaiteDialog();
        addSubscribe(apiModule.exchange(String.valueOf(id),exchangeId,exchange_type)
                .compose(RxUtil.<HttpResult<ShareWithExChangeBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<ShareWithExChangeBeans>(this) {
                    @Override
                    public void onAnalysisNext(ShareWithExChangeBeans data) {
                        mView.exchangeSuccess(data);
                    }
                }));
    }
    public void weixinCash(String groupId, String wx,String wx_openid,String name,String weixinImg) {
        addSubscribe(apiModule.weixinCash(groupId,wx,wx_openid,name,weixinImg)
                .compose(RxUtil.<HttpResult<CashBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<CashBeans>(this) {
                    @Override
                    public void onAnalysisNext(CashBeans data) {
                        mView.weixinBindCashSuccess(data);
                    }
                }));
    }
}
