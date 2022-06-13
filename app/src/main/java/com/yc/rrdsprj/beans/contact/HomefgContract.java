package com.yc.rrdsprj.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.rrdsprj.beans.module.beans.GameInfoBeans;
import com.yc.rrdsprj.beans.module.beans.GamedolaBeans;
import com.yc.rrdsprj.beans.module.beans.NesRedBeans;
import com.yc.rrdsprj.beans.module.beans.SavaMonyeHotBeans;
import com.yc.rrdsprj.beans.module.beans.SaveMoneysInfo;

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
