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

import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.LevelDialog;
import com.yc.redevenlopes.dialog.SnatchDialog;
import com.yc.redevenlopes.homeModule.contact.GrabRedEvenlopesContact;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoBeans;
import com.yc.redevenlopes.homeModule.module.bean.LookVideoMoneyBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.present.GrabRedEvenlopesPresenter;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

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
    private int step;
    private int position = 2;
    private float moveSpan;
    private int lookVideoNums;
    private int totalVideoNums;
    private int type;//看视频的类型  1 看视频不翻倍 2 看视频翻倍
    private String info_id;

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
    public void initEventAndData() {
        step = 1;
        int left = relaRedOne.getLeft();
        int leftTwo = relaRedTwo.getLeft();
        int leftThree = relaRedThree.getLeft();
        Log.d("ccc", "---------startRed: " + left + "---" + leftTwo + "---" + leftThree);
        moveSpan = leftTwo - left;
        initRedView();
        loadVideo();
        mPresenter.getlookVideo(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    public void showRedDialog() {
        LevelDialog redDialogs = new LevelDialog(this);
        View builder = redDialogs.builder(R.layout.level_reward_item);

        redDialogs.setOutCancle(false);
        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
            redDialogs.setShow();
        }
    }

    public void initRedView() {
        if (step == 1) {
            ivRedOne.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_air));
            ivRedTwo.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_monert));
            ivRedThree.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_air));
            tvLookRh.setBackground(getResources().getDrawable(R.drawable.yellow_gradient2));
            tvLookRh.setTextColor(getResources().getColor(R.color.A1_C40000));
            tvLookRh.setText("开始找红包");
        } else if (step == 2) {
            ivRedOne.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_air));
            ivRedTwo.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_air));
            ivRedThree.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_air));
            tvLookRh.setBackground(getResources().getDrawable(R.drawable.gray_gradient2));
            tvLookRh.setTextColor(getResources().getColor(R.color.A1_666666));
            tvLookRh.setText("红包准备中");
        } else if (step == 3) {
            ivRedOne.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_air));
            ivRedTwo.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_air));
            ivRedThree.setImageDrawable(getResources().getDrawable(R.drawable.bg_red_air));
            tvLookRh.setBackground(getResources().getDrawable(R.drawable.gray_gradient2));
            tvLookRh.setTextColor(getResources().getColor(R.color.A1_666666));
            tvLookRh.setText("哪个红包有金币");
        }
    }

    //type  1 2幸运抽红包领金币  2幸运抽红包领金币翻倍  3幸运抽红包领金币不翻倍 4没有抽中
    public void showRedDialogTwo(int type) {
        SnatchDialog redDialogs = new SnatchDialog(this);
        View builder = redDialogs.builder(R.layout.level_reward_money);
        ImageView iv_top = builder.findViewById(R.id.iv_top);
        TextView tv2 = builder.findViewById(R.id.tv2);
        TextView tv_noPrize = builder.findViewById(R.id.tv_noPrize);
        LinearLayout line_money = builder.findViewById(R.id.line_money);
        TextView tv_money = builder.findViewById(R.id.tv_money);
        RelativeLayout rela_one = builder.findViewById(R.id.rela_one);
        RelativeLayout rela_one_one = builder.findViewById(R.id.rela_one_one);
        TextView tv_iwantCheat = builder.findViewById(R.id.tv_iwantCheat);
        TextView tv_levelNums = builder.findViewById(R.id.tv_levelNums);
        TextView tv_sureOne = builder.findViewById(R.id.tv_sureOne);
        TextView tv_sureTwo = builder.findViewById(R.id.tv_sureTwo);
        TextView tv_sureThree = builder.findViewById(R.id.tv_sureThree);
        RelativeLayout rela_two = builder.findViewById(R.id.rela_two);

        if (type == 1) {
            iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain));
            tv2.setVisibility(View.VISIBLE);
            line_money.setVisibility(View.VISIBLE);
            tv_noPrize.setVisibility(View.GONE);
            rela_one.setVisibility(View.GONE);
            tv_sureTwo.setVisibility(View.GONE);
            tv_sureOne.setVisibility(View.VISIBLE);
            tv_sureThree.setVisibility(View.GONE);
        } else if (type == 2) {
            tv_iwantCheat.setVisibility(View.GONE);
            iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain));
            tv2.setVisibility(View.VISIBLE);
            line_money.setVisibility(View.VISIBLE);
            tv_noPrize.setVisibility(View.GONE);
            rela_one_one.setVisibility(View.VISIBLE);
            rela_one.setVisibility(View.VISIBLE);
            tv_sureTwo.setVisibility(View.GONE);
            tv_sureOne.setVisibility(View.GONE);
            tv_sureThree.setVisibility(View.VISIBLE);
        } else if (type == 3) {
            tv_iwantCheat.setVisibility(View.GONE);
            iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain));
            tv2.setVisibility(View.VISIBLE);
            line_money.setVisibility(View.VISIBLE);
            tv_noPrize.setVisibility(View.GONE);
            rela_one_one.setVisibility(View.VISIBLE);
            rela_one.setVisibility(View.GONE);
            tv_sureTwo.setVisibility(View.GONE);
            tv_sureOne.setVisibility(View.GONE);
            tv_sureThree.setVisibility(View.VISIBLE);
        } else if (type == 4) {
            iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain_no));
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


        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
            redDialogs.setShow();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_lookRh, R.id.iv_lookVideo, R.id.iv_turn, R.id.iv_getRed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_lookRh:
                step = 2;
                initRedView();
                startRed();
                break;
            case R.id.iv_lookVideo:
                if (lookVideoNums>0){
                    type=1;
                    showVideo();
                }
                break;
            case R.id.iv_turn:
                TurnTableActivity.TurnTableJump(this);
                break;
            case R.id.iv_getRed:
                SmokeHbActivity.smokehbJump(this);
                break;
        }
    }

    private void startRed() {
        List<Float> lists = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int random = getRandom();
            // if ()
        }

        //   ObjectAnimator transXAnim = ObjectAnimator.ofFloat(myView, "translationX", 100, 400);
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
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_kanshiping", new AdCallback() {
            @Override
            public void onDismissed() {
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                if (type==1){
                      mPresenter.getlookVideoRedMoney(userInfo.getImei(),userInfo.getGroup_id(),"0","","");
                  }else if (type==2){
                      mPresenter.getlookVideoRedMoney(userInfo.getImei(),userInfo.getGroup_id(),"1",info_id,"");
                  }
            }

            @Override
            public void onNoAd(AdError adError) {

            }

            @Override
            public void onComplete() {
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
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
        adPlatformSDK.showRewardVideoAd();
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
    }

    @Override
    public void updtreasureSuccess(UpQuanNumsBeans data) {

    }

    @Override
    public void getlookVideoSuccess(LookVideoBeans data) {
        lookVideoNums = data.getVideo_num();
        totalVideoNums = data.getTotal();
        int s=data.getTotal()-data.getVideo_num();
        tvLookVideoNums.setText("看视频领红包（"+s+"/"+totalVideoNums+")");
    }

    @Override
    public void getlookVideoRedMoneySuccess(LookVideoMoneyBeans data) {
       // info_id=String.valueOf(data.getInfo_id())
    }
}