package com.yc.redguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redguess.homeModule.contact.SnatchTreasureHistoryContact;
import com.yc.redguess.homeModule.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class SnatchTreasureHistoryPresenter extends RxPresenter<SnatchTreasureHistoryContact.View> implements SnatchTreasureHistoryContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public SnatchTreasureHistoryPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
