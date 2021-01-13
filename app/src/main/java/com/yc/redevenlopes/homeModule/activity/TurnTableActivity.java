package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.homeModule.contact.TurnTableContact;
import com.yc.redevenlopes.homeModule.fragment.ExitTintFragment;
import com.yc.redevenlopes.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnTablePrizeInfoBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redevenlopes.homeModule.present.TurnTablePresenter;
import com.yc.redevenlopes.homeModule.widget.LuckPanLayout;
import com.yc.redevenlopes.homeModule.widget.RotatePan;
import com.yc.redevenlopes.homeModule.widget.ToastShowViews;
import com.yc.redevenlopes.service.event.Event;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.ClickListenNameTwo;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.DisplayUtil;
import com.yc.redevenlopes.utils.SoundPoolUtils;
import com.yc.redevenlopes.utils.ToastUtilsViews;
import org.greenrobot.eventbus.EventBus;
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
        luckpanLayout.setAnimationEndListener(this);
        rotatePan.setStr(strs);
        mPresenter.getPrizeInfoData(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
        loadInsertView(null);
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
                            showVideo();
                        }else {
                            mPresenter.getGoPrize(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
                        }
                    } else {
                        ToastUtil.showToast("今日抽奖次数已用完");
                    }
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
                    RobRedEvenlopesActivity.robRedEvenlopesJump(TurnTableActivity.this, "3", "转盘红包", "", turnGoPrizeBeans.getMoney(),"","");
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

    @Override
    public void updtreasureSuccess(UpQuanNumsBeans data) {

    }


    private void loadVideo(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_dazhuangpan",new AdCallback() {
            @Override
            public void onDismissed() {
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    ToastUtilsViews.showCenterToast("1","");
                }
                mPresenter.getGoPrize(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    ToastShowViews.getInstance().cancleToast();
                }
            }

            @Override
            public void onNoAd(AdError adError) {

            }

            @Override
            public void onComplete() {
                if (redDialogs != null) {
                    redDialogs.setDismiss();
                }
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    ToastShowViews.getInstance().cancleToast();
                }
            }

            @Override
            public void onPresent() {
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    ToastShowViews.getInstance().showMyToast();
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
}