package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.bean.DayUpgradeDayLeveAddBeans;
import com.yc.qqzz.homeModule.bean.UpgradeTaskitemBeans;
import com.yc.qqzz.homeModule.module.bean.DayUpgradeDayCashFinshBeans;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface DayUpgradeContract {
    interface View extends BaseView {


        void getDayUpLeletSuccess(UpgradeTaskitemBeans data);

        void getDayCashSuccess(UpgradeTaskitemBeans data);

        void getDayleveltaskaddSuccess(DayUpgradeDayLeveAddBeans data);

        void getDaycashtaskaddSuccess(DayUpgradeDayLeveAddBeans data);

        void getDaycashfinishSuccess(DayUpgradeDayCashFinshBeans data);

        void getDaylevelfinishSuccess(DayUpgradeDayCashFinshBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
