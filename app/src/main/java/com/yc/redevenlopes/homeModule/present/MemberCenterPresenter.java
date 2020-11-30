package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.MemberCenterContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;
import com.yc.redevenlopes.homeModule.personModule.PersonApiModule;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class MemberCenterPresenter extends RxPresenter<MemberCenterContact.View> implements MemberCenterContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public MemberCenterPresenter(HomeApiModule apis) {
        this.apis = apis;
    }

    public void getOtherInfo(String group_id, String user_id) {
        showWaiteDialog();
        addSubscribe(apis.getOtherInfo(group_id,user_id)
                .compose(RxUtil.<HttpResult<OtherBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<OtherBeans>(this) {
                    @Override
                    public void onAnalysisNext(OtherBeans data) {
                        mView.getOtherInfoSuccess(data);
                    }
                }));
    }

}
