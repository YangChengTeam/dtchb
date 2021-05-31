package com.yc.redkingguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redkingguess.homeModule.contact.SnatchTreasureDeatilsContact;
import com.yc.redkingguess.homeModule.module.HomeApiModule;
import com.yc.redkingguess.homeModule.module.bean.SnatchTreasureDetailssBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class SnatchTreasureDetailsPresenter extends RxPresenter<SnatchTreasureDeatilsContact.View> implements SnatchTreasureDeatilsContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public SnatchTreasureDetailsPresenter(HomeApiModule apis) {
        this.apis = apis;
    }
    public void getSnatchDetailss(String group_id, String id) {
        showWaiteDialog();
        addSubscribe(apis.getSnatchDetailss(group_id,id)
                .compose(RxUtil.<HttpResult<SnatchTreasureDetailssBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SnatchTreasureDetailssBeans>(this) {
                    @Override
                    public void onAnalysisNext(SnatchTreasureDetailssBeans data) {
                        mView.getSnatchDetailssSuccess(data);
                    }
                }));
    }

}
