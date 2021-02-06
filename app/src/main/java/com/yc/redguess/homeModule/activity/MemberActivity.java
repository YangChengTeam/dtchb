package com.yc.redguess.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.dialog.LevelDialog;
import com.yc.redguess.dialog.RedDialogTwo;
import com.yc.redguess.dialog.SnatchDialog;
import com.yc.redguess.homeModule.adapter.VipTaskAdapter;
import com.yc.redguess.homeModule.contact.MemberConstact;
import com.yc.redguess.homeModule.module.bean.RedReceiveInfo;
import com.yc.redguess.homeModule.module.bean.UserAccountInfo;
import com.yc.redguess.homeModule.module.bean.UserInfo;
import com.yc.redguess.homeModule.module.bean.VipTaskInfo;
import com.yc.redguess.homeModule.module.bean.VipTaskInfoWrapper;
import com.yc.redguess.homeModule.present.MemberPresenter;
import com.yc.redguess.homeModule.widget.ToastShowViews;
import com.yc.redguess.service.event.Event;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.ClickListenNameTwo;
import com.yc.redguess.utils.CommonUtils;
import com.yc.redguess.utils.DisplayUtil;
import com.yc.redguess.utils.SoundPoolUtils;
import com.yc.redguess.utils.VUiKit;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员
 */
public class MemberActivity extends BaseActivity<MemberPresenter> implements MemberConstact.View {
    @BindView(R.id.tv_platform_money)
    TextView tvPlatformMoney;
    @BindView(R.id.tv_vip_money)
    TextView tvVipMoney;
    @BindView(R.id.tv_level_reward)
    TextView tvLevelReward;
    @BindView(R.id.recyclerView_task)
    RecyclerView recyclerViewTask;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.ll_task_container)
    LinearLayout llTaskContainer;
    @BindView(R.id.tv_down_time)
    TextView tvDownTime;
    @BindView(R.id.ll_count_down_container)
    RelativeLayout llCountDownContainer;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_platform_title)
    TextView tvPlatformTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
    private VipTaskAdapter vipTaskAdapter;
    private String redTypeName;
    private int taskIds;

    private double redMoney;
    private int level;
    private String hongbaoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_member;
    }

    @Override
    public void initEventAndData() {
        initRecyclerView();
        initListener();
        String str = "完成下方 今日任务 并领取即可升级，每天可升一级";
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DA7420")), 4, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 4, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
        spannableString.setSpan(new RelativeSizeSpan(1.4f), 4, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //2.0f表示默认字体大小的两倍
        tvDes.setText(spannableString);
        String member = CacheDataUtils.getInstance().getMember();
        if (TextUtils.isEmpty(member)) {
            mPresenter.getRegUserLog(CacheDataUtils.getInstance().getUserInfo().getId(), "3");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVideo();
        initData();
    }

    private void initListener() {
        vipTaskAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (ClickListenNameTwo.isFastClick()) {
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    VipTaskInfo vipTaskInfo = vipTaskAdapter.getItem(position);
                    if (vipTaskInfo != null) {
                        if (llCountDownContainer.getVisibility() == View.GONE) {
                            if (view.getId() == R.id.rela_re) {
                                int taskId = vipTaskInfo.task_id;
                                taskIds = vipTaskInfo.task_id;
                                int status = vipTaskInfo.status;
                                if (WithdrawActivity.instance != null && WithdrawActivity.instance.get() != null) {
                                    WithdrawActivity.instance.get().finish();
                                }
                                switch (taskId) {
                                    case 1://手气红包
                                        if (status == 0) {
                                            finish();
                                        } else if (status == 1) {
                                            CacheDataUtils.getInstance().setTaskShou("shou");
                                            String tips="手气红包每5分钟刷新一个哦！";
                                            receivePacket(redMoney, 1, taskId,tips);
                                        }
                                        break;
                                    case 2://答题
                                        if (status == 0) {
                                            AnswerActivity.answerJump(MemberActivity.this);
                                        } else if (status == 1) {
                                            String tips="答题选对答案争取一次通过哦！";
                                            receivePacket(redMoney, 2, taskId,tips);
                                        }
//                                receivePacket(redMoney, 2, taskId);
                                        break;
                                    case 3://转盘
                                        if (status == 0) {
                                            if (TurnTableActivity.instance != null && TurnTableActivity.instance.get() != null) {
                                                TurnTableActivity.instance.get().finish();
                                            }
                                            TurnTableActivity.TurnTableJump(MemberActivity.this);
                                        } else if (status == 1) {
                                            String tips="转盘是最简单轻松的升级玩法！";
                                            receivePacket(redMoney, 3, taskId,tips);
                                        }
                                        break;
                                    case 4://夺宝
                                        if (status == 0) {
                                            SnatchTreasureActivity.snatchTreasureJump(MemberActivity.this);
                                        } else if (status == 1) {
                                            String tips="领取手气红包和答题都能获得夺宝卷哦！";
                                            receivePacket(redMoney, 4, taskId,tips);
                                        }
                                        break;
                                    case 5://竞猜
                                        if (status == 0) {
                                            GuessingActivity.GuessingJump(MemberActivity.this);
                                        } else if (status == 1) {
                                            String tips="一分钟了解下数字竞猜的规则哦！";
                                            receivePacket(redMoney, 5, taskId,tips);
                                        }
                                        break;
                                    case 6://在线红包
                                        if (status == 0) {
                                            String taskRed = CacheDataUtils.getInstance().getTaskRed();
                                            if (TextUtils.isEmpty(taskRed)){
                                                CacheDataUtils.getInstance().setTaskRed("1");
                                                Log.d("ccc", "-----------onItemChildClick: ");
                                                EventBus.getDefault().post(new Event.TaskHongBaoEvent());
                                            }
                                            finish();
                                        } else if (status == 1) {
                                            String tips="在线红包就是我们首页的宝箱哦！";
                                            receivePacket(redMoney, 6, taskId,tips);
                                        }
                                        break;
                                    case 7://签到
                                        if (status == 0) {
                                            if (GrabRedEvenlopesActivity.instance != null && GrabRedEvenlopesActivity.instance.get() != null) {
                                                GrabRedEvenlopesActivity.instance.get().finish();
                                            }
                                            Intent intent = new Intent(MemberActivity.this, GrabRedEvenlopesActivity.class);
                                            startActivity(intent);
                                        } else if (status == 1) {
                                            String tips="一定要连续签到7天才可以哦！";
                                            receivePacket(redMoney, 7, taskId,tips);
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }

            }
        });
    }

    private RedDialogTwo redDialog;
    private void receivePacket(double money, int status, int taskId,String tips) {
        this.taskIds = taskId;
        redDialog = new RedDialogTwo(this);
        View builder = redDialog.builder(R.layout.red_dialog_item_two);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_type = builder.findViewById(R.id.tv_typeName);
        TextView tv_money = builder.findViewById(R.id.tv_money);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        LinearLayout line_getRed = builder.findViewById(R.id.line_getRed);
        RelativeLayout rela_status = builder.findViewById(R.id.rela_status);
        TextView tv_tips=builder.findViewById(R.id.tv_tips);
        FrameLayout fl_banner=builder.findViewById(R.id.fl_banner);
        line_getRed.setVisibility(View.VISIBLE);
        rela_status.setVisibility(View.GONE);
        tv_type.setText(getRedType(status));
        tv_tips.setText(tips);
        redTypeName = getRedType(status);
        tv_money.setText(String.valueOf(money));
        iv_open.setOnClickListener(v -> {
            if (level == 1 && taskId != 2) {
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                mPresenter.getReceiveInfo(userInfo.getGroup_id(), taskIds);
            } else {
                AdPlatformSDK instance = AdPlatformSDK.getInstance(MemberActivity.this);
                instance.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
                loadVideo();
                instance.showRewardVideoAd();
            }
            if (redDialog != null) {
                redDialog.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                redDialog.setDismiss();
            }
        });
        VUiKit.postDelayed(2000, () -> {
            loadBanner(fl_banner);
            iv_close.setVisibility(View.VISIBLE);
        });
        if (redDialog != null && !CommonUtils.isDestory(this)) {
            redDialog.setShow();
        }
    }
    private int videoCounts=1;
    private void loadVideo() {
        AdPlatformSDK instance = AdPlatformSDK.getInstance(MemberActivity.this);
        instance.loadRewardVideoVerticalAd(MemberActivity.this, "ad_member", new AdCallback() {
            @Override
            public void onDismissed() {
                if (redDialog != null) {
                    redDialog.setDismiss();
                }
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                mPresenter.getReceiveInfo(userInfo.getGroup_id(), taskIds);
                if (!CommonUtils.isDestory(MemberActivity.this)) {
                    ToastShowViews.getInstance().cancleToast();
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                videoCounts++;
                if (videoCounts>3){
                    videoCounts=1;
                    if (!CommonUtils.isDestory(MemberActivity.this)){
                        ToastUtil.showToast("加载广告失败，可能是网络不好的原因，试试重新启动APP再来领取奖励哦！");
                    }
                }
            }

            @Override
            public void onComplete() {
                if (!CommonUtils.isDestory(MemberActivity.this)) {
                    ToastShowViews.getInstance().cancleToast();
                }
            }

            @Override
            public void onPresent() {
                if (!CommonUtils.isDestory(MemberActivity.this)) {
                    videoCounts=1;
                    ToastShowViews.getInstance().showMyToast();
                }
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {

            }
        });
    }


    private void showDialogsTwo(String jishu) {
        CacheDataUtils.getInstance().setLevel("1");
        SnatchDialog snatchDialog = new SnatchDialog(this);
        View builder = snatchDialog.builder(R.layout.upgrade_item);
        TextView tv_know_btn = builder.findViewById(R.id.tv_know_btn);
        TextView tv_jishu = builder.findViewById(R.id.tv_jishu);
        tv_jishu.setText(jishu);
        tv_know_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snatchDialog.setDismiss();
            }
        });
        snatchDialog.setShow();
    }


    private String getRedType(int status) {
        String str = "";
        switch (status) {
            case 1:
                str = "手气红包";
                break;
            case 2:
                str = "答题红包";
                break;
            case 3:
                str = "转盘红包";
                break;
            case 4:
                str = "夺宝红包";
                break;
            case 5:
                str = "竞猜红包";
                break;
            case 6:
                str = "在线红包";
                break;
            case 7:
                str = "签到红包";
                break;

        }
        return str;
    }


    private void initData() {
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        mPresenter.getUserTaskInfo(userInfo.getGroup_id());
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewTask.setLayoutManager(linearLayoutManager);
        vipTaskAdapter = new VipTaskAdapter(null);
        recyclerViewTask.setAdapter(vipTaskAdapter);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void memberJump(Context context) {
        Intent intent = new Intent(context, MemberActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_level_reward, R.id.iv_backs, R.id.view})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_level_reward:
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                HelpQuestionActivity.helpJump(MemberActivity.this);
               // MemberLevelRewardActivity.memberJump(MemberActivity.this, level);
                break;
            case R.id.iv_backs:
                finish();
                break;
            case R.id.view:

                break;
        }
    }

    @Override
    public void showVipTaskInfo(VipTaskInfoWrapper data) {
        if (data != null) {

            double allMoney = data.all_money;
            long uplevelTime = data.uplevel_time;
            redMoney = data.red_money;
            String strMoney = String.valueOf(allMoney);
            if (allMoney >= 10000) {
                BigDecimal bigDecimal = new BigDecimal(allMoney / 10000);
                strMoney = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "万";
            }
            tvPlatformMoney.setText(strMoney);
            tvVipMoney.setText(String.valueOf(data.member_money));
            UserAccountInfo accountInfo = data.user_other;
            if (accountInfo != null) {
                level = accountInfo.level;
                tvLevel.setText(String.valueOf(level));
                if (level>1){
                    CacheDataUtils.getInstance().setTaskShou("shou");
                }
            }

            if (uplevelTime > 0) {
                llCountDownContainer.setVisibility(View.VISIBLE);
                countDownTime();
                if (TextUtils.isEmpty(CacheDataUtils.getInstance().getLevel())) {
                    if (data.getUser_other().getLevel() == 2) {
                        tixianDialogs(String.valueOf(data.getUser_other().getCash()));
                    } else {
                        showDialogsTwo(data.getUser_other().getLevel() + "");
                    }
                }
            } else {
                CacheDataUtils.getInstance().setLevel("");
                llCountDownContainer.setVisibility(View.GONE);
            }

            List<VipTaskInfo> taskInfo = data.task_info;
            vipTaskAdapter.setNewData(taskInfo);
        }
    }

    @Override
    public void showReceiveSuccess(RedReceiveInfo data) {
        initData();
        if (data != null) {
            RobRedEvenlopesActivity.robRedEvenlopesJump(MemberActivity.this, "2", redTypeName, "", data.money + "", "", "");
        }
    }

    @Override
    public void showUpgradeInfos(List<VipTaskInfo> data) {

    }

    @Override
    public void showUpdateRewardSuccess(List<VipTaskInfo> data, int position) {

    }

    private void countDownTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        long time = zero.getTime() + 24 * 60 * 60 * 1000;
        CountDownTimer countDownTimer = new CountDownTimer(time - System.currentTimeMillis(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long day = millisUntilFinished / (1000 * 24 * 60 * 60); //单位天

                long hour = (millisUntilFinished - day * (1000 * 24 * 60 * 60)) / (1000 * 60 * 60); //单位时
                String strHour = hour + "";
                if (hour < 10) {
                    strHour = "0" + hour;
                }
                long minute = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60)) / (1000 * 60); //单位分
                String strMinute = minute + "";
                if (minute < 10) {
                    strMinute = "0" + minute;
                }
                long second = (millisUntilFinished - day * (1000 * 24 * 60 * 60) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000;//单位秒
                String strSecond = second + "";
                if (second < 10) {
                    strSecond = "0" + second;
                }
                tvDownTime.setText(String.format(getString(R.string.count_down_time), strHour, strMinute, strSecond));
            }

            @Override
            public void onFinish() {
                llCountDownContainer.setVisibility(View.GONE);
            }
        };
        countDownTimer.start();
    }

    public void tixianDialogs(String moneys) {
        CacheDataUtils.getInstance().setLevel("1");
        LevelDialog tixanDialog = new LevelDialog(this);
        View builder = tixanDialog.builder(R.layout.member_tixian_dialog);
        TextView tv_moneys = builder.findViewById(R.id.tv_moneys);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_moneys.setText(moneys);
        TextView tv_sure = builder.findViewById(R.id.tv_goWithDraw);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tixanDialog.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithdrawActivity.WithdrawJump(MemberActivity.this);
                tixanDialog.setDismiss();
            }
        });
//        VUiKit.postDelayed(2000, () -> {
//            iv_close.setVisibility(View.VISIBLE);
//        });

        tixanDialog.setOutCancle(false);
        tixanDialog.setShow();
    }
    private void showBanner() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showBannerAd();
    }

    private void loadBanner(FrameLayout fl_ad_containe) {
        int screenWidth = CommonUtils.getScreenWidth(this);
        int w = (int) (screenWidth);
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(MemberActivity.this, w);
        adPlatformSDK.loadBannerAd(this, "ad_banner", dpw, 70, new AdCallback() {
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

}