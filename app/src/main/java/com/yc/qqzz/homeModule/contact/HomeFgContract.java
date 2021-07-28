package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.bean.GetHomeLineRedBeans;
import com.yc.qqzz.homeModule.bean.HomeNewHbBeans;
import com.yc.qqzz.homeModule.bean.SignBeans;
import com.yc.qqzz.homeModule.module.bean.HomeAllBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeGetRedMoneyBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeMsgBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeOnlineBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeRedMessagezq;
import com.yc.qqzz.homeModule.module.bean.Info0Beanzq;
import com.yc.qqzz.homeModule.module.bean.OpenRedEvenlopeszq;
import com.yc.qqzz.homeModule.module.bean.OtherBeanszq;
import com.yc.qqzz.homeModule.module.bean.UpQuanNumsBeanszq;
import com.yc.qqzz.utils.UpDataVersion;

import java.util.List;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface HomeFgContract {
    interface View extends BaseView {

        void getHomeDataSuccess(HomeAllBeanszq data);

        void getOtherInfoSuccess(OtherBeanszq data);

        void getHomeMessageRedDataInfo(List<HomeRedMessagezq> data);

        void getRedEvenlopsInfoSuccess(OpenRedEvenlopeszq data);

        void getMsgListSuccess(List<HomeMsgBeanszq> data);

        void getHomeMsgDataPollingSuccess(List<Info0Beanzq> data);

        void getMoneyRedSuccess(HomeGetRedMoneyBeanszq data);

        void updtreasureSuccess(UpQuanNumsBeanszq data);

        void getonLineRedSuccess(HomeOnlineBeanszq data);

        void upVersionSuccess(UpDataVersion data);

        void getMsgListTwoSuccess(List<HomeMsgBeanszq> data);

        void getMsgListTwoError();

        void getSignSuccess(SignBeans data);


        void getHomeDataError();

        void getNewHbSuccess(HomeNewHbBeans data);

        void gethbonlineSuccess(GetHomeLineRedBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
