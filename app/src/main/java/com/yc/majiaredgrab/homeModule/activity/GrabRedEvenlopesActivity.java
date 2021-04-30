package com.yc.majiaredgrab.homeModule.activity;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import com.yc.majiaredgrab.dialog.SnatchDialog;
import com.yc.majiaredgrab.homeModule.adapter.TaskUnlockAdapter;
import com.yc.majiaredgrab.homeModule.contact.GrabRedEvenlopesContact;
import com.yc.majiaredgrab.homeModule.module.bean.GoToSignBeans;
import com.yc.majiaredgrab.homeModule.module.bean.LookVideoBeans;
import com.yc.majiaredgrab.homeModule.module.bean.LookVideoMoneyBeans;
import com.yc.majiaredgrab.homeModule.module.bean.SeekBeans;
import com.yc.majiaredgrab.homeModule.module.bean.SeekRedMoneyBean;
import com.yc.majiaredgrab.homeModule.module.bean.SignInfoBeans;
import com.yc.majiaredgrab.homeModule.module.bean.TaskUnLockResBeans;
import com.yc.majiaredgrab.homeModule.module.bean.TaskUnlock;
import com.yc.majiaredgrab.homeModule.module.bean.UpFindRedBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UserInfo;
import com.yc.majiaredgrab.homeModule.present.GrabRedEvenlopesPresenter;
import com.yc.majiaredgrab.homeModule.widget.SignView;
import com.yc.majiaredgrab.homeModule.widget.SimpleComponentTwo;
import com.yc.majiaredgrab.homeModule.widget.ToastShowViews;
import com.yc.majiaredgrab.homeModule.widget.gu.Guide;
import com.yc.majiaredgrab.homeModule.widget.gu.GuideBuilder;
import com.yc.majiaredgrab.utils.AppSettingUtils;
import com.yc.majiaredgrab.utils.CacheDataUtils;
import com.yc.majiaredgrab.utils.ClickListenName;
import com.yc.majiaredgrab.utils.ClickListenNameTwo;
import com.yc.majiaredgrab.utils.CommonUtils;
import com.yc.majiaredgrab.utils.PermissionHelper;
import com.yc.majiaredgrab.utils.SoundPoolUtils;
import com.yc.majiaredgrab.utils.TimesUtils;
import com.yc.majiaredgrab.utils.ToastUtilsViews;
import com.yc.majiaredgrab.utils.VUiKit;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class GrabRedEvenlopesActivity extends BaseActivity<GrabRedEvenlopesPresenter> implements GrabRedEvenlopesContact.View {

    @BindView(R.id.tv_lookRh)
    TextView tvLookRh;
    @BindView(R.id.rela_redOne)
    RelativeLayout relaRedOne;
    @BindView(R.id.rela_redTwo)
    RelativeLayout relaRedTwo;
    @BindView(R.id.rela_redThree)
    RelativeLayout relaRedThree;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_getRed)
    ImageView ivGetRed;
    @BindView(R.id.iv_redOne)
    ImageView ivRedOne;
    @BindView(R.id.iv_redTwo)
    ImageView ivRedTwo;
    @BindView(R.id.iv_redThree)
    ImageView ivRedThree;
    @BindView(R.id.tv_lookVideoNums)
    TextView tvLookVideoNums;
    @BindView(R.id.signView)
    SignView signView;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.line_signTixian)
    LinearLayout lineSignTixian;
    private int step;
    private int position = 2;
    private float moveSpan;
    private int lookVideoNums;
    private int totalVideoNums;
    private int type;//看视频的类型  1 看视频不翻倍 2 看视频翻倍  3 没有抽中 4 找红包   5作弊
    private int taskType;//看视频的类型  1 看视频 2 找红包  3签到   4 第7天签到
    private int redNumType;//看视频的类型  1 找红包 2 翻红包
    private String info_id;
    private String seek_info_id;
    private int seekRedFindNums;
    //  private String seekMoney;
    private int seekRedFindFanNums;
    private SeekBeans seekBeans;
    private float fanNums;//翻倍倍率
    private SignInfoBeans signInfoBeans;
    public int singDaysNums;
    public static WeakReference<GrabRedEvenlopesActivity> instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_grab_red_evenlopes;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVideo();

    }

    @Override
    public void initEventAndData() {
        instance = new WeakReference<>(this);
        map = new HashMap();
        relaRedOne.post(new Runnable() {
            @Override
            public void run() {
                int left = relaRedOne.getLeft();
                int leftTwo = relaRedTwo.getLeft();
                int leftThree = relaRedThree.getLeft();
                moveSpan = leftTwo - left;
            }
        });
        step = 1;
        initRedView();
        initRedDialogOne();
        initRedDialogTwo();
        loadTx();
        mPresenter.getlookVideo(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
        mPresenter.getSeekRed(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
        mPresenter.getSignInfo(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
        String qhb = CacheDataUtils.getInstance().getQhb();
        if (TextUtils.isEmpty(qhb)) {
            mPresenter.getRegUserLog(CacheDataUtils.getInstance().getUserInfo().getId(), "2");
        }
    }


    private LevelDialog redDialogsone;
    private FrameLayout fl_content_one;
    private RelativeLayout rela_fanbei;
    private ImageView iv_close;
    private TextView tv_money;
    private boolean isshowOne;
    private TextView tv_fanbeiNumss;
    private ImageView iv_jiasu;

    private void initRedDialogOne() {
        redDialogsone = new LevelDialog(this);
        View builder = redDialogsone.builder(R.layout.level_reward_item);
        fl_content_one = builder.findViewById(R.id.fl_content_one);
        rela_fanbei = builder.findViewById(R.id.rela_fanbei);
        tv_money = builder.findViewById(R.id.tv_money);
        iv_close = builder.findViewById(R.id.iv_close);
        iv_jiasu = builder.findViewById(R.id.iv_jiasu);
        tv_fanbeiNumss = builder.findViewById(R.id.tv_fanbeiNums);
        redDialogsone.setOutCancle(false);
        loadExone();
    }

    private void loadExone() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadExpressAd(this, "ad_qianghongb_one", 300, 200, new AdCallback() {
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

    private ObjectAnimator signScalex;
    private ObjectAnimator signScaley;
    private AnimatorSet signAnimatorSet;

    @SuppressLint("WrongConstant")
    public void startSignAn() {
        signScalex = ObjectAnimator.ofFloat(tvSign, "scaleX", 1.f, 0.85f, 1.15f, 1.0f);
        signScaley = ObjectAnimator.ofFloat(tvSign, "scaleY", 1.f, 0.85f, 1.15f, 1.0f);
        signScalex.setInterpolator(new LinearInterpolator());
        signScalex.setTarget(tvSign);
        signScalex.setDuration(1300);
        signScalex.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        signScalex.setRepeatMode(ValueAnimator.INFINITE);//
        signScaley.setTarget(tvSign);
        signScaley.setDuration(1300);
        signScaley.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        signScaley.setRepeatMode(ValueAnimator.INFINITE);//
        signScaley.setInterpolator(new LinearInterpolator());
        signAnimatorSet = new AnimatorSet();
        signAnimatorSet.setDuration(1300);
        signAnimatorSet.playTogether(signScalex, signScaley);
        signAnimatorSet.start();
    }


    public void inSet() {
        step = 1;
        initRedView();
        String one = (String) map.get("1");
        String two = (String) map.get("2");
        String three = (String) map.get("3");
        ObjectAnimator transXAnimones = null;
        if ("2".equals(one)) {
            transXAnimones = ObjectAnimator.ofFloat(relaRedTwo, "translationX", -moveSpan, 0);
            transXAnimones.setTarget(relaRedTwo);
            transXAnimones.setDuration(10);
            transXAnimones.start();
        } else if ("3".equals(one)) {
            transXAnimones = ObjectAnimator.ofFloat(relaRedThree, "translationX", -moveSpan * 2, 0);
            transXAnimones.setTarget(relaRedThree);
            transXAnimones.setDuration(10);
            transXAnimones.start();
        }

        ObjectAnimator transXAnimtwos = null;
        if ("1".equals(two)) {
            transXAnimtwos = ObjectAnimator.ofFloat(relaRedOne, "translationX", moveSpan, 0);
            transXAnimtwos.setTarget(relaRedOne);
            transXAnimtwos.setDuration(10);
            transXAnimtwos.start();
        } else if ("3".equals(two)) {
            transXAnimtwos = ObjectAnimator.ofFloat(relaRedThree, "translationX", -moveSpan, 0);
            transXAnimtwos.setTarget(relaRedThree);
            transXAnimtwos.setDuration(10);
            transXAnimtwos.start();
        }

        ObjectAnimator transXAnimthrees = null;
        if ("1".equals(three)) {
            transXAnimthrees = ObjectAnimator.ofFloat(relaRedOne, "translationX", moveSpan * 2, 0);
            transXAnimthrees.setTarget(relaRedOne);
            transXAnimthrees.setDuration(10);
            transXAnimthrees.start();
        } else if ("2".equals(three)) {
            transXAnimthrees = ObjectAnimator.ofFloat(relaRedTwo, "translationX", moveSpan, 0);
            transXAnimthrees.setTarget(relaRedTwo);
            transXAnimthrees.setDuration(10);
            transXAnimthrees.start();
        }
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
    }

    public void showRedDialogOne(String money, String sign) {
        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
            if (redDialogsone != null) {
                if (tv_money != null) {
                    tv_money.setText(money);
                    if (fanNums != 0) {
                        tv_fanbeiNumss.setText("翻倍奖励X" + fanNums);
                    }
                    rela_fanbei.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SoundPoolUtils instance = SoundPoolUtils.getInstance();
                            instance.initSound();
                            type = 2;
                            if ("1".equals(AppSettingUtils.getVideoType())) {//先头条
                                showVideo();
                            } else {
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
                        }
                    });
                    if (!TextUtils.isEmpty(sign)) {
                        rela_fanbei.setVisibility(View.GONE);
                    }
                }
                Glide.with(GrabRedEvenlopesActivity.this).asGif().load(R.drawable.jiasu).into(iv_jiasu);
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


    private SnatchDialog redDialogsTwo;
    ImageView iv_top;
    TextView tv2;
    TextView tv_noPrize;
    LinearLayout line_money;
    TextView tv_money_two;
    RelativeLayout rela_one;
    RelativeLayout rela_one_one;
    TextView tv_iwantCheat;
    TextView tv_levelNums;
    TextView tv_sureOne;
    TextView tv_sureTwo;
    TextView tv_sureThree;
    RelativeLayout rela_two;
    FrameLayout fl_content_Two;
    private boolean isshowTwo;

    private void initRedDialogTwo() {
        redDialogsTwo = new SnatchDialog(this);
        View builder = redDialogsTwo.builder(R.layout.level_reward_money);
        iv_top = builder.findViewById(R.id.iv_top);
        tv2 = builder.findViewById(R.id.tv2);
        fl_content_Two = builder.findViewById(R.id.fl_content);
        tv_noPrize = builder.findViewById(R.id.tv_noPrize);
        line_money = builder.findViewById(R.id.line_money);
        tv_money_two = builder.findViewById(R.id.tv_money);
        rela_one = builder.findViewById(R.id.rela_one);
        rela_one_one = builder.findViewById(R.id.rela_one_one);
        tv_iwantCheat = builder.findViewById(R.id.tv_iwantCheat);
        tv_levelNums = builder.findViewById(R.id.tv_levelNums);
        tv_sureOne = builder.findViewById(R.id.tv_sureOne);
        tv_sureTwo = builder.findViewById(R.id.tv_sureTwo);
        tv_sureThree = builder.findViewById(R.id.tv_sureThree);
        rela_two = builder.findViewById(R.id.rela_two);
        redDialogsTwo.setOutCancle(false);
        loadExTwo();
    }

    //type  1 2幸运抽红包领金币  2幸运抽红包领金币翻倍  3幸运抽红包领金币不翻倍 4没有抽中  5签到
    public void showRedDialogTwo(int types, String money) {
        if (redDialogsTwo != null) {
            if (tv_money_two != null) {
                if (types == 1) {
                    iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain_one));
                    iv_top.setScaleType(ImageView.ScaleType.FIT_XY);
                    tv2.setVisibility(View.VISIBLE);
                    line_money.setVisibility(View.VISIBLE);
                    tv_noPrize.setVisibility(View.GONE);
                    rela_one.setVisibility(View.GONE);
                    tv_sureTwo.setVisibility(View.GONE);
                    tv_sureOne.setVisibility(View.VISIBLE);
                    tv_sureThree.setVisibility(View.GONE);
                } else if (types == 2) {
                    tv_iwantCheat.setVisibility(View.GONE);
                    iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain_one));
                    iv_top.setScaleType(ImageView.ScaleType.FIT_XY);
                    tv2.setVisibility(View.VISIBLE);
                    line_money.setVisibility(View.VISIBLE);
                    tv_noPrize.setVisibility(View.GONE);
                    rela_one_one.setVisibility(View.VISIBLE);
                    rela_one.setVisibility(View.VISIBLE);
                    tv_sureTwo.setVisibility(View.GONE);
                    tv_sureOne.setVisibility(View.GONE);
                    tv_sureThree.setVisibility(View.VISIBLE);
                } else if (types == 3) {
                    tv_iwantCheat.setVisibility(View.GONE);
                    iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain_one));
                    iv_top.setScaleType(ImageView.ScaleType.FIT_XY);
                    tv2.setVisibility(View.VISIBLE);
                    line_money.setVisibility(View.VISIBLE);
                    tv_noPrize.setVisibility(View.GONE);
                    rela_one_one.setVisibility(View.VISIBLE);
                    rela_one.setVisibility(View.GONE);
                    tv_sureTwo.setVisibility(View.GONE);
                    tv_sureOne.setVisibility(View.GONE);
                    tv_sureThree.setVisibility(View.VISIBLE);
                } else if (types == 4) {
                    iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain));
                    iv_top.setScaleType(ImageView.ScaleType.FIT_XY);
                    tv_iwantCheat.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.GONE);
                    line_money.setVisibility(View.GONE);
                    tv_noPrize.setVisibility(View.VISIBLE);
                    rela_one_one.setVisibility(View.GONE);
                    rela_one.setVisibility(View.VISIBLE);
                    tv_sureTwo.setVisibility(View.VISIBLE);
                    tv_sureOne.setVisibility(View.GONE);
                    tv_sureThree.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(money)) {
                    tv_money_two.setText(money);
                }
                tv_sureOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SoundPoolUtils instance = SoundPoolUtils.getInstance();
                        instance.initSound();
                        if (redDialogsTwo != null) {
                            redDialogsTwo.setDismiss();
                        }
                    }
                });
                tv_sureTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SoundPoolUtils instance = SoundPoolUtils.getInstance();
                        instance.initSound();
                        if (redDialogsTwo != null) {
                            redDialogsTwo.setDismiss();
                        }
                    }
                });
                tv_sureThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SoundPoolUtils instance = SoundPoolUtils.getInstance();
                        instance.initSound();
                        if (redDialogsTwo != null) {
                            redDialogsTwo.setDismiss();
                        }
                    }
                });
                tv_iwantCheat.setOnClickListener(new View.OnClickListener() {//没有抽中
                    @Override
                    public void onClick(View v) {
                        SoundPoolUtils instance = SoundPoolUtils.getInstance();
                        instance.initSound();
                        type = 5;
                        if ("1".equals(AppSettingUtils.getVideoType())) {//先头条
                            showVideo();
                        } else {
                            showTx();
                        }
                        if (redDialogsTwo != null) {
                            redDialogsTwo.setDismiss();
                        }
                    }
                });
            }
            loadExTwo();
            final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
            adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
            isshowTwo = adPlatformSDK.showExpressAd();
            if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                redDialogsTwo.setShow();
            }
        }
    }

    private void loadExTwo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadExpressAd(this, "ad_qianghongb_two", 300, 200, new AdCallback() {
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
                if (!isshowTwo) {
                    adPlatformSDK.showExpressAd();
                }
            }
        }, fl_content_Two);
    }


    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    public void initRedView() {
        if (step == 1) {
            tvLookRh.setEnabled(true);
            ivRedOne.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_air));
            ivRedTwo.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_monert));
            ivRedThree.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_air));
            tvLookRh.setBackground(getResources().getDrawable(R.drawable.yellow_gradient2));
            relaRedOne.setEnabled(false);
            relaRedTwo.setEnabled(false);
            relaRedThree.setEnabled(false);


            tvLookRh.setTextColor(getResources().getColor(R.color.A1_C40000));
            tvLookRh.setText("开始找红包");
        } else if (step == 2) {
            tvLookRh.setEnabled(false);
            ivRedOne.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_box));
            ivRedTwo.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_box));
            ivRedThree.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_box));

            relaRedOne.setEnabled(false);
            relaRedTwo.setEnabled(false);
            relaRedThree.setEnabled(false);

            tvLookRh.setBackground(getResources().getDrawable(R.drawable.gray_gradient2));
            tvLookRh.setTextColor(getResources().getColor(R.color.A1_666666));
            tvLookRh.setText("红包准备中");
        } else if (step == 3) {
            tvLookRh.setEnabled(false);
            ivRedOne.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_box));
            ivRedTwo.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_box));
            ivRedThree.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_box));
            tvLookRh.setBackground(getResources().getDrawable(R.drawable.yellow_gradient2));

            relaRedOne.setEnabled(true);
            relaRedTwo.setEnabled(true);
            relaRedThree.setEnabled(true);

            tvLookRh.setTextColor(getResources().getColor(R.color.A1_C40000));
            tvLookRh.setText("哪个红包有金币");
        }
    }


    @OnClick({R.id.iv_back, R.id.tv_lookRh, R.id.rela_lookVideo, R.id.iv_turn, R.id.iv_getRed, R.id.rela_redTwo, R.id.rela_redOne, R.id.rela_redThree, R.id.iv_help, R.id.tv_sign})
    public void onViewClicked(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_lookRh:
                if (ClickListenNameTwo.isFastClick()) {
                    taskType = 2;
                    redNumType = 1;
                    mPresenter.getUpFindRed(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "1");
                }
                break;
            case R.id.rela_lookVideo:
                if (ClickListenNameTwo.isFastClick()) {
                    taskType = 1;
                    if (lookVideoNums > 0) {
                        type = 1;
                        if ("1".equals(AppSettingUtils.getVideoType())) {//先头条
                            showVideo();
                        } else {
                            showTx();
                        }
                    } else {
                        ToastUtil.showToast("您今天的看视频次数已用完");
                    }
                }
                break;
            case R.id.iv_turn:
                MobclickAgent.onEvent(this, "turnTable");//参数二为当前统计的事件ID
                TurnTableActivity.TurnTableJump(this);
                break;
            case R.id.iv_getRed:
                MobclickAgent.onEvent(this, "smokehb2");//幸运抽红包
                SmokeHbActivity.smokehbJump(this);
                break;
            case R.id.rela_redThree:
            case R.id.rela_redOne:
                if (ClickListenName.isFastClick()) {
                    taskType = 2;
                    if (seekRedFindFanNums == 2 || ((seekRedFindFanNums - 2) % 4) == 0) {//看视频
                        type = 3;
                        if ("1".equals(AppSettingUtils.getVideoType())) {//先头条
                            showVideo();
                        } else {
                            showTx();
                        }
                    } else {
                        showRedDialogTwo(4, "");
                    }
                    redNumType = 2;
                    mPresenter.getUpFindRed(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "2");
                    VUiKit.postDelayed(1000, () -> {
                        inSet();
                    });
                }
                break;
            case R.id.rela_redTwo:
                if (ClickListenName.isFastClick()) {
                    if (seekBeans != null) {
                        taskType = 2;
                        if (seekRedFindFanNums == 2 || ((seekRedFindFanNums - 2) % 4) == 0) {//看视频
                            type = 1;
                            if ("1".equals(AppSettingUtils.getVideoType())) {//先头条
                                showVideo();
                            } else {
                                showTx();
                            }
                        } else {
                            type = 1;
                            mPresenter.getSeekGetRedMoney(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "0", "", seekBeans.getMoney());
                        }
                        redNumType = 2;
                        mPresenter.getUpFindRed(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "2");
                        VUiKit.postDelayed(1000, () -> {
                            inSet();
                        });
                    }
                }
                break;
            case R.id.iv_help:
                showHelpDialog();
                break;
            case R.id.tv_sign:
                if (signInfoBeans != null) {
                    String signStr = tvSign.getText().toString().trim();
                    if (!TextUtils.isEmpty(signStr) && "前往提现".equals(signStr)) {
                        WithdrawActivity.WithdrawJump(this);
                    } else {
                        MobclickAgent.onEvent(this, "sign2");//签到
                        int is_signed = signInfoBeans.getIs_signed();
                        if (is_signed == 0) {
                            taskType = 3;
                            if (singDaysNums==1){
                                long currentTimeMillis = System.currentTimeMillis();
                                String signCalendar = CacheDataUtils.getInstance().getSignCalendar();
                                if (!TextUtils.isEmpty(signCalendar)){
                                    long ends = Long.parseLong(signCalendar);
                                    if (currentTimeMillis<=ends){
                                        if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                                            showVideo();
                                        }else {
                                            showTx();
                                        }
                                    }else {
                                        initPermissions();
                                    }
                                }else {
                                    initPermissions();
                                }
                            }else if (unlock==0&&other_info!=null){
                                taskType = 4;
                                unlockingDialog();
                            }else {
                                if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                                    showVideo();
                                }else {
                                    showTx();
                                }
                            }
                        } else {
                            ToastUtil.showToast("您今天已经签到过了");
                        }
                    }

                } else {
                    ToastUtil.showToast("签到信息错误，请重新打开这个页面");
                }
                break;

        }
    }


    private String[] request_permissions = new String[]{
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.READ_CALENDAR
    };
    private PermissionHelper mPermissionHelper;
    private void initPermissions() {
        mPermissionHelper = new PermissionHelper();
        mPermissionHelper.setMustPermissions(request_permissions);
        mPermissionHelper.checkAndRequestPermission(this, new PermissionHelper.OnRequestPermissionsCallback() {
            @Override
            public void onRequestPermissionSuccess() {
                if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                    showVideo();
                }else {
                    showTx();
                }
            }

            @Override
            public void onRequestPermissionError() {
                if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                    showVideo();
                }else {
                    showTx();
                }
            }
        });
    }


    private HashMap map;

    private void startRed() {
        for (int i = 0; i < 6; i++) {
            int times = i * 650;
            int finalI = i;
            Observable.timer(times, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Long aLong) {
                            int random = getRandom();
                            an(random, finalI);
                            initMap(random);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void initMap(int index) {
        for (int i = 0; i < map.size(); i++) {
            if (index == 0) {
                String one = (String) map.get("1");
                String two = (String) map.get("2");
                map.put("2", one);
                map.put("1", two);
            } else if (index == 1) {
                String two = (String) map.get("2");
                String three = (String) map.get("3");
                map.put("2", three);
                map.put("3", two);
            }
        }
    }

    ObjectAnimator transXAnimOne = null;
    ObjectAnimator transXAnimTwo = null;
    AnimatorSet animatorSet = null;
    ObjectAnimator transXAnimThree = null;
    ObjectAnimator transXAnimFour = null;

    public void an(int index, int position) {
        if (index == 0) {
            String one = (String) map.get("1");
            String two = (String) map.get("2");
            if ("1".equals(one)) {
                transXAnimOne = ObjectAnimator.ofFloat(relaRedOne, "translationX", 0, moveSpan);
                transXAnimOne.setTarget(relaRedOne);
                transXAnimOne.setDuration(400);
            } else if ("2".equals(one)) {
                transXAnimOne = ObjectAnimator.ofFloat(relaRedTwo, "translationX", -moveSpan, 0);
                transXAnimOne.setTarget(relaRedTwo);
                transXAnimOne.setDuration(400);
            } else if ("3".equals(one)) {
                transXAnimOne = ObjectAnimator.ofFloat(relaRedThree, "translationX", -moveSpan * 2, -moveSpan);
                transXAnimOne.setTarget(relaRedThree);
                transXAnimOne.setDuration(400);
            }
            if ("1".equals(two)) {
                transXAnimTwo = ObjectAnimator.ofFloat(relaRedOne, "translationX", moveSpan, 0);
                transXAnimTwo.setTarget(relaRedOne);
                transXAnimTwo.setDuration(400);
            } else if ("2".equals(two)) {
                transXAnimTwo = ObjectAnimator.ofFloat(relaRedTwo, "translationX", 0, -moveSpan);
                transXAnimTwo.setTarget(relaRedTwo);
                transXAnimTwo.setDuration(400);
            } else if ("3".equals(two)) {
                transXAnimTwo = ObjectAnimator.ofFloat(relaRedThree, "translationX", -moveSpan, -moveSpan * 2);
                transXAnimTwo.setTarget(relaRedThree);
                transXAnimTwo.setDuration(400);
            }

            animatorSet = new AnimatorSet();
            animatorSet.playTogether(transXAnimOne, transXAnimTwo);
            animatorSet.start();

        } else if (index == 1) {
            String one = (String) map.get("2");
            String two = (String) map.get("3");
            if ("1".equals(one)) {
                transXAnimThree = ObjectAnimator.ofFloat(relaRedOne, "translationX", moveSpan, moveSpan * 2);
                transXAnimThree.setTarget(relaRedOne);
                transXAnimThree.setDuration(400);
            } else if ("2".equals(one)) {
                transXAnimThree = ObjectAnimator.ofFloat(relaRedTwo, "translationX", 0, moveSpan);
                transXAnimThree.setTarget(relaRedTwo);
                transXAnimThree.setDuration(400);
            } else if ("3".equals(one)) {
                transXAnimThree = ObjectAnimator.ofFloat(relaRedThree, "translationX", -moveSpan, 0);
                transXAnimThree.setTarget(relaRedThree);
                transXAnimThree.setDuration(400);
            }
            if ("1".equals(two)) {
                transXAnimFour = ObjectAnimator.ofFloat(relaRedOne, "translationX", moveSpan * 2, moveSpan);
                transXAnimFour.setTarget(relaRedOne);
                transXAnimFour.setDuration(400);
            } else if ("2".equals(two)) {
                transXAnimFour = ObjectAnimator.ofFloat(relaRedTwo, "translationX", moveSpan, 0);
                transXAnimFour.setTarget(relaRedTwo);
                transXAnimFour.setDuration(400);
            } else if ("3".equals(two)) {
                transXAnimFour = ObjectAnimator.ofFloat(relaRedThree, "translationX", 0, -moveSpan);
                transXAnimFour.setTarget(relaRedThree);
                transXAnimFour.setDuration(400);
            }
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(transXAnimThree, transXAnimFour);
            animatorSet.start();
        }

        if (position == 5) {
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    step = 3;
                    initRedView();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    public int getRandom() {
        Random random = new Random();
        int i = random.nextInt(2);
        return i;
    }

    public static void GrabRedJump(Context context) {
        Intent intent = new Intent(context, GrabRedEvenlopesActivity.class);
        context.startActivity(intent);
    }

    private void loadVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_qianghongb_three", new AdCallback() {
            @Override
            public void onDismissed() {
                if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                    ToastShowViews.cancleToast();
                }
                if (upTreasure > 0) {
                    if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                        ToastUtilsViews.showCenterToast("1", "");
                    }
                }
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                if (taskType == 1) {//看视频
                    if (type == 1) {//不翻倍
                        mPresenter.getlookVideoRedMoney(userInfo.getImei(), userInfo.getGroup_id(), "0", "", "");
                    } else if (type == 2) {//翻倍
                        mPresenter.getlookVideoRedMoney(userInfo.getImei(), userInfo.getGroup_id(), "1", info_id, "");
                    }
                } else if (taskType == 2) {//找红包
                    if (type == 2) {//翻倍
                        mPresenter.getSeekGetRedMoney(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "1", seek_info_id, seekBeans.getMoney());
                    } else if (type == 1) {//不翻倍
                        mPresenter.getSeekGetRedMoney(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "0", "", seekBeans.getMoney());
                    } else if (type == 3) {
                        showRedDialogTwo(4, "");
                    } else if (type == 4) {
                        VUiKit.postDelayed(400, () -> {
                            startAnmotions();
                        });
                    } else if (type == 5) {//不翻倍
                        mPresenter.getSeekGetRedMoney(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "0", "", seekBeans.getMoney());
                    }
                } else if (taskType == 3) {
                    mPresenter.sign(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
                    if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                        ToastShowViews.cancleToastTwo();
                    }
                }else if (taskType==4){
                    if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)){
                        ToastShowViews.cancleToastTwo();
                    }
                    if ("2".equals(Constant.ISBANNER)){
                        if (isVideoClick){
                            UserInfo userInfo1ss = CacheDataUtils.getInstance().getUserInfo();
                            mPresenter.getUnlociTask(userInfo1ss.getImei(),userInfo1ss.getGroup_id(),unLockTaskId);
                        }else {
                            showjiesuoTaskError();
                        }
                    }else {
                        UserInfo userInfo1ss = CacheDataUtils.getInstance().getUserInfo();
                        mPresenter.getUnlociTask(userInfo1ss.getImei(),userInfo1ss.getGroup_id(),unLockTaskId);
                    }
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                //  Log.d("ccc", "-------------onNoAd: "+adError.getMessage()+"----"+adError.getCode()+"-------"+adError.getTripartiteCode());
                if (taskType == 3) {
                    if ("1".equals(isLoadAdSuccess)) {
                        isLoadAdSuccess = "2";
                        //失败了播放腾讯的
                        if ("1".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                            showTx();
                        } else {
                            if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                                ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                            }
                        }
                    }
                }
                //loadVideo();
                // Log.d("ccc", "----------onNoAd: "+adError.getMessage()+"---"+adError.getCode());
            }

            @Override
            public void onComplete() {
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
                if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                    ToastShowViews.cancleToastTwo();
                }
            }

            @Override
            public void onPresent() {
                isLoadAdSuccess = "3";
                if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                    if (taskType == 3) {
                        long currentTimeMillis = System.currentTimeMillis();
                        String str = TimesUtils.getStr(currentTimeMillis);
                        if (!TextUtils.isEmpty(str) && !str.equals(String.valueOf(CacheDataUtils.getInstance().getUserInfo().getReg_date()))) {
                            ToastShowViews.showMyToastTwo("点击下载广告 解锁快速签到", "1");
                        }
                    }
                }
            }

            @Override
            public void onClick() {
                isVideoClick=true;
            }

            @Override
            public void onLoaded() {
                isLoadAdSuccess = "3";
                Log.d("ccc", "------------onLoaded: ");
            }
        });
    }

    private String isLoadAdSuccess = "0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功

    private void showVideo() {
        isLoadAdSuccess = "1";
        isVideoClick=false;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        loadVideo();
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showRewardVideoAd();
    }

    private int upTreasure = 0;

    @Override
    public void updtreasureSuccess(UpQuanNumsBeans data) {
        if (data != null) {
            upTreasure = data.getRand_num();
        }
    }

    @Override
    public void getlookVideoSuccess(LookVideoBeans data) {
        lookVideoNums = data.getVideo_num();
        totalVideoNums = data.getTotal();
        int s = data.getTotal() - data.getVideo_num();
        tvLookVideoNums.setText("看视频领红包（" + s + "/" + totalVideoNums + ")");
    }

    @Override
    public void getlookVideoRedMoneySuccess(LookVideoMoneyBeans data) {
        info_id = String.valueOf(data.getInfo_id());
        lookVideoNums = data.getOther_num();
        taskType = 1;
        int s = totalVideoNums - data.getOther_num();
        tvLookVideoNums.setText("看视频领红包（" + s + "/" + totalVideoNums + ")");
        if (type == 1) {
            showRedDialogOne(data.getMoney(), "");
        } else if (type == 2) {
            showRedDialogTwo(1, data.getMoney());
        }
    }

    @Override
    public void getSeekRedSuccess(SeekBeans seekBeans) {
        seekRedFindNums = seekBeans.getFind_num();
        seekRedFindFanNums = seekBeans.getTurn_num();
        this.seekBeans = seekBeans;
    }

    @Override
    public void getSeekGetRedMoneySuccess(SeekRedMoneyBean data) {
        seek_info_id = data.getInfo_id();
        taskType = 2;
        if (type == 1) {
            showRedDialogOne(data.getMoney(), "");
        } else if (type == 2) {
            showRedDialogTwo(1, data.getMoney());
        } else if (type == 5) {//作弊
            showRedDialogTwo(1, data.getMoney());
        }
    }

    @Override
    public void getUpFindRedSuccess(UpFindRedBeans data) {//更新红包
        if (data.getFind_num() != 0) {
            seekRedFindNums = data.getFind_num();
        }
        if (data.getTurn_num() != 0) {
            seekRedFindFanNums = data.getTurn_num();
        }
        if (redNumType == 1) {
            if (seekRedFindNums == 3 || ((seekRedFindNums - 3) % 4) == 0) {//看视频
                taskType = 2;
                type = 4;
                if ("1".equals(AppSettingUtils.getVideoType())) {//先头条
                    showVideo();
                } else {
                    showTx();
                }
            } else {
                startAnmotions();
            }
        }
    }
    private  int unlock;
    @Override
    public void getSignInfoSuccess(SignInfoBeans data) {//签到
        singDaysNums = data.getDays();
        unlock = data.getUnlock();
        if (data.getIs_signed()==0){//未签到
            tvSign.setText("立即签到");
            tvSign.setBackground(getResources().getDrawable(R.drawable.gray_gradient_yellow));
            startSignAn();
        }else {
            if (singDaysNums<7){
                tvSign.setText("再签到"+(7-data.getDays()+"天可提"+data.getMoney()+"元"));
                tvSign.setBackground(getResources().getDrawable(R.drawable.tv_bg_gray3));
            }else {
                tvSign.setText("前往提现");
                tvSign.setBackground(getResources().getDrawable(R.drawable.gray_gradient_yellow));
                startSignAn();
            }
        }
        if (unlock==0){
            other_info = data.getOther_info();
        }
        this.signInfoBeans = data;
        int progress = data.getDays() * 100 / 7;
        signView.setProgressBar(progress, data.getMoney());
    }

    @Override
    public void signSuccess(GoToSignBeans data) {
        if (signInfoBeans != null) {
            signInfoBeans.setIs_signed(1);
        }
        tvSign.setBackground(getResources().getDrawable(R.drawable.tv_bg_gray3));
        if (data.getDays() < 7) {
            tvSign.setText("再签到" + (7 - data.getDays() + "天可提" + signInfoBeans.getMoney() + "元"));
        } else {
            tvSign.setText("前往提现");
            tvSign.setBackground(getResources().getDrawable(R.drawable.gray_gradient_yellow));
        }
        String money = data.getMoney();
        int progress = data.getDays() * 100 / 7;
        signView.setProgressBar(progress, "");
        if (!TextUtils.isEmpty(money)) {
            showRedDialogOne(data.getMoney(), "1");
        }
        if (((MyApplication) MyApplication.getInstance()).levels == 1) {
            String signYinDao = CacheDataUtils.getInstance().getSignYinDao();
            if (TextUtils.isEmpty(signYinDao)){
                tixianSignDialogs();
            }
        }
    }

    @Override
    public void getUnlockTaskSuccess(TaskUnLockResBeans data) {
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
            mPresenter.sign(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
        }else {
            ToastUtil.showToast("请继续完成下一个任务哦！");
        }
    }

    @Override
    public void getUnlockTaskReeorState() {

    }


    public void startAnmotions() {//开始移动红包位置
        step = 2;
        initRedView();
        ivRedOne.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_box));
        ivRedTwo.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_box));
        ivRedThree.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_box));
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        startRed();
    }

    private void showHelpDialog() {
        SnatchDialog snatchDialog = new SnatchDialog(this);
        View builder = snatchDialog.builder(R.layout.help_dialog);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                snatchDialog.setDismiss();
            }
        });
        snatchDialog.setOutCancle(true);
        snatchDialog.setShow();
    }

    @Override
    protected void onDestroy() {
        if (transXAnimOne != null) {
            transXAnimOne.cancel();
            transXAnimOne = null;
        }
        if (transXAnimTwo != null) {
            transXAnimTwo.cancel();
            transXAnimTwo = null;
        }
        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet = null;
        }
        if (transXAnimThree != null) {
            transXAnimThree.cancel();
            transXAnimThree = null;
        }
        if (transXAnimFour != null) {
            transXAnimFour.cancel();
            transXAnimFour = null;
        }
        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet = null;
        }
        if (signScalex != null) {
            signScalex.cancel();
        }
        if (signScalex != null) {
            signScaley.cancel();
        }
        if (signAnimatorSet != null) {
            signAnimatorSet.cancel();
        }
        if (mRewardVideoAD != null) {
            mRewardVideoAD.destroy();
        }
        super.onDestroy();
    }

    private boolean isVideoToken;

    public void showTx() {
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            Log.i("ccc", "--222-showTx: ");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
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
                    Log.i("ccc", "--error-showTx: " + validity);
                    loadTxTwo();
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
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
                    isVideoClick=false;
                    mRewardVideoAD
                            .showAD(GrabRedEvenlopesActivity.this);
                    // 展示广告
                    break;
            }
        }
    }

    public void loadTxTwo() {
        mIsLoaded = false;
        loadTx();
    }


    private String isTxLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private ExpressRewardVideoAD mRewardVideoAD;
    private boolean isVideoClick;
    private boolean mIsLoaded;
    private boolean mIsCached;

    public void loadTx() {
        mRewardVideoAD = new ExpressRewardVideoAD(this, Constant.TXRVIDEO, new ExpressRewardVideoAdListener() {
            @Override
            public void onAdLoaded() {
                mIsLoaded = true;
                isTxLoadAdSuccess = "3";
                Log.i("ccc", "----onAdLoaded: ");
            }

            @Override
            public void onVideoCached() {
                // 在视频缓存完成之后再进行广告展示，以保证用户体验
                mIsCached = true;
                Log.i("ccc", "---onVideoCached: ");
            }

            @Override
            public void onShow() {
                isTxLoadAdSuccess = "3";
                AppSettingUtils.showTxShow("tx_ad_qianghongb_three");
                if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                    if (taskType == 3) {
                        long currentTimeMillis = System.currentTimeMillis();
                        String str = TimesUtils.getStr(currentTimeMillis);
                        if (!TextUtils.isEmpty(str) && !str.equals(String.valueOf(CacheDataUtils.getInstance().getUserInfo().getReg_date()))) {
                            ToastShowViews.showMyToastTwo("点击下载广告 解锁快速签到", "1");
                        }
                    }
                }
            }

            @Override
            public void onExpose() {
                Log.i("ccc", "---onExpose: ");
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
                AppSettingUtils.showTxClick("tx_ad_qianghongb_three");

            }

            @Override
            public void onVideoComplete() {
                if (mRewardVideoAD.hasShown()) {
                    loadTxTwo();
                }
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
                if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                    ToastShowViews.cancleToastTwo();
                }
            }

            @Override
            public void onClose() {
                if (mRewardVideoAD.hasShown()) {
                    loadTxTwo();
                }
                if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                    ToastShowViews.cancleToast();
                }
                if (upTreasure > 0) {
                    if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                        ToastUtilsViews.showCenterToast("1", "");
                    }
                }
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                if (taskType == 1) {//看视频
                    if (type == 1) {//不翻倍
                        mPresenter.getlookVideoRedMoney(userInfo.getImei(), userInfo.getGroup_id(), "0", "", "");
                    } else if (type == 2) {//翻倍
                        mPresenter.getlookVideoRedMoney(userInfo.getImei(), userInfo.getGroup_id(), "1", info_id, "");
                    }
                } else if (taskType == 2) {//找红包
                    if (type == 2) {//翻倍
                        mPresenter.getSeekGetRedMoney(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "1", seek_info_id, seekBeans.getMoney());
                    } else if (type == 1) {//不翻倍
                        mPresenter.getSeekGetRedMoney(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "0", "", seekBeans.getMoney());
                    } else if (type == 3) {
                        showRedDialogTwo(4, "");
                    } else if (type == 4) {
                        VUiKit.postDelayed(400, () -> {
                            startAnmotions();
                        });
                    } else if (type == 5) {//不翻倍
                        mPresenter.getSeekGetRedMoney(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "0", "", seekBeans.getMoney());
                    }
                } else if (taskType == 3) {
                    mPresenter.sign(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
                    if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                        ToastShowViews.cancleToastTwo();
                    }
                }else if (taskType==4){
                    if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)){
                        ToastShowViews.cancleToastTwo();
                    }
                    if ("2".equals(Constant.ISBANNER)){
                        if (isVideoClick){
                            UserInfo userInfo1ss = CacheDataUtils.getInstance().getUserInfo();
                            mPresenter.getUnlociTask(userInfo1ss.getImei(),userInfo1ss.getGroup_id(),unLockTaskId);
                        }else {
                            showjiesuoTaskError();
                        }
                    }else {
                        UserInfo userInfo1ss = CacheDataUtils.getInstance().getUserInfo();
                        mPresenter.getUnlociTask(userInfo1ss.getImei(),userInfo1ss.getGroup_id(),unLockTaskId);
                    }
                }
            }

            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                Log.d("ccc", "------showTx----------onError: " + adError.getErrorMsg() + "---" + adError.getErrorCode());
                if ("1".equals(isTxLoadAdSuccess)) {
                    isTxLoadAdSuccess = "2";
                    //失败了播放腾讯的
                    loadTxTwo();
                    if ("2".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                        showVideo();
                    } else {
                        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
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

    private LevelDialog tixanDialog;

    public void tixianSignDialogs() {
        CacheDataUtils.getInstance().setSignYinDao("signyindao");
        tixanDialog = new LevelDialog(this);
        View builder = tixanDialog.builder(R.layout.grab_tixian_dialog);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MemberActivity.instance != null && MemberActivity.instance.get() != null) {
                    MemberActivity.instance.get().finish();
                }
                MemberActivity.memberJump(GrabRedEvenlopesActivity.this);
                tixanDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
            tixanDialog.setOutCancle(false);
            tixanDialog.setShow();
        }
    }


    private Guide guide;
    public void showGuideView(View view) {
        GuideBuilder builder = new GuideBuilder();
        builder.setTargetView(view)
                .setAlpha(180)
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
                taskType = 3;
                if ("1".equals(AppSettingUtils.getVideoType())) {//先头条
                    showVideo();
                } else {
                    showTx();
                }
            }
        });
        builder.addComponent(new SimpleComponentTwo(-30));
        guide = builder.createGuide();
        guide.show(GrabRedEvenlopesActivity.this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(this, requestCode);
    }

    private String calanderURL="content://com.android.calendar/calendars";
    private String calanderEventURL="content://com.android.calendar/events";
    private String calanderRemiderURL="content://com.android.calendar/reminders";
    private void  tixingCalendar(int type){
        //获取要出入的gmail账户的id
        String calId = "";
        Cursor userCursor = getContentResolver().query(Uri.parse(calanderURL), null,
                null, null, null);
        if (userCursor!=null){
            try {
                if(userCursor.getCount() > 0){
                    userCursor.moveToFirst();
                    calId = userCursor.getString(userCursor.getColumnIndex("_id"));
                }
            }catch (Exception e){

            }
        }

        for (int i = 0; i < 30; i++) {
            if (!TextUtils.isEmpty(calId)){
                try {
                    ContentValues event = new ContentValues();
                    event.put("title", "红包来了，请提现");
                    event.put("description", "红包无限抢app邀请你来提现，微信秒到账！官网(点击访问)：http://m.hncj.com/sjrj/34282.html");
                    //插入hoohbood@gmail.com这个账户
                    event.put("calendar_id",calId);

                    Calendar mCalendar = Calendar.getInstance();
                    mCalendar.setTime(new Date());
                    mCalendar.add(Calendar.DATE, i+1);
                    mCalendar.set(Calendar.HOUR_OF_DAY,18);
                    mCalendar.set(Calendar.MINUTE,30);
                    long start = mCalendar.getTime().getTime();
                    mCalendar.set(Calendar.HOUR_OF_DAY,19);
                    mCalendar.set(Calendar.MINUTE,30);
                    long end = mCalendar.getTime().getTime();

                    event.put("dtstart", start);
                    event.put("dtend", end);
                    event.put("hasAlarm",1);
                    event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
                    Uri newEvent = getContentResolver().insert(Uri.parse(calanderEventURL), event);
                    long id=-1;
                    if (newEvent!=null){
                        id = Long.parseLong( newEvent.getLastPathSegment() );
                    }
                    if (id!=-1){
                        ContentValues values = new ContentValues();
                        values.put( "event_id", id );
                        //提前10分钟有提醒
                        values.put( "minutes", 10 );
                        getContentResolver().insert(Uri.parse(calanderRemiderURL), values);
                        if (i==29){
                            String strTimessssss = TimesUtils.getStrTimessssss(end);
                            CacheDataUtils.getInstance().setSignCalendar(String.valueOf(end));
                        }
                    }
                }catch (Exception e){

                }
            }
        }
    }



    private List<TaskUnlock> other_info;
    private TaskUnlockAdapter taskUnlockAdapte;
    private BottomListDialog unlockingDialog;
    private RecyclerView recyclerView;
    private int unLockTaskId;
    public void unlockingDialog(){
        unlockingDialog = new BottomListDialog(this);
        View builder = unlockingDialog.builder(R.layout.member_unlocking_dialog);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        recyclerView=builder.findViewById(R.id.recyclerView);
        TextView tv_titles=builder.findViewById(R.id.tv_titles);
        tv_titles.setText("签到解锁");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(GrabRedEvenlopesActivity.this,LinearLayoutManager.VERTICAL,false);
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
                        MobclickAgent.onEvent(GrabRedEvenlopesActivity.this, "jiesuotask1");//参数二为当前统计的事件ID
                    }else{
                        MobclickAgent.onEvent(GrabRedEvenlopesActivity.this, "jiesuotask2");//参数二为当前统计的事件ID
                    }
                    taskType = 4;
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
        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
            unlockingDialog.setOutCancle(true);
            unlockingDialog.setShow();
        }
    }
    private SnatchDialog snatchDialogsss;
    private void showjiesuoTaskError() {
        snatchDialogsss = new SnatchDialog(this);
        View builder = snatchDialogsss.builder(R.layout.jiesuotaskerror_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snatchDialogsss.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
            snatchDialogsss.setShow();
        }
    }
}