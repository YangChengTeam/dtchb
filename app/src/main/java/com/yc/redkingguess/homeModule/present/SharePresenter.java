package com.yc.redkingguess.homeModule.present;


import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redkingguess.homeModule.contact.ShareContact;
import com.yc.redkingguess.homeModule.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class SharePresenter extends RxPresenter<ShareContact.View> implements ShareContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public SharePresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
