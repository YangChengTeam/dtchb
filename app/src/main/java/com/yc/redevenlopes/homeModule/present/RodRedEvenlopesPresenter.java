package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.contact.RodRedEvenlopesContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;
import com.yc.redevenlopes.homeModule.module.bean.RedDetailsBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class RodRedEvenlopesPresenter extends RxPresenter<RodRedEvenlopesContact.View> implements RodRedEvenlopesContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public RodRedEvenlopesPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getRedEvenlopesDetails(String group_id,String id) {
        showWaiteDialog();
        addSubscribe(apis.getRedEvenlopesDetails(group_id,id)
                .compose(RxUtil.<HttpResult<RedDetailsBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<RedDetailsBeans>(this) {
                    @Override
                    public void onAnalysisNext(RedDetailsBeans data) {
                        mView.getRedEvenlopesDetailsSuccess(data);
                    }
                }));
    }
}
