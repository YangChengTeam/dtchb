package com.yc.redguess.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.http.ResultRefreshSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.dialog.LevelDialog;
import com.yc.redguess.homeModule.contact.RedRainContact;
import com.yc.redguess.homeModule.module.HomeApiModule;
import com.yc.redguess.homeModule.module.bean.RedRainBeans;
import com.yc.redguess.homeModule.present.RedRainPresenter;
import com.yc.redguess.homeModule.widget.RedPacketsLayout;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.CommonUtils;
import com.yc.redguess.utils.DisplayUtil;
import com.yc.redguess.utils.SoundPoolUtils;
import com.yc.redguess.utils.VUiKit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;


public class RedRainActivity extends BaseActivity<RedRainPresenter> implements RedRainContact.View {
    @BindView(R.id.redpacketslayout)
    RedPacketsLayout redpacketslayout;
    @BindView(R.id.tv_times)
    TextView tvTimes;
    @BindView(R.id.viewssss)
    View view;
    private boolean isShowMoneyDialog;
    private String info_id;
    private int type;//1 翻倍  2不翻倍
    private boolean isShowInset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    private CountDownTimer downTimer = new CountDownTimer(15 * 1000, 1000) {
        @Override
        public void onTick(long time) {
            long l = time / 1000;
            if (l == 11) {
                showInsertVideo();
            }
        }

        @Override
        public void onFinish() {
            finish();
        }
    };

    private CountDownTimer downTimerTwo = new CountDownTimer(4 * 1000, 1000) {
        @Override
        public void onTick(long time) {
            long l = time / 1000;
            tvTimes.setText(l + "");
            tvTimes.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFinish() {
            if (redpacketslayout != null && downTimer != null) {
                RedRainActivity.this.runOnUiThread(() -> {
                    view.setAlpha(0.4f);
                    view.setBackgroundColor(Color.parseColor("#000000"));
                    view.setVisibility(View.VISIBLE);
                    tvTimes.setVisibility(View.GONE);
                });
                redpacketslayout.post(new Runnable() {
                    @Override
                    public void run() {
                        redpacketslayout.startRain();
                    }
                });
                downTimer.start();
            }
        }
    };


    @Override
    public int getLayout() {
        return R.layout.activity_red_rain;
    }

    public CompositeDisposable mDisposables;
    public HomeApiModule apis;

    @Override
    public void initEventAndData() {
        setFullScreen();
        apis = new HomeApiModule();
        mDisposables = new CompositeDisposable();
        loadInsertView(null);
        initRedDialogOne();
        loadVideo();
        view.setVisibility(View.GONE);
        initRedRain();
    }

    private void initRedRain() {
        downTimerTwo.start();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    public static void redRainJump(Context context) {
        Intent intent = new Intent(context, RedRainActivity.class);
        context.startActivity(intent);
    }


    private void loadInsertView(Runnable runnable) {
        int screenWidth = CommonUtils.getScreenWidth(this);
        int screenHeight = CommonUtils.getScreenHeight(this);
        int w = (int) (screenWidth) * 9 / 10;
        int h = w * 3 / 2;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(RedRainActivity.this, w);
        int dph = DisplayUtil.px2dip(RedRainActivity.this, h);
        adPlatformSDK.loadInsertAd(this, "raininsert", dpw, dph, new AdCallback() {
            @Override
            public void onDismissed() {
                Log.d("ccc", "----------finsh------------onDismissed: "+isShowMoneyDialog);
                if (!isShowMoneyDialog) {
                    finish();
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("ccc", "========onNoAd=====loadInsertView: " + adError.getCode() + "---" + adError.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {
                Log.d("ccc", "==========loadInsertView===onPresent: ");
                isShowInset = true;
                if (downTimer != null) {
                    downTimer.cancel();
                    downTimer = null;
                }
                if (redpacketslayout != null) {
                    redpacketslayout.stopRain();
                }
            }

            @Override
            public void onClick() {
                RedRainActivity.this.runOnUiThread(() -> {
                    if (!isShowMoneyDialog){
                        isShowMoneyDialog=true;
                        info_id = "";
                        type = 1;
                        view.setVisibility(View.GONE);
                        getMoneyData(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", info_id);
                    }
                });
            }

            @Override
            public void onLoaded() {
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    private void showInsertVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setAdPosition("raininsert");
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

    @Override
    public void getRedRainMoneySuccess(RedRainBeans data) {
        info_id = data.getInfo_id();
        if (type == 1) {
            showRedDialogOne(data.getMoney());
        }
        if (type == 2) {
            initNewLogin(data.getMoney());
        }
    }

    private LevelDialog redDialogsone;
    private FrameLayout fl_content_one;
    private RelativeLayout rela_fanbei;
    private ImageView iv_close;
    private TextView tv_money;
    private boolean isshowOne;
    private TextView tv_fanbeiNumss;

    private void initRedDialogOne() {
        redDialogsone = new LevelDialog(this);
        View builder = redDialogsone.builder(R.layout.level_reward_item);
        fl_content_one = builder.findViewById(R.id.fl_content_one);
        rela_fanbei = builder.findViewById(R.id.rela_fanbei);
        tv_money = builder.findViewById(R.id.tv_money);
        iv_close = builder.findViewById(R.id.iv_close);
        tv_fanbeiNumss = builder.findViewById(R.id.tv_fanbeiNums);
        redDialogsone.setOutCancle(false);
        loadExone();
    }

    private void loadExone() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadExpressAd(this, "rainexpress", 300, 200, new AdCallback() {
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
                if (!isshowOne) {
                    adPlatformSDK.showExpressAd();
                }
            }
        }, fl_content_one);
    }

    public void showRedDialogOne(String money) {
        if (!CommonUtils.isDestory(RedRainActivity.this)) {
            if (redDialogsone != null) {
                if (tv_money != null) {
                    tv_money.setText(money);
                    tv_fanbeiNumss.setText("翻倍奖励X" + 2);
                    rela_fanbei.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SoundPoolUtils instance = SoundPoolUtils.getInstance();
                            instance.initSound();
                            showVideo();
                            if (redDialogsone != null) {
                                redDialogsone.setDismiss();
                            }
                        }
                    });
                    iv_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SoundPoolUtils instance = SoundPoolUtils.getInstance();
                            instance.initSound();
                            if (redDialogsone != null) {
                                redDialogsone.setDismiss();
                            }
                            finish();
                        }
                    });
                }
                loadExone();
                final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
                adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
                isshowOne = adPlatformSDK.showExpressAd();
                VUiKit.postDelayed(2000, () -> {
                    iv_close.setVisibility(View.VISIBLE);
                });
                if (!CommonUtils.isDestory(RedRainActivity.this)) {
                    isShowMoneyDialog = true;
                    Log.d("ccc", "------------dasgasdfgsdfgsfdg----------showRedDialogOne: ");
                    redDialogsone.setShow();
                }
            }
        }
    }


    private void loadVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "rainvideo", new AdCallback() {
            @Override
            public void onDismissed() {
                type = 2;
                getMoneyData(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", info_id);
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

            }
        });
    }

    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        loadVideo();
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showRewardVideoAd();
    }

    @Override
    protected void onDestroy() {
        if (redpacketslayout != null) {
            redpacketslayout.clearAnimation();
            redpacketslayout = null;
        }
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = null;
        }
        if (downTimerTwo != null) {
            downTimerTwo.cancel();
            downTimerTwo = null;
        }
        if (red != null) {
            red.setDismiss();
        }
        super.onDestroy();
    }

    private LevelDialog red;

    public void initNewLogin(String moneys) {
        red = new LevelDialog(this);
        View builder = red.builder(R.layout.newlogin_dialog_item);
        TextView tv_moneys = builder.findViewById(R.id.tv_moneys);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_moneys.setText(moneys + "元红包");
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        red.setOutCancle(false);
        if (!CommonUtils.isDestory(RedRainActivity.this)) {
            red.setShow();
        }
    }

    public void getMoneyData(String imei, String groupId, String info_idss) {
        if (mDisposables != null && apis != null) {
            mDisposables.add(apis.getRedRainMoney(imei, groupId, info_idss).compose(RxUtil.rxSchedulerHelper())
                    .subscribeWith(new ResultRefreshSubscriber<RedRainBeans>() {
                        @Override
                        public void onAnalysisNext(RedRainBeans data) {
                            if (!CommonUtils.isDestory(RedRainActivity.this)){
                                info_id = data.getInfo_id();
                                if (type == 1) {
                                    if (!CommonUtils.isDestory(RedRainActivity.this)){
                                        showRedDialogOne(data.getMoney());
                                    }
                                }
                                if (type == 2) {
                                    if (!CommonUtils.isDestory(RedRainActivity.this)){
                                        initNewLogin(data.getMoney());
                                    }
                                }
                            }

                        }

                        @Override
                        public void errorState(String message, String state) {
                            Log.d("ccc", "-----errorState--finsh------------: ");
                               finish();
                        }
                    }));
        }
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                Log.d("ccc", "-------22122---finsh------------onDismissed: "+isShowMoneyDialog);
                finish();
                break;
        }
    }
}