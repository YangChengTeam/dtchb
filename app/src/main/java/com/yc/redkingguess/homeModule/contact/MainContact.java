package com.yc.redkingguess.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redkingguess.homeModule.module.bean.HomeAllBeans;
import com.yc.redkingguess.homeModule.module.bean.HomeGetRedMoneyBeans;
import com.yc.redkingguess.homeModule.module.bean.HomeMsgBeans;
import com.yc.redkingguess.homeModule.module.bean.HomeOnlineBeans;
import com.yc.redkingguess.homeModule.module.bean.HomeRedMessage;
import com.yc.redkingguess.homeModule.module.bean.Info0Bean;
import com.yc.redkingguess.homeModule.module.bean.NewsLoginBeans;
import com.yc.redkingguess.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.redkingguess.homeModule.module.bean.OtherBeans;
import com.yc.redkingguess.homeModule.module.bean.SignBeans;
import com.yc.redkingguess.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redkingguess.homeModule.module.bean.VipTaskInfHomeBeans;
import com.yc.redkingguess.utils.UpDataVersion;

import java.util.List;

public interface MainContact {
    interface View extends BaseView {

        void getHomeDataSuccess(HomeAllBeans data);

        void getOtherInfoSuccess(OtherBeans data);

        void getHomeMessageRedDataInfo(List<HomeRedMessage> data);

        void getRedEvenlopsInfoSuccess(OpenRedEvenlopes data);

        void getMsgListSuccess(List<HomeMsgBeans> data);

        void getHomeMsgDataPollingSuccess(List<Info0Bean> data);

        void getMoneyRedSuccess(HomeGetRedMoneyBeans data);

        void updtreasureSuccess(UpQuanNumsBeans data);

        void getonLineRedSuccess(HomeOnlineBeans data);

        void upVersionSuccess(UpDataVersion data);

        void getMsgListTwoSuccess(List<HomeMsgBeans> data);

        void getMsgListTwoError();

        void getSignSuccess(SignBeans data);

        void getNewsLoginHbSuccess(NewsLoginBeans data);

        void getFirstWithDrawMoneySuccess(NewsLoginBeans data);

        void getHomeDataError();

        void showVipTaskInfo(VipTaskInfHomeBeans data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
