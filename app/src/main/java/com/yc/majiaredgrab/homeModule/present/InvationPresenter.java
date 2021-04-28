package com.yc.majiaredgrab.homeModule.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;
import com.yc.majiaredgrab.homeModule.module.bean.EmptyBeans;
import com.yc.majiaredgrab.homeModule.module.bean.InvationsBeans;


import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class InvationPresenter extends RxPresenter<InvationContact.View> implements InvationContact.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public InvationPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getInvationData(int id) {
            showWaiteDialog();
            addSubscribe(apiModule.getInvationData(String.valueOf(id))
                    .compose(RxUtil.<HttpResult<InvationsBeans>>rxSchedulerHelper())
                    .subscribeWith(new ResultSubscriber<InvationsBeans>(this) {
                        @Override
                        public void onAnalysisNext(InvationsBeans data) {
                            mView.getInvationDataSuccess(data);
                        }
                    }));
        }

    public void inputCode(int id, String codes) {
        showWaiteDialog();
        addSubscribe(apiModule.getInputCode(String.valueOf(id),codes)
                .compose(RxUtil.<HttpResult<EmptyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<EmptyBeans>(this) {
                    @Override
                    public void onAnalysisNext(EmptyBeans data) {
                        mView.getInputCodeSuccess();
                    }
                }));
    }
}
