package com.yc.majiaredgrab.homeModule.activity;


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

import com.bumptech.glide.Glide;
import com.lq.lianjibusiness.base_libary.http.ResultRefreshSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.constants.Constant;
import com.yc.majiaredgrab.dialog.LevelDialog;
import com.yc.majiaredgrab.homeModule.contact.RedRainContact;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;
import com.yc.majiaredgrab.homeModule.module.bean.RedRainBeans;
import com.yc.majiaredgrab.homeModule.present.RedRainPresenter;
import com.yc.majiaredgrab.homeModule.widget.RedPacketsLayout;
import com.yc.majiaredgrab.utils.AppSettingUtils;
import com.yc.majiaredgrab.utils.CacheDataUtils;
import com.yc.majiaredgrab.utils.CommonUtils;
import com.yc.majiaredgrab.utils.DisplayUtil;
import com.yc.majiaredgrab.utils.SoundPoolUtils;
import com.yc.majiaredgrab.utils.VUiKit;

import java.util.Map;

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
        loadTx();
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
    private ImageView iv_jiasu;
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
        iv_jiasu=builder.findViewById(R.id.iv_jiasu);
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
                            if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                                showVideo();
                            }else {
                                showTx();
                            }
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
                Glide.with(RedRainActivity.this).asGif().load(R.drawable.jiasu).into(iv_jiasu);
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
    private String isLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功

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
                if ("1".equals(isLoadAdSuccess)){
                    isLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        showTx();
                    }else {
                        if (!CommonUtils.isDestory(RedRainActivity.this)) {
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
                isLoadAdSuccess="3";
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                isLoadAdSuccess="3";
            }
        });
    }

    private void showVideo() {
        isLoadAdSuccess="1";
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
        if (mRewardVideoAD != null) {
            mRewardVideoAD.destroy();
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
                               finish();
                        }
                    }));
        }
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }


    public void showTx(){
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                if (!CommonUtils.isDestory(RedRainActivity.this)) {
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
                        if (!CommonUtils.isDestory(RedRainActivity.this)) {
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
                    // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                    isTxLoadAdSuccess="1";
                    mRewardVideoAD
                            .showAD(RedRainActivity.this);
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
            }

            @Override
            public void onShow() {
                isTxLoadAdSuccess="3";
                AppSettingUtils.showTxShow("tx_rainvideo");
            }

            @Override
            public void onExpose() {
            }

            /**
             * 模板激励视频触发激励
             *
             * @param map 若选择了服务端验证，可以通过 ServerSideVerificationOptions#TRANS_ID 键从 map 中获取此次交易的 id；若未选择服务端验证，则不需关注 map 参数。
             */
            @Override
            public void onReward(Map<String, Object> map) {

            }

            @Override
            public void onClick() {
                AppSettingUtils.showTxClick("tx_rainvideo");

            }

            @Override
            public void onVideoComplete() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
            }

            @Override
            public void onClose() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
                type = 2;
                getMoneyData(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", info_id);
            }

            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                if ("2".equals(isTxLoadAdSuccess)){
                    isTxLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("2".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        showVideo();
                    }else {
                        if (!CommonUtils.isDestory(RedRainActivity.this)) {
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