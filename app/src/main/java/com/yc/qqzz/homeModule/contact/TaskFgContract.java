package com.yc.qqzz.homeModule.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.qqzz.homeModule.bean.TaskBeans;
import com.yc.qqzz.homeModule.module.bean.TaskFgPrizeBeans;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface TaskFgContract {
    interface View extends BaseView {
        void getTaskinfoSuccess(TaskBeans data);

        void getLevelprizeSuccess(TaskFgPrizeBeans data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
