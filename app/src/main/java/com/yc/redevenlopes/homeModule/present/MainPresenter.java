package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeGetRedMoneyBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeMsgBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeOnlineBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeRedMessage;
import com.yc.redevenlopes.homeModule.module.bean.Info0Bean;
import com.yc.redevenlopes.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redevenlopes.utils.UpDataVersion;


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

    public void getMsgList(String group_id, int page, String pagesize) {
        addSubscribe(apis.getMsgList(group_id,page,pagesize)
                .compose(RxUtil.<HttpResult<List<HomeMsgBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<HomeMsgBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<HomeMsgBeans> data) {
                        mView.getMsgListSuccess(data);
                    }
                }));
    }

    public void getHomeMsgDataPolling(String group_id, String msgId) {
        addSubscribe(apis.getHomeMsgDataPolling(group_id,msgId)
                .compose(RxUtil.<HttpResult<List<Info0Bean>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<Info0Bean>>(this) {
                    @Override
                    public void onAnalysisNext(List<Info0Bean> data) {
                        mView.getHomeMsgDataPollingSuccess(data);
                    }
                }));
    }

    public void getMoneyRed(String group_id, String hongbao_id) {
        addSubscribe(apis.getMoneyRed(group_id,hongbao_id)
                .compose(RxUtil.<HttpResult<HomeGetRedMoneyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HomeGetRedMoneyBeans>(this) {
                    @Override
                    public void onAnalysisNext(HomeGetRedMoneyBeans data) {
                        mView.getMoneyRedSuccess(data);
                    }
                }));
    }

    public void updtreasure(String group_id) {
        addSubscribe(apis.updtreasure(group_id)
                .compose(RxUtil.<HttpResult<UpQuanNumsBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpQuanNumsBeans>(this) {
                    @Override
                    public void onAnalysisNext(UpQuanNumsBeans data) {
                        mView.updtreasureSuccess(data);
                    }
                }));
    }

    public void getonLineRed(String group_id, String on_money) {
        addSubscribe(apis.getonLineRed(group_id,on_money)
                .compose(RxUtil.<HttpResult<HomeOnlineBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HomeOnlineBeans>(this) {
                    @Override
                    public void onAnalysisNext(HomeOnlineBeans data) {
                        mView.getonLineRedSuccess(data);
                    }
                }));

    }

    public void upVersion(String agentId) {
        showWaiteDialog();
        addSubscribe(apis.upVersion(agentId)
                .compose(RxUtil.<HttpResult<UpDataVersion>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpDataVersion>(this) {
                    @Override
                    public void onAnalysisNext(UpDataVersion data) {
                        mView.upVersionSuccess(data);
                    }
                }));
    }
}
