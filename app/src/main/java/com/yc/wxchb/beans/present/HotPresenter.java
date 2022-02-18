package com.yc.wxchb.beans.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.HotContract;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.module.beans.HotWithDrawBeans;
import com.yc.wxchb.beans.module.beans.MoneysBeans;
import com.yc.wxchb.beans.module.beans.QuesTionsHotBeans;


import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class HotPresenter extends RxPresenter<HotContract.View> implements HotContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public HotPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }


    public void getHotIndex(String userId) {
            showWaiteDialog();
            addSubscribe(apiModule.getHotIndex(userId)
                    .compose(RxUtil.<HttpResult<HotWithDrawBeans>>rxSchedulerHelper())
                    .subscribeWith(new ResultSubscriber<HotWithDrawBeans>(this) {
                        @Override
                        public void onAnalysisNext(HotWithDrawBeans data) {
                            mView.getHotIndexSuccess(data);
                        }
                    }));
    }

    public void hottixian(String userId, String wx_openid, String tixianMoneys, String appVersionCode) {
        showWaiteDialog();
        addSubscribe(apiModule.hottixian(userId,wx_openid,tixianMoneys,appVersionCode)
                .compose(RxUtil.<HttpResult<MoneysBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<MoneysBeans>(this) {
                    @Override
                    public void onAnalysisNext(MoneysBeans data) {
                        mView.hottixianSuccess(data);
                    }
                }));
    }

    public void getQuestionHot(String userId, String agentId) {
        addSubscribe(apiModule.getQuestionHot(userId,agentId)
                .compose(RxUtil.<HttpResult<QuesTionsHotBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<QuesTionsHotBeans>(this) {
                    @Override
                    public void onAnalysisNext(QuesTionsHotBeans data) {
                        mView.getQuestionHotSuccess(data);
                    }
                }));
    }
}
