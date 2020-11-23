package com.yc.redevenlopes.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.VipTaskAdapter;
import com.yc.redevenlopes.homeModule.contact.MemberConstact;
import com.yc.redevenlopes.homeModule.module.bean.UserAccountInfo;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfoWrapper;
import com.yc.redevenlopes.homeModule.present.MemberPresenter;
import com.yc.redevenlopes.utils.CacheDataUtils;

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
    private int timeStemp = 86400000;

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
    }

    private void initData() {
        List<VipTaskInfo> vipTaskInfos = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            vipTaskInfos.add(new VipTaskInfo());
        }
        vipTaskAdapter.setNewData(vipTaskInfos);

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
                MemberLevelRewardActivity.memberJump(MemberActivity.this);
                break;
        }
    }

    @Override
    public void showVipTaskInfo(VipTaskInfoWrapper data) {
        if (data != null) {

            double allMoney = data.all_money;
            long uplevelTime = data.uplevel_time;
            String strMoney = String.valueOf(allMoney);
            if (allMoney >= 10000) {
                BigDecimal bigDecimal = new BigDecimal(allMoney / 10000);
                strMoney = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "万";
            }
            tvPlatformMoney.setText(strMoney);
            tvVipMoney.setText(String.valueOf(data.member_money));
            UserAccountInfo accountInfo = data.user_other;
            if (accountInfo != null)
                tvLevel.setText(String.valueOf(accountInfo.level));
            uplevelTime = 1605929981;
            if (uplevelTime > 0) {
                llTaskContainer.setVisibility(View.VISIBLE);
                llTaskContainer.setAlpha(0.3f);
                countDownTime();
            }
        }
    }

    private void countDownTime() {

        Log.e(TAG, "countDownTime: new Date().getTime()=" + new Date().getTime());
        Log.e(TAG, "countDownTime: Calendar.getInstance().get(Calendar.DATE)=" + Calendar.getInstance().get(Calendar.DATE));

        Log.e(TAG, "countDownTime: System.currentTimeMillis()=" + System.currentTimeMillis());
        CountDownTimer countDownTimer = new CountDownTimer(new Date().getTime() - System.currentTimeMillis(), 1000) {
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