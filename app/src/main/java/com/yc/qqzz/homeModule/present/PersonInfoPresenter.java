package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.PersonInfoContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.OtherBeanszq;
import com.yc.qqzz.utils.CacheDataUtils;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class PersonInfoPresenter extends RxPresenter<PersonInfoContract.View> implements PersonInfoContract.Presenter {

    private HomeApiModule apis;

    @Inject
    public PersonInfoPresenter(HomeApiModule apiModule) {
        this.apis = apiModule;
    }
    public void getOtherInfo(String group_id, String user_id) {
        showWaiteDialog();
        addSubscribe(apis.getOtherInfo(group_id,user_id, CacheDataUtils.getInstance().getUserInfo().getImei())
                .compose(RxUtil.<HttpResult<OtherBeanszq>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<OtherBeanszq>(this) {
                    @Override
                    public void onAnalysisNext(OtherBeanszq data) {
                        mView.getOtherInfoSuccess(data);
                    }
                }));
    }
}
