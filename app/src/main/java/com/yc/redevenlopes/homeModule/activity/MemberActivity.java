package com.yc.redevenlopes.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.homeModule.adapter.VipTaskAdapter;
import com.yc.redevenlopes.homeModule.contact.MemberConstact;
import com.yc.redevenlopes.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.redevenlopes.homeModule.module.bean.RedReceiveInfo;
import com.yc.redevenlopes.homeModule.module.bean.UserAccountInfo;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfoWrapper;
import com.yc.redevenlopes.homeModule.present.MemberPresenter;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.VUiKit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    LinearLayout llCountDownContainer;
    private VipTaskAdapter vipTaskAdapter;
    //24小时换算成毫秒
    private long timeStemp = 1606147200000L;

    private double redMoney;
    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_member;
    }

    @Override
    public void initEventAndData() {
        setTitle("会员");
        initRecyclerView();
        initData();
        initListener();
    }

    private void initListener() {
        vipTaskAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                VipTaskInfo vipTaskInfo = vipTaskAdapter.getItem(position);
                if (vipTaskInfo != null) {
                    if (view.getId() == R.id.tv_reward_state) {
                        int taskId = vipTaskInfo.task_id;
                        int status = vipTaskInfo.status;
                        switch (taskId) {
                            case 1://手气红包
                                if (status == 0) {
                                    finish();
                                } else if (status == 1) {
                                    receivePacket(redMoney, 1, taskId);
                                }
                                break;
                            case 2://答题
                                if (status == 0) {
                                    AnswerActivity.answerJump(MemberActivity.this);
                                } else if (status == 1) {
                                    receivePacket(redMoney, 2, taskId);
                                }
//                                receivePacket(redMoney, 2, taskId);
                                break;
                            case 3://转盘
                                if (status == 0) {
                                    TurnTableActivity.TurnTableJump(MemberActivity.this);
                                } else if (status == 1) {
                                    receivePacket(redMoney, 3, taskId);
                                }
                                break;
                            case 4://夺宝
                                if (status == 0) {
                                    SnatchTreasureActivity.snatchTreasureJump(MemberActivity.this);
                                } else if (status == 1) {
                                    receivePacket(redMoney, 4, taskId);
                                }
                                break;
                            case 5://竞猜
                                if (status == 0) {
                                    GuessingActivity.GuessingJump(MemberActivity.this);
                                } else if (status == 1) {
                                    receivePacket(redMoney, 5, taskId);
                                }
                                break;
                            case 6://在线红包
                                if (status == 0) {
                                    finish();
                                } else if (status == 1) {
                                    receivePacket(redMoney, 6, taskId);
                                }
                                break;
                        }
                    }
                }
            }
        });
    }

    private void receivePacket(double money, int status, int taskId) {

        RedDialog redDialog = new RedDialog(this);
        View builder = redDialog.builder(R.layout.red_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_type = builder.findViewById(R.id.tv_typeName);
        TextView tv_money = builder.findViewById(R.id.tv_money);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        LinearLayout line_getRed = builder.findViewById(R.id.line_getRed);
        RelativeLayout rela_status = builder.findViewById(R.id.rela_status);
        TextView tv_getRedDetails = builder.findViewById(R.id.tv_getRedDetails);
        TextView tv_getRedDes = builder.findViewById(R.id.tv_getRedDes);
        line_getRed.setVisibility(View.VISIBLE);
        rela_status.setVisibility(View.GONE);

        tv_type.setText(getRedType(status));

        tv_money.setText(String.valueOf(money));

        iv_open.setOnClickListener(v -> {

            AdPlatformSDK.getInstance(MemberActivity.this).showRewardVideoVerticalAd(MemberActivity.this, new AdCallback() {
                @Override
                public void onDismissed() {
                    UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                    mPresenter.getReceiveInfo(userInfo.getGroup_id(), taskId);

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
            });
        });

        iv_close.setOnClickListener(v -> redDialog.setDismiss());
        VUiKit.postDelayed(2000, () -> {
            iv_close.setVisibility(View.VISIBLE);
        });
        redDialog.setShow();

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

    @OnClick({R.id.tv_level_reward})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_level_reward:
                MemberLevelRewardActivity.memberJump(MemberActivity.this, level);
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
            }

            if (uplevelTime > 0) {
                llCountDownContainer.setVisibility(View.VISIBLE);
                llCountDownContainer.setAlpha(0.3f);
                countDownTime();
            } else {
                llCountDownContainer.setVisibility(View.GONE);
            }

            List<VipTaskInfo> taskInfo = data.task_info;
            vipTaskAdapter.setNewData(taskInfo);
        }
    }

    @Override
    public void showReceiveSuccess(RedReceiveInfo data) {
        if (data != null) {
            RobRedEvenlopesActivity.robRedEvenlopesJump(MemberActivity.this, "2", getRedType(data.status),  "",data.money+"");
        }
    }

    @Override
    public void showUpgradeInfos(List<VipTaskInfo> data) {

    }

    @Override
    public void showUpdateRewardSuccess(List<VipTaskInfo> data, int position) {

    }

    private void countDownTime() {


        CountDownTimer countDownTimer = new CountDownTimer(timeStemp - System.currentTimeMillis(), 1000) {
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

            }
        };
        countDownTimer.start();
    }
}