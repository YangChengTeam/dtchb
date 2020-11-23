package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.RedReceiveInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfoWrapper;

import java.util.List;

public interface MemberConstact {
    interface View extends BaseView {

        void showVipTaskInfo(VipTaskInfoWrapper data);

        void showReceiveSuccess(RedReceiveInfo data);

        void showUpgradeInfos(List<VipTaskInfo> data);

        void showUpdateRewardSuccess(List<VipTaskInfo> data, int position);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
