package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.AllLeaderBoarderContact;
import com.yc.redevenlopes.homeModule.contact.AnswerContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.LeaderRankInfo;
import com.yc.redevenlopes.homeModule.module.bean.WithDrawRecordBeans;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class AllLeaderBoarderPresenter extends RxPresenter<AllLeaderBoarderContact.View> implements AllLeaderBoarderContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public AllLeaderBoarderPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getAllLeaderList(String groupId) {
        addSubscribe(apis.getAllLeaderList(groupId)
                .compose(RxUtil.<HttpResult<List<LeaderRankInfo>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<LeaderRankInfo>>(this) {
                    @Override
                    public void onAnalysisNext(List<LeaderRankInfo> data) {
                        mView.getAllLeaderListSuccess(data);
                    }
                }));
    }
}
