package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.AnswerContact;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchDetailsBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchPostBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchTreasureDetailssBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class SnatchTreasurePresenter extends RxPresenter<SnatchTreasureContact.View> implements SnatchTreasureContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public SnatchTreasurePresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getSnatchinfoDetails(String groupId) {
        addSubscribe(apis.getSnatchinfoDetails(groupId)
                .compose(RxUtil.<HttpResult<SnatchDetailsBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SnatchDetailsBeans>(this) {
                    @Override
                    public void onAnalysisNext(SnatchDetailsBeans data) {
                        mView.getSnatchinfoDetailsSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                        mView.getSnatchinfoDetailsError();
                    }
                }));
    }

    public void getSnatchPost(String group_id, String num, String info_id) {
        showWaiteDialog();
        addSubscribe(apis.getSnatchPost(group_id,num,info_id)
                .compose(RxUtil.<HttpResult<SnatchPostBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SnatchPostBeans>(this) {
                    @Override
                    public void onAnalysisNext(SnatchPostBeans data) {
                        mView.getSnatchPostSuccess(data);
                    }
                }));
    }


}
