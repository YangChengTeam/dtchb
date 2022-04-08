package com.yc.wxchb.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.HomefgContract;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.module.beans.NesRedBeans;
import com.yc.wxchb.beans.module.beans.SavaMonyeHotBeans;
import com.yc.wxchb.beans.module.beans.SaveMoneysInfo;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class HomefgPresenter extends RxPresenter<HomefgContract.View> implements HomefgContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public HomefgPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }
    public void getSaveMoneyInfos(String userId) {
        addSubscribe(apiModule.getSaveMoneyInfos(userId)
                .compose(RxUtil.<HttpResult<SaveMoneysInfo>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SaveMoneysInfo>(this) {
                    @Override
                    public void onAnalysisNext(SaveMoneysInfo data) {
                        mView.getSaveMoneyInfosSuccess(data);
                    }
                }));
    }

    public void getHomSaveMoney(String userId) {
        addSubscribe(apiModule.getHomSaveMoney(userId)
                .compose(RxUtil.<HttpResult<SavaMonyeHotBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SavaMonyeHotBeans>(this) {
                    @Override
                    public void onAnalysisNext(SavaMonyeHotBeans data) {
                        mView.getHomSaveMoneySuccess(data);
                    }
                }));
    }

    public void getNewRed(int id) {
        addSubscribe(apiModule.getNewRed(String.valueOf(id))
                .compose(RxUtil.<HttpResult<NesRedBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<NesRedBeans>(this) {
                    @Override
                    public void onAnalysisNext(NesRedBeans data) {
                        mView.getNewRedSuccess(data);
                    }
                }));
    }
}
