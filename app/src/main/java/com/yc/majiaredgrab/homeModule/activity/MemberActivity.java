package com.yc.majiaredgrab.homeModule.activity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.umeng.analytics.MobclickAgent;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.application.MyApplication;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.constants.Constant;
import com.yc.majiaredgrab.dialog.BottomListDialog;
import com.yc.majiaredgrab.dialog.LevelDialog;
import com.yc.majiaredgrab.dialog.RedDialogTwo;
import com.yc.majiaredgrab.dialog.SnatchDialog;
import com.yc.majiaredgrab.homeModule.adapter.TaskUnlockAdapter;
import com.yc.majiaredgrab.homeModule.adapter.VipTaskAdapter;
import com.yc.majiaredgrab.homeModule.contact.MemberConstact;
import com.yc.majiaredgrab.homeModule.module.bean.RedReceiveInfo;
import com.yc.majiaredgrab.homeModule.module.bean.TaskUnLockResBeans;
import com.yc.majiaredgrab.homeModule.module.bean.TaskUnlock;
import com.yc.majiaredgrab.homeModule.module.bean.UserAccountInfo;
import com.yc.majiaredgrab.homeModule.module.bean.UserInfo;
import com.yc.majiaredgrab.homeModule.module.bean.VipTaskInfo;
import com.yc.majiaredgrab.homeModule.module.bean.VipTaskInfoWrapper;
import com.yc.majiaredgrab.homeModule.present.MemberPresenter;
import com.yc.majiaredgrab.homeModule.widget.SimpleComponent;
import com.yc.majiaredgrab.homeModule.widget.SimpleComponentThree;
import com.yc.majiaredgrab.homeModule.widget.ToastShowViews;
import com.yc.majiaredgrab.homeModule.widget.gu.Guide;
import com.yc.majiaredgrab.homeModule.widget.gu.GuideBuilder;
import com.yc.majiaredgrab.service.event.Event;
import com.yc.majiaredgrab.utils.AppSettingUtils;
import com.yc.majiaredgrab.utils.CacheDataUtils;
import com.yc.majiaredgrab.utils.ClickListenNameTwo;
import com.yc.majiaredgrab.utils.CommonUtils;
import com.yc.majiaredgrab.utils.DisplayUtil;
import com.yc.majiaredgrab.utils.SoundPoolUtils;
import com.yc.majiaredgrab.utils.TimesUtils;
import com.yc.majiaredgrab.utils.VUiKit;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.tv_unlocking)
    TextView tvUnlocking;
    private VipTaskAdapter vipTaskAdapter;
    private String redTypeName;
    private int taskIds;
    private List<TaskUnlock> other_info;
    private double redMoney;
    private int level;
    private String hongbaoId;
    private int unLockTaskId;
    private int videoType;//1 任务  2 解锁任务1  3解锁任务2
    public static WeakReference<MemberActivity> instance;
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
        instance=new WeakReference<>(this);
        initRecyclerView();
        initListener();
        loadTx();
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
                            if (view.getId() == R.id.rela_re||view.getId()==R.id.tv_reward_state) {
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
                                            String tips = "手气红包每5分钟刷新一个哦！";
                                            receivePacket(redMoney, 1, taskId, tips);
                                        }
                                        break;
                                    case 2://答题
                                        if (status == 0) {
                                            AnswerActivity.answerJump(MemberActivity.this);
                                        } else if (status == 1) {
                                            String tips = "答题选对答案争取一次通过哦！";
                                            receivePacket(redMoney, 2, taskId, tips);
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
                                            String tips = "转盘是最简单轻松的升级玩法！";
                                            receivePacket(redMoney, 3, taskId, tips);
                                        }
                                        break;
                                    case 4://夺宝
                                        if (status == 0) {
                                            SnatchTreasureActivity.snatchTreasureJump(MemberActivity.this);
                                        } else if (status == 1) {
                                            String tips = "领取手气红包和答题都能获得夺宝卷哦！";
                                            receivePacket(redMoney, 4, taskId, tips);
                                        }
                                        break;
                                    case 5://竞猜
                                        if (status == 0) {
                                            GuessingActivity.GuessingJump(MemberActivity.this);
                                        } else if (status == 1) {
                                            String tips = "一分钟了解下数字竞猜的规则哦！";
                                            receivePacket(redMoney, 5, taskId, tips);
                                        }
                                        break;
                                    case 6://在线红包
                                        if (status == 0) {
                                            String taskRed = CacheDataUtils.getInstance().getTaskRed();
                                            if (TextUtils.isEmpty(taskRed)) {
                                                CacheDataUtils.getInstance().setTaskRed("1");
                                                EventBus.getDefault().post(new Event.TaskHongBaoEvent());
                                            }
                                            finish();
                                        } else if (status == 1) {
                                            String tips = "在线红包就是我们首页的宝箱哦！";
                                            receivePacket(redMoney, 6, taskId, tips);
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
                                            String tips = "一定要连续签到7天才可以哦！";
                                            receivePacket(redMoney, 7, taskId, tips);
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

    private void receivePacket(double money, int status, int taskId, String tips) {
        this.taskIds = taskId;
        redDialog = new RedDialogTwo(this);
        View builder = redDialog.builder(R.layout.red_dialog_item_two);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_type = builder.findViewById(R.id.tv_typeName);
        TextView tv_money = builder.findViewById(R.id.tv_money);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        LinearLayout line_getRed = builder.findViewById(R.id.line_getRed);
        RelativeLayout rela_status = builder.findViewById(R.id.rela_status);
        TextView tv_tips = builder.findViewById(R.id.tv_tips);
        FrameLayout fl_banner = builder.findViewById(R.id.fl_banner);
        line_getRed.setVisibility(View.VISIBLE);
        rela_status.setVisibility(View.GONE);
        tv_type.setText(getRedType(status));
        tv_tips.setText(tips);
        redTypeName = getRedType(status);
        tv_money.setText(String.valueOf(money));
        iv_open.setOnClickListener(v -> {
            if (level == 1 ) {
                if (taskId==1){
                    videoType=1;
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideo();
                    }else {
                        showTx();
                    }
                }else {
                    UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                    mPresenter.getReceiveInfo(userInfo.getGroup_id(), taskIds);
                }
            } else if (level == 2 && (taskId == 1||taskId == 3)){
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                mPresenter.getReceiveInfo(userInfo.getGroup_id(), taskIds);
            }else {
                videoType=1;
                if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                    showVideo();
                }else {
                    showTx();
                }
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
            if ("1".equals(Constant.ISBANNER)) {
                loadBanner(fl_banner);
            }
            iv_close.setVisibility(View.VISIBLE);
        });
        if (redDialog != null && !CommonUtils.isDestory(this)) {
            redDialog.setShow();
        }
    }
    private boolean isVideoClick;
    private String isLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功


    private void loadVideo() {
        AdPlatformSDK instance = AdPlatformSDK.getInstance(MemberActivity.this);
        instance.loadRewardVideoVerticalAd(MemberActivity.this, "ad_member", new AdCallback() {
            @Override
            public void onDismissed() {
                if (videoType==1){
                    if (redDialog != null) {
                        redDialog.setDismiss();
                    }
                    UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                    mPresenter.getReceiveInfo(userInfo.getGroup_id(), taskIds);
                }else {
                    MobclickAgent.onEvent(MemberActivity.this, "jiesuotaskvideo");//参数二为当前统计的事件ID
                    if (isVideoClick){//点击视频
                        MobclickAgent.onEvent(MemberActivity.this, "jiesuotasksevice");//参数二为当前统计的事件ID
                        mPresenter.getUnlockTask(CacheDataUtils.getInstance().getUserInfo().getImei(),CacheDataUtils.getInstance().getUserInfo().getGroup_id(),unLockTaskId);
                    }else {
                        showjiesuoTaskError();
                    }
                }
                if (videoType!=1){
                    if (!CommonUtils.isDestory(MemberActivity.this)){
                        ToastShowViews.cancleToastTwo();
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
                        if (!CommonUtils.isDestory(MemberActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }
                }
            }

            @Override
            public void onComplete() {
                if (!CommonUtils.isDestory(MemberActivity.this)) {
                    ToastShowViews.cancleToast();
                }
                if (videoType!=1){
                    if (!CommonUtils.isDestory(MemberActivity.this)){
                        ToastShowViews.cancleToastTwo();
                    }
                }
            }

            @Override
            public void onPresent() {
                isLoadAdSuccess="3";
                if (videoType!=1){
                    if (!CommonUtils.isDestory(MemberActivity.this)) {
                        ToastShowViews.showMyToastTwo("点击下载视频游戏  加速升到3级","4");
                    }
                }else {
                    if (!CommonUtils.isDestory(MemberActivity.this)&&level==2) {
                        if (taskIds==2){//答题红包
                            ToastShowViews.showMyToastTwo("点击广告下载完 ，马上就能提现了","5");
                        }else if (taskIds==6){//在线红包
                            ToastShowViews.showMyToastTwo("点击广告下载试玩 ，有机会直接升级","6");
                        }
                    }
                }
            }

            @Override
            public void onClick() {
                Log.d("ccc", "--isVideoClick---------onClick------: ");
                isVideoClick=true;
            }

            @Override
            public void onLoaded() {
                isLoadAdSuccess="3";
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

        if (!CommonUtils.isDestory(MemberActivity.this)) {
            snatchDialog.setShow();
        }
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

    @OnClick({R.id.tv_level_reward, R.id.iv_backs, R.id.view, R.id.tv_unlocking, R.id.tv_gotoTixian})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_level_reward:
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                HelpQuestionActivity.helpJump(MemberActivity.this);
                break;
            case R.id.iv_backs:
                finish();
                break;
            case R.id.view:

                break;
            case R.id.tv_unlocking:
                if (other_info!=null&&other_info.size()>0){
                    MobclickAgent.onEvent(MemberActivity.this, "jiesuo");//参数二为当前统计的事件ID
                    unlockingDialog();
                }
                break;
            case R.id.tv_gotoTixian:
                if (WithdrawActivity.instance != null && WithdrawActivity.instance.get() != null) {
                    WithdrawActivity.instance.get().finish();
                }
                WithdrawActivity.WithdrawJump(this);
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
                ((MyApplication) MyApplication.getInstance()).levels= level;
                tvLevel.setText(String.valueOf(level));
                if (level > 1) {
                    CacheDataUtils.getInstance().setTaskShou("shou");
                }
            }



            if (data.getUnlock()==2){//不需要解锁
                tvUnlocking.setVisibility(View.GONE);
                long l = System.currentTimeMillis();
                String strUpLevel = TimesUtils.getStr(uplevelTime*1000);
                String currTimes = TimesUtils.getStr(l);
                if (uplevelTime > 0&&!currTimes.equals(strUpLevel)) {
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
            }else if (data.getUnlock()==0){//需要解锁
                tvUnlocking.setVisibility(View.VISIBLE);
                if (uplevelTime > 0) {
                    llCountDownContainer.setVisibility(View.VISIBLE);
                    countDownTime();
                    other_info = data.getOther_info();
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
            }else {//解锁任务完成
                tvUnlocking.setVisibility(View.GONE);
                CacheDataUtils.getInstance().setLevel("");
                llCountDownContainer.setVisibility(View.GONE);
            }

            List<VipTaskInfo> taskInfo = data.task_info;
            vipTaskAdapter.setNewData(taskInfo);

              if(level==1){
                String yindao = CacheDataUtils.getInstance().getYindao();
                if (TextUtils.isEmpty(yindao)){
                    recyclerViewTask.post(new Runnable() {
                        @Override
                        public void run() {
                            layout = vipTaskAdapter.getViewByPosition(recyclerViewTask, 0, R.id.tv_reward_state);
                            if (layout != null) {
                                layout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showGuideView(layout);
                                    }
                                });
                            }
                        }
                    });
                }
            }

        }
    }
    private Guide guide;
    private View layout;
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

    @Override
    public void getUnlockTaskSuccess(TaskUnLockResBeans data) {
        MobclickAgent.onEvent(MemberActivity.this, "jiesuotasksevicesuccess");//参数二为当前统计的事件ID
        if (other_info!=null&&other_info.size()>0){
            for (int i = 0; i < other_info.size(); i++) {
                if (unLockTaskId==other_info.get(i).getOther_id()){
                    other_info.get(i).setFinish_num(data.getFinish_num());
                }
            }
        }
        if (taskUnlockAdapte!=null){
            taskUnlockAdapte.notifyDataSetChanged();
        }
        if (data.getUnlock()==1){
            EventBus.getDefault().post(new Event.TaskUnLociEvent());
            ToastUtil.showToast("解锁任务成功");
            initData();
            if (unlockingDialog!=null){
                if (!CommonUtils.isDestory(this)){
                    unlockingDialog.setDismiss();
                }
            }
        }else {
            ToastUtil.showToast("请继续完成下一个任务哦！");
        }
    }

    @Override
    public void getUnlockTaskReeorState() {
        MobclickAgent.onEvent(MemberActivity.this, "jiesuotaskseviceerrir");//参数二为当前统计的事件ID
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

        if (!CommonUtils.isDestory(MemberActivity.this)) {
            tixanDialog.setOutCancle(false);
            tixanDialog.setShow();
        }
    }
   private  TaskUnlockAdapter taskUnlockAdapte;
    private  BottomListDialog unlockingDialog;
    public void unlockingDialog(){
        unlockingDialog = new BottomListDialog(this);
        View builder = unlockingDialog.builder(R.layout.member_unlocking_dialog);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        RecyclerView recyclerView=builder.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MemberActivity.this,LinearLayoutManager.VERTICAL,false);
        taskUnlockAdapte=new TaskUnlockAdapter(other_info);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskUnlockAdapte);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockingDialog.setDismiss();
            }
        });
        taskUnlockAdapte.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<TaskUnlock> data = adapter.getData();
                TaskUnlock taskUnlock = data.get(position);
                if (taskUnlock.getFinish_num()<taskUnlock.getNum()){//未完成
                    unLockTaskId=taskUnlock.getOther_id();
                     if (position==0){
                         MobclickAgent.onEvent(MemberActivity.this, "jiesuotask1");//参数二为当前统计的事件ID
                         videoType=2;
                     }else{
                         MobclickAgent.onEvent(MemberActivity.this, "jiesuotask2");//参数二为当前统计的事件ID
                         videoType=3;
                     }
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideo();
                    }else {
                        showTx();
                    }
                }else {
                    ToastUtil.showToast("该任务已经完成");
                }
            }
        });
        if (!CommonUtils.isDestory(MemberActivity.this)) {
            unlockingDialog.setOutCancle(true);
            unlockingDialog.setShow();
        }
    }

    private void showVideo(){
        isVideoClick=false;
        isLoadAdSuccess="1";
        AdPlatformSDK instance = AdPlatformSDK.getInstance(MemberActivity.this);
        instance.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        loadVideo();
        instance.showRewardVideoAd();
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

    private SnatchDialog snatchDialogs;
    private void showjiesuoTaskError() {
        CacheDataUtils.getInstance().setLevel("1");
         snatchDialogs = new SnatchDialog(this);
        View builder = snatchDialogs.builder(R.layout.jiesuotaskerror_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snatchDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(MemberActivity.this)) {
            snatchDialogs.setShow();
        }
    }

    @Override
    protected void onDestroy() {
        if (snatchDialogs!=null){
            snatchDialogs.setDismiss();
        }
        if (mRewardVideoAD != null) {
            mRewardVideoAD.destroy();
        }
        super.onDestroy();
    }



    public void showTx(){
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                if (!CommonUtils.isDestory(MemberActivity.this)) {
                    ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                }
            }else {
                showVideo();
            }
        }else {
            VideoAdValidity validity = mRewardVideoAD.checkValidity();
            switch (validity) {
                case SHOWED:
                case OVERDUE:
                    loadTxTwo();
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        if (!CommonUtils.isDestory(MemberActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }else {
                        showVideo();
                    }
                    return;
                // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                case NONE_CACHE:
                    //  showToast("广告素材未缓存成功！");
//            return;
                case VALID:
                    isVideoClick=false;
                    // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                    isTxLoadAdSuccess="1";
                    mRewardVideoAD
                            .showAD(MemberActivity.this);
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
        mRewardVideoAD = new ExpressRewardVideoAD(this, Constant.TXRVIDEO, new ExpressRewardVideoAdListener() {
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
                AppSettingUtils.showTxShow("tx_ad_member");
                if (videoType!=1){
                    if (!CommonUtils.isDestory(MemberActivity.this)) {
                        ToastShowViews.showMyToastTwo("点击下载视频游戏  加速升到3级","4");
                    }
                }else {
                    if (!CommonUtils.isDestory(MemberActivity.this)&&level==2) {
                        if (taskIds==2){//答题红包
                            ToastShowViews.showMyToastTwo("点击广告下载完 ，马上就能提现了","7");
                        }else if (taskIds==6){//在线红包
                            ToastShowViews.showMyToastTwo("点击广告下载试玩 ，有机会直接升级","8");
                        }
                    }
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
                isVideoClick=true;
                AppSettingUtils.showTxClick("tx_ad_member");
                Log.i("ccc", "onClick: ");
            }

            @Override
            public void onVideoComplete() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
                if (!CommonUtils.isDestory(MemberActivity.this)) {
                    ToastShowViews.cancleToast();
                }
                if (videoType!=1){
                    if (!CommonUtils.isDestory(MemberActivity.this)){
                        ToastShowViews.cancleToastTwo();
                    }
                }
            }

            @Override
            public void onClose() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
                if (videoType==1){
                    if (redDialog != null) {
                        redDialog.setDismiss();
                    }
                    UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                    mPresenter.getReceiveInfo(userInfo.getGroup_id(), taskIds);
                }else {
                    MobclickAgent.onEvent(MemberActivity.this, "jiesuotaskvideo");//参数二为当前统计的事件ID
                    if (isVideoClick){//点击视频
                        MobclickAgent.onEvent(MemberActivity.this, "jiesuotasksevice");//参数二为当前统计的事件ID
                        mPresenter.getUnlockTask(CacheDataUtils.getInstance().getUserInfo().getImei(),CacheDataUtils.getInstance().getUserInfo().getGroup_id(),unLockTaskId);
                    }else {
                        showjiesuoTaskError();
                    }
                }
                if (videoType!=1){
                    if (!CommonUtils.isDestory(MemberActivity.this)){
                        ToastShowViews.cancleToastTwo();
                    }
                }
            }

            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                if ("1".equals(isTxLoadAdSuccess)){
                    isTxLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("2".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        showVideo();
                    }else {
                        if (!CommonUtils.isDestory(MemberActivity.this)) {
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



    public void notice(){
        String channelId="ss";
        Intent intent = new Intent(MemberActivity.this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(MemberActivity.this,0,intent,0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setAutoCancel(false)
                .setContentTitle("收到聊天消息")
                .setContentText("今天晚上吃什么")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.redlogo)
                //设置红色
                .setColor(Color.parseColor("#F00606"))
                .setContentIntent(pi)
                .build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance,manager);
        }
        manager.notify(1, notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance,NotificationManager manager) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        manager.createNotificationChannel(channel);
    }

    private void showPop() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
            } else {
                initWindows();
                //有权限了，可以用service或者直接用第三步开启悬浮窗
            }
        }
    }

    public void  initWindows(){
        WindowManager windowManager = (WindowManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams    = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        // 实现悬浮窗可以移动的属性（把这个值改成其他值可以操作悬浮窗底下的内容）
      //  layoutParams.flags = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
//        layoutParams.x = 0;
//        layoutParams.y = 0;

        layoutParams.format = PixelFormat.RGBA_8888;
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        layoutParams.windowAnimations = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
            // mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }



        //隐藏虚拟导航栏
//        layoutParams.systemUiVisibility=View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局.activity_member
        RelativeLayout  lightLayout = (RelativeLayout) inflater.inflate(R.layout.member_tixian_dialog,null);

        //添加toucherlayout
        windowManager.addView(lightLayout,layoutParams);
    }
    public static void startService(){
        Context applicationContext = MyApplication.getInstance().getApplicationContext();
        Intent intent=new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            Intent intent2=new Intent(Settings.ACTION_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                applicationContext.startActivity(intent);
            } catch (Exception e) {
                applicationContext.startActivity(intent2);
                e.printStackTrace();
            }
            return;
    }



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
                    CacheDataUtils.getInstance().setYindao("memberyindao");
                    view.performClick();
                }
            }
        });
        builder.addComponent(new SimpleComponentThree());
        guide = builder.createGuide();
        guide.show(MemberActivity.this);
    }


}