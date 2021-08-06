package com.yc.qqzz.homeModule.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.constants.Constant;
import com.yc.qqzz.dialog.BottomListDialog;
import com.yc.qqzz.dialog.SignDialog;
import com.yc.qqzz.dialog.SnatchDialog;
import com.yc.qqzz.homeModule.adapter.UpgradeTaskitemAdapter;
import com.yc.qqzz.homeModule.bean.DayUpgradeDayLeveAddBeans;
import com.yc.qqzz.homeModule.bean.UpgradeTaskitemBeans;
import com.yc.qqzz.homeModule.contact.DayUpgradeContract;
import com.yc.qqzz.homeModule.module.bean.DayUpgradeDayCashFinshBeans;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.homeModule.present.DayUpgradePresenter;
import com.yc.qqzz.utils.AppSettingUtils;
import com.yc.qqzz.utils.CacheDataUtils;
import com.yc.qqzz.utils.CommonUtils;
import com.yc.qqzz.widget.MyTextSwitchView;
import com.yc.qqzz.widget.NineLuckPan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class DayUpgradeActivity extends BaseActivity<DayUpgradePresenter> implements DayUpgradeContract.View {
    @BindView(R.id.textswitchView)
    MyTextSwitchView textswitchView;
    @BindView(R.id.nineluckpan)
    NineLuckPan nineluckpan;
    @BindView(R.id.line_memberss)
    LinearLayout lineMemberss;
    @BindView(R.id.iv_ac)
    ImageView ivAc;
    private NineLuckPan nineLuckPan;
    private List<String> strImg;
    private UpgradeTaskitemAdapter upgradeTaskitemAdapter;
    private String type;//1：每日升级  2 每日提现
    private int taskStatus;//0:未完成前置任务 1:已完成前置任务,未抽奖 2:已抽奖,未完成提现任务,3:已完成提现任务,未提现 4:已提现
    private int prizeNums;//抽奖次数
    private String cashMoneys;//抽奖金额
    private String videoType;//1  taskStatus 4:已提现看视频   2 前置任务和后置任务
    private String out_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_day_upgrade2;
    }

    @Override
    public void initEventAndData() {
        strImg = new ArrayList<>();
        strImg.add("哈哈哈哈哈哈哈哈哈");
        strImg.add("凡人修仙传哈哈哈");
        strImg.add("斗罗大陆");
        strImg.add("恭喜中奖");
        textswitchView.setBanner(strImg);
        nineLuckPan = findViewById(R.id.nineluckpan);
        type = getIntent().getStringExtra("type");
        nineLuckPan.setType(type);
        nineLuckPan.setOnLuckPanListener(new NineLuckPan.OnLuckPanListener() {
            @Override
            public void onLuckStart() {

            }

            @Override
            public void onAnimEnd(int position, String msg) {
                initDatas();
                if ("1".equals(type)) {//升级
                    if (prizeType == 1) {//红包
                        redDialog(prizeMoneys);
                    } else {//升级
                        showUpgradeDialog();
                    }
                } else {
                    if (prizeType == 1) {//红包
                        redDialog(prizeMoneys);
                    } else {//提现
                        cashGoDialog(prizeMoneys, "1");
                    }
                }
            }
        });
        upgradeTaskDialog();
        initDatas();
        loadTx();
        loadVideo();
        upgradeDialog();
    }

    private SnatchDialog prizeDialog;

    public void redDialog(String moneys) {
        prizeDialog = new SnatchDialog(this);
        View builder = prizeDialog.builder(R.layout.reds_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_typeName = builder.findViewById(R.id.tv_typeName);
        TextView tv_moneys = builder.findViewById(R.id.tv_money);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        tv_typeName.setText("抽奖红包");
        if (!TextUtils.isEmpty(moneys)) {
            tv_moneys.setText(moneys);
        }
        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RobRedEvenlopesActivity.robRedEvenlopesJump(DayUpgradeActivity.this, "5", "抽奖红包", "", moneys, "", "");
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prizeDialog.setDismiss();
            }
        });
        prizeDialog.setShow();
    }

    public void initDatas() {
        int userId = CacheDataUtils.getInstance().getUserInfo().getId();
        if (!TextUtils.isEmpty(type) && "1".equals(type)) {
            mPresenter.getDayUpLelet(userId);
        } else {
            mPresenter.getDayCash(userId);
        }
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void DayUpgradeActivityJump(Context context, String type) {
        Intent intent = new Intent(context, DayUpgradeActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    @OnClick({R.id.line_answer, R.id.line_withdraw, R.id.line_mine, R.id.line_memberss,  R.id.line_start})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.line_answer:
                intent = new Intent(DayUpgradeActivity.this, MainActivity.class);
                intent.putExtra("position", "1");
                startActivity(intent);
                break;
            case R.id.line_withdraw:
                intent = new Intent(DayUpgradeActivity.this, MainActivity.class);
                intent.putExtra("position", "2");
                startActivity(intent);
                break;
            case R.id.line_mine:
                intent = new Intent(DayUpgradeActivity.this, MainActivity.class);
                intent.putExtra("position", "3");
                startActivity(intent);
                break;
            case R.id.line_memberss:
                intent = new Intent(DayUpgradeActivity.this, MainActivity.class);
                intent.putExtra("position", "0");
                startActivity(intent);
                break;
            case R.id.line_start:
                if (!TextUtils.isEmpty(out_money) && !"0".equals(out_money)) {
                    cashGoDialog(out_money, "2");
                } else {
                    if (taskStatus == 0) {//未完成前置任务
                        showTaskDialog();
                    } else if (taskStatus == 1) {//已完成前置任务,未抽奖
                        UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
                        if ("1".equals(type)) {//每日升级
                            mPresenter.getDaylevelfinish(userInfo.getId());
                        } else {
                            mPresenter.getDaycashfinish(userInfo.getId());
                        }
                    } else if (taskStatus == 2) {//已抽奖,未完成提现任务
                        showTaskDialog();
                    } else if (taskStatus == 3) {//已完成提现任务,未提现
                        videoType = "1";
                        showjiliAd();
                    } else if (taskStatus == 4) {//已提现
                        videoType = "1";
                        showjiliAd();
                    }
                }
                break;
        }
    }

    private SnatchDialog upgradeDialog;
    private SignDialog withDrawDialog;
    private BottomListDialog upgradeTaskDialog;
    private SnatchDialog cashGoDialog;
    private SignDialog shegnjiSuccessDialog;


    private FrameLayout fl_ad_containerss;
    private TextView tv_sure;
    private boolean isshow;

    public void upgradeDialog() {
        upgradeDialog = new SnatchDialog(this);
        View builder = upgradeDialog.builder(R.layout.upgrade_item);
        tv_sure = builder.findViewById(R.id.tv_sure);
        fl_ad_containerss = builder.findViewById(R.id.fl_ad_containerss);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradeDialog.setDismiss();
            }
        });
        loadExVideo();
    }

    private void showUpgradeDialog() {
        if (upgradeDialog != null) {
            if (tv_sure != null) {
                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upgradeTaskDialog();
                        upgradeDialog.setDismiss();
                    }
                });
            }
            final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
            loadExVideo();
            adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
            upgradeDialog.setDismissListen(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                }
            });
            isshow = adPlatformSDK.showExpressAd();
            upgradeDialog.setShow();
        }
    }

    private void loadExVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadExpressAd(this, "ad_duobao", 300, 200, new AdCallback() {
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
                if (!isshow) {
                    adPlatformSDK.showExpressAd();
                }
            }
        }, fl_ad_containerss);
    }


    public void cashGoDialog(String moneys, String type) {
        cashGoDialog = new SnatchDialog(this);
        View builder = cashGoDialog.builder(R.layout.cashgodialog_item);
        TextView tv_cashSure = builder.findViewById(R.id.tv_sure);
        TextView tv_moneys = builder.findViewById(R.id.tv_moneys);
        if (!TextUtils.isEmpty(moneys)) {
            tv_moneys.setText("+" + moneys + "元");
        }
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_cashSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(type)) {
                    showTaskDialog();
                } else {
                    //跳转到首页
                }
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashGoDialog.setDismiss();
            }
        });
    }

    public void withDrawDialog() {
        withDrawDialog = new SignDialog(this);
        View builder = withDrawDialog.builder(R.layout.withdraw_item_dialog);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawDialog.setDismiss();
            }
        });
        withDrawDialog.setShow();
    }

    public void shegnjiSuccessDialog(int level) {
        shegnjiSuccessDialog = new SignDialog(this);
        View builder = shegnjiSuccessDialog.builder(R.layout.shengjisuccess_item_dialog);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_level = builder.findViewById(R.id.tv_level);
        tv_level.setText(level + "");
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shegnjiSuccessDialog.setShow();
            }
        });
        shegnjiSuccessDialog.setShow();
    }


    private TextView tv_taskNums;
    private int taskPosition;
    private int taskPositionId;
    private int isClick;

    public void upgradeTaskDialog() {
        upgradeTaskDialog = new BottomListDialog(this);
        View builder = upgradeTaskDialog.builder(R.layout.upgrade_task_dialog);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_taskNums = builder.findViewById(R.id.tv_taskNumss);
        RecyclerView recyclerView = builder.findViewById(R.id.recyclerView);
        upgradeTaskitemAdapter = new UpgradeTaskitemAdapter(null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DayUpgradeActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(upgradeTaskitemAdapter);
        upgradeTaskitemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                taskPosition = position;
                List<UpgradeTaskitemBeans.TaskArrBean> lists = adapter.getData();
                isClick = lists.get(position).getIs_click();
                UpgradeTaskitemBeans.TaskArrBean taskArrBean = lists.get(position);
                if (taskArrBean.getFinish_num() >= taskArrBean.getNum()) {//已完成
                    ToastUtil.showToast("该任务已经完成");
                } else {
                    taskPositionId = taskArrBean.getOther_id();
                    videoType = "2";
                    showjiliAd();
                }
            }
        });

        recyclerView.setLayoutManager(linearLayoutManager);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upgradeTaskDialog.setDismiss();
            }
        });
    }

    public void showTaskDialog() {
        if (upgradeTaskDialog != null) {
            if (tv_taskNums != null) {
                if (taskStatus == 0) {//前置任务
                    tv_taskNums.setText("仅差1个任务可抽奖提现");
                } else if (taskStatus == 2) {
                    tv_taskNums.setText("完成任务即可升级成功");
                }
            }
            upgradeTaskDialog.setShow();
        }
    }

    private List<UpgradeTaskitemBeans.DaylevelBean> daylevel;
    private List<UpgradeTaskitemBeans.DayCashBean> day_cash;

    @Override
    public void getDayUpLeletSuccess(UpgradeTaskitemBeans data) {
        if (data != null) {
            this.taskStatus = data.getState();
            daylevel = data.getDaylevel();
            prizeNums = data.getNum();
            int is_before = data.getIs_before();
            int[] mImgs = new int[]{R.drawable.prize1, R.drawable.luckpan1, R.drawable.prize1, R.drawable.luckpan1, R.drawable.prize1, R.drawable.luckpan1, R.drawable.prize1, R.drawable.luckpan1, R.drawable.ljcj};
            String[] mLuckStr = new String[8];
            if (daylevel != null) {
                for (int i = 0; i < daylevel.size(); i++) {
                    if (daylevel.get(i).getType() == 2) {//升级
                        mLuckStr[i] = "升" + daylevel.get(i).getLevel_num() + "级";
                    } else {//2 提现
                        mLuckStr[i] = daylevel.get(i).getMoney() + "元";
                    }
                }
            }
            nineLuckPan.setmImgs(mImgs);
            nineLuckPan.setmLuckStr(mLuckStr);
            if (taskStatus == 0) {//未完成前置任务
                List<UpgradeTaskitemBeans.TaskArrBean> task_arr = data.getTask_arr();
                if (upgradeTaskitemAdapter != null) {
                    upgradeTaskitemAdapter.setNewData(task_arr);
                }
            } else if (taskStatus == 1) {//已完成前置任务,未抽奖
                List<UpgradeTaskitemBeans.TaskArrBean> after_arr = data.getAfter_task();
                if (upgradeTaskitemAdapter != null) {
                    upgradeTaskitemAdapter.setNewData(after_arr);
                }
            } else if (taskStatus == 2) {//已抽奖,未完成提现任务
                List<UpgradeTaskitemBeans.TaskArrBean> after_arr = data.getAfter_task();
                if (upgradeTaskitemAdapter != null) {
                    upgradeTaskitemAdapter.setNewData(after_arr);
                }
            } else if (taskStatus == 3) {//已完成提现任务,未提现
                List<UpgradeTaskitemBeans.TaskArrBean> task_arr = data.getTask_arr();
                if (upgradeTaskitemAdapter != null) {
                    upgradeTaskitemAdapter.setNewData(task_arr);
                }
            } else if (taskStatus == 4) {//已提现
                List<UpgradeTaskitemBeans.TaskArrBean> task_arr = data.getTask_arr();
                if (upgradeTaskitemAdapter != null) {
                    upgradeTaskitemAdapter.setNewData(task_arr);
                }
            }
            upgradeTaskitemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDayCashSuccess(UpgradeTaskitemBeans data) {
        if (data != null) {
            this.taskStatus = data.getState();
            day_cash = data.getDay_cash();
            prizeNums = data.getNum();
            int is_before = data.getIs_before();
            out_money = data.getOut_money();
            int[] mImgs = new int[]{R.drawable.luckpan1, R.drawable.luckpan1, R.drawable.luckpan1, R.drawable.luckpan1, R.drawable.luckpan1, R.drawable.luckpan1, R.drawable.luckpan1, R.drawable.luckpan1, R.drawable.ljcj};
            String[] mLuckStr = new String[8];
            if (day_cash != null) {
                for (int i = 0; i < day_cash.size(); i++) {
                    mLuckStr[i] = day_cash.get(i).getMoney() + "元";
                }
            }
            nineLuckPan.setmImgs(mImgs);
            nineLuckPan.setmLuckStr(mLuckStr);
            if (taskStatus == 0) {//未完成前置任务
                List<UpgradeTaskitemBeans.TaskArrBean> task_arr = data.getTask_arr();
                if (upgradeTaskitemAdapter != null) {
                    upgradeTaskitemAdapter.setNewData(task_arr);
                }
            } else if (taskStatus == 1) {//已完成前置任务,未抽奖
                List<UpgradeTaskitemBeans.TaskArrBean> after_arr = data.getAfter_task();
                if (upgradeTaskitemAdapter != null) {
                    upgradeTaskitemAdapter.setNewData(after_arr);
                }
            } else if (taskStatus == 2) {//已抽奖,未完成提现任务
                List<UpgradeTaskitemBeans.TaskArrBean> after_arr = data.getAfter_task();
                if (upgradeTaskitemAdapter != null) {
                    upgradeTaskitemAdapter.setNewData(after_arr);
                }
            } else if (taskStatus == 3) {//已完成提现任务,未提现
                List<UpgradeTaskitemBeans.TaskArrBean> task_arr = data.getTask_arr();
                if (upgradeTaskitemAdapter != null) {
                    upgradeTaskitemAdapter.setNewData(task_arr);
                }
            } else if (taskStatus == 4) {//已提现
                List<UpgradeTaskitemBeans.TaskArrBean> task_arr = data.getTask_arr();
                if (upgradeTaskitemAdapter != null) {
                    upgradeTaskitemAdapter.setNewData(task_arr);
                }
            }
            upgradeTaskitemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDayleveltaskaddSuccess(DayUpgradeDayLeveAddBeans data) {
        if (data != null) {
            int finish_num = data.getFinish_num();
            int new_level = data.getNew_level();
            List<UpgradeTaskitemBeans.TaskArrBean> lists = upgradeTaskitemAdapter.getData();
            lists.get(taskPosition).setFinish_num(finish_num);
            upgradeTaskitemAdapter.notifyDataSetChanged();
            if (new_level > 0) {//任务已完成
                if (taskStatus == 0) {//前置任务 刷新
                    taskStatus = 1;
                    if (upgradeTaskDialog != null) {
                        upgradeTaskDialog.setDismiss();
                    }
                } else {//后置任务
                    taskStatus = 4;
                    shegnjiSuccessDialog(new_level);
                }
                initDatas();
            }
        }
    }

    private SnatchDialog showjiesuoTaskErrorDialog;

    private void showjiesuoTaskError() {
        CacheDataUtils.getInstance().setLevel("1");
        showjiesuoTaskErrorDialog = new SnatchDialog(this);
        View builder = showjiesuoTaskErrorDialog.builder(R.layout.jiesuotaskerror_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showjiesuoTaskErrorDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(DayUpgradeActivity.this)) {
            showjiesuoTaskErrorDialog.setShow();
        }
    }


    @Override
    public void getDaycashtaskaddSuccess(DayUpgradeDayLeveAddBeans data) {
        if (data != null) {
            int finish_num = data.getFinish_num();
            int is_finish = data.getIs_finish();
            List<UpgradeTaskitemBeans.TaskArrBean> lists = upgradeTaskitemAdapter.getData();
            lists.get(taskPosition).setFinish_num(finish_num);
            upgradeTaskitemAdapter.notifyDataSetChanged();
            if (is_finish == 1) {//任务已完成
                if (taskStatus == 0) {//前置任务 刷新
                    taskStatus = 1;
                    if (upgradeTaskDialog != null) {
                        upgradeTaskDialog.setDismiss();
                    }
                } else {//后置任务
                    taskStatus = 4;
                    withDrawDialog();
                }
                initDatas();
            }
        }
    }

    private int prizeType;//中奖类型  1红包  2 提现 升级
    private int priziPosition;
    private String prizeMoneys;//中奖金额
    private int prizeLevel;//中奖升级级数

    @Override
    public void getDaycashfinishSuccess(DayUpgradeDayCashFinshBeans data) {
        if (data != null && day_cash != null) {
            for (int i = 0; i < day_cash.size(); i++) {
                if (data.getId() == day_cash.get(i).getId()) {//中奖的id
                    priziPosition = i;
                    prizeType = day_cash.get(i).getType();
                    prizeMoneys = day_cash.get(i).getMoney();
                }
            }
            nineLuckPan.setmLuckNum(priziPosition);
            nineLuckPan.startAnim();
        }
    }

    @Override
    public void getDaylevelfinishSuccess(DayUpgradeDayCashFinshBeans data) {
        if (data != null && daylevel != null) {
            for (int i = 0; i < daylevel.size(); i++) {
                if (data.getId() == daylevel.get(i).getId()) {//中奖的id
                    priziPosition = i;
                    prizeType = daylevel.get(i).getType();
                    prizeLevel = daylevel.get(i).getLevel_num();
                }
            }
            nineLuckPan.setmLuckNum(priziPosition);
            nineLuckPan.startAnim();
        }
    }

    private void showjiliAd() {
        if ("1".equals(AppSettingUtils.getVideoType())) {//先头条
            showVideo();
        } else {
            showTx();
        }
    }


    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        isLoadAdSuccess = "1";
        isVideoClick = false;
        loadVideo();
        adPlatformSDK.showRewardVideoAd();
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
    }

    private String isLoadAdSuccess = "0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private int videoCounts;

    private void loadVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_dazhuangpan", new AdCallback() {
            @Override
            public void onDismissed() {
                if ("1".equals(videoType)) {
                    ToastUtil.showToast("今日抽奖任务已经完成，请明日再来");
                } else if ("2".equals(videoType)) {//前置任务和后置任务
                    if ("1".equals(type)) {//每日升级
                        if (taskStatus == 0) {
                            mPresenter.getDayleveltaskadd(CacheDataUtils.getInstance().getUserInfo().getId(), taskPositionId, "1");
                        } else {
                            mPresenter.getDayleveltaskadd(CacheDataUtils.getInstance().getUserInfo().getId(), taskPositionId, "2");
                        }
                    } else {//每日提现
                        if (taskStatus == 0) {
                            mPresenter.getDaycashtaskadd(CacheDataUtils.getInstance().getUserInfo().getId(), taskPositionId, "1");
                        } else {
                            mPresenter.getDaycashtaskadd(CacheDataUtils.getInstance().getUserInfo().getId(), taskPositionId, "2");
                        }
                    }
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                if ("1".equals(isLoadAdSuccess)) {
                    isLoadAdSuccess = "2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                        showTx();
                    } else {
                        if (!CommonUtils.isDestory(DayUpgradeActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }
                }
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {
                isLoadAdSuccess = "3";

            }

            @Override
            public void onClick() {
                isVideoClick = true;
            }

            @Override
            public void onLoaded() {
                isLoadAdSuccess = "3";
            }
        });
    }

    public void showTx() {
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                if (!CommonUtils.isDestory(DayUpgradeActivity.this)) {
                    ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                }
            } else {
                showVideo();
            }
        } else {
            VideoAdValidity validity = mRewardVideoAD.checkValidity();
            switch (validity) {
                case SHOWED:
                case OVERDUE:
                    loadTxTwo();
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                        if (!CommonUtils.isDestory(DayUpgradeActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    } else {
                        showVideo();
                    }
                    return;
                // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                case NONE_CACHE:
                    //  showToast("广告素材未缓存成功！");
//            return;
                case VALID:
                    // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                    isTxLoadAdSuccess = "1";
                    isVideoClick = false;
                    mRewardVideoAD
                            .showAD(DayUpgradeActivity.this);
                    // 展示广告
                    break;
            }
        }
    }


    public void loadTxTwo() {
        mIsLoaded = false;
        loadTx();
    }

    private ExpressRewardVideoAD mRewardVideoAD;
    private boolean mIsLoaded;
    private boolean mIsCached;
    private String isTxLoadAdSuccess = "0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private boolean isVideoClick;

    public void loadTx() {
        mRewardVideoAD = new ExpressRewardVideoAD(this, Constant.TXRVIDEO, new ExpressRewardVideoAdListener() {
            @Override
            public void onAdLoaded() {
                mIsLoaded = true;
                isTxLoadAdSuccess = "3";
            }

            @Override
            public void onVideoCached() {
                // 在视频缓存完成之后再进行广告展示，以保证用户体验
                mIsCached = true;
                Log.i("ccc", "onVideoCached: ");
            }

            @Override
            public void onShow() {
                isTxLoadAdSuccess = "3";
                AppSettingUtils.showTxShow("tx_ad_dazhuangpan");
                if (!CommonUtils.isDestory(DayUpgradeActivity.this)) {
                    videoCounts = 1;
                }
            }

            @Override
            public void onExpose() {
                Log.i("ccc", "onExpose: ");
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
                isVideoClick = true;
                AppSettingUtils.showTxClick("tx_ad_dazhuangpan");
            }

            @Override
            public void onVideoComplete() {
                if (mRewardVideoAD.hasShown()) {
                    loadTxTwo();
                }

            }

            @Override
            public void onClose() {
                if (mRewardVideoAD.hasShown()) {
                    loadTxTwo();
                }
                if ("1".equals(videoType)) {
                    ToastUtil.showToast("今日抽奖任务已经完成，请明日再来");
                } else if ("2".equals(videoType)) {//前置任务和后置任务
                    if ("1".equals(type)) {//每日升级
                        if (taskStatus == 0) {
                            mPresenter.getDayleveltaskadd(CacheDataUtils.getInstance().getUserInfo().getId(), taskPositionId, "1");
                        } else {
                            mPresenter.getDayleveltaskadd(CacheDataUtils.getInstance().getUserInfo().getId(), taskPositionId, "2");
                        }
                    } else {//每日提现
                        if (taskStatus == 0) {
                            mPresenter.getDaycashtaskadd(CacheDataUtils.getInstance().getUserInfo().getId(), taskPositionId, "1");
                        } else {
                            mPresenter.getDaycashtaskadd(CacheDataUtils.getInstance().getUserInfo().getId(), taskPositionId, "2");
                        }
                    }
                }
            }

            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                if ("1".equals(isTxLoadAdSuccess)) {
                    isTxLoadAdSuccess = "2";
                    //失败了播放腾讯的
                    if ("2".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                        showVideo();
                    } else {
                        if (!CommonUtils.isDestory(DayUpgradeActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
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

}