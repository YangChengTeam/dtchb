package com.yc.qqzz.homeModule.present;


import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.CashTaskContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class CashTaskPresenter extends RxPresenter<CashTaskContract.View> implements CashTaskContract.Presenter {

    private HomeApiModule apis;

    @Inject
    public CashTaskPresenter(HomeApiModule apis) {
        this.apis = apis;
    }



}
