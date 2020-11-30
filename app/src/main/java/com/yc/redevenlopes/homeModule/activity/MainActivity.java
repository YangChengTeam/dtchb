package com.yc.redevenlopes.homeModule.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.DynamicTimeFormat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.application.MyApplication;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.constants.Constant;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.dialog.UpdateDialog;
import com.yc.redevenlopes.homeModule.adapter.HomeAdapter;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.fragment.ExitTintFragment;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeGetRedMoneyBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeMsgBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeOnlineBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeRedMessage;
import com.yc.redevenlopes.homeModule.module.bean.Info0Bean;
import com.yc.redevenlopes.homeModule.module.bean.Info1Bean;
import com.yc.redevenlopes.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpgradeInfo;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.present.MainPresenter;
import com.yc.redevenlopes.homeModule.widget.BCRefreshHeader;
import com.yc.redevenlopes.homeModule.widget.DividerItemLastDecorations;
import com.yc.redevenlopes.service.event.Event;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.ClickListenName;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.CountDownUtilsThree;
import com.yc.redevenlopes.utils.DisplayUtil;
import com.yc.redevenlopes.utils.TimesUtils;
import com.yc.redevenlopes.utils.ToastUtilsViews;
import com.yc.redevenlopes.utils.UpDataVersion;
import com.yc.redevenlopes.utils.VUiKit;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContact.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.line_snatchTreasure)
    LinearLayout lineSnatchTreasure;
    @BindView(R.id.line_duobao)
    LinearLayout lineDuobao;
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
    @BindView(R.id.iv_ac)
    ImageView ivAc;
    @BindView(R.id.tv_redTimes)
    TextView tvRedTimes;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    private HomeAdapter homeAdapter;
    private OtherBeans otherBeans;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initEventAndData() {
        EventBus.getDefault().register(this);
        initViews();
        initRecyclerView();
        initData();
        initTimes();
    }


    private void initViews() {
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        tvTitle.setText("红包" + userInfo.getGroup_id() + "群");

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
                tvRedTimes.setText("领取");
            }
        });
    }

    private void initData() {
        if (CacheDataUtils.getInstance().isLogin()) {
            mPresenter.upVersion(((MyApplication) MyApplication.getInstance()).getAgentId());
            UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
            mPresenter.getHomeData(userInfo.getGroup_id() + "");
            mPresenter.getOtherInfo(userInfo.getGroup_id() + "", userInfo.getId() + "");
            mPresenter.getMsgList(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", page, "10");
        }
    }

    private void initRecyclerView() {
        srlRefresh.setEnableAutoLoadMore(false);//开启自动加载功能（非必须）
        srlRefresh.setEnableLoadMore(false);
        srlRefresh.setEnableRefresh(false);
        BCRefreshHeader bcRefreshHeader=new BCRefreshHeader(this){
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
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        homeAdapter = new HomeAdapter(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeAdapter);
        recyclerView.addItemDecoration(new DividerItemLastDecorations(this, R.drawable.devider_grey_1_14dp, homeAdapter.getData().size()));
        recyclerView.scrollToPosition(homeAdapter.getData().size() - 1);
        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.line_open:
                        List<HomeBeans> lists = adapter.getData();
                        HomeBeans homeBeans = lists.get(position);
                        if (homeBeans.getItemType() == Constant.TYPE_TWO) {
                            HomeRedMessage homeRedMessage = homeBeans.getHomeRedMessage();
                            redTypeName = homeRedMessage.getTypename();
                            balanceMoney = "";
                            if ("1".equals(homeRedMessage.getStype())) {//手气红包
                                balanceMoney = homeRedMessage.getBalance_money();
                            }
                            jumpRedEvenlopesId=homeRedMessage.getId()+"";
                            redOnclickType=2;
                            redOnclickIndex=position;
                            mPresenter.getRedEvenlopsInfo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", homeRedMessage.getId() + "");
                        } else if (homeBeans.getItemType() == Constant.TYPE_FIVE) {//
                            Info1Bean info1Bean = homeBeans.getInfo1Bean();
                            redTypeName = info1Bean.getTypename();
                            String moneys = "";
                            if (!TextUtils.isEmpty(info1Bean.getMoney()) && !"0.00".equals(info1Bean.getMoney()) && !"0".equals(info1Bean.getMoney())) {
                                moneys = info1Bean.getMoney();
                            } else {
                                moneys = info1Bean.getMember_money();
                            }
                            jumpRedEvenlopesId=info1Bean.getId()+"";
                            redOnclickType=5;
                            redOnclickIndex=position;
                            if (info1Bean.getStatus()==1){
                                RobRedEvenlopesActivity.robRedEvenlopesJump(MainActivity.this, "1", redTypeName, balanceMoney, "",jumpRedEvenlopesId);
                            }else {
                                showRedDialog(moneys, info1Bean.getTypename(), "", info1Bean.getStatus() + "");
                            }
                        }
                        break;
                    case R.id.line_member:
                        MemberActivity.memberJump(MainActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        mPresenter.getOtherInfo(userInfo.getGroup_id() + "", userInfo.getId() + "");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.line_members, R.id.line_activitys, R.id.line_snatchTreasure, R.id.line_withdraw, R.id.iv_avatar, R.id.iv_red})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_members:
                MobclickAgent.onEvent(this, "member","1");//参数二为当前统计的事件ID
                MemberActivity.memberJump(this);
                break;
            case R.id.line_activitys:
                MobclickAgent.onEvent(this, "activity");//参数二为当前统计的事件ID
                showPopupWindow();
                break;
            case R.id.line_snatchTreasure:
                MobclickAgent.onEvent(this, "snatchTraeasure");//参数二为当前统计的事件ID
                SnatchTreasureActivity.snatchTreasureJump(this);
                break;
            case R.id.line_withdraw:
                MobclickAgent.onEvent(this, "withdraw");//参数二为当前统计的事件ID
                WithdrawActivity.WithdrawJump(this);
                break;
            case R.id.iv_avatar:
                if (TextUtils.isEmpty(cashMoney)) {
                    cashMoney = "";
                }
                MemberCenterActivity.memberCenterJump(this, cashMoney);
                break;
            case R.id.iv_red:
                if (isOnclick) {
                    jumpRedEvenlopesId="";
                    showRedDialog(on_money, "在线红包", "", "4");
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
                        if (!TextUtils.isEmpty(msgId)) {
                            mPresenter.getHomeMsgDataPolling(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", msgId);
                        }
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
                        if (!TextUtils.isEmpty(hongbao_id)) {  //红包消息轮询
                            mPresenter.getHomeMessageRedData(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", hongbao_id);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    RedDialog redDialog;

    public void showRedDialog(String money, String redTypeName, String balanceMoney, String status) {
        redDialog = new RedDialog(this);
        View builder = redDialog.builder(R.layout.red_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_type = builder.findViewById(R.id.tv_typeName);
        TextView tv_money = builder.findViewById(R.id.tv_money);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        LinearLayout line_getRed = builder.findViewById(R.id.line_getRed);
        RelativeLayout rela_status = builder.findViewById(R.id.rela_status);
        TextView tv_getRedDetails = builder.findViewById(R.id.tv_getRedDetails);
        TextView tv_getRedDes = builder.findViewById(R.id.tv_getRedDes);

        if ("0".equals(status)) {
            line_getRed.setVisibility(View.VISIBLE);
            rela_status.setVisibility(View.GONE);
        } else if ("1".equals(status)) {
            tv_getRedDes.setVisibility(View.VISIBLE);
            tv_getRedDes.setText("您已经领取该改红包了");
            line_getRed.setVisibility(View.GONE);
            rela_status.setVisibility(View.VISIBLE);
        } else if ("2".equals(status)) {
            tv_getRedDes.setVisibility(View.VISIBLE);
            line_getRed.setVisibility(View.GONE);
            rela_status.setVisibility(View.VISIBLE);
        } else if ("4".equals(status)) {
            line_getRed.setVisibility(View.VISIBLE);
            rela_status.setVisibility(View.GONE);
        }
        tv_getRedDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<HomeBeans> lists = homeAdapter.getData();
                if (redOnclickType==2){
                    HomeBeans homeBeans = lists.get(redOnclickIndex);
                    HomeRedMessage homeRedMessage = homeBeans.getHomeRedMessage();
                    homeRedMessage.setStatus(1);
                    homeAdapter.notifyItemChanged(redOnclickIndex);
                }else if (redOnclickType==5){
                    HomeBeans homeBeans = lists.get(redOnclickIndex);
                    Info1Bean info1Bean = homeBeans.getInfo1Bean();
                    info1Bean.setStatus(1);
                    homeAdapter.notifyItemChanged(redOnclickIndex);
                }
                mPresenter.getMoneyRed(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", jumpRedEvenlopesId);//获取红包金额
            }
        });
        if (!TextUtils.isEmpty(redTypeName)) {
            tv_type.setText(redTypeName);
        }
        MainActivity.this.redTypeName = redTypeName;
        tv_money.setText(money);
        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//看广告
                if (ClickListenName.isFastClick()){
                    showVideo(status);
                    redDialog.setDismiss();
                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redDialog.setDismiss();
            }
        });
        VUiKit.postDelayed(2000, () -> {
            iv_close.setVisibility(View.VISIBLE);
        });
        redDialog.setShow();
    }

    private void showPopupWindow() {
        ivAc.setImageDrawable(getResources().getDrawable(R.drawable.bottom_activity2));
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_home_item, null);
        ConstraintLayout line_answer = inflate.findViewById(R.id.cons_answer);
        ConstraintLayout line_guessr = inflate.findViewById(R.id.cons_guess);
        ConstraintLayout line_turn = inflate.findViewById(R.id.cons_turn);
        line_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerActivity.answerJump(MainActivity.this);
                popupWindow.dismiss();
            }
        });
        line_guessr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuessingActivity.GuessingJump(MainActivity.this);
                popupWindow.dismiss();
            }
        });
        line_turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TurnTableActivity.TurnTableJump(MainActivity.this);
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(inflate);
        popupWindow.setFocusable(true);
        // 设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // 外部点击事件
        popupWindow.setOutsideTouchable(true);
        int screenHeight = CommonUtils.getScreenHeight(this);
        int screenWidth = CommonUtils.getScreenWidth(this);
        int statusBarHeight = CommonUtils.getStatusBarHeight(this);
        int he = DisplayUtil.dip2px(this, 114);
        int wh = screenWidth / 8;
        popupWindow.showAtLocation(lineLay, Gravity.TOP, -wh, screenHeight - he - statusBarHeight);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivAc.setImageDrawable(getResources().getDrawable(R.drawable.bottom_activity));
            }
        });
    }

    @Override
    public void getHomeDataSuccess(HomeAllBeans data) {
        if (data != null) {
            tvMoney.setText(data.getUser_other().getCash() + "元");
            cashMoney = data.getUser_other().getCash();
            on_money = data.getOn_money();
            VUiKit.postDelayed(400, () -> {
                HomeBeans homeBeans = new HomeBeans();
                homeBeans.setItemType(Constant.TYPE_THREE);
                homeBeans.setHomeAllBeans(data);
                homeAdapter.addData(homeBeans);

                HomeBeans homeBeansTwo = new HomeBeans();
                homeBeansTwo.setItemType(Constant.TYPE_FOUR);
                homeBeansTwo.setHomeAllBeans(data);
                homeAdapter.addData(homeBeansTwo);
                int itemDecorationCount = recyclerView.getItemDecorationCount();
                if (itemDecorationCount > 0) {
                    for (int i = 0; i < itemDecorationCount; i++) {
                        recyclerView.removeItemDecorationAt(i);
                    }
                }
                recyclerView.addItemDecoration(new DividerItemLastDecorations(this, R.drawable.devider_grey_1_14dp, homeAdapter.getData().size()));
                recyclerView.scrollToPosition(homeAdapter.getData().size() - 1);
            });
            if (data.getOnline_red() == 0) {
                isOnclick = true;
            } else {
                long sys_time = data.getSys_time() * 1000;
                long online_red = data.getOnline_red() * 1000;
                long yuTimes = sys_time - online_red;
                isOnclick = true;
                if (data.getSys_time()-data.getOnline_red()<120){
                    if (yuTimes > 0) {
                        isOnclick = false;
                        countDownUtilsThree.setHours(TimesUtils.getMinDiff(yuTimes), TimesUtils.getSecondDiff(yuTimes));
                    }
                }
            }
        }
    }

    @Override
    public void getOtherInfoSuccess(OtherBeans data) {
        this.otherBeans = data;
        tvRank.setText("LV." + data.getLevel() + "");
        tvMoney.setText(data.getCash()+"元");
    }

    @Override
    public void getHomeMessageRedDataInfo(List<HomeRedMessage> data) {
        VUiKit.postDelayed(400, () -> {
            for (int i = 0; i < data.size(); i++) {
                if (i == 0) {
                    hongbao_id = data.get(i).getId() + "";
                }
                HomeBeans homeBeans = new HomeBeans();
                homeBeans.setItemType(Constant.TYPE_TWO);
                homeBeans.setHomeRedMessage(data.get(i));
                homeAdapter.addData(homeBeans);
            }
            int itemDecorationCount = recyclerView.getItemDecorationCount();
            if (itemDecorationCount > 0) {
                for (int i = 0; i < itemDecorationCount; i++) {
                    recyclerView.removeItemDecorationAt(i);
                }
            }
            recyclerView.addItemDecoration(new DividerItemLastDecorations(this, R.drawable.devider_grey_1_14dp, homeAdapter.getData().size()));
            recyclerView.scrollToPosition(homeAdapter.getData().size() - 1);
        });
    }

    @Override
    public void getRedEvenlopsInfoSuccess(OpenRedEvenlopes data) {
        jumpRedEvenlopesId=data.getId()+"";
        if (data.getStatus()==1){
            List<HomeBeans> lists = homeAdapter.getData();
            if (redOnclickType==2){
                HomeBeans homeBeans = lists.get(redOnclickIndex);
                HomeRedMessage homeRedMessage = homeBeans.getHomeRedMessage();
                homeRedMessage.setStatus(1);
                homeAdapter.notifyItemChanged(redOnclickIndex);
            }
            RobRedEvenlopesActivity.robRedEvenlopesJump(MainActivity.this, "1", redTypeName, balanceMoney, data.getBalance_money(),jumpRedEvenlopesId);
        }else {
            showRedDialog(data.getBalance_money(), redTypeName, balanceMoney, data.getStatus() + "");
        }
    }

    @Override
    public void getMsgListSuccess(List<HomeMsgBeans> data) {
        for (int i = 0; i < data.size(); i++) {
            int stype = data.get(i).getStype();
            if (stype == 0) {
                Info0Bean info0 = data.get(i).getInfo0();
                if (TextUtils.isEmpty(msgId)) {
                    msgId = info0.getId() + "";
                }
                info0.setStype(0);
                HomeBeans homeBeans = new HomeBeans();
                homeBeans.setInfo0Bean(info0);
                homeBeans.setItemType(Constant.TYPE_ONE);
                homeAdapter.addData(homeBeans);
            } else {
                Info1Bean info1 = data.get(i).getInfo1();
                if (TextUtils.isEmpty(hongbao_id)) {
                    hongbao_id = info1.getId() + "";
                }
                info1.setStype(1);
                HomeBeans homeBeans = new HomeBeans();
                homeBeans.setInfo1Bean(info1);
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
        recyclerView.addItemDecoration(new DividerItemLastDecorations(this, R.drawable.devider_grey_1_14dp, homeAdapter.getData().size()));
        recyclerView.scrollToPosition(homeAdapter.getData().size() - 1);
        if (data.size()<10){
             srlRefresh.setEnableRefresh(false);
        }else {
            srlRefresh.setEnableRefresh(true);
        }
    }

    @Override
    public void getHomeMsgDataPollingSuccess(List<Info0Bean> data) {
        if (data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (i == 0) {
                    msgId = data.get(0).getId() + "";
                }
                HomeBeans homeBeans = new HomeBeans();
                Info0Bean info0Bean = data.get(i);
                info0Bean.setStype(0);
                homeBeans.setItemType(Constant.TYPE_ONE);
                homeBeans.setInfo0Bean(info0Bean);
                homeAdapter.addData(homeBeans);
            }
            int itemDecorationCount = recyclerView.getItemDecorationCount();
            if (itemDecorationCount > 0) {
                for (int i = 0; i < itemDecorationCount; i++) {
                    recyclerView.removeItemDecorationAt(i);
                }
            }
            recyclerView.addItemDecoration(new DividerItemLastDecorations(this, R.drawable.devider_grey_1_14dp, homeAdapter.getData().size()));
            recyclerView.scrollToPosition(homeAdapter.getData().size() - 1);
        }
    }

    @Override
    public void getMoneyRedSuccess(HomeGetRedMoneyBeans data) {
        if (data.getNew_level()>0){
            if (otherBeans!=null){
                int le= otherBeans.getLevel()+1;
                tvRank.setText("LV."+le);
            }
        }
        tvMoney.setText(data.getCash()+"元");
        RobRedEvenlopesActivity.robRedEvenlopesJump(MainActivity.this, "1", redTypeName, balanceMoney, data.getRed_money(),jumpRedEvenlopesId);
        if (redDialog != null) {
            redDialog.setDismiss();
        }
    }

    @Override
    public void updtreasureSuccess(UpQuanNumsBeans data) {//更新券回调

    }

    @Override
    public void getonLineRedSuccess(HomeOnlineBeans data) {//在线红包
        if (data.getNew_level()>0){
            if (otherBeans!=null){
                int le= otherBeans.getLevel()+1;
                tvRank.setText("LV."+le);
            }
        }
        tvMoney.setText(data.getCash()+"元");
        RobRedEvenlopesActivity.robRedEvenlopesJump(MainActivity.this, "3", "在线红包", "", data.getRed_money(),"");
        if (redDialog != null) {
            redDialog.setDismiss();
        }
    }

    @Override
    public void upVersionSuccess(UpDataVersion data) {
        UpgradeInfo upgradeInfo = new UpgradeInfo();
        upgradeInfo.setDesc(data.getUpdate_content());
        upgradeInfo.setDownUrl(data.getDownload_url());
        upgradeInfo.setVersion(data.getVersion_name());
        upgradeInfo.setVersionCode(data.getVersion_code());
        upgradeInfo.setForce_update(data.getForce_update());
        try {
            PackageInfo info = getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);

            if (upgradeInfo != null && upgradeInfo.getVersionCode() > info.versionCode) {
                UpdateDialog dialog = new UpdateDialog(this);
                dialog.setInfo(upgradeInfo);
                dialog.show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMsgListTwoSuccess(List<HomeMsgBeans> data) {//加载跟多
        srlRefresh.finishRefresh();
        if (data.size()>0){
            int lastItemPosition=0;
            if (linearLayoutManager!=null){
                lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            }
            for (int i = 0; i < data.size(); i++) {
                int stype = data.get(i).getStype();
                if (stype == 0) {
                    Info0Bean info0 = data.get(i).getInfo0();
                    if (TextUtils.isEmpty(msgId)) {
                        msgId = info0.getId() + "";
                    }
                    info0.setStype(0);
                    HomeBeans homeBeans = new HomeBeans();
                    homeBeans.setInfo0Bean(info0);
                    homeBeans.setItemType(Constant.TYPE_ONE);
                    homeAdapter.addData(0,homeBeans);
                } else {
                    Info1Bean info1 = data.get(i).getInfo1();
                    if (TextUtils.isEmpty(hongbao_id)) {
                        hongbao_id = info1.getId() + "";
                    }
                    info1.setStype(1);
                    HomeBeans homeBeans = new HomeBeans();
                    homeBeans.setInfo1Bean(info1);
                    homeBeans.setItemType(Constant.TYPE_FIVE);
                    homeAdapter.addData(0,homeBeans);
                }
            }
            int itemDecorationCount = recyclerView.getItemDecorationCount();
            if (itemDecorationCount > 0) {
                for (int i = 0; i < itemDecorationCount; i++) {
                    recyclerView.removeItemDecorationAt(i);
                }
            }
            recyclerView.addItemDecoration(new DividerItemLastDecorations(this, R.drawable.devider_grey_1_14dp, homeAdapter.getData().size()));
            if (data.size()>1){
                recyclerView.scrollToPosition(lastItemPosition+data.size()-1);
            }else {
                recyclerView.scrollToPosition(lastItemPosition+data.size());
            }
        }
        if (data.size()<10){
            srlRefresh.setEnableRefresh(false);
        }else {
            srlRefresh.setEnableRefresh(true);
        }
    }

    @Override
    public void getMsgListTwoError() {
        srlRefresh.finishRefresh();
        srlRefresh.setEnableRefresh(false);
    }


    @Override
    public void onBackPressed() {
        ExitTintFragment exitTintFragment = new ExitTintFragment();
        exitTintFragment.show(getSupportFragmentManager(), "");
        exitTintFragment.setExitListener(new ExitTintFragment.OnExitListener() {
            @Override
            public void onExit() {
                finish();
            }

            @Override
            public void onSure() {

            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHomePage(Event event) {
        if (event instanceof Event.LoginEvent) {
            initViews();
            if (homeAdapter != null) {
                List<HomeBeans> data = homeAdapter.getData();
                data.clear();
                homeAdapter.notifyDataSetChanged();
            }
            if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getUserInfo().getFace())) {
                Glide.with(this)
                        .load(CacheDataUtils.getInstance().getUserInfo().getFace())
                        .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                        .into(ivAvatar);
            }
            initData();
        } else if (event instanceof Event.CashEvent) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showVideo(String status) {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId()+"");
        adPlatformSDK.showRewardVideoVerticalAd(this, new AdCallback() {
            @Override
            public void onDismissed() {
                ToastUtilsViews.showCenterToast("1","");
                if (redDialog != null) {
                    redDialog.setDismiss();
                }
                List<HomeBeans> lists = homeAdapter.getData();
                if (redOnclickType==2){
                    HomeBeans homeBeans = lists.get(redOnclickIndex);
                    HomeRedMessage homeRedMessage = homeBeans.getHomeRedMessage();
                    homeRedMessage.setStatus(1);
                    homeAdapter.notifyItemChanged(redOnclickIndex);
                }else if (redOnclickType==5){
                    HomeBeans homeBeans = lists.get(redOnclickIndex);
                    Info1Bean info1Bean = homeBeans.getInfo1Bean();
                    info1Bean.setStatus(1);
                    homeAdapter.notifyItemChanged(redOnclickIndex);
                }
                if ("4".equals(status)) {
                    isOnclick = false;
                    countDownUtilsThree.setHours(1, 59);
                    mPresenter.getonLineRed(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", on_money);//获取红包金额
                } else {
                    mPresenter.getMoneyRed(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", jumpRedEvenlopesId);//获取红包金额
                }
            }

            @Override
            public void onNoAd(AdError adError) {

            }

            @Override
            public void onComplete() {
                if (redDialog != null) {
                    redDialog.setDismiss();
                }
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onPresent() {

            }

            @Override
            public void onClick() {

            }
        });
    }

    private String getTv(long l) {
        if (l >= 10) {
            return l + "";
        } else {
            return "0" + l;//小于10,,前面补位一个"0"
        }
    }
}