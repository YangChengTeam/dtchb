package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.bean.DayUpgradeDayLeveAddBeans;
import com.yc.qqzz.homeModule.bean.UpgradeTaskitemBeans;
import com.yc.qqzz.homeModule.contact.DayUpgradeContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.DayUpgradeDayCashFinshBeans;
import com.yc.qqzz.homeModule.module.bean.OtherBeanszq;
import com.yc.qqzz.utils.CacheDataUtils;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class DayUpgradePresenter extends RxPresenter<DayUpgradeContract.View> implements DayUpgradeContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public DayUpgradePresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

    public void getDayUpLelet(int userId) {
        showWaiteDialog();
        addSubscribe(apiModule.getDayUpLelet(String.valueOf(userId))
                .compose(RxUtil.<HttpResult<UpgradeTaskitemBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpgradeTaskitemBeans>(this) {
                    @Override
                    public void onAnalysisNext(UpgradeTaskitemBeans data) {
                        mView.getDayUpLeletSuccess(data);
                    }
                }));

    }


    public void getDayCash(int userId) {
        showWaiteDialog();
        addSubscribe(apiModule.getDayCash(String.valueOf(userId))
                .compose(RxUtil.<HttpResult<UpgradeTaskitemBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpgradeTaskitemBeans>(this) {
                    @Override
                    public void onAnalysisNext(UpgradeTaskitemBeans data) {
                        mView.getDayCashSuccess(data);
                    }
                }));
    }

    public void getDayleveltaskadd(int id, int taskPositionId, String is_before) {
        showWaiteDialog();
        addSubscribe(apiModule.getDayleveltaskadd(String.valueOf(id),String.valueOf(taskPositionId),is_before)
                .compose(RxUtil.<HttpResult<DayUpgradeDayLeveAddBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<DayUpgradeDayLeveAddBeans>(this) {
                    @Override
                    public void onAnalysisNext(DayUpgradeDayLeveAddBeans data) {
                        mView.getDayleveltaskaddSuccess(data);
                    }
                }));
    }

    public void getDaycashtaskadd(int id, int taskPositionId, String is_before) {
        showWaiteDialog();
        addSubscribe(apiModule.getDaycashtaskadd(String.valueOf(id),String.valueOf(taskPositionId),is_before)
                .compose(RxUtil.<HttpResult<DayUpgradeDayLeveAddBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<DayUpgradeDayLeveAddBeans>(this) {
                    @Override
                    public void onAnalysisNext(DayUpgradeDayLeveAddBeans data) {
                        mView.getDaycashtaskaddSuccess(data);
                    }
                }));
    }

    public void getDaycashfinish(int id) {
        showWaiteDialog();
        addSubscribe(apiModule.getDaycashfinish(String.valueOf(id))
                .compose(RxUtil.<HttpResult<DayUpgradeDayCashFinshBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<DayUpgradeDayCashFinshBeans>(this) {
                    @Override
                    public void onAnalysisNext(DayUpgradeDayCashFinshBeans data) {
                        mView.getDaycashfinishSuccess(data);
                    }
                }));

    }

    public void getDaylevelfinish(int id) {
        showWaiteDialog();
        addSubscribe(apiModule.getDaylevelfinish(String.valueOf(id))
                .compose(RxUtil.<HttpResult<DayUpgradeDayCashFinshBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<DayUpgradeDayCashFinshBeans>(this) {
                    @Override
                    public void onAnalysisNext(DayUpgradeDayCashFinshBeans data) {
                        mView.getDaylevelfinishSuccess(data);
                    }
                }));
    }
}
