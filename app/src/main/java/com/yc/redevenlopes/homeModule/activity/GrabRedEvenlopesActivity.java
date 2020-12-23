package com.yc.redevenlopes.homeModule.activity;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        relaRedOne.post(new Runnable() {
            @Override
            public void run() {
                int left = relaRedOne.getLeft();
                int leftTwo = relaRedTwo.getLeft();
                int leftThree = relaRedThree.getLeft();
                moveSpan=leftTwo-left;
            }
        });
        initRedView();
        initRedDialogOne();
        initRedDialogTwo();
        loadVideo();
        mPresenter.getlookVideo(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
    }






    private LevelDialog redDialogsone;
    private FrameLayout fl_content_one;
    private RelativeLayout rela_fanbei;
    private ImageView iv_close;
    private TextView tv_money;
    private boolean isshowOne;
    private void initRedDialogOne() {
         redDialogsone = new LevelDialog(this);
         View builder = redDialogsone.builder(R.layout.level_reward_item);
         fl_content_one=builder.findViewById(R.id.fl_content_one);
         rela_fanbei=builder.findViewById(R.id.rela_fanbei);
         tv_money=builder.findViewById(R.id.tv_money);
         iv_close=builder.findViewById(R.id.iv_close);
        redDialogsone.setOutCancle(false);
        loadExone();
    }
    private void loadExone(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadExpressAd(this,"ad_duobao", 300,200,new AdCallback() {
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
                if(!isshowOne){
                    adPlatformSDK.showExpressAd();
                }
            }
        }, fl_content_one);
    }
    public void showRedDialogOne(String money) {
        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
            if (tv_money!=null){
                tv_money.setText(money);
                rela_fanbei.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type=2;
                        showVideo();
                        if (redDialogsone!=null){
                            redDialogsone.setDismiss();
                        }
                    }
                });
                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (redDialogsone!=null){
                            redDialogsone.setDismiss();
                        }
                    }
                });
            }
            loadExone();
            final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
            adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId()+"");
            isshowOne = adPlatformSDK.showExpressAd();
            redDialogsone.setShow();
        }
    }


   private  SnatchDialog redDialogsTwo;
    ImageView iv_top ;
    TextView tv2 ;
    TextView tv_noPrize ;
    LinearLayout line_money ;
    TextView tv_money_two ;
    RelativeLayout rela_one;
    RelativeLayout rela_one_one ;
    TextView tv_iwantCheat ;
    TextView tv_levelNums;
    TextView tv_sureOne ;
    TextView tv_sureTwo ;
    TextView tv_sureThree ;
    RelativeLayout rela_two;
    FrameLayout fl_content_Two;
    private boolean isshowTwo;
    private void initRedDialogTwo() {
         redDialogsTwo = new SnatchDialog(this);
         View builder = redDialogsTwo.builder(R.layout.level_reward_money);
         iv_top = builder.findViewById(R.id.iv_top);
         tv2 = builder.findViewById(R.id.tv2);
         fl_content_Two=builder.findViewById(R.id.fl_content);
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

    //type  1 2幸运抽红包领金币  2幸运抽红包领金币翻倍  3幸运抽红包领金币不翻倍 4没有抽中
    public void showRedDialogTwo(int type,String money) {
        if (redDialogsTwo!=null){
            if (tv_money_two!=null){
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
                tv_money_two.setText(money);
                tv_sureOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (redDialogsTwo!=null){
                            redDialogsTwo.setDismiss();
                        }
                    }
                });
                tv_sureTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (redDialogsTwo!=null){
                            redDialogsTwo.setDismiss();
                        }
                    }
                });
                tv_sureThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (redDialogsTwo!=null){
                            redDialogsTwo.setDismiss();
                        }
                    }
                });
            }
            loadExTwo();
            final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
            adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId()+"");
            isshowTwo = adPlatformSDK.showExpressAd();
            if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
                redDialogsTwo.setShow();
            }
        }
    }

    private void loadExTwo(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadExpressAd(this,"ad_duobao", 300,200,new AdCallback() {
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
                if(!isshowTwo){
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



    @OnClick({R.id.iv_back, R.id.tv_lookRh, R.id.rela_lookVideo, R.id.iv_turn, R.id.iv_getRed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_lookRh:
                step = 2;
                //initRedView();
                ivRedOne.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain_no));
                startRed();
                break;
            case R.id.rela_lookVideo:
                if (lookVideoNums>0){
                    type=1;
                    showVideo();
                }else {
                    ToastUtil.showToast("您今天的看视频次数已用完");
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
            if (random==0){//第一个和第二个交换

            }else if (random==1){//第二个和第三个交换

            }
            Log.d("ccc", "----------startRed: ");
            // if ()
        }

         ObjectAnimator transXAnim = ObjectAnimator.ofFloat(relaRedTwo, "translationX",0, moveSpan);
         transXAnim.setDuration(500);
         transXAnim.setTarget(relaRedTwo);

        ObjectAnimator transXAnimtwo = ObjectAnimator.ofFloat(relaRedTwo, "translationX", moveSpan, 0);
        transXAnimtwo.setDuration(500);
        transXAnimtwo.setTarget(relaRedTwo);
        transXAnimtwo.setStartDelay(800);

        ObjectAnimator transXAnimThree = ObjectAnimator.ofFloat(relaRedTwo, "translationX", 0, -moveSpan);
        transXAnimThree.setDuration(500);
        transXAnimThree.setTarget(relaRedTwo);
        transXAnimThree.setStartDelay(1600);



        ObjectAnimator transXAnimfour = ObjectAnimator.ofFloat(relaRedOne, "translationX",0, moveSpan);
        transXAnim.setDuration(500);
        transXAnim.setTarget(relaRedOne);

        ObjectAnimator transXAnimfive = ObjectAnimator.ofFloat(relaRedOne, "translationX", moveSpan, 0);
        transXAnimtwo.setDuration(500);
        transXAnimtwo.setTarget(relaRedOne);
        transXAnimtwo.setStartDelay(800);

        ObjectAnimator transXAnimTsix = ObjectAnimator.ofFloat(relaRedOne, "translationX", 0, -moveSpan);
        transXAnimThree.setDuration(500);
        transXAnimThree.setTarget(relaRedOne);
        transXAnimThree.setStartDelay(1600);


        ObjectAnimator transXAnimseven = ObjectAnimator.ofFloat(relaRedThree, "translationX",0, moveSpan);
        transXAnim.setDuration(500);
        transXAnim.setTarget(relaRedThree);

        ObjectAnimator transXAnimeight = ObjectAnimator.ofFloat(relaRedThree, "translationX", moveSpan, 0);
        transXAnimtwo.setDuration(500);
        transXAnimtwo.setTarget(relaRedThree);
        transXAnimtwo.setStartDelay(800);

        ObjectAnimator transXAnimnine = ObjectAnimator.ofFloat(relaRedThree, "translationX", 0, -moveSpan);
        transXAnimThree.setDuration(500);
        transXAnimThree.setTarget(relaRedThree);
        transXAnimThree.setStartDelay(1600);



        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(transXAnim,transXAnimtwo,transXAnimThree);
        animatorSet.start();

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
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showRewardVideoAd();
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
        info_id=String.valueOf(data.getInfo_id());
        lookVideoNums=data.getOther_num();
        if (type==1){
            showRedDialogOne(data.getMoney());
        }else if (type==2){
            showRedDialogTwo(1,data.getMoney());
        }
    }
}