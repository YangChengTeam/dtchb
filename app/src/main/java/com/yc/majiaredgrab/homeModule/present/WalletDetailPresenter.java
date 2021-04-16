package com.yc.majiaredgrab.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.majiaredgrab.homeModule.contact.WalletDetailContract;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;
import com.yc.majiaredgrab.homeModule.module.bean.WalletDetailBeans;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/18 09:17.
 */
public class WalletDetailPresenter extends RxPresenter<WalletDetailContract.View> implements WalletDetailContract.Presenter {

    private HomeApiModule apis;

    @Inject
    public WalletDetailPresenter(HomeApiModule apis) {
        this.apis = apis;
    }

    public void getWalletDetailsData(String groupId,String page,String pagesize) {
        showWaiteDialog();
        addSubscribe(apis.getWalletDetailsData(groupId,page,pagesize)
                .compose(RxUtil.<HttpResult<List<WalletDetailBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<WalletDetailBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<WalletDetailBeans> data) {
                        mView.getWalletDetailsDataSuccess(data);
                    }
                }));
    }
}
