package com.yc.rrdsprj.beans.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.rrdsprj.beans.contact.ComplaintEdContract;
import com.yc.rrdsprj.beans.module.HomeApiModule;
import com.yc.rrdsprj.beans.module.beans.EmptyBeans;


import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class ComplaintEdPresenter extends RxPresenter<ComplaintEdContract.View> implements ComplaintEdContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public ComplaintEdPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void comPlaint(String userId, String trim, String infoId) {
        addSubscribe(apiModule.comPlaint(userId,trim,infoId)
                .compose(RxUtil.<HttpResult<EmptyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<EmptyBeans>(this) {
                    @Override
                    public void onAnalysisNext(EmptyBeans data) {
                        mView.comPlaintSuccess();
                    }
                }));
    }
}
