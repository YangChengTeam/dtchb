package com.yc.wxchb.beans.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.wxchb.R;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.adapter.MoneyTaskAdapter;
import com.yc.wxchb.beans.contact.MoneyTaskContract;
import com.yc.wxchb.beans.module.beans.EmptyBeans;
import com.yc.wxchb.beans.module.beans.MoneyTaskBeans;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.beans.present.MoneyTaskPresenter;
import com.yc.wxchb.dialog.SignDialog;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.SoundPoolUtils;
import com.yc.wxchb.utils.adgromore.GromoreAdShowTwo;
import com.yc.wxchb.widget.ScrollWithRecyclerView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MoneyTaskActivity extends BaseActivity<MoneyTaskPresenter> implements MoneyTaskContract.View {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_money1)
    TextView tvMoney1;
    @BindView(R.id.iv_moneyAsk1)
    ImageView ivMoneyAsk1;
    @BindView(R.id.iv_money1)
    ImageView ivMoney1;
    @BindView(R.id.tv_status1)
    TextView tvStatus1;
    @BindView(R.id.tv_money2)
    TextView tvMoney2;
    @BindView(R.id.iv_moneyAsk2)
    ImageView ivMoneyAsk2;
    @BindView(R.id.iv_money2)
    ImageView ivMoney2;
    @BindView(R.id.tv_status2)
    TextView tvStatus2;
    @BindView(R.id.tv_money3)
    TextView tvMoney3;
    @BindView(R.id.iv_moneyAsk3)
    ImageView ivMoneyAsk3;
    @BindView(R.id.iv_money3)
    ImageView ivMoney3;
    @BindView(R.id.tv_status3)
    TextView tvStatus3;
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_money4)
    TextView tvMoney4;
    @BindView(R.id.iv_moneyAsk4)
    ImageView ivMoneyAsk4;
    @BindView(R.id.iv_money4)
    ImageView ivMoney4;
    @BindView(R.id.tv_status4)
    TextView tvStatus4;
    private MoneyTaskAdapter moneyTaskAdapter;
    private int txid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_money_task;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        initRecyclerView();
        GromoreAdShowTwo.getInstance().setContexts(this, "2");
        mPresenter.getMoneyTask(CacheDataUtils.getInstance().getUserInfo().getId() + "");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void moneyTaskJump(Context context) {
        Intent intent = new Intent(context, MoneyTaskActivity.class);
        context.startActivity(intent);
    }

    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        moneyTaskAdapter = new MoneyTaskAdapter(null);
        recyclerView.setAdapter(moneyTaskAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        moneyTaskAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<MoneyTaskBeans> data = adapter.getData();
                MoneyTaskBeans moneyTaskBeans = data.get(position);
                int status = moneyTaskBeans.getStatus();
                if (status == 0) {//未完成
                    MobclickAgent.onEvent(MoneyTaskActivity.this, "moneytasks", "1");//参数二为当前统计的事件ID
                    //看视频
                    showAd();
                }
            }
        });
    }

    public void showAd() {
        GromoreAdShowTwo.getInstance().showjiliAd("", "2", new GromoreAdShowTwo.OnAdShowCaback() {
            @Override
            public void onRewardedAdShow() {
                MobclickAgent.onEvent(MoneyTaskActivity.this, "moneytasksshow", "1");//参数二为当前统计的事件ID
            }

            @Override
            public void onRewardedAdShowFail() {
                moneyDialogs4();
            }

            @Override
            public void onRewardClick() {

            }

            @Override
            public void onVideoComplete() {

            }

            @Override
            public void setVideoCallBacks() {

            }

            @Override
            public void onRewardedAdClosed(boolean isVideoClick, boolean isCompeter) {
                if (isCompeter) {
                    mPresenter.getMoneyTask(CacheDataUtils.getInstance().getUserInfo().getId() + "");
                } else {
                    ToastUtil.showToast("请重新观看哦！");
                }

            }
        });
    }


    @OnClick({R.id.iv_close, R.id.tv_sure, R.id.line1, R.id.line2, R.id.line3, R.id.line4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_sure:
                if (index ==10) {
                    ToastUtil.showToast("今日任务已完成，请明日再来哦！");
                } else {
                    if (status == 0) {
                        ToastUtil.showToast("请先完成任务再提现哦！");
                    } else {
                        wxLogin();
                    }
                }
                break;
            case R.id.line1:
                if (index == 0) {
                    moneyDialogs3();
                }
                break;
            case R.id.line2:
                if (index == 1 || index == 0) {
                    moneyDialogs5("请先完成"+moneys1+"任务", "才能开启"+moneys2+"提现任务");
                }
                break;
            case R.id.line3:
                if (index == 2 || index == 1 || index == 0) {
                    moneyDialogs5("请先完成"+moneys2+"任务", "才能开启"+moneys3+"提现任务");
                }
                break;
            case R.id.line4:
                if (index == 2 || index == 1 || index == 0|| index == 3) {
                    moneyDialogs5("请先完成"+moneys3+"任务", "才能开启"+moneys4+"提现任务");
                }
                break;
        }
    }

    public class MyAuthLoginListener implements UMAuthListener {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            String unionid = map.get("unionid");
            String wx_openid = map.get("openid");
            String name = map.get("name");
            String profile_image_url = map.get("profile_image_url");
            if (!TextUtils.isEmpty(wx_openid)) {
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                String appVersionCode = CommonUtils.getAppVersionCode(MoneyTaskActivity.this);
                int appType = 2;
                mPresenter.getMoneyTaskTx(CacheDataUtils.getInstance().getUserInfo().getId() + "", txid, wx_openid, appVersionCode);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            ToastUtil.showToast("授权失败:" + throwable.getMessage() + i + "------getLocalizedMessage()" + throwable.getLocalizedMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            ToastUtil.showToast("授权取消");
        }
    }

    private void wxLogin() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN, null);
        UMShareAPI.get(this).release();
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new MyAuthLoginListener());
    }

    private SignDialog moneyDialogs2;

    public void moneyDialogs3() {
        moneyDialogs2 = new SignDialog(this);
        View builder = moneyDialogs2.builder(R.layout.money_task3_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                moneyDialogs2.setDismiss();
            }
        });
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                moneyDialogs2.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            moneyDialogs2.setShow();
        }
    }

    private SignDialog moneyDialogs4;

    public void moneyDialogs4() {
        moneyDialogs4 = new SignDialog(this);
        View builder = moneyDialogs4.builder(R.layout.money_task4_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                moneyDialogs4.setDismiss();
            }
        });
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                moneyDialogs4.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            moneyDialogs4.setShow();
        }
    }

    private SnatchDialog moneyDialogs5;

    public void moneyDialogs5(String tips1, String tips2) {
        moneyDialogs5 = new SnatchDialog(this);
        View builder = moneyDialogs5.builder(R.layout.money_task5_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv1 = builder.findViewById(R.id.tv1);
        TextView tv2 = builder.findViewById(R.id.tv2);
        tv1.setText(tips1);
        tv2.setText(tips2);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                moneyDialogs5.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            moneyDialogs5.setShow();
        }
    }

    private int index;
    private int status;
    private String moneys1;
    private String moneys2;
    private String moneys3;
    private String moneys4;

    @Override
    public void getMoneyTaskSuccess(List<MoneyTaskBeans> data) {
        if (data != null) {
            List<MoneyTaskBeans> s = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getStatus() == 0) {
                    txid = data.get(i).getId();
                    index = i;
                    status = data.get(i).getStatus();
                    s.add(data.get(i));
                    break;
                } else {
                    if (data.get(i).getIs_tx() == 0) {
                        txid = data.get(i).getId();
                        index = i;
                        status = data.get(i).getStatus();
                        s.add(data.get(i));
                        break;
                    }
                }
            }
            if (s.size() == 0) {
                index =10;
                status = data.get(data.size() - 1).getStatus();
                txid = data.get(data.size() - 1).getId();
                s.add(data.get(data.size() - 1));
            }
            for (int i = 0; i < data.size(); i++) {
                if (i == 0) {
                    tvMoney1.setText(data.get(i).getMoney());
                    moneys1=data.get(i).getMoney();
                } else if (i == 1) {
                    tvMoney2.setText(data.get(i).getMoney());
                    moneys2=data.get(i).getMoney();
                } else if (i == 2) {
                    tvMoney3.setText(data.get(i).getMoney());
                    moneys3=data.get(i).getMoney();
                } else if (i == 3) {
                    tvMoney4.setText(data.get(i).getMoney());
                    moneys4=data.get(i).getMoney();
                }
            }
            if (index == 0) {
                tvMoney1.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvMoney2.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvMoney3.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvMoney4.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvStatus1.setText("待完成");
                tvStatus2.setText("待完成");
                tvStatus3.setText("待完成");
                tvStatus4.setText("待完成");

                tvStatus1.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvStatus2.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvStatus3.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvStatus4.setTextColor(getResources().getColor(R.color.yellow_FBFE02));

                ivMoneyAsk1.setVisibility(View.VISIBLE);
                ivMoneyAsk2.setVisibility(View.VISIBLE);
                ivMoneyAsk3.setVisibility(View.VISIBLE);
                ivMoneyAsk4.setVisibility(View.VISIBLE);
                ivMoney1.setImageDrawable(getResources().getDrawable(R.drawable.you_icon4));
                ivMoney2.setImageDrawable(getResources().getDrawable(R.drawable.you_icon4));
                ivMoney3.setImageDrawable(getResources().getDrawable(R.drawable.you_icon4));
                ivMoney4.setImageDrawable(getResources().getDrawable(R.drawable.you_icon4));
            } else if (index == 1) {
                tvMoney1.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvMoney2.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvMoney3.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvMoney4.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvStatus1.setText("已完成");
                tvStatus2.setText("待完成");
                tvStatus3.setText("待完成");
                tvStatus4.setText("待完成");
                tvStatus1.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvStatus2.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvStatus3.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvStatus4.setTextColor(getResources().getColor(R.color.yellow_FBFE02));

                ivMoneyAsk1.setVisibility(View.GONE);
                ivMoneyAsk2.setVisibility(View.VISIBLE);
                ivMoneyAsk3.setVisibility(View.VISIBLE);
                ivMoneyAsk4.setVisibility(View.VISIBLE);

                ivMoney1.setImageDrawable(getResources().getDrawable(R.drawable.you_icon5));
                ivMoney2.setImageDrawable(getResources().getDrawable(R.drawable.you_icon4));
                ivMoney3.setImageDrawable(getResources().getDrawable(R.drawable.you_icon4));
                ivMoney4.setImageDrawable(getResources().getDrawable(R.drawable.you_icon4));
            } else if (index == 2) {
                tvMoney1.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvMoney2.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvMoney3.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvMoney4.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvStatus1.setText("已完成");
                tvStatus2.setText("已完成");
                tvStatus3.setText("待完成");
                tvStatus4.setText("待完成");
                tvStatus1.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvStatus2.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvStatus3.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvStatus4.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                ivMoneyAsk1.setVisibility(View.GONE);
                ivMoneyAsk2.setVisibility(View.GONE);
                ivMoneyAsk3.setVisibility(View.VISIBLE);
                ivMoneyAsk4.setVisibility(View.VISIBLE);
                ivMoney1.setImageDrawable(getResources().getDrawable(R.drawable.you_icon5));
                ivMoney2.setImageDrawable(getResources().getDrawable(R.drawable.you_icon5));
                ivMoney3.setImageDrawable(getResources().getDrawable(R.drawable.you_icon4));
                ivMoney4.setImageDrawable(getResources().getDrawable(R.drawable.you_icon4));
            }else if (index == 3) {
                tvMoney1.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvMoney2.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvMoney3.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvMoney4.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                tvStatus1.setText("已完成");
                tvStatus2.setText("已完成");
                tvStatus3.setText("已完成");
                tvStatus4.setText("待完成");
                tvStatus1.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvStatus2.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvStatus3.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvStatus4.setTextColor(getResources().getColor(R.color.yellow_FBFE02));
                ivMoneyAsk1.setVisibility(View.GONE);
                ivMoneyAsk2.setVisibility(View.GONE);
                ivMoneyAsk3.setVisibility(View.GONE);
                ivMoneyAsk4.setVisibility(View.VISIBLE);
                ivMoney1.setImageDrawable(getResources().getDrawable(R.drawable.you_icon5));
                ivMoney2.setImageDrawable(getResources().getDrawable(R.drawable.you_icon5));
                ivMoney3.setImageDrawable(getResources().getDrawable(R.drawable.you_icon5));
                ivMoney4.setImageDrawable(getResources().getDrawable(R.drawable.you_icon4));
            } else {
                tvMoney1.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvMoney2.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvMoney3.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvMoney4.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvStatus1.setText("已完成");
                tvStatus2.setText("已完成");
                tvStatus3.setText("已完成");
                tvStatus4.setText("已完成");
                tvStatus1.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvStatus2.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvStatus3.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                tvStatus4.setTextColor(getResources().getColor(R.color.yellow_FEB402));
                ivMoneyAsk1.setVisibility(View.GONE);
                ivMoneyAsk2.setVisibility(View.GONE);
                ivMoneyAsk3.setVisibility(View.GONE);
                ivMoneyAsk4.setVisibility(View.GONE);
                ivMoney1.setImageDrawable(getResources().getDrawable(R.drawable.you_icon5));
                ivMoney2.setImageDrawable(getResources().getDrawable(R.drawable.you_icon5));
                ivMoney3.setImageDrawable(getResources().getDrawable(R.drawable.you_icon5));
                ivMoney4.setImageDrawable(getResources().getDrawable(R.drawable.you_icon5));
            }
            moneyTaskAdapter.setNewData(s);
            moneyTaskAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getMoneyTaskTxSuccess(EmptyBeans data) {
        mPresenter.getMoneyTask(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        cashSuccessDialogs();
    }

    private SnatchDialog cashSuccessDialog;

    public void cashSuccessDialogs() {
        cashSuccessDialog = new SnatchDialog(this);
        View builder = cashSuccessDialog.builder(R.layout.cashsuccess_item_dialog);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                cashSuccessDialog.setDismiss();
            }
        });
        cashSuccessDialog.setOutCancle(false);
        if (!CommonUtils.isDestory(this)) {
            cashSuccessDialog.setShow();
        }
    }
}