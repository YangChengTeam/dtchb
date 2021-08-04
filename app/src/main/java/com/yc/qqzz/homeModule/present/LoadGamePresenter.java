package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.LoadGameContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.DayCashTashBeans;
import com.yc.qqzz.homeModule.module.bean.LoadGameLoadApkBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class LoadGamePresenter extends RxPresenter<LoadGameContract.View> implements LoadGameContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public LoadGamePresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getCashdown(String imei, int group_id, int taskId) {
        showWaiteDialog();
        addSubscribe(apiModule.getCashdownApk(imei,String.valueOf(group_id),String.valueOf(taskId))
                .compose(RxUtil.<HttpResult<LoadGameLoadApkBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<LoadGameLoadApkBeans>(this) {
                    @Override
                    public void onAnalysisNext(LoadGameLoadApkBeans data) {
                        mView.getCashdownApkSuccess(data);
                    }
                }));
    }
}
