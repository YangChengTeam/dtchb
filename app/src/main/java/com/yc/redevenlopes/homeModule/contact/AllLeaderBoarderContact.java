package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.LeaderRankInfo;

import java.util.List;

public interface AllLeaderBoarderContact {
    interface View extends BaseView {

        void getAllLeaderListSuccess(List<LeaderRankInfo> data);

    }

    interface Presenter extends BasePresenter<View> {

    }
}
