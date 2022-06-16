package com.yc.jsdsp.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.jsdsp.beans.module.beans.EmptyBeans;
import com.yc.jsdsp.beans.module.beans.InvationFriendExchangeBeans;
import com.yc.jsdsp.beans.module.beans.InvationPeopleListBeans;
import com.yc.jsdsp.beans.module.beans.InvitationShareBeans;

import java.util.List;


/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface InvationFriendContract {
    interface View extends BaseView {
        void getShareListSuccess(InvitationShareBeans data);


        void getInvitationCodeSuccess(EmptyBeans data);

        void getExchangeaddSuccess(InvationFriendExchangeBeans data);

        void getPeopleSuccess(List<InvationPeopleListBeans> data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}