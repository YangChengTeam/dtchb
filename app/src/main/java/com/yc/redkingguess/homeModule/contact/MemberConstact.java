package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.EmptyBeans;
import com.yc.redkingguess.homeModule.module.bean.RedReceiveInfo;
import com.yc.redkingguess.homeModule.module.bean.TaskUnLockResBeans;
import com.yc.redkingguess.homeModule.module.bean.VipTaskInfo;
import com.yc.redkingguess.homeModule.module.bean.VipTaskInfoWrapper;

import java.util.List;

public interface MemberConstact {
    interface View extends BaseView {

        void showVipTaskInfo(VipTaskInfoWrapper data);

        void showReceiveSuccess(RedReceiveInfo data);

        void showUpgradeInfos(List<VipTaskInfo> data);

        void showUpdateRewardSuccess(List<VipTaskInfo> data, int position);

        void getUnlockTaskSuccess(TaskUnLockResBeans data);

        void getUnlockTaskReeorState();

        void getbaijinUnlockTaskSuccess(TaskUnLockResBeans data);

        void getbaijinUnlockTaskReeorState();

        void bonusescash(EmptyBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
