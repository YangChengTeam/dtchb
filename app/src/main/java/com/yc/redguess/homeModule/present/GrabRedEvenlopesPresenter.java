package com.yc.redguess.homeModule.present;


import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.redguess.homeModule.contact.GrabRedEvenlopesContact;
import com.yc.redguess.homeModule.module.HomeApiModule;
import com.yc.redguess.homeModule.module.bean.EmptyBeans;
import com.yc.redguess.homeModule.module.bean.GoToSignBeans;
import com.yc.redguess.homeModule.module.bean.LookVideoBeans;
import com.yc.redguess.homeModule.module.bean.LookVideoMoneyBeans;
import com.yc.redguess.homeModule.module.bean.SeekBeans;
import com.yc.redguess.homeModule.module.bean.SeekRedMoneyBean;
import com.yc.redguess.homeModule.module.bean.SignInfoBeans;
import com.yc.redguess.homeModule.module.bean.UpFindRedBeans;
import com.yc.redguess.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redguess.utils.CacheDataUtils;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class GrabRedEvenlopesPresenter extends RxPresenter<GrabRedEvenlopesContact.View> implements GrabRedEvenlopesContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public GrabRedEvenlopesPresenter(HomeApiModule apis) {
        this.apis = apis;
    }

    public void updtreasure(String group_id) {
        addSubscribe(apis.updtreasure(group_id)
                .compose(RxUtil.<HttpResult<UpQuanNumsBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpQuanNumsBeans>(this) {
                    @Override
                    public void onAnalysisNext(UpQuanNumsBeans data) {
                        mView.updtreasureSuccess(data);
                    }
                }));
    }

    public void getlookVideo(String imei, String group_id) {
        showWaiteDialog();
        addSubscribe(apis.getlookVideo(imei,group_id)
                .compose(RxUtil.<HttpResult<LookVideoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<LookVideoBeans>(this) {
                    @Override
                    public void onAnalysisNext(LookVideoBeans data) {
                        mView.getlookVideoSuccess(data);
                    }
                }));
    }

    public void getlookVideoRedMoney(String imei, int group_id, String is_double, String info_id, String money) {
        addSubscribe(apis.getlookVideoRedMoney(imei,String.valueOf(group_id),is_double,info_id,money)
                .compose(RxUtil.<HttpResult<LookVideoMoneyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<LookVideoMoneyBeans>(this) {
                    @Override
                    public void onAnalysisNext(LookVideoMoneyBeans data) {
                        mView.getlookVideoRedMoneySuccess(data);
                    }
                }));
    }

    public void getSeekRed(String imei, String group_id) {
        showWaiteDialog();
        addSubscribe(apis.getSeekRed(imei,group_id)
                .compose(RxUtil.<HttpResult<SeekBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SeekBeans>(this) {
                    @Override
                    public void onAnalysisNext(SeekBeans data) {
                        mView.getSeekRedSuccess(data);
                    }
                }));
    }

    public void getSeekGetRedMoney(String imei, String group_id, String is_double, String info_id, String money) {
        addSubscribe(apis.getSeekGetRedMoney(imei,group_id,is_double,info_id,money)
                .compose(RxUtil.<HttpResult<SeekRedMoneyBean>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SeekRedMoneyBean>(this) {
                    @Override
                    public void onAnalysisNext(SeekRedMoneyBean data) {
                        mView.getSeekGetRedMoneySuccess(data);
                    }
                }));
    }

    public void getUpFindRed(String imei, String group_id,String type) {
        addSubscribe(apis.getUpFindRed(imei,group_id,type)
                .compose(RxUtil.<HttpResult<UpFindRedBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpFindRedBeans>(this) {
                    @Override
                    public void onAnalysisNext(UpFindRedBeans data) {
                        mView.getUpFindRedSuccess(data);
                    }
                }));
    }

    public void getSignInfo(String imei, String group_id) {
        showWaiteDialog();
        addSubscribe(apis.getSignInfo(imei,group_id)
                .compose(RxUtil.<HttpResult<SignInfoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SignInfoBeans>(this) {
                    @Override
                    public void onAnalysisNext(SignInfoBeans data) {
                        mView.getSignInfoSuccess(data);
                    }
                }));
    }

    public void sign(String imei, String group_id) {
        showWaiteDialog();
        addSubscribe(apis.sign(imei,group_id)
                .compose(RxUtil.<HttpResult<GoToSignBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<GoToSignBeans>(this) {
                    @Override
                    public void onAnalysisNext(GoToSignBeans data) {
                        ToastUtil.showToast("签到成功");
                        mView.signSuccess(data);
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
                        CacheDataUtils.getInstance().setQhb();
                    }
                }));
    }

}