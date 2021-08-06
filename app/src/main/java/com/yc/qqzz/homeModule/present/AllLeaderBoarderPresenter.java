package com.yc.qqzz.homeModule.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.AllLeaderBoarderContact;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.LeaderRankInfo;
import com.yc.qqzz.utils.CacheDataUtils;


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
        addSubscribe(apis.getAllLeaderList(groupId, CacheDataUtils.getInstance().getUserInfo().getImei())
                .compose(RxUtil.<HttpResult<List<LeaderRankInfo>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<LeaderRankInfo>>(this) {
                    @Override
                    public void onAnalysisNext(List<LeaderRankInfo> data) {
                        mView.getAllLeaderListSuccess(data);
                    }
                }));
    }
}
