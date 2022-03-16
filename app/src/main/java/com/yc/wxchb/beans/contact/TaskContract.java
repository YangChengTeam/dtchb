package com.yc.wxchb.beans.contact;

import com.lq.lianjibusiness.base_libary.ui.base.BasePresenter;
import com.lq.lianjibusiness.base_libary.ui.base.BaseView;
import com.yc.wxchb.beans.module.beans.LimitedBeans;
import com.yc.wxchb.beans.module.beans.LimitedRedBeans;
import com.yc.wxchb.beans.module.beans.RedTaskBeans;
import com.yc.wxchb.beans.module.beans.TaskLineBean;

import java.util.List;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public interface TaskContract {
    interface View extends BaseView {

        void getLimitedDataSuccess(List<LimitedBeans> data);

        void getRedTaskDataSuccess(RedTaskBeans data);

        void getLimiteRedSuccess(LimitedRedBeans data);

        void getTaskLineSuccess(TaskLineBean data);

        void getTaskMoneySuccess(TaskLineBean data);
    }

    interface Presenter extends BasePresenter<View> {
    }
}
