package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.bean.WithDrawHomeBeans;
import com.yc.qqzz.homeModule.bean.WxCashBeans;
import com.yc.qqzz.homeModule.contact.WithDrawitemContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.DayCashTashBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class WithDrawitemPresenter extends RxPresenter<WithDrawitemContract.View> implements WithDrawitemContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public WithDrawitemPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getPayinfo(String imei, int group_id) {
            showWaiteDialog();
            addSubscribe(apiModule.getPayinfo(imei,String.valueOf(group_id))
                    .compose(RxUtil.<HttpResult<WithDrawHomeBeans>>rxSchedulerHelper())
                    .subscribeWith(new ResultSubscriber<WithDrawHomeBeans>(this) {
                        @Override
                        public void onAnalysisNext(WithDrawHomeBeans data) {
                            mView.getPayinfoSuccess(data);
                        }
                    }));
    }

    public void weixinCash(String imei, String group_id, String wx, String wx_openid, String cashMoney, String appVersionCode) {
        showWaiteDialog();
        addSubscribe(apiModule.weixinCash(imei,String.valueOf(group_id),wx,wx_openid,cashMoney,appVersionCode)
                .compose(RxUtil.<HttpResult<WxCashBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<WxCashBeans>(this) {
                    @Override
                    public void onAnalysisNext(WxCashBeans data) {
                        mView.getWeixinCashSuccess(data);
                    }
                }));
    }
}
