package com.yc.rrdsprj.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.rrdsprj.beans.contact.AdHotContract;
import com.yc.rrdsprj.beans.module.HomeApiModule;
import com.yc.rrdsprj.beans.module.beans.HotIndexBeans;
import com.yc.rrdsprj.beans.module.beans.HotTaskBeans;


import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class AdHotPresenter extends RxPresenter<AdHotContract.View> implements AdHotContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public AdHotPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }
    public void getHotInfoIndex(String userId, String agentId) {
        addSubscribe(apiModule.getHotInfoIndex(userId,agentId)
                .compose(RxUtil.<HttpResult<HotIndexBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HotIndexBeans>(this) {
                    @Override
                    public void onAnalysisNext(HotIndexBeans data) {
                        mView.getHotInfoIndexSuccess(data);
                    }
                }));
    }

    public void gethotTask(String userId, String agentId,String appPack,String appName,String type) {
        addSubscribe(apiModule.gethotTask(userId,agentId,appPack,appName,type)
                .compose(RxUtil.<HttpResult<HotTaskBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HotTaskBeans>(this) {
                    @Override
                    public void onAnalysisNext(HotTaskBeans data) {
                        mView.gethotTaskSuccess(data);
                    }
                }));
    }
}
