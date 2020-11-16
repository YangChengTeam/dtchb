package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureRuleContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class SnatchTreasureRulePresenter extends RxPresenter<SnatchTreasureRuleContact.View> implements SnatchTreasureRuleContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public SnatchTreasureRulePresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
