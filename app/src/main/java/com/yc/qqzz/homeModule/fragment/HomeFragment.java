package com.yc.qqzz.homeModule.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.DynamicTimeFormat;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.qqzz.R;
import com.yc.qqzz.application.MyApplication;
import com.yc.qqzz.base.BaseLazyFragment;
import com.yc.qqzz.constants.Constant;
import com.yc.qqzz.dialog.RedDialogTwo;
import com.yc.qqzz.dialog.SignDialog;
import com.yc.qqzz.dialog.UpdateDialog;
import com.yc.qqzz.homeModule.adapter.HomeAdapter;
import com.yc.qqzz.homeModule.bean.SignBeans;
import com.yc.qqzz.homeModule.contact.HomeFgContract;
import com.yc.qqzz.homeModule.module.bean.HomeAllBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeGetRedMoneyBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeMsgBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeOnlineBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeRedMessagezq;
import com.yc.qqzz.homeModule.module.bean.Info0Beanzq;
import com.yc.qqzz.homeModule.module.bean.Info1Beanzq;
import com.yc.qqzz.homeModule.module.bean.OpenRedEvenlopeszq;
import com.yc.qqzz.homeModule.module.bean.OtherBeanszq;
import com.yc.qqzz.homeModule.module.bean.UpQuanNumsBeanszq;
import com.yc.qqzz.homeModule.module.bean.UpgradeInfozq;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.homeModule.present.HomeFgPresenter;
import com.yc.qqzz.service.event.Event;
import com.yc.qqzz.utils.AppSettingUtils;
import com.yc.qqzz.utils.CacheDataUtils;
import com.yc.qqzz.utils.ClickListenNameTwo;
import com.yc.qqzz.utils.CommonUtils;
import com.yc.qqzz.utils.CountDownUtilsThree;
import com.yc.qqzz.utils.DisplayUtil;
import com.yc.qqzz.utils.RomUtil;
import com.yc.qqzz.utils.SoundPoolUtils;
import com.yc.qqzz.utils.TimesUtils;
import com.yc.qqzz.utils.UpDataVersion;
import com.yc.qqzz.utils.VUiKit;
import com.yc.qqzz.widget.BCRefreshHeader;
import com.yc.qqzz.widget.DividerItemLastDecorations;
import com.yc.qqzz.widget.SimpleComponent;
import com.yc.qqzz.widget.gu.Guide;
import com.yc.qqzz.widget.gu.GuideBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends BaseLazyFragment<HomeFgPresenter> implements HomeFgContract.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.line_lay)
    LinearLayout lineLay;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_titles)
    TextView tvTitle;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;

    @BindView(R.id.tv_redTimes)
    TextView tvRedTimes;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    @BindView(R.id.iv_shouzaixian)
    ImageView ivShouzaixian;

    private HomeAdapter homeAdapter;
    private OtherBeanszq otherBeanszq;
    private String hongbao_id;
    private String redTypeName;
    private int page = 1;
    private String msgId;
    private String balanceMoney;
    private boolean isOnclick;
    private CountDownUtilsThree countDownUtilsThree;
    private String on_money;
    private String cashMoney;
    private LinearLayoutManager linearLayoutManager;
    private String jumpRedEvenlopesId;

    private int redOnclickType;
    private int redOnclickIndex;
    private String tongjiStr;
    private String status;
    private String signId;
    private int newLoginStatus;
    private boolean isFirst;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = (View) super.onCreateView(inflater, container, savedInstanceState);
        return (View) rootView;
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initLazyData() {
        isFirst=true;
        loadInsertView(null);
        EventBus.getDefault().register(this);
        initViews();
        initRecyclerView();
        loadTx();
        initData();
        status = "0";
        logins();
    }


    private void initViews() {
        UserInfozq userInfozq = CacheDataUtils.getInstance().getUserInfo();
        tvTitle.setText("红包" + userInfozq.getGroup_id() + "群");

        if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getUserInfo().getFace())) {
            Glide.with(this)
                    .load(CacheDataUtils.getInstance().getUserInfo().getFace())
                    .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                    .into(ivAvatar);
        }

        countDownUtilsThree = new CountDownUtilsThree();
        countDownUtilsThree.setOnCountDownListen(new CountDownUtilsThree.OnCountDownListen() {
            @Override
            public void count(long mMin, long mSecond) {
                tvRedTimes.setText(getTv(mMin) + ":" + getTv(mSecond));
            }

            @Override
            public void countFinsh() {
                isOnclick = true;
                tvRedTimes.setText("在线红包");
            }
        });
    }

    private void initData() {
        if (CacheDataUtils.getInstance().isLogin()) {
            //  mPresenter.upVersion(((MyApplication) MyApplication.getInstance()).getAgentId());
            UserInfozq userInfozq = CacheDataUtils.getInstance().getUserInfo();
            mPresenter.getHomeData(userInfozq.getGroup_id() + "");
            mPresenter.getOtherInfo(userInfozq.getGroup_id() + "", userInfozq.getId() + "");

        }
    }

    private void initRecyclerView() {
        srlRefresh.setEnableAutoLoadMore(false);//开启自动加载功能（非必须）
        srlRefresh.setEnableLoadMore(false);
        srlRefresh.setEnableRefresh(false);
        BCRefreshHeader bcRefreshHeader = new BCRefreshHeader(getActivity()) {
            @Override
            public int onFinish(@NonNull RefreshLayout layout, boolean success) {
                this.mFinishDuration = 5;
                return super.onFinish(layout, success);
            }
        };
        bcRefreshHeader.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
        srlRefresh.setRefreshHeader((RefreshHeader) bcRefreshHeader);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page++;
                mPresenter.getMsgListTwo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", page, "10");
            }
        });
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        homeAdapter = new HomeAdapter(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.addItemDecoration(new DividerItemLastDecorations(getActivity(), R.drawable.devider_grey_1_14dpzq, homeAdapter.getData().size()));
        recyclerView.scrollToPosition(homeAdapter.getData().size() - 1);
        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (ClickListenNameTwo.isFastClick()) {
                    if (guide != null) {
                        guide.dismiss();
                        guide.clear();
                    }
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    switch (view.getId()) {
                        case R.id.line_open:
                            List<HomeBeanszq> lists = adapter.getData();
                            HomeBeanszq homeBeans = lists.get(position);
                            if (homeBeans.getItemType() == Constant.TYPE_TWO) {
                                HomeRedMessagezq homeRedMessage = homeBeans.getHomeRedMessage();
                                redTypeName = homeRedMessage.getTypename();
                                balanceMoney = "";
                                if ("1".equals(homeRedMessage.getStype())) {//手气红包
                                    balanceMoney = homeRedMessage.getBalance_money();
                                }
                                if (homeRedMessage.getType() == 1) {//手气红包
                                    tongjiStr = "ad_shouqi";
                                } else if (homeRedMessage.getType() == 2) {//惊喜红包
                                    tongjiStr = "ad_jingxi";
                                } else if (homeRedMessage.getType() == 3) {//定向红包
                                    tongjiStr = "ad_dingxiang";
                                } else if (homeRedMessage.getType() == 4) {//悬浮红包
                                    tongjiStr = "ad_zaixian";
                                }

                                jumpRedEvenlopesId = homeRedMessage.getId() + "";
                                redOnclickType = 2;
                                redOnclickIndex = position;
                                mPresenter.getRedEvenlopsInfo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", homeRedMessage.getId() + "");
                            } else if (homeBeans.getItemType() == Constant.TYPE_FIVE) {//
                                Info1Beanzq info1Beanzq = homeBeans.getInfo1Beanzq();
                                if (info1Beanzq.getType() == 1) {//手气红包
                                    tongjiStr = "ad_shouqi";
                                } else if (info1Beanzq.getType() == 2) {//惊喜红包
                                    tongjiStr = "ad_jingxi";
                                } else if (info1Beanzq.getType() == 3) {//定向红包
                                    tongjiStr = "ad_dingxiang";
                                } else if (info1Beanzq.getType() == 4) {//悬浮红包
                                    tongjiStr = "ad_zaixian";
                                }
                                redTypeName = info1Beanzq.getTypename();
                                String moneys = "";
                                if (!TextUtils.isEmpty(info1Beanzq.getMoney()) && !"0.00".equals(info1Beanzq.getMoney()) && !"0".equals(info1Beanzq.getMoney())) {
                                    moneys = info1Beanzq.getMoney();
                                } else {
                                    moneys = info1Beanzq.getMember_money();
                                }
                                jumpRedEvenlopesId = info1Beanzq.getId() + "";
                                redOnclickType = 5;
                                redOnclickIndex = position;
                                if (info1Beanzq.getStatus() == 1) {
                                    //  RobRedEvenlopesActivity.robRedEvenlopesJump(MainActivity.this, "1", redTypeName, balanceMoney, "", jumpRedEvenlopesId, "");
                                } else {
                                    showRedDialog(moneys, info1Beanzq.getTypename(), "", info1Beanzq.getStatus() + "");
                                }
                            }
                            break;
                        case R.id.line_member:
                            //  MemberActivity.memberJump(MainActivity.this);
                            break;
                    }
                }
            }
        });
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadVideo();
//        UserInfozq userInfozq = CacheDataUtils.getInstance().getUserInfo();
//        mPresenter.getOtherInfo(userInfozq.getGroup_id() + "", userInfozq.getId() + "");
//    }



    @OnClick({R.id.rela_avatar, R.id.iv_red,  R.id.line_redzaixian})
    public void onViewClicked(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        switch (view.getId()) {
            case R.id.line_redzaixian:
            case R.id.iv_red:
                if (ClickListenNameTwo.isFastClick()) {
                    if (isOnclick) {
                        if (ivShouzaixian.getVisibility()==View.VISIBLE){
                            ivShouzaixian.setVisibility(View.GONE);
                        }
                        String hbZaiXian = CacheDataUtils.getInstance().getHbZaiXian();
                        if (TextUtils.isEmpty(hbZaiXian)) {
                            mPresenter.getRegUserLog(CacheDataUtils.getInstance().getUserInfo().getId(), "1", "1");
                        }
                        tongjiStr = "ad_zaixian";
                        showRedDialog(on_money, "在线红包", "", "4");
                    }
                }
                break;

        }
    }

    private Disposable disposableTwo;

    public void initTimes() {
        Observable.interval(38, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableTwo = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (TextUtils.isEmpty(msgId)) {
                            msgId = "";
                        }
                        mPresenter.getHomeMsgDataPolling(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", msgId);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        Observable.interval(60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableTwo = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (TextUtils.isEmpty(hongbao_id)) {  //红包消息轮询
                            hongbao_id = "";
                        }
                        mPresenter.getHomeMessageRedData(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", hongbao_id);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    RedDialogTwo redDialog;
    public void showRedDialog(String money, String redTypeNames, String balanceMoneys, String statusss) {
        redDialog = new RedDialogTwo(getActivity());
        View builder = redDialog.builder(R.layout.red_dialog_item_two);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_type = builder.findViewById(R.id.tv_typeName);
        TextView tv_money = builder.findViewById(R.id.tv_money);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        LinearLayout line_getRed = builder.findViewById(R.id.line_getRed);
        RelativeLayout rela_status = builder.findViewById(R.id.rela_status);
        TextView tv_getRedDetails = builder.findViewById(R.id.tv_getRedDetails);
        TextView tv_getRedDes = builder.findViewById(R.id.tv_getRedDes);
        RelativeLayout rela_shou = builder.findViewById(R.id.rela_shou);
        FrameLayout fl_banner = builder.findViewById(R.id.fl_banner);
        TextView tv_tips = builder.findViewById(R.id.tv_tips);
        LinearLayout line_moneys = builder.findViewById(R.id.line_moneys);
        tv_tips.setVisibility(View.GONE);
        line_moneys.setVisibility(View.VISIBLE);
        redDialog.setOutCancle(false);
        if ("0".equals(statusss)) {
            line_getRed.setVisibility(View.VISIBLE);
            rela_status.setVisibility(View.GONE);
        } else if ("1".equals(statusss)) {
            tv_getRedDes.setVisibility(View.VISIBLE);
            tv_getRedDes.setText("您已经领取该改红包了");
            line_getRed.setVisibility(View.GONE);
            rela_status.setVisibility(View.VISIBLE);
        } else if ("2".equals(statusss)) {
            tv_getRedDes.setVisibility(View.VISIBLE);
            line_getRed.setVisibility(View.GONE);
            rela_status.setVisibility(View.VISIBLE);
        } else if ("4".equals(statusss)) {
            tv_tips.setVisibility(View.VISIBLE);
            line_moneys.setVisibility(View.GONE);
            line_getRed.setVisibility(View.VISIBLE);
            rela_status.setVisibility(View.GONE);
        }
        tv_getRedDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                List<HomeBeanszq> lists = homeAdapter.getData();
                if (redOnclickType == 2) {
                    HomeBeanszq homeBeans = lists.get(redOnclickIndex);
                    HomeRedMessagezq homeRedMessage = homeBeans.getHomeRedMessage();
                    if (homeRedMessage != null) {
                        homeRedMessage.setStatus(1);
                        homeAdapter.notifyItemChanged(redOnclickIndex);
                    }
                } else if (redOnclickType == 5) {
                    HomeBeanszq homeBeans = lists.get(redOnclickIndex);
                    Info1Beanzq info1Beanzq = homeBeans.getInfo1Beanzq();
                    if (info1Beanzq != null) {
                        info1Beanzq.setStatus(1);
                        homeAdapter.notifyItemChanged(redOnclickIndex);
                    }
                }
                mPresenter.getMoneyRed(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", jumpRedEvenlopesId);//获取红包金额
            }
        });
        if (!TextUtils.isEmpty(redTypeName)) {
            tv_type.setText(redTypeName);
        }
        redTypeName = redTypeName;
        tv_money.setText(money);
        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//看广告
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                String newGu = CacheDataUtils.getInstance().getNewGu();
                if (TextUtils.isEmpty(newGu)) {
                    initTimes();
                    CacheDataUtils.getInstance().setNewGu("news");
                    MobclickAgent.onEvent(getActivity(), "yindaohbkai");//参数二为当前统计的事件ID
                    mPresenter.getRegUserLog(CacheDataUtils.getInstance().getUserInfo().getId(), "6", "2");
                }

                if (!TextUtils.isEmpty(tongjiStr) && "ad_shouqi".equals(tongjiStr)) {
                    String shouqiVideo = CacheDataUtils.getInstance().getShouqiVideo();
                    if (TextUtils.isEmpty(shouqiVideo)) {//第一次
                        List<HomeBeanszq> lists = homeAdapter.getData();
                        if (lists!=null&&lists.size()>0){
                            if (redOnclickType == 2) {
                                HomeBeanszq homeBeans = lists.get(redOnclickIndex);
                                HomeRedMessagezq homeRedMessage = homeBeans.getHomeRedMessage();
                                if (homeRedMessage != null) {
                                    homeRedMessage.setStatus(1);
                                    homeAdapter.notifyItemChanged(redOnclickIndex);
                                }
                            } else if (redOnclickType == 5) {
                                HomeBeanszq homeBeans = lists.get(redOnclickIndex);
                                Info1Beanzq info1Beanzq = homeBeans.getInfo1Beanzq();
                                if (info1Beanzq != null) {
                                    info1Beanzq.setStatus(1);
                                    homeAdapter.notifyItemChanged(redOnclickIndex);
                                }
                            }
                        }
                        CacheDataUtils.getInstance().setShouqiVideo();
                        mPresenter.getMoneyRed(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", jumpRedEvenlopesId);//获取红包金额
                    } else {
                        if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                            showVideo(statusss);
                        }else {
                            showTx();
                        }
                        redDialog.setDismiss();
                    }
                } else {
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideo(statusss);
                    }else {
                         status = statusss;
                        showTx();
                    }
                    redDialog.setDismiss();
                }
            }
        });


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                redDialog.setDismiss();
                showInsertVideo();
            }
        });

        String newGu = CacheDataUtils.getInstance().getNewGu();
        if (TextUtils.isEmpty(newGu)) {
            rela_shou.setVisibility(View.VISIBLE);
            iv_close.setVisibility(View.GONE);
        } else {
            rela_shou.setVisibility(View.GONE);
            VUiKit.postDelayed(2000, () -> {
                if ("2".equals(Constant.ISBANNER)){
                    loadBanner(fl_banner);
                }
                iv_close.setVisibility(View.VISIBLE);
            });
        }
        redDialog.setShow();
    }


    @Override
    public void getHomeDataSuccess(HomeAllBeanszq data) {
        MobclickAgent.onEvent(getActivity(), "denglumoney");//参数二为当前统计的事件ID
        if (data != null) {
            ((MyApplication) MyApplication.getInstance()).levels=data.getUser_other().getLevel();
            tvMoney.setText(data.getUser_other().getCash() + "元");
            cashMoney = data.getUser_other().getCash();
            on_money = data.getOn_money();
            VUiKit.postDelayed(400, () -> {
                HomeBeanszq homeBeans = new HomeBeanszq();
                homeBeans.setItemType(Constant.TYPE_THREE);
                homeBeans.setHomeAllBeans(data);
                homeAdapter.addData(homeBeans);

                HomeBeanszq homeBeansTwo = new HomeBeanszq();
                homeBeansTwo.setItemType(Constant.TYPE_FOUR);
                homeBeansTwo.setHomeAllBeans(data);
                homeAdapter.addData(homeBeansTwo);
                int itemDecorationCount = recyclerView.getItemDecorationCount();
                if (itemDecorationCount > 0) {
                    for (int i = 0; i < itemDecorationCount; i++) {
                        recyclerView.removeItemDecorationAt(i);
                    }
                }
                recyclerView.addItemDecoration(new DividerItemLastDecorations(getActivity(), R.drawable.devider_grey_1_14dpzq, homeAdapter.getData().size()));
                recyclerView.scrollToPosition(homeAdapter.getData().size() - 1);
                mPresenter.getMsgList(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", page, "10");
            });
            if (data.getOnline_red() == 0) {
                isOnclick = true;
            } else {
                isOnclick = true;
                long sys_time = data.getSys_time() * 1000;
                long online_red = data.getOnline_red() * 1000;
                long nextTime = 0;
                if (online_red > 0) {
                    nextTime = online_red + 120 * 1000;
                    if (nextTime > sys_time) {
                        long yuTimes = nextTime - sys_time;
                        isOnclick = true;
                        if (yuTimes < 120000) {
                            isOnclick = false;
                            countDownUtilsThree.setHours(TimesUtils.getMinDiff(yuTimes), TimesUtils.getSecondDiff(yuTimes));
                        }
                    }
                }
            }


            String new_hongbao = data.getNew_hongbao();
            if (!TextUtils.isEmpty(new_hongbao) && !"0".equals(new_hongbao) && !"0.0".equals(new_hongbao) && !"0.00".equals(new_hongbao)) {//可以领取登录奖励红包

            } else {
                HomeAllBeanszq.SiginInfoBean sign_info = data.getSign_info();
                if (sign_info != null && !TextUtils.isEmpty(sign_info.getMoney())) {
                    signId = String.valueOf(sign_info.getSign_id());
                    showSignDialog(sign_info.getMoney(), 1);
                }
            }
            newLoginStatus = data.getTreasure_state();
        }

        if (data.getUnlock()==2){//不需要解锁

        }else if (data.getUnlock()==0){//需要解锁

        }else {//解锁任务完成

        }

    }

    @Override
    public void getOtherInfoSuccess(OtherBeanszq data) {
        this.otherBeanszq = data;
        ((MyApplication) MyApplication.getInstance()).levels= data.getLevel();
        tvRank.setText("LV." + data.getLevel() + "");
        tvMoney.setText(data.getCash() + "元");
    }

    @Override
    public void getHomeMessageRedDataInfo(List<HomeRedMessagezq> datas) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                if (i == 0) {
                    hongbao_id = datas.get(i).getId() + "";
                }
                HomeBeanszq homeBeans = new HomeBeanszq();
                homeBeans.setItemType(Constant.TYPE_TWO);
                homeBeans.setHomeRedMessage(datas.get(i));
                homeAdapter.addData(homeBeans);
            }
            int itemDecorationCount = recyclerView.getItemDecorationCount();
            if (itemDecorationCount > 0) {
                for (int i = 0; i < itemDecorationCount; i++) {
                    recyclerView.removeItemDecorationAt(i);
                }
            }
            recyclerView.addItemDecoration(new DividerItemLastDecorations(getActivity(), R.drawable.devider_grey_1_14dpzq, homeAdapter.getData().size()));
            recyclerView.scrollToPosition(homeAdapter.getData().size() - 1);
        }
    }

    @Override
    public void getRedEvenlopsInfoSuccess(OpenRedEvenlopeszq data) {
        if (data.getStatus() == 1) {
            List<HomeBeanszq> lists = homeAdapter.getData();
            if (redOnclickType == 2) {
                HomeBeanszq homeBeans = lists.get(redOnclickIndex);
                HomeRedMessagezq homeRedMessage = homeBeans.getHomeRedMessage();
                if (homeRedMessage != null) {
                    homeRedMessage.setStatus(1);
                    homeAdapter.notifyItemChanged(redOnclickIndex);
                }
            }
            //    RobRedEvenlopesActivity.robRedEvenlopesJump(MainActivity.this, "1", redTypeName, balanceMoney, data.getBalance_money(), jumpRedEvenlopesId, "1");
        } else {
            showRedDialog(data.getBalance_money(), redTypeName, balanceMoney, data.getStatus() + "");
        }
    }

    @Override
    public void getMsgListSuccess(List<HomeMsgBeanszq> data) {
        for (int i = 0; i < data.size(); i++) {
            int stype = data.get(i).getStype();
            if (stype == 0) {
                Info0Beanzq info0 = data.get(i).getInfo0();
                if (TextUtils.isEmpty(msgId)) {
                    msgId = info0.getId() + "";
                }
                info0.setStype(0);

                int rand_level = info0.getRand_level();
                if (rand_level==0){
                    rand_level= CommonUtils.getRandom(4,20);
                    info0.setRand_level(rand_level);
                }

                HomeBeanszq homeBeans = new HomeBeanszq();
                homeBeans.setInfo0Beanzq(info0);
                homeBeans.setItemType(Constant.TYPE_ONE);

                String newGu = CacheDataUtils.getInstance().getNewGu();
                if (!TextUtils.isEmpty(newGu)) {
                    homeAdapter.addData(homeBeans);
                }
            } else {
                Info1Beanzq info1 = data.get(i).getInfo1();
                if (TextUtils.isEmpty(hongbao_id)) {
                    hongbao_id = info1.getId() + "";
                }
                info1.setStype(1);
                HomeBeanszq homeBeans = new HomeBeanszq();
                homeBeans.setInfo1Beanzq(info1);
                homeBeans.setItemType(Constant.TYPE_FIVE);
                homeAdapter.addData(homeBeans);
            }
        }
        int itemDecorationCount = recyclerView.getItemDecorationCount();
        if (itemDecorationCount > 0) {
            for (int i = 0; i < itemDecorationCount; i++) {
                recyclerView.removeItemDecorationAt(i);
            }
        }
        recyclerView.addItemDecoration(new DividerItemLastDecorations(getActivity(), R.drawable.devider_grey_1_14dpzq, homeAdapter.getData().size()));
        recyclerView.scrollToPosition(homeAdapter.getData().size() - 1);


        String newGu = CacheDataUtils.getInstance().getNewGu();
        if (TextUtils.isEmpty(newGu)) {
            List<HomeBeanszq> listss = homeAdapter.getData();
            int poIndex = -1;
            for (int i = 0; i < listss.size(); i++) {
                HomeBeanszq homeBeans = listss.get(i);
                if (homeBeans.getItemType() == Constant.TYPE_FIVE) {
                    Info1Beanzq info1 = listss.get(i).getInfo1Beanzq();
                    if (info1 != null && info1.getType() == 1) {//手气红包
                        poIndex = i;
                    }
                }
            }
            if (poIndex != -1&&((MyApplication) MyApplication.getInstance()).levels<=1) {
                int finalPoIndex = poIndex;
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        layout = homeAdapter.getViewByPosition(recyclerView, finalPoIndex, R.id.line_open);
                        if (layout != null) {
                            layout.post(new Runnable() {
                                @Override
                                public void run() {
                                    showGuideView(layout);
                                }
                            });
                        } else {
                            initTimes();
                        }
                    }
                });
            } else {
                initTimes();
            }
        } else {
            initTimes();
        }

        if (data.size() < 10) {
            srlRefresh.setEnableRefresh(false);
        } else {
            srlRefresh.setEnableRefresh(true);
        }
    }

    private Guide guide;
    private View layout;

    public void showGuideView(View view) {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(view)
                .setAlpha(150)
                .setHighTargetCorner(20)
                .setOutsideTouchable(false)
                .setAutoDismiss(false)
                .setHighTargetPadding(10);
        builder.setOnTarListener(new GuideBuilder.OnTarLintens() {
            @Override
            public void onTarLinten() {
                MobclickAgent.onEvent(getActivity(), "yindaohongbao");//参数二为当前统计的事件ID
                mPresenter.getRegUserLog(CacheDataUtils.getInstance().getUserInfo().getId(), "5", "2");
                if (guide != null) {
                    guide.dismiss();
                }
            }
        });

        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
            @Override
            public void onShown() {

            }

            @Override
            public void onDismiss() {
                if (view != null) {
                    view.performClick();
                }
            }
        });
        builder.addComponent(new SimpleComponent());
        guide = builder.createGuide();
        guide.show(getActivity());
    }

    @Override
    public void getHomeMsgDataPollingSuccess(List<Info0Beanzq> data) {
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (i == 0) {
                    msgId = data.get(0).getId() + "";
                }
                HomeBeanszq homeBeans = new HomeBeanszq();
                Info0Beanzq info0Beanzq = data.get(i);
                info0Beanzq.setStype(0);
                int rand_level = info0Beanzq.getRand_level();
                Log.d(TAG, "getHomeMsgDataPollingSuccess: ");
                if (rand_level==0){
                    rand_level= CommonUtils.getRandom(4,20);
                    info0Beanzq.setRand_level(rand_level);
                }

                homeBeans.setItemType(Constant.TYPE_ONE);
                homeBeans.setInfo0Beanzq(info0Beanzq);
                homeAdapter.addData(homeBeans);
            }
            int itemDecorationCount = recyclerView.getItemDecorationCount();
            if (itemDecorationCount > 0) {
                for (int i = 0; i < itemDecorationCount; i++) {
                    recyclerView.removeItemDecorationAt(i);
                }
            }
            recyclerView.addItemDecoration(new DividerItemLastDecorations(getActivity(), R.drawable.devider_grey_1_14dpzq, homeAdapter.getData().size()));
            recyclerView.scrollToPosition(homeAdapter.getData().size() - 1);
        }
    }

    @Override
    public void getMoneyRedSuccess(HomeGetRedMoneyBeanszq data) {
        if (data.getNew_level() > 0) {
            if (otherBeanszq != null) {
                int le = otherBeanszq.getLevel() + 1;
                tvRank.setText("LV." + le);
            }
        }
        tvMoney.setText(data.getCash() + "元");
        //  RobRedEvenlopesActivity.robRedEvenlopesJump(MainActivity.this, "1", redTypeName, balanceMoney, data.getRed_money(), jumpRedEvenlopesId, "");
        if (redDialog != null) {
            redDialog.setDismiss();
        }
    }
    private int upTreasure=0;
    @Override
    public void updtreasureSuccess(UpQuanNumsBeanszq data) {//更新券回调
        if (data!=null){
            upTreasure=data.getRand_num();
        }
    }

    @Override
    public void getonLineRedSuccess(HomeOnlineBeanszq data) {//在线红包
        if (data.getNew_level() > 0) {
            if (otherBeanszq != null) {
                int le = otherBeanszq.getLevel() + 1;
                tvRank.setText("LV." + le);
            }
        }
        tvMoney.setText(data.getCash() + "元");
        // RobRedEvenlopesActivity.robRedEvenlopesJump(MainActivity.this, "3", "在线红包", "", data.getRed_money(), "", "");
        if (redDialog != null) {
            redDialog.setDismiss();
        }
    }

    @Override
    public void upVersionSuccess(UpDataVersion data) {
        UpgradeInfozq upgradeInfozq = new UpgradeInfozq();
        upgradeInfozq.setDesc(data.getUpdate_content());
        upgradeInfozq.setDownUrl(data.getDownload_url());
        upgradeInfozq.setVersion(data.getVersion_name());
        upgradeInfozq.setVersionCode(data.getVersion_code());
        upgradeInfozq.setForce_update(data.getForce_update());
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_ACTIVITIES);

            if (upgradeInfozq != null && upgradeInfozq.getVersionCode() > info.versionCode) {
                if (!TextUtils.isEmpty(data.getDownload_url())) {
                    UpdateDialog dialog = new UpdateDialog(getActivity());
                    dialog.setInfo(upgradeInfozq);
                    dialog.show();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMsgListTwoSuccess(List<HomeMsgBeanszq> data) {//加载跟多
        srlRefresh.finishRefresh();
        if (data.size() > 0) {
            int lastItemPosition = 0;
            if (linearLayoutManager != null) {
                lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            }
            List<HomeBeanszq> lists = homeAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                int stype = data.get(i).getStype();
                if (stype == 0) {
                    Info0Beanzq info0 = data.get(i).getInfo0();
                    if (TextUtils.isEmpty(msgId)) {
                        msgId = info0.getId() + "";
                    }
                    info0.setStype(0);
                    int rand_level = info0.getRand_level();
                    if (rand_level==0){
                        rand_level= CommonUtils.getRandom(4,20);
                        info0.setRand_level(rand_level);
                    }
                    HomeBeanszq homeBeans = new HomeBeanszq();
                    homeBeans.setInfo0Beanzq(info0);
                    homeBeans.setItemType(Constant.TYPE_ONE);


                    lists.add(0, homeBeans);
                } else {
                    Info1Beanzq info1 = data.get(i).getInfo1();
                    if (TextUtils.isEmpty(hongbao_id)) {
                        hongbao_id = info1.getId() + "";
                    }
                    info1.setStype(1);
                    HomeBeanszq homeBeans = new HomeBeanszq();
                    homeBeans.setInfo1Beanzq(info1);
                    homeBeans.setItemType(Constant.TYPE_FIVE);
                    lists.add(0, homeBeans);
                }
            }
            int itemDecorationCount = recyclerView.getItemDecorationCount();
            if (itemDecorationCount > 0) {
                for (int i = 0; i < itemDecorationCount; i++) {
                    recyclerView.removeItemDecorationAt(i);
                }
            }
            recyclerView.addItemDecoration(new DividerItemLastDecorations(getActivity(), R.drawable.devider_grey_1_14dpzq, homeAdapter.getData().size()));
            if (data.size() > 1) {
                recyclerView.scrollToPosition(lastItemPosition + data.size() - 1);
            } else {
                recyclerView.scrollToPosition(lastItemPosition + data.size());
            }
        }
        if (data.size() < 10) {
            srlRefresh.setEnableRefresh(false);
        } else {
            srlRefresh.setEnableRefresh(true);
        }
    }

    @Override
    public void getMsgListTwoError() {
        srlRefresh.finishRefresh();
        srlRefresh.setEnableRefresh(false);
    }


    @Override
    public void getSignSuccess(SignBeans data) {
        if (data != null) {
            showSignDialog(data.getDouble_money(), 2);
        }
    }


    @Override
    public void getHomeDataError() {
        initTimes();
    }
    private List<String> addIndexList=new ArrayList<>();



    private Disposable unlockDis;
    public void unlockTaskTipsTimes(){
        if (RomUtil.isVivo()&& Build.VERSION.SDK_INT ==22){

        }else {
            List<String> datas=new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                datas.add("2");
            }
            Observable<String> listObservable = Observable.fromIterable(datas);
            Observable<Long> timeObservable = Observable.interval(180 ,TimeUnit.SECONDS);
            Observable.zip(listObservable, timeObservable, new BiFunction<String, Long, Object>() {
                @Override
                public Object apply(String s, Long aLong) throws Exception {
                    return s;
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Object>() {
                @Override
                public void onSubscribe(Disposable d) {
                    unlockDis=d;
                }

                @Override
                public void onNext(Object o) {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }

    }



    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHomePage(Event event) {
        if (event instanceof Event.LoginEvent) {
            Event.LoginEvent event1 = (Event.LoginEvent) event;
            initViews();
            if (homeAdapter != null) {
                List<HomeBeanszq> data = homeAdapter.getData();
                data.clear();
                homeAdapter.notifyDataSetChanged();
            }
            String faces = "";
            if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getUserInfo().getFace())) {
                faces = CacheDataUtils.getInstance().getUserInfo().getFace();
            }
            if (TextUtils.isEmpty(faces)) {
                faces = event1.getFace();
            }
            if (!TextUtils.isEmpty(faces)) {
                Glide.with(this)
                        .load(faces)
                        .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                        .into(ivAvatar);
            }
            initData();
        }
    }



    private void loadInsertView(Runnable runnable) {
        int screenWidth = CommonUtils.getScreenWidth(getActivity());
        int screenHeight = CommonUtils.getScreenHeight(getActivity());
        int w = (int) (screenWidth) * 9 / 10;
        int h = screenHeight * 9 / 10;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(getActivity());
        int dpw = DisplayUtil.px2dip(getActivity(), w);
        int dph = DisplayUtil.px2dip(getActivity(), h);
        adPlatformSDK.loadInsertAd(getActivity(), "chaping", dpw, dph, new AdCallback() {
            @Override
            public void onDismissed() {

            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("ccc", "-----------loadInsertView------onNoAd: " + adError.getCode() + "--" + adError.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {

            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    private String isLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private void loadVideo(Runnable runnable) {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(getActivity());
        adPlatformSDK.loadRewardVideoVerticalAd(getActivity(), tongjiStr, new AdCallback() {
            @Override
            public void onDismissed() {
                Log.d("ccc", "----onDismissed: ");
                if ("5".equals(status)) {
                    mPresenter.getSign(CacheDataUtils.getInstance().getUserInfo().getId(), signId);
                } else {
                    if (redDialog != null) {
                        redDialog.setDismiss();
                    }
                    List<HomeBeanszq> lists = homeAdapter.getData();


                    if (redOnclickIndex < lists.size()) {
                        if (redOnclickType == 2) {
                            HomeBeanszq homeBeans = lists.get(redOnclickIndex);
                            HomeRedMessagezq homeRedMessage = homeBeans.getHomeRedMessage();
                            if (homeRedMessage != null) {
                                homeRedMessage.setStatus(1);
                                homeAdapter.notifyItemChanged(redOnclickIndex);
                            }
                        } else if (redOnclickType == 5) {
                            HomeBeanszq homeBeans = lists.get(redOnclickIndex);
                            Info1Beanzq info1Beanzq = homeBeans.getInfo1Beanzq();
                            if (info1Beanzq != null) {
                                info1Beanzq.setStatus(1);
                                homeAdapter.notifyItemChanged(redOnclickIndex);
                            }
                        }
                    }
                    if ("4".equals(status)) {
                        isOnclick = false;
                        countDownUtilsThree.setHours(1, 59);
                        mPresenter.getonLineRed(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", on_money);//获取红包金额
                    } else {
                        mPresenter.getMoneyRed(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", jumpRedEvenlopesId);//获取红包金额
                    }
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                if ("1".equals(isLoadAdSuccess)){
                    isLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        showTx();
                    }else {
                        if (!CommonUtils.isDestory(getActivity())) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }
                }

                //   Log.d("ccc", "-------------onNoAd: " + adError.getMessage() + "----" + adError.getCode() + "-------" + adError.getTripartiteCode());
//                videoCounts++;
//                if (videoCounts > 3) {
//                    videoCounts = 1;
//                    if (!CommonUtils.isDestory(MainActivity.this)) {
//                        ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
//                    }
//                    return;
//                }
//                loadVideo();
            }

            @Override
            public void onComplete() {
                if (redDialog != null) {
                    redDialog.setDismiss();
                }
                upTreasure=0;
                Log.d("ccc", "----onComplete: ");
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onPresent() {
                isLoadAdSuccess="3";
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                isLoadAdSuccess="3";
//                if (runnable != null) {
//                    runnable.run();
//                }
            }
        });
    }

    private void loadVideo() {
        loadVideo(null);
    }
    private void showVideo(String status) {
        if (!TextUtils.isEmpty(status)){
            this.status = status;
        }
        isLoadAdSuccess="1";
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(getActivity());
        adPlatformSDK.setAdPosition(tongjiStr);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showRewardVideoAd();
        loadVideo();
    }

    private void showInsertVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(getActivity());
        adPlatformSDK.setAdPosition("chaping");
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        if (adPlatformSDK.showInsertAd()) {
            loadInsertView(null);
        } else {
            loadInsertView(new Runnable() {
                @Override
                public void run() {
                    adPlatformSDK.showInsertAd();
                }
            });
        }
    }


    private String getTv(long l) {
        if (l >= 10) {
            return l + "";
        } else {
            return "0" + l;//小于10,,前面补位一个"0"
        }
    }




    private FrameLayout fl_content;
    private SignDialog redDialogs;
    private TextView tv_money;
    private TextView tv_title;
    private RelativeLayout rela_fanbei;
    private ImageView iv_close;
    private LinearLayout line_close;
    private boolean isShow;

    public void showSignDialog(String money, int type) {
        MobclickAgent.onEvent(getActivity(), "denglujiangli");//参数二为当前统计的事件ID
        if (redDialogs != null) {
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    redDialogs.setDismiss();
                }
            });
            rela_fanbei.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    status = "5";
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideo(status);
                    }else {
                        status = status;
                        showTx();
                    }
                    redDialogs.setDismiss();
                }
            });
            if (type == 1) {
                rela_fanbei.setVisibility(View.VISIBLE);
            } else {
                rela_fanbei.setVisibility(View.GONE);
            }

            tv_money.setText(money);
            tv_title.setText("登录奖励");
            loadixinxiVideo();
            video();
            redDialogs.setOutCancle(false);

            VUiKit.postDelayed(2000, () -> {
                line_close.setVisibility(View.VISIBLE);
            });

            if (!CommonUtils.isDestory(getActivity())) {
                redDialogs.setShow();
            }
        }
    }

    private void video() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(getActivity());
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        isShow = adPlatformSDK.showExpressAd();
    }

    private void loadixinxiVideo() {
        int screenWidth = CommonUtils.getScreenWidth(getActivity());
        int w = (int) (screenWidth) * 4 / 5;
        int h = w * 2 / 3;
        int dpw = DisplayUtil.px2dip(getActivity(), w);
        int dph = DisplayUtil.px2dip(getActivity(), h);
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(getActivity());
        adPlatformSDK.loadExpressAd(getActivity(), "ad_home_login", dpw + 6, dph - 20, new AdCallback() {
            @Override
            public void onDismissed() {

            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("ccc", "----------onNoAd: " + adError.getMessage() + "---" + adError.getCode());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {

            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                if (!isShow) {
                    adPlatformSDK.showExpressAd();
                }
            }
        }, fl_content);
    }


//    public void initNewLogin(String moneys) {
//        NesLoginDialog newLogin = new NesLoginDialog(this);
//        View builder = newLogin.builder(R.layout.newlogin_dialog_item);
//        TextView tv_moneys = builder.findViewById(R.id.tv_moneys);
//        TextView tv_sure = builder.findViewById(R.id.tv_sure);
//        tv_moneys.setText(moneys + "元提现机会");
//        tv_sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPresenter.getNewsLoginHb(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id());
//                newLogin.setDismiss();
//            }
//        });
//        newLogin.setOutCancle(false);
//        newLogin.setShow();
//    }




    private void showBanner() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(getActivity());
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showBannerAd();
    }

    private void loadBanner(FrameLayout fl_ad_containe) {
        int screenWidth = CommonUtils.getScreenWidth(getActivity());
        int w = (int) (screenWidth);
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(getActivity());
        int dpw = DisplayUtil.px2dip(getActivity(), w);
        adPlatformSDK.loadBannerAd(getActivity(), "ad_banner", dpw, 70, new AdCallback() {
            @Override
            public void onDismissed() {

            }

            @Override
            public void onNoAd(AdError adError) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {

            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                showBanner();
            }
        }, fl_ad_containe);
    }




    public void logins() {
        String imei = CacheDataUtils.getInstance().getUserInfo().getImei();
        String imei2 = CacheDataUtils.getInstance().getUserInfo().getImei2();
        int app_type1 = CacheDataUtils.getInstance().getUserInfo().getApp_type();
        String face1 = CacheDataUtils.getInstance().getUserInfo().getFace();
        String nickname = CacheDataUtils.getInstance().getUserInfo().getNickname();
        String mac = CacheDataUtils.getInstance().getUserInfo().getMac();
        String wx_openid = CacheDataUtils.getInstance().getUserInfo().getWx_openid();
        String oaid = CacheDataUtils.getInstance().getUserInfo().getOaid();
        String phone_brand = CacheDataUtils.getInstance().getUserInfo().getPhone_brand();
        mPresenter.login(app_type1, wx_openid, "", "", nickname, 2, face1, ((MyApplication) MyApplication.getInstance()).getAgentId(), imei, imei2, mac, oaid, phone_brand);
//        if (TextUtils.isEmpty(loginTimes)) {
//            mPresenter.login(app_type1, wx_openid, "", "", nickname, 2, face1, ((MyApplication) MyApplication.getInstance()).getAgentId(), imei, imei2, mac, oaid, phone_brand);
//        } else {
//            long curr = System.currentTimeMillis();
//            String strTimessssss = TimesUtils.getStrTimessssss(curr);
//            if (!TextUtils.isEmpty(strTimessssss)) {
//                if (!strTimessssss.equals(loginTimes)) {
//                    mPresenter.login(app_type1, wx_openid, "", "", nickname, 2, face1, ((MyApplication) MyApplication.getInstance()).getAgentId(), imei, imei2, mac, oaid, phone_brand);
//                }
//            }
//        }
    }

    public void showTx(){
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                if (!CommonUtils.isDestory(getActivity())) {
                    ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                }
            }else {
                showVideo(null);
            }
        }else {
            VideoAdValidity validity = mRewardVideoAD.checkValidity();
            switch (validity) {
                case SHOWED:
                case OVERDUE:
                    loadTxTwo();
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        if (!CommonUtils.isDestory(getActivity())) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }else {
                        showVideo(null);
                    }
                    return;
                // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                case NONE_CACHE:
                    //  showToast("广告素材未缓存成功！");
//            return;
                case VALID:
                    // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                    isTxLoadAdSuccess="1";
                    mRewardVideoAD
                            .showAD(getActivity());
                    // 展示广告
                    break;
            }
        }

    }
    public void loadTxTwo(){
        mIsLoaded=false;
        loadTx();
    }
    private ExpressRewardVideoAD mRewardVideoAD;
    private boolean mIsLoaded;
    private boolean mIsCached;
    private String isTxLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    public void loadTx(){
        String posId="1081870061070830";
        mRewardVideoAD = new ExpressRewardVideoAD(getActivity(), Constant.TXRVIDEO, new ExpressRewardVideoAdListener() {
            @Override
            public void onAdLoaded() {
                mIsLoaded = true;
                isTxLoadAdSuccess="3";
            }

            @Override
            public void onVideoCached() {
                // 在视频缓存完成之后再进行广告展示，以保证用户体验
                mIsCached = true;
                Log.i("ccc", "onVideoCached: ");
            }

            @Override
            public void onShow() {
                isTxLoadAdSuccess="3";
                if (!CommonUtils.isDestory(getActivity())) {
                    AppSettingUtils.showTxShow("tx_"+tongjiStr);
                    long currentTimeMillis= System.currentTimeMillis();
                    String str = TimesUtils.getStr(currentTimeMillis);
                }
            }

            @Override
            public void onExpose() {

            }

            /**
             * 模板激励视频触发激励
             *
             * @param map 若选择了服务端验证，可以通过 ServerSideVerificationOptions#TRANS_ID 键从 map 中获取此次交易的 id；若未选择服务端验证，则不需关注 map 参数。
             */
            @Override
            public void onReward(Map<String, Object> map) {
                //  Object o = map.get(ServerSideVerificationOptions.TRANS_ID); // 获取服务端验证的唯一 ID
                //   Log.i("ccc", "onReward " + o);
            }

            @Override
            public void onClick() {
                AppSettingUtils.showTxClick("tx_"+tongjiStr);
            }

            @Override
            public void onVideoComplete() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
                if (redDialog != null) {
                    redDialog.setDismiss();
                }
                upTreasure=0;
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onClose() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
                if ("5".equals(status)) {
                    mPresenter.getSign(CacheDataUtils.getInstance().getUserInfo().getId(), signId);
                } else {
                    if (redDialog != null) {
                        redDialog.setDismiss();
                    }
                    List<HomeBeanszq> lists = homeAdapter.getData();


                    if (redOnclickIndex < lists.size()) {
                        if (redOnclickType == 2) {
                            HomeBeanszq homeBeans = lists.get(redOnclickIndex);
                            HomeRedMessagezq homeRedMessage = homeBeans.getHomeRedMessage();
                            if (homeRedMessage != null) {
                                homeRedMessage.setStatus(1);
                                homeAdapter.notifyItemChanged(redOnclickIndex);
                            }
                        } else if (redOnclickType == 5) {
                            HomeBeanszq homeBeans = lists.get(redOnclickIndex);
                            Info1Beanzq info1Beanzq = homeBeans.getInfo1Beanzq();
                            if (info1Beanzq != null) {
                                info1Beanzq.setStatus(1);
                                homeAdapter.notifyItemChanged(redOnclickIndex);
                            }
                        }
                    }
                    if ("4".equals(status)) {
                        isOnclick = false;
                        countDownUtilsThree.setHours(1, 59);
                        mPresenter.getonLineRed(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", on_money);//获取红包金额
                    } else {
                        mPresenter.getMoneyRed(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", jumpRedEvenlopesId);//获取红包金额
                    }
                }
            }

            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                if ("1".equals(isTxLoadAdSuccess)){
                    loadTxTwo();
                    isTxLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("2".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        showVideo(null);
                    }
                }
            }
        });
        // 设置播放时静音状态
        // mRewardVideoAD.setVolumeOn(volumeOn);
        // 拉取广告
        mRewardVideoAD.loadAD();
        // 展示广告
    }

//
//    private ObjectAnimator signScalex;
//    private ObjectAnimator signScaley;
//    private AnimatorSet signAnimatorSet;
//
//    @SuppressLint("WrongConstant")
//    public void startSignAn() {
//        signScalex = ObjectAnimator.ofFloat(ivShare, "scaleX", 1.f, 0.85f, 1.2f, 1.0f);
//        signScaley = ObjectAnimator.ofFloat(ivShare, "scaleY", 1.f, 0.85f, 1.2f, 1.0f);
//        signScalex.setInterpolator(new LinearInterpolator());
//        signScalex.setTarget(ivShare);
//        signScalex.setDuration(1300);
//        signScalex.setRepeatCount(ValueAnimator.INFINITE);//无限循环
//        signScalex.setRepeatMode(ValueAnimator.INFINITE);//
//        signScaley.setTarget(ivShare);
//        signScaley.setDuration(1300);
//        signScaley.setRepeatCount(ValueAnimator.INFINITE);//无限循环
//        signScaley.setRepeatMode(ValueAnimator.INFINITE);//
//        signScaley.setInterpolator(new LinearInterpolator());
//        signAnimatorSet = new AnimatorSet();
//        signAnimatorSet.setDuration(1300);
//        signAnimatorSet.playTogether(signScalex, signScaley);
//        signAnimatorSet.start();
//    }

}