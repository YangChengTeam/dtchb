package com.yc.qqzz.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.contact.RodRedEvenlopesContact;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.EmptyBeans;
import com.yc.qqzz.homeModule.module.bean.RedDetailsBeans;

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


    public void getRegUserLog(int id, String type) {
        showWaiteDialog();
        addSubscribe(apis.getRegUserLog(String.valueOf(id),type)
                .compose(RxUtil.<HttpResult<EmptyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<EmptyBeans>(this) {
                    @Override
                    public void onAnalysisNext(EmptyBeans data) {
                       mView.getRegUserLogSuccess(data);
                    }
                }));
    }

}
