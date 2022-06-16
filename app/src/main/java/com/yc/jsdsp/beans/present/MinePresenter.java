package com.yc.jsdsp.beans.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.jsdsp.beans.contact.MineContract;
import com.yc.jsdsp.beans.module.HomeApiModule;
import com.yc.jsdsp.beans.module.beans.OtherBeans;
import com.yc.jsdsp.beans.module.beans.TelBeans;
import com.yc.jsdsp.utils.CacheDataUtils;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class MinePresenter extends RxPresenter<MineContract.View> implements MineContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public MinePresenter(HomeApiModule apiModule) {
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

    public void getTel(int id) {
        addSubscribe(apiModule.getTel(String.valueOf(id))
                .compose(RxUtil.<HttpResult<TelBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<TelBeans>(this) {
                    @Override
                    public void onAnalysisNext(TelBeans data) {
                        mView.getTelSuccess(data);
                    }
                }));
    }
}
