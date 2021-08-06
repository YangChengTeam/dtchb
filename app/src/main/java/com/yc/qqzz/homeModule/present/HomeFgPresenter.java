package com.yc.qqzz.homeModule.present;

import android.text.TextUtils;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.qqzz.homeModule.bean.GetHomeLineRedBeans;
import com.yc.qqzz.homeModule.bean.HomeNewHbBeans;
import com.yc.qqzz.homeModule.bean.SignBeans;
import com.yc.qqzz.homeModule.contact.HomeFgContract;
import com.yc.qqzz.homeModule.module.HomeApiModule;
import com.yc.qqzz.homeModule.module.bean.EmptyBeans;
import com.yc.qqzz.homeModule.module.bean.HomeAllBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeGetRedMoneyBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeMsgBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeOnlineBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeRedMessagezq;
import com.yc.qqzz.homeModule.module.bean.Info0Beanzq;
import com.yc.qqzz.homeModule.module.bean.OpenRedEvenlopeszq;
import com.yc.qqzz.homeModule.module.bean.OtherBeanszq;
import com.yc.qqzz.homeModule.module.bean.UpQuanNumsBeanszq;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.utils.CacheDataUtils;
import com.yc.qqzz.utils.TimesUtils;
import com.yc.qqzz.utils.UpDataVersion;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class HomeFgPresenter extends RxPresenter<HomeFgContract.View> implements HomeFgContract.Presenter {

    private HomeApiModule apis;

    @Inject
    public HomeFgPresenter(HomeApiModule apiModule) {
        this.apis = apiModule;
    }

    public void getHomeData(String groupId) {
        showWaiteDialog();
        addSubscribe(apis.getHomeData(groupId, CacheDataUtils.getInstance().getUserInfo().getImei())
                .compose(RxUtil.<HttpResult<HomeAllBeanszq>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HomeAllBeanszq>(this) {
                    @Override
                    public void onAnalysisNext(HomeAllBeanszq data) {
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
        addSubscribe(apis.getOtherInfo(group_id,user_id, CacheDataUtils.getInstance().getUserInfo().getImei())
                .compose(RxUtil.<HttpResult<OtherBeanszq>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<OtherBeanszq>(this) {
                    @Override
                    public void onAnalysisNext(OtherBeanszq data) {
                        mView.getOtherInfoSuccess(data);
                    }
                }));
    }

    public void getHomeMessageRedData(String group_id, String hongbao_id) {
        addSubscribe(apis.getHomeMessageRedDataInfo(group_id,hongbao_id, CacheDataUtils.getInstance().getUserInfo().getImei())
                .compose(RxUtil.<HttpResult<List<HomeRedMessagezq>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<HomeRedMessagezq>>(this) {
                    @Override
                    public void onAnalysisNext(List<HomeRedMessagezq> data) {
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
                .compose(RxUtil.<HttpResult<OpenRedEvenlopeszq>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<OpenRedEvenlopeszq>(this) {
                    @Override
                    public void onAnalysisNext(OpenRedEvenlopeszq data) {
                        mView.getRedEvenlopsInfoSuccess(data);
                    }
                }));
    }

    public void getMsgList(String group_id, int page, String pagesize) {
        addSubscribe(apis.getMsgList(group_id,page,pagesize)
                .compose(RxUtil.<HttpResult<List<HomeMsgBeanszq>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<HomeMsgBeanszq>>(this) {
                    @Override
                    public void onAnalysisNext(List<HomeMsgBeanszq> data) {
                        mView.getMsgListSuccess(data);
                    }
                }));
    }
    public void getMsgListTwo(String group_id, int page, String pagesize) {
        addSubscribe(apis.getMsgList(group_id,page,pagesize)
                .compose(RxUtil.<HttpResult<List<HomeMsgBeanszq>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<HomeMsgBeanszq>>(this) {
                    @Override
                    public void onAnalysisNext(List<HomeMsgBeanszq> data) {
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
                .compose(RxUtil.<HttpResult<List<Info0Beanzq>>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<List<Info0Beanzq>>(this) {
                    @Override
                    public void onAnalysisNext(List<Info0Beanzq> data) {
                        mView.getHomeMsgDataPollingSuccess(data);
                    }
                }));
    }

    public void getMoneyRed(String group_id, String hongbao_id) {
        addSubscribe(apis.getMoneyRed(group_id,hongbao_id)
                .compose(RxUtil.<HttpResult<HomeGetRedMoneyBeanszq>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HomeGetRedMoneyBeanszq>(this) {
                    @Override
                    public void onAnalysisNext(HomeGetRedMoneyBeanszq data) {
                        mView.getMoneyRedSuccess(data);
                    }
                }));
    }

    public void updtreasure(String group_id) {
        addSubscribe(apis.updtreasure(group_id)
                .compose(RxUtil.<HttpResult<UpQuanNumsBeanszq>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpQuanNumsBeanszq>(this) {
                    @Override
                    public void onAnalysisNext(UpQuanNumsBeanszq data) {
                        mView.updtreasureSuccess(data);
                    }
                }));
    }

    public void getonLineRed(String group_id, String on_money) {
        addSubscribe(apis.getonLineRed(group_id,on_money)
                .compose(RxUtil.<HttpResult<HomeOnlineBeanszq>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<HomeOnlineBeanszq>(this) {
                    @Override
                    public void onAnalysisNext(HomeOnlineBeanszq data) {
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


    public void login(int app_type, String wx_openid, String qq_openid,
                      String age, String nickname, int sex, String face,String agent_id,String imei,String imie2,String macAddress,String oid,String model) {
        addSubscribe(apis.login(app_type, wx_openid, qq_openid, age, nickname, sex, face,agent_id, imei,oid,macAddress,imie2,model)
                .compose(RxUtil.rxSchedulerHelper()).subscribeWith(new ResultSubscriber<UserInfozq>(this) {
                    @Override
                    public void onAnalysisNext(UserInfozq data) {
                        long l = System.currentTimeMillis();
                        String strTimessssss = TimesUtils.getStrTimessssss(l);
                        if (!TextUtils.isEmpty(strTimessssss)){
                            CacheDataUtils.getInstance().setLoginTimes(strTimessssss);
                        }
                    }
                }));
    }

    public void getUserTaskInfo(int group_id) {

    }

    public void getNewHb(String imei, int group_id) {
        addSubscribe(apis.getNewHb(imei, String.valueOf(group_id))
                .compose(RxUtil.rxSchedulerHelper()).subscribeWith(new ResultSubscriber<HomeNewHbBeans>(this) {
                    @Override
                    public void onAnalysisNext(HomeNewHbBeans data) {
                        mView.getNewHbSuccess(data);
                    }
                }));
    }

    public void gethbonline(String imei, int group_id) {
        addSubscribe(apis.gethbonline(imei, String.valueOf(group_id))
                .compose(RxUtil.rxSchedulerHelper()).subscribeWith(new ResultSubscriber<GetHomeLineRedBeans>(this) {
                    @Override
                    public void onAnalysisNext(GetHomeLineRedBeans data) {
                        mView.gethbonlineSuccess(data);
                    }
                }));
    }
}
