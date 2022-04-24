package com.yc.wxchb.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.wxchb.beans.module.beans.GameInfoBeans;
import com.yc.wxchb.beans.module.beans.GamedolaBeans;
import com.yc.wxchb.beans.module.beans.NesRedBeans;
import com.yc.wxchb.beans.module.beans.SavaMonyeHotBeans;
import com.yc.wxchb.beans.module.beans.SaveMoneysInfo;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface HomefgContract {
    interface View extends BaseView {


        void getHomSaveMoneySuccess(SavaMonyeHotBeans data);

        void getSaveMoneyInfosSuccess(SaveMoneysInfo data);

        void getNewRedSuccess(NesRedBeans data);

        void getGameloadInfoSuccess(GamedolaBeans data);

        void gameloadAddSuccess(GameInfoBeans data);

        void getGamehotSuccess(GameInfoBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
