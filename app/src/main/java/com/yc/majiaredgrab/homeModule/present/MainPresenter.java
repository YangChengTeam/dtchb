package com.yc.majiaredgrab.homeModule.present;

import android.text.TextUtils;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.majiaredgrab.homeModule.contact.MainContact;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;
import com.yc.majiaredgrab.homeModule.module.bean.EmptyBeans;
import com.yc.majiaredgrab.homeModule.module.bean.HomeAllBeans;
import com.yc.majiaredgrab.homeModule.module.bean.HomeGetRedMoneyBeans;
import com.yc.majiaredgrab.homeModule.module.bean.HomeMsgBeans;
import com.yc.majiaredgrab.homeModule.module.bean.HomeOnlineBeans;
import com.yc.majiaredgrab.homeModule.module.bean.HomeRedMessage;
import com.yc.majiaredgrab.homeModule.module.bean.Info0Bean;
import com.yc.majiaredgrab.homeModule.module.bean.NewsLoginBeans;
import com.yc.majiaredgrab.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.majiaredgrab.homeModule.module.bean.OtherBeans;
import com.yc.majiaredgrab.homeModule.module.bean.SignBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UserInfo;
import com.yc.majiaredgrab.homeModule.module.bean.VipTaskInfHomeBeans;
import com.yc.majiaredgrab.utils.CacheDataUtils;
import com.yc.majiaredgrab.utils.TimesUtils;
import com.yc.majiaredgrab.utils.UpDataVersion;


import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class MainPresenter extends RxPresenter<MainContact.View> implements MainContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public MainPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getHomeData(String groupId) {
        showWaiteDialog();
        addSubscribe(apis.getHomeData(groupId, CacheDataUtils.getInstance().getUserInfo().getImei())
                .compose(RxUtil.<HttpResult<HomeAllBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HomeAllBeans>(this) {
                    @Override
                    public void onAnalysisNext(HomeAllBeans data) {
                        mView.getHomeDataSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        mView.getHomeDataError();
                    }
                }));
    }

    public void getOtherInfo(String group_id, String user_id) {
        showWaiteDialog();
        addSubscribe(apis.getOtherInfo(group_id,user_id,CacheDataUtils.getInstance().getUserInfo().getImei())
                .compose(RxUtil.<HttpResult<OtherBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<OtherBeans>(this) {
                    @Override
                    public void onAnalysisNext(OtherBeans data) {
                        mView.getOtherInfoSuccess(data);
                    }
                }));
    }

    public void getHomeMessageRedData(String group_id, String hongbao_id) {
        addSubscribe(apis.getHomeMessageRedDataInfo(group_id,hongbao_id,CacheDataUtils.getInstance().getUserInfo().getImei())
                .compose(RxUtil.<HttpResult<List<HomeRedMessage>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<HomeRedMessage>>(this) {
                    @Override
                    public void onAnalysisNext(List<HomeRedMessage> data) {
                        mView.getHomeMessageRedDataInfo(data);
                    }
                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                    }
                }));

    }

    public void getRedEvenlopsInfo(String group_id, String hongbao_id) {
        showWaiteDialog();
        addSubscribe(apis.getRedEvenlopsInfo(group_id,hongbao_id)
                .compose(RxUtil.<HttpResult<OpenRedEvenlopes>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<OpenRedEvenlopes>(this) {
                    @Override
                    public void onAnalysisNext(OpenRedEvenlopes data) {
                        mView.getRedEvenlopsInfoSuccess(data);
                    }
                }));
    }

    public void getMsgList(String group_id, int page, String pagesize) {
        addSubscribe(apis.getMsgList(group_id,page,pagesize)
                .compose(RxUtil.<HttpResult<List<HomeMsgBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<HomeMsgBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<HomeMsgBeans> data) {
                        mView.getMsgListSuccess(data);
                    }
                }));
    }
    public void getMsgListTwo(String group_id, int page, String pagesize) {
        addSubscribe(apis.getMsgList(group_id,page,pagesize)
                .compose(RxUtil.<HttpResult<List<HomeMsgBeans>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<HomeMsgBeans>>(this) {
                    @Override
                    public void onAnalysisNext(List<HomeMsgBeans> data) {
                        mView.getMsgListTwoSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                        mView.getMsgListTwoError();
                    }
                }));
    }


    public void getHomeMsgDataPolling(String group_id, String msgId) {
        addSubscribe(apis.getHomeMsgDataPolling(group_id,msgId)
                .compose(RxUtil.<HttpResult<List<Info0Bean>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<Info0Bean>>(this) {
                    @Override
                    public void onAnalysisNext(List<Info0Bean> data) {
                        mView.getHomeMsgDataPollingSuccess(data);
                    }
                }));
    }

    public void getMoneyRed(String group_id, String hongbao_id) {
        addSubscribe(apis.getMoneyRed(group_id,hongbao_id)
                .compose(RxUtil.<HttpResult<HomeGetRedMoneyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HomeGetRedMoneyBeans>(this) {
                    @Override
                    public void onAnalysisNext(HomeGetRedMoneyBeans data) {
                        mView.getMoneyRedSuccess(data);
                    }
                }));
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

    public void getonLineRed(String group_id, String on_money) {
        addSubscribe(apis.getonLineRed(group_id,on_money)
                .compose(RxUtil.<HttpResult<HomeOnlineBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HomeOnlineBeans>(this) {
                    @Override
                    public void onAnalysisNext(HomeOnlineBeans data) {
                        mView.getonLineRedSuccess(data);
                    }
                }));

    }

    public void upVersion(String agentId) {
        showWaiteDialog();
        addSubscribe(apis.upVersion(agentId)
                .compose(RxUtil.<HttpResult<UpDataVersion>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpDataVersion>(this) {
                    @Override
                    public void onAnalysisNext(UpDataVersion data) {
                        mView.upVersionSuccess(data);
                    }
                }));
    }

    public void getSign(int id, String signId) {
        showWaiteDialog();
        addSubscribe(apis.getSign(String.valueOf(id),signId)
                .compose(RxUtil.<HttpResult<SignBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<SignBeans>(this) {
                    @Override
                    public void onAnalysisNext(SignBeans data) {
                        mView.getSignSuccess(data);
                    }
                }));
    }

    public void getRegUserLog(int id, String type,String zaixianType) {
        showWaiteDialog();
        addSubscribe(apis.getRegUserLog(String.valueOf(id),type)
                .compose(RxUtil.<HttpResult<EmptyBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<EmptyBeans>(this) {
                    @Override
                    public void onAnalysisNext(EmptyBeans data) {
                        if (!TextUtils.isEmpty(zaixianType)&&"1".equals(zaixianType)){
                            CacheDataUtils.getInstance().setHbZaiXian();
                        }
                    }
                }));
    }

    public void getNewsLoginHb(String imei, int group_id) {
        showWaiteDialog();
        addSubscribe(apis.getNewsLoginHb(imei,String.valueOf(group_id))
                .compose(RxUtil.<HttpResult<NewsLoginBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<NewsLoginBeans>(this) {
                    @Override
                    public void onAnalysisNext(NewsLoginBeans data) {
                         mView.getNewsLoginHbSuccess(data);
                    }
                }));
    }

    public void getFirstWithDrawMoney(String imei, int group_id) {
        showWaiteDialog();
        addSubscribe(apis.getFirstWithDrawMoney(imei,String.valueOf(group_id))
                .compose(RxUtil.<HttpResult<NewsLoginBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<NewsLoginBeans>(this) {
                    @Override
                    public void onAnalysisNext(NewsLoginBeans data) {
                        mView.getFirstWithDrawMoneySuccess(data);
                    }
                }));
    }

        public void login(int app_type, String wx_openid, String qq_openid,
                      String age, String nickname, int sex, String face,String agent_id,String imei,String imie2,String macAddress,String oid,String model) {
            addSubscribe(apis.login(app_type, wx_openid, qq_openid, age, nickname, sex, face,agent_id, imei,oid,macAddress,imie2,model)
                .compose(RxUtil.rxSchedulerHelper()).subscribeWith(new ResultSubscriber<UserInfo>(this) {
                    @Override
                    public void onAnalysisNext(UserInfo data) {
                        long l = System.currentTimeMillis();
                        String strTimessssss = TimesUtils.getStrTimessssss(l);
                        if (!TextUtils.isEmpty(strTimessssss)){
                            CacheDataUtils.getInstance().setLoginTimes(strTimessssss);
                        }
                    }
                }));
    }


    public void getUserTaskInfo(int groupId) {
        addSubscribe(apis.getUserTaskInfo(groupId).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<VipTaskInfHomeBeans>(this) {
                    @Override
                    public void onAnalysisNext(VipTaskInfHomeBeans data) {
                        mView.showVipTaskInfo(data);
                    }
                }));
    }



}
