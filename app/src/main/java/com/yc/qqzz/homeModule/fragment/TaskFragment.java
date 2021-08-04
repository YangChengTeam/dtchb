package com.yc.qqzz.homeModule.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.qqzz.R;
import com.yc.qqzz.application.MyApplication;
import com.yc.qqzz.base.BaseLazyFragment;
import com.yc.qqzz.dialog.SignDialog;
import com.yc.qqzz.dialog.SnatchDialog;
import com.yc.qqzz.homeModule.activity.MainActivity;
import com.yc.qqzz.homeModule.adapter.VipTaskAdapter;
import com.yc.qqzz.homeModule.bean.TaskBeans;
import com.yc.qqzz.homeModule.bean.TaskUnlockBeans;
import com.yc.qqzz.homeModule.bean.UserAccountInfoBeans;
import com.yc.qqzz.homeModule.contact.TaskFgContract;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.homeModule.present.TaskFgPresenter;
import com.yc.qqzz.service.event.Event;
import com.yc.qqzz.utils.CacheDataUtils;
import com.yc.qqzz.utils.ClickListenNameTwo;
import com.yc.qqzz.utils.SoundPoolUtils;
import com.yc.qqzz.utils.TimesUtils;
import com.yc.qqzz.widget.ScrollWithRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;


public class TaskFragment extends BaseLazyFragment<TaskFgPresenter> implements TaskFgContract.View {


    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.rela_avatar)
    RelativeLayout relaAvatar;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_down_time)
    TextView tvDownTime;
    @BindView(R.id.ll_count_down_container)
    RelativeLayout llCountDownContainer;
    private VipTaskAdapter vipTaskAdapter;
    private String redTypeName;
    private String taskIds;
    private List<TaskUnlockBeans> other_info;
    private double redMoney;
    private int level;
    private String hongbaoId;
    private int unLockTaskId;
    private int videoType;//1 任务  2 解锁任务1  3解锁任务2  4//
    private boolean isNeedClick;
    private int baijinunLockTaskId;
    private MainActivity activity;
    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
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
        MainActivity activity = (MainActivity) getActivity();

        initRecyclerView();
        initDatas();
    }

    private void initDatas() {
        UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
        mPresenter.getTaskinfo(userInfo.getImei(), userInfo.getGroup_id());
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        vipTaskAdapter = new VipTaskAdapter(null);
        recyclerView.setAdapter(vipTaskAdapter);
        vipTaskAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (ClickListenNameTwo.isFastClick()) {
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    TaskUnlockBeans vipTaskInfo = vipTaskAdapter.getItem(position);
                    if (vipTaskInfo != null) {
                        if (llCountDownContainer.getVisibility() == View.GONE) {
                            if (view.getId() == R.id.rela_re || view.getId() == R.id.tv_reward_state) {
                                taskIds = vipTaskInfo.getTask_id();
                                int status = vipTaskInfo.getStatus();
//                                switch (taskIds) {
//                                    case "1"://手气红包
//                                        if (status == 0) {
//                                            finish();
//                                        } else if (status == 1) {
//                                            CacheDataUtils.getInstance().setTaskShou("shou");
//                                            String tips = "手气红包每5分钟刷新一个哦！";
//                                            receivePacket(redMoney, 1, taskId, tips);
//                                        }
//                                        break;
//                                    case "2"://在线红包
//                                        if (status == 0) {
//                                            String taskRed = CacheDataUtils.getInstance().getTaskRed();
//                                            if (TextUtils.isEmpty(taskRed)) {
//                                                CacheDataUtils.getInstance().setTaskRed("1");
//                                                EventBus.getDefault().post(new Event.TaskHongBaoEvent());
//                                            }
//                                            finish();
//                                        } else if (status == 1) {
//                                            String tips = "在线红包就是我们首页的宝箱哦！";
//                                            receivePacket(redMoney, 6, taskId, tips);
//                                        }
//                                        break;
//                                    case "3"://转盘
//                                        if (status == 0) {
//                                            if (TurnTableActivity.instance != null && TurnTableActivity.instance.get() != null) {
//                                                TurnTableActivity.instance.get().finish();
//                                            }
//                                            TurnTableActivity.TurnTableJump(MemberActivity.this);
//                                        } else if (status == 1) {
//                                            String tips = "转盘是最简单轻松的升级玩法！";
//                                            receivePacket(redMoney, 3, taskId, tips);
//                                        }
//                                        break;
//                                    case "4"://答题
//                                        if (status == 0) {
//                                            AnswerActivity.answerJump(MemberActivity.this);
//                                        } else if (status == 1) {
//                                            String tips = "答题选对答案争取一次通过哦！";
//                                            receivePacket(redMoney, 2, taskId, tips);
//                                        }
////                                receivePacket(redMoney, 2, taskId);
//                                        break;
//                                }
                            }
                        }
                    }
                }
            }
        });
    }
    private SnatchDialog redDialog;
    public void redDialog() {
        redDialog = new SnatchDialog(getActivity());
        View builder = redDialog.builder(R.layout.reds_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_typeName=builder.findViewById(R.id.tv_typeName);
        TextView tv_money=builder.findViewById(R.id.tv_money);
        ImageView ivOpen=builder.findViewById(R.id.iv_open);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redDialog.setDismiss();
            }
        });
        redDialog.setShow();
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
    @Override
    public void getTaskinfoSuccess(TaskBeans data) {
        if (data != null) {
            String allMoney = data.getAll_money();
            String strMoney = "";
            float v = Float.parseFloat(allMoney);
            long uplevelTime = data.getUplevel_time();
            if (v >= 10000) {
                BigDecimal bigDecimal = new BigDecimal(v / 10000);
                strMoney = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "万";
            }
            EventBus.getDefault().post(new Event.CashEvent(strMoney,data.getMember_money()));

            UserAccountInfoBeans accountInfo = data.getUser_other();
            if (accountInfo != null) {
                level = accountInfo.getLevel();
                ((MyApplication) MyApplication.getInstance()).levels = level;
                tvLevel.setText(String.valueOf(level));
//                if (level > 1) {
//                    CacheDataUtils.getInstance().setTaskShou("shou");
//                }
                int is_vip = accountInfo.getIs_vip();
//                if (is_vip == 1) {//白金会员
//                    tvDes2.setVisibility(View.GONE);
//                    tvTaskTitle.setText("分红任务");
//                    bonuses_info = data.getBonuses_info();
//                    if (bonuses_info != null) {
//                        baijinother_infos = bonuses_info.getOther_info();
//                    }
//                    llCountDownContainer.setVisibility(View.GONE);
//                    tvBaijinTask.setVisibility(View.VISIBLE);
//                    recyclerViewTask.setVisibility(View.GONE);
//                } else {
//                    tvDes2.setVisibility(View.VISIBLE);
//                    if (level == 20) {
//                        tvDes2.setVisibility(View.GONE);
//                        tvTaskTitle.setText("白金会员任务");
//                    }
//                    tvBaijinTask.setVisibility(View.GONE);
//                    recyclerViewTask.setVisibility(View.VISIBLE);
//                }
            }

            long l = System.currentTimeMillis();
            String strUpLevel = TimesUtils.getStr(uplevelTime * 1000);
            String currTimes = TimesUtils.getStr(l);
            if (uplevelTime > 0 && !currTimes.equals(strUpLevel)) {
                llCountDownContainer.setVisibility(View.VISIBLE);
                countDownTime();
                if (TextUtils.isEmpty(CacheDataUtils.getInstance().getLevel())) {
                    if (data.getUser_other().getLevel() == 2) {
                        shegnjiSuccessDialog(String.valueOf(data.getUser_other().getCash()));
                    } else {
                        shegnjiSuccessDialog(data.getUser_other().getLevel() + "");
                    }
                }
            } else {
                CacheDataUtils.getInstance().setLevel("");
                llCountDownContainer.setVisibility(View.GONE);
            }


//            if (data.getUnlock() == 2) {//不需要解锁
//                tvUnlocking.setVisibility(View.GONE);
//                long l = System.currentTimeMillis();
//                String strUpLevel = TimesUtils.getStr(uplevelTime * 1000);
//                String currTimes = TimesUtils.getStr(l);
//                if (uplevelTime > 0 && !currTimes.equals(strUpLevel)) {
//                    llCountDownContainer.setVisibility(View.VISIBLE);
//                    countDownTime();
//                    if (TextUtils.isEmpty(CacheDataUtils.getInstance().getLevel())) {
//                        if (data.getUser_other().getLevel() == 2) {
//                            tixianDialogs(String.valueOf(data.getUser_other().getCash()));
//                        } else {
//                            showDialogsTwo(data.getUser_other().getLevel() + "");
//                        }
//                    }
//                } else {
//                    CacheDataUtils.getInstance().setLevel("");
//                    llCountDownContainer.setVisibility(View.GONE);
//                }
//            } else if (data.getUnlock() == 0) {//需要解锁
//                tvUnlocking.setVisibility(View.VISIBLE);
//                if (uplevelTime > 0) {
//                    llCountDownContainer.setVisibility(View.VISIBLE);
//                    countDownTime();
//                    other_info = data.getOther_info();
//                    if (TextUtils.isEmpty(CacheDataUtils.getInstance().getLevel())) {
//                        if (data.getUser_other().getLevel() == 2) {
//                            tixianDialogs(String.valueOf(data.getUser_other().getCash()));
//                        } else {
//                            showDialogsTwo(data.getUser_other().getLevel() + "");
//                        }
//                    }
//                } else {
//                    CacheDataUtils.getInstance().setLevel("");
//                    llCountDownContainer.setVisibility(View.GONE);
//                }
//            } else {//解锁任务完成
//                tvUnlocking.setVisibility(View.GONE);
//                CacheDataUtils.getInstance().setLevel("");
//                llCountDownContainer.setVisibility(View.GONE);
//            }

            List<TaskUnlockBeans> task_info = data.getTask_info();
            vipTaskAdapter.setNewData(task_info);
            vipTaskAdapter.notifyDataSetChanged();
//            if (level == 1) {
//                String yindao = CacheDataUtils.getInstance().getYindao();
//                if (TextUtils.isEmpty(yindao)) {
//                    recyclerView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            layout = vipTaskAdapter.getViewByPosition(recyclerView, 0, R.id.tv_reward_state);
//                            if (layout != null) {
//                                layout.post(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        showGuideView(layout);
//                                    }
//                                });
//                            }
//                        }
//                    });
//                }
//            }

        }
    }

    private  SignDialog shegnjiSuccessDialog;
    public void shegnjiSuccessDialog(String level) {
        shegnjiSuccessDialog = new SignDialog(getActivity());
        View builder = shegnjiSuccessDialog.builder(R.layout.shengjisuccess_item_dialog);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        TextView tv_sure=builder.findViewById(R.id.tv_sure);
        TextView tv_level=builder.findViewById(R.id.tv_level);
        tv_level.setText(level+"");
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shegnjiSuccessDialog.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shegnjiSuccessDialog.setDismiss();
            }
        });
        shegnjiSuccessDialog.setShow();
    }
}