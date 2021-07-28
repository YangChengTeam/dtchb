package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.MineContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.OtherBeanszq;
import com.yc.qqzz.utils.CacheDataUtils;

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
        showWaiteDialog();
        addSubscribe(apiModule.getOtherInfo(group_id,user_id, CacheDataUtils.getInstance().getUserInfo().getImei())
                .compose(RxUtil.<HttpResult<OtherBeanszq>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<OtherBeanszq>(this) {
                    @Override
                    public void onAnalysisNext(OtherBeanszq data) {
                        mView.getOtherInfoSuccess(data);
                    }
                }));
    }
}
