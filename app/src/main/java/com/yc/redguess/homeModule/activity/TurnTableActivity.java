package com.yc.redguess.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.dialog.RedDialog;
import com.yc.redguess.homeModule.contact.TurnTableContact;
import com.yc.redguess.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.redguess.homeModule.module.bean.TurnTablePrizeInfoBeans;
import com.yc.redguess.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redguess.homeModule.present.TurnTablePresenter;
import com.yc.redguess.homeModule.widget.LuckPanLayout;
import com.yc.redguess.homeModule.widget.RotatePan;
import com.yc.redguess.homeModule.widget.ToastShowViews;
import com.yc.redguess.service.event.Event;
import com.yc.redguess.utils.AppSettingUtils;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.ClickListenNameTwo;
import com.yc.redguess.utils.CommonUtils;
import com.yc.redguess.utils.DisplayUtil;
import com.yc.redguess.utils.SoundPoolUtils;
import com.yc.redguess.utils.TimesUtils;
import com.yc.redguess.utils.ToastUtilsViews;
import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 转盘
 */
public class TurnTableActivity extends BaseActivity<TurnTablePresenter> implements TurnTableContact.View, LuckPanLayout.AnimationEndListener {

    @BindView(R.id.rotatePan)
    RotatePan rotatePan;
    @BindView(R.id.go)
    ImageView go;
    @BindView(R.id.luckpan_layout)
    LuckPanLayout luckpanLayout;
    @BindView(R.id.tv_prizeNums)
    TextView tvPrizeNums;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.iv_needVideo)
    ImageView ivNeedVideo;
    @BindView(R.id.line_go)
    LinearLayout lineGo;
    private String[] strs = {"0.01元", "0.02元", "0.05元", "3元", "50元", "iPhone12P512G"};
    private List<TurnTablePrizeInfoBeans.PrizeInfoBean> prize_info;
    private TurnGoPrizeBeans turnGoPrizeBeans;
    private int prizeNums;
    private FrameLayout fl_ad_containe;
    public static WeakReference<TurnTableActivity> instance;
    private int videoType;//1 开始转盘的时候  2，点击开的时候
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
        loadVideo();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_turn_table;
    }

    @Override
    public void initEventAndData() {
        instance = new WeakReference<>(this);
        fl_ad_containe = findViewById(R.id.fl_ad_containe);
        luckpanLayout.setAnimationEndListener(this);
        rotatePan.setStr(strs);
        mPresenter.getPrizeInfoData(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
        loadInsertView(null);
        showExpress();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.line_go, R.id.iv_back, R.id.tv_address})
    public void onViewClicked(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        switch (view.getId()) {
            case R.id.line_go:
                if (ClickListenNameTwo.isFastClick()) {
                    if (prizeNums > 0) {
                        if (prizeNums == 1 || prizeNums == 3 || prizeNums == 7) {
                            videoType=1;
                            showVideo();
                        }else {
                            mPresenter.getGoPrize(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
                        }
                    } else {
                        ToastUtil.showToast("今日抽奖次数已用完");
                    }
                }else {
                    ToastUtil.showToast("请勿重复快速点击");
                }
                break;
            case R.id.iv_back:
                 finish();
                break;
            case R.id.tv_address:
                ToastUtil.showToast("尚未获得奖励");
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVideo();
    }

    @Override
    public void endAnimation(int position) {
        tvGo.setEnabled(true);
        lineGo.setEnabled(true);
        setViewStatus();
        showRedDialog();
    }

    private void setViewStatus() {
        tvPrizeNums.setText(prizeNums + "");
        if (prizeNums > 0) {
            tvPrizeNums.setTextColor(getResources().getColor(R.color.A1_AB5B0F));
            tvGo.setTextColor(getResources().getColor(R.color.A1_AB5B0F));
            lineGo.setBackground(getResources().getDrawable(R.drawable.line_bg_yellow5));
        } else {
            tvPrizeNums.setTextColor(getResources().getColor(R.color.A1_999999));
            lineGo.setBackground(getResources().getDrawable(R.drawable.tv_bg_gray3));
            tvGo.setTextColor(getResources().getColor(R.color.white));
        }
    }

    private RedDialog redDialogs;

    public void showRedDialog() {
        redDialogs = new RedDialog(this);
        View builder = redDialogs.builder(R.layout.red_dialog_item);
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
        tv_type.setText("转盘红包");
        if (turnGoPrizeBeans != null) {
            tv_money.setText(turnGoPrizeBeans.getMoney());
        }
        redDialogs.setOutCancle(false);
        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (AppSettingUtils.isIntegralWall()){//积分墙渠道
                        String turaFirstTimes = CacheDataUtils.getInstance().getTuraFirst();
                      if (TextUtils.isEmpty(turaFirstTimes)) {
                          videoType=2;
                          showVideo();
                      } else {
                         long curr = System.currentTimeMillis();
                         String strTimessssss = TimesUtils.getStrTimessssss(curr);
                        if (!TextUtils.isEmpty(strTimessssss)) {
                          if (!strTimessssss.equals(turaFirstTimes)) {
                              videoType=2;
                              showVideo();
                           }else {
                              RobRedEvenlopesActivity.robRedEvenlopesJump(TurnTableActivity.this, "3", "转盘红包", "", turnGoPrizeBeans.getMoney(),"","");
                          }
                         }else {
                            RobRedEvenlopesActivity.robRedEvenlopesJump(TurnTableActivity.this, "3", "转盘红包", "", turnGoPrizeBeans.getMoney(),"","");
                        }
                      }
                    }else {
                        RobRedEvenlopesActivity.robRedEvenlopesJump(TurnTableActivity.this, "3", "转盘红包", "", turnGoPrizeBeans.getMoney(),"","");
                    }
                    redDialogs.setDismiss();
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                redDialogs.setDismiss();
                showInsertVideo();
            }
        });
        iv_close.setVisibility(View.GONE);
        if (!CommonUtils.isDestory(TurnTableActivity.this)) {
            redDialogs.setShow();
        }
    }


    public static void TurnTableJump(Context context) {
        Intent intent = new Intent(context, TurnTableActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getPrizeInfoDataSuccess(TurnTablePrizeInfoBeans data) {
        prize_info = data.getPrize_info();
        prizeNums = data.getUser_other().getPrize_num();
        setViewStatus();
        if (prizeNums == 1 || prizeNums == 3|| prizeNums ==7) {
            ivNeedVideo.setVisibility(View.VISIBLE);
        } else {
            ivNeedVideo.setVisibility(View.GONE);
        }
        for (int i = 0; i < prize_info.size(); i++) {
            strs[i] = prize_info.get(i).getName();
        }
        rotatePan.setStr(strs);
    }

    @Override
    public void getGoPrizeSuccess(TurnGoPrizeBeans data) {
        this.turnGoPrizeBeans = data;
        prizeNums=data.getPrize_num();
        if (data.getNew_level() > 0) {
            EventBus.getDefault().post(new Event.CashEvent());
        }
        if (prizeNums == 1 || prizeNums == 3|| prizeNums == 7) {
            ivNeedVideo.setVisibility(View.VISIBLE);
        } else {
            ivNeedVideo.setVisibility(View.GONE);
        }
        int id = data.getId();
        if (prize_info != null) {
            for (int i = 0; i < prize_info.size(); i++) {
                if (id == prize_info.get(i).getId()) {
                    luckpanLayout.rotate(i, 100);
                    lineGo.setEnabled(false);
                    tvGo.setEnabled(false);
                }
            }
        }
    }
    private int upTreasure=0;
    @Override
    public void updtreasureSuccess(UpQuanNumsBeans data) {
        if (data!=null){
            upTreasure=data.getRand_num();
        }
    }

    private int videoCounts;
    private void loadVideo(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_dazhuangpan",new AdCallback() {
            @Override
            public void onDismissed() {
                if (upTreasure>0){
                    if (!CommonUtils.isDestory(TurnTableActivity.this)) {
                        ToastUtilsViews.showCenterToast("1", "");
                    }
                }
                if (videoType==1){//开始转盘
                    mPresenter.getGoPrize(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
                }else {
                    long l = System.currentTimeMillis();
                    String strTimessssss = TimesUtils.getStrTimessssss(l);
                    if (!TextUtils.isEmpty(strTimessssss)){
                        CacheDataUtils.getInstance().setTuraFirst(strTimessssss);
                    }
                    RobRedEvenlopesActivity.robRedEvenlopesJump(TurnTableActivity.this, "3", "转盘红包", "", turnGoPrizeBeans.getMoney(),"","");
                }
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    ToastShowViews.getInstance().cancleToastTwo();
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                videoCounts++;
                if (videoCounts>3){
                    videoCounts=1;
                    if (!CommonUtils.isDestory(TurnTableActivity.this)){
                        ToastUtil.showToast("加载广告失败，可能是网络不好的原因，请检查下网络是否正常哦！");
                    }
                }
            }

            @Override
            public void onComplete() {
                if (redDialogs != null) {
                    redDialogs.setDismiss();
                }
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    ToastShowViews.getInstance().cancleToastTwo();
                }
            }

            @Override
            public void onPresent() {
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    videoCounts=1;
                    ToastShowViews.getInstance().showMyToastTwo("");
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

    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        loadVideo();
        adPlatformSDK.showRewardVideoAd();
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId()+"");
    }


    private void showInsertVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setAdPosition("chapingturn");
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        if(adPlatformSDK.showInsertAd()){
            loadInsertView(null);
        } else {
            loadInsertView( new Runnable() {
                @Override
                public void run() {
                    adPlatformSDK.showInsertAd();
                }
            });
        }
    }
    private void loadInsertView(Runnable runnable){
        int screenWidth = CommonUtils.getScreenWidth(this);
        int screenHeight = CommonUtils.getScreenHeight(this);
        int w = (int) (screenWidth)*9/10;
        int h = screenHeight*9/10;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(TurnTableActivity.this, w);
        int dph = DisplayUtil.px2dip(TurnTableActivity.this, h);
        adPlatformSDK.loadInsertAd(this, "chapingturn", dpw, dph, new AdCallback() {
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
                if(runnable != null){
                    runnable.run();
                }
            }
        });
    }


    private void showExpress() {
        loadExpressVideo();
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        isShow= adPlatformSDK.showExpressAd();
    }
    private boolean isShow;
    private void loadExpressVideo() {
        int screenWidth = CommonUtils.getScreenWidth(this);
        int w = (int) (screenWidth);
        int h = w * 2 / 3;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(TurnTableActivity.this, w);
        int dph = DisplayUtil.px2dip(TurnTableActivity.this, h);
        adPlatformSDK.loadExpressAd(this, "ad_expredd_turn", dpw, dph, new AdCallback() {
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

            }
        }, fl_ad_containe);
    }

}