package com.yc.wxchb.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.MainContract;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.module.beans.OtherBeans;
import com.yc.wxchb.utils.CacheDataUtils;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public MainPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }
    public void getOtherInfo(String group_id, String user_id) {
        addSubscribe(apiModule.getOtherInfo(group_id,user_id, CacheDataUtils.getInstance().getUserInfo().getImei())
                .compose(RxUtil.<HttpResult<OtherBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<OtherBeans>(this) {
                    @Override
                    public void onAnalysisNext(OtherBeans data) {
                        mView.getOtherInfoSuccess(data);
                    }
                }));
    }
}
