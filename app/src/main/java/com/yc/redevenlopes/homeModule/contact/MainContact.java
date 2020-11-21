package com.yc.redevenlopes.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeRedMessage;
import com.yc.redevenlopes.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;

import java.util.List;

public interface MainContact {
    interface View extends BaseView {

        void getHomeDataSuccess(HomeAllBeans data);

        void getOtherInfoSuccess(OtherBeans data);

        void getHomeMessageRedDataInfo(List<HomeRedMessage> data);

        void getRedEvenlopsInfoSuccess(OpenRedEvenlopes data);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
