package com.yc.jsdsp.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.jsdsp.beans.module.beans.HotNumsInfoBeans;
import com.yc.jsdsp.beans.module.beans.RedTaskBeans;
import com.yc.jsdsp.beans.module.beans.TaskLineBean;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface VideoContract {
    interface View extends BaseView {


        void getRedTaskDataSuccess(RedTaskBeans data);

        void getTaskLineSuccess(TaskLineBean data);


        void getHotInfoSuccess(HotNumsInfoBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
