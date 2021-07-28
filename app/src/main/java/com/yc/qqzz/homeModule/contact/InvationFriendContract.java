package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.module.bean.InvitationCodeBeans;
import com.yc.qqzz.homeModule.module.bean.InvitationInfoBeans;
import com.yc.qqzz.homeModule.module.bean.InvitationShareBeans;

import java.util.List;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface InvationFriendContract {
    interface View extends BaseView {
        void getShareListSuccess(List<InvitationShareBeans> data);

        void getInvitationInfoSuccess(InvitationInfoBeans data);

        void getInvitationCodeSuccess(InvitationCodeBeans data);

    }

    interface Presenter extends BasePresenter<View> {
    }
}
