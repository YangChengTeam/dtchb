package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeRedMessage;
import com.yc.redevenlopes.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.utils.CacheDataUtils;


import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class MainPresenter extends RxPresenter<MainContact.View> implements MainContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public MainPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getHomeData(String groupId) {
        showWaiteDialog();
        addSubscribe(apis.getHomeData(groupId)
                .compose(RxUtil.<HttpResult<HomeAllBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HomeAllBeans>(this) {
                    @Override
                    public void onAnalysisNext(HomeAllBeans data) {
                        mView.getHomeDataSuccess(data);
                    }
                }));
    }

    public void getOtherInfo(String group_id, String user_id) {
        showWaiteDialog();
        addSubscribe(apis.getOtherInfo(group_id,user_id)
                .compose(RxUtil.<HttpResult<OtherBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<OtherBeans>(this) {
                    @Override
                    public void onAnalysisNext(OtherBeans data) {
                        mView.getOtherInfoSuccess(data);
                    }
                }));
    }

    public void getHomeMessageRedData(String group_id, String hongbao_id) {
        addSubscribe(apis.getHomeMessageRedDataInfo(group_id,hongbao_id)
                .compose(RxUtil.<HttpResult<List<HomeRedMessage>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<HomeRedMessage>>(this) {
                    @Override
                    public void onAnalysisNext(List<HomeRedMessage> data) {
                        mView.getHomeMessageRedDataInfo(data);
                    }
                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));

    }

    public void getRedEvenlopsInfo(String group_id, String hongbao_id) {
        showWaiteDialog();
        addSubscribe(apis.getRedEvenlopsInfo(group_id,hongbao_id)
                .compose(RxUtil.<HttpResult<OpenRedEvenlopes>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<OpenRedEvenlopes>(this) {
                    @Override
                    public void onAnalysisNext(OpenRedEvenlopes data) {
                        mView.getRedEvenlopsInfoSuccess(data);
                    }
                }));
    }
}
