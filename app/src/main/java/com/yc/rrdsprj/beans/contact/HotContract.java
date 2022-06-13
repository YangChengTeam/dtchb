package com.yc.rrdsprj.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.rrdsprj.beans.module.beans.HotWithDrawBeans;
import com.yc.rrdsprj.beans.module.beans.MoneysBeans;
import com.yc.rrdsprj.beans.module.beans.QuesTionsHotBeans;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface HotContract {
    interface View extends BaseView {


        void getHotIndexSuccess(HotWithDrawBeans data);

        void hottixianSuccess(MoneysBeans data);

        void getQuestionHotSuccess(QuesTionsHotBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
