package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.LevelDialog;
import com.yc.redevenlopes.homeModule.contact.RedRainContact;
import com.yc.redevenlopes.homeModule.module.bean.RedRainBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.present.RedRainPresenter;
import com.yc.redevenlopes.homeModule.widget.RedPacketsLayout;
import com.yc.redevenlopes.homeModule.widget.ToastShowViews;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.DisplayUtil;
import com.yc.redevenlopes.utils.SoundPoolUtils;
import com.yc.redevenlopes.utils.VUiKit;

import butterknife.BindView;


public class RedRainActivity extends BaseActivity<RedRainPresenter> implements RedRainContact.View {
    @BindView(R.id.redpacketslayout)
    RedPacketsLayout redpacketslayout;

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
            Log.d("ccc", "-------------onTick: "+l);
            if (l==12){
                showInsertVideo();
            }
        }

        @Override
        public void onFinish() {
                 finish();
        }
    };
    @Override
    public int getLayout() {
        return R.layout.activity_red_rain;
    }

    @Override
    public void initEventAndData() {
        loadInsertView(null);
        initRedDialogOne();
        loadVideo();
        setFullScreen();
        initRedRain();
    }

    private void initRedRain() {
        redpacketslayout.post(new Runnable() {
            @Override
            public void run() {
                redpacketslayout.startRain();
            }
        });
        downTimer.start();
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
                finish();
            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("ccc", "========onNoAd=====loadInsertView: "+adError.getCode()+"---"+adError.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {
                Log.d("ccc", "==========loadInsertView===onPresent: ");
                isShowInset=true;
                if (downTimer != null) {
                    downTimer.cancel();
                    downTimer = null;
                }
                if (redpacketslayout!=null){
                    redpacketslayout.stopRain();
                }
            }

            @Override
            public void onClick() {
                    info_id="";
                    type=1;
                    mPresenter.getRedRainMoney(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", info_id);
            }

            @Override
            public void onLoaded() {
                Log.d("ccc", "==========loadInsertView===onLoaded: ");
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
            Log.d("ccc", "======00000000000====loadInsertView===: ");
            loadInsertView(null);
        } else {
            Log.d("ccc", "======11111111111====loadInsertView===: ");
            loadInsertView(new Runnable() {
                @Override
                public void run() {
                    Log.d("ccc", "======22222222222====loadInsertView===: ");
                    adPlatformSDK.showInsertAd();
                }
            });
        }
    }

    @Override
    public void getRedRainMoneySuccess(RedRainBeans data) {
        info_id = data.getInfo_id();
        if (type==1){
            VUiKit.postDelayed(500, () -> {
                showRedDialogOne(data.getMoney());
            });
        }
        if (type==2){
            ToastUtil.showToast("领取成功");
            finish();
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
            if (redDialogsone!=null){
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
                redDialogsone.setShow();
            }
        }
    }


    private void loadVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "rainvideo", new AdCallback() {
            @Override
            public void onDismissed() {
                type=2;
                mPresenter.getRedRainMoney(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", info_id);
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
        if (redpacketslayout!=null){
            redpacketslayout.clearAnimation();
            redpacketslayout=null;
        }
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = null;
        }
        super.onDestroy();
    }
}