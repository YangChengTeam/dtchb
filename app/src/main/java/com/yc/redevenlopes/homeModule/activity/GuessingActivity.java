package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.GuessDialog;
import com.yc.redevenlopes.homeModule.contact.GuessingContact;
import com.yc.redevenlopes.homeModule.module.bean.GuessBeans;
import com.yc.redevenlopes.homeModule.module.bean.PostGuessNoBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redevenlopes.homeModule.present.GuessingPresenter;
import com.yc.redevenlopes.homeModule.widget.BarChartView;
import com.yc.redevenlopes.homeModule.widget.NumberPickerView;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.DisplayUtil;
import com.yc.redevenlopes.utils.SoundPoolUtils;
import com.yc.redevenlopes.utils.ToastUtilsViews;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 竞猜
 */
public class GuessingActivity extends BaseActivity<GuessingPresenter> implements GuessingContact.View {

    @BindView(R.id.tv_sumber)
    LinearLayout tvSumber;
    @BindView(R.id.barchartView)
    BarChartView barchartView;
    @BindView(R.id.tv_guessPeriodss)
    TextView tvGuessPeriods;
    @BindView(R.id.tv_openPrizeTimes)
    TextView tvOpenPrizeTimes;
    @BindView(R.id.guessMoney)
    TextView guessMoney;
    @BindView(R.id.guessPeopleNums)
    TextView guessPeopleNums;
    @BindView(R.id.tv_prizeDetails)
    TextView tvPrizeDetails;
    @BindView(R.id.myGuessNums)
    TextView myGuessNums;
    @BindView(R.id.picker1)
    NumberPickerView picker1;
    @BindView(R.id.picker2)
    NumberPickerView picker2;
    @BindView(R.id.picker3)
    NumberPickerView picker3;
    @BindView(R.id.picker4)
    NumberPickerView picker4;
    @BindView(R.id.iv_needVideo)
    ImageView ivNeedVideo;
    private GuessBeans guessBeans;
    private int guess_num;
    private int guessPick1 = 0;
    private int guessPick2 = 0;
    private int guessPick3 = 0;
    private int guessPick4 = 0;
    private View nodData;
    private FrameLayout fl_ad_containe;
    private String guessNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_guessing;
    }

    @Override
    public void initEventAndData() {
        fl_ad_containe = findViewById(R.id.fl_ad_containe);
        nodData = findViewById(R.id.view_nodata);
        initData();
        initPick();
        loadVideo();
        showExpress();
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
        int dpw = DisplayUtil.px2dip(GuessingActivity.this, w);
        int dph = DisplayUtil.px2dip(GuessingActivity.this, h);
        adPlatformSDK.loadExpressAd(this, "ad_expredd_guess", dpw, dph, new AdCallback() {
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
//                if (!isShow) {
//                    adPlatformSDK.showExpressAd();
//                }
            }
        }, fl_ad_containe);
    }

    private void initPick() {
        String[] strs = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        picker1.refreshByNewDisplayedValues(strs);
        picker2.refreshByNewDisplayedValues(strs);
        picker3.refreshByNewDisplayedValues(strs);
        picker4.refreshByNewDisplayedValues(strs);
        picker1.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                guessPick1 = newVal;
            }
        });
        picker2.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                guessPick2 = newVal;
            }
        });
        picker3.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                guessPick3 = newVal;
            }
        });
        picker4.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                guessPick4 = newVal;
            }
        });
    }

    private void initData() {
        mPresenter.getGuessData(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void GuessingJump(Context context) {
        Intent intent = new Intent(context, GuessingActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_back, R.id.tv_history, R.id.tv_prizeDetails, R.id.tv_sumber})
    public void onViewClicked(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_history:
                if (guessBeans != null) {
                    GuessingResultActivity.GuessingResultJump(this, guessBeans.getInfo_id() + "");
                }
                break;
            case R.id.tv_prizeDetails:
                if (guessBeans != null) {
                    String contents = "";
                    if (!TextUtils.isEmpty(guessBeans.getContent())) {
                        contents = guessBeans.getContent();
                    }
                    GuessingDetailsActivity.guessingDetailsJump(this, contents);
                }
                break;
            case R.id.tv_sumber:
                if (guess_num > 0) {
                    String guessNums = guessPick1 + "" + guessPick2 + "" + guessPick3 + "" + guessPick4;
                    String myGuess = myGuessNums.getText().toString();
                    if (!TextUtils.isEmpty(myGuess)){
                        if (myGuess.contains(guessNums)){
                            ToastUtil.showToast("不能重复提交");
                            return;
                        }
                    }
                    showGuessDialog(guessNums);
                } else {
                    ToastUtil.showToast("您今日的竞猜次数已用完");
                }
                break;
        }
    }


    private GuessDialog guessDialog;
    private void showGuessDialog(String guessNumss) {
        guessDialog = new GuessDialog(this);
        View builder = guessDialog.builder(R.layout.guess_item_two);
        TextView tv_title = builder.findViewById(R.id.tv_title);
        TextView tv_des = builder.findViewById(R.id.tv_des);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_cancle = builder.findViewById(R.id.tv_cancle);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_des.setText(guessNumss);
        this.guessNums=guessNumss;
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                guessDialog.setDismiss();
                if (guess_num == 7 || guess_num == 4) {
                    showVideo();
                } else {
                    mPresenter.submitGuessNo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", guessBeans.getInfo_id() + "", guessNumss);
                }
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                guessDialog.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                guessDialog.setDismiss();
            }
        });
        guessDialog.setShow();
    }


    @Override
    public void getGuessDataSuccess(GuessBeans data) {
        if (data != null) {
            nodData.setVisibility(View.GONE);
            guessBeans = data;
            tvGuessPeriods.setText(data.getGuessno() + "期");
            guessPeopleNums.setText(data.getTotal() + "");
            guessMoney.setText(data.getMoney());
            guess_num = data.getUser_other().getGuess_num();
            if (guess_num==7||guess_num==4){
                ivNeedVideo.setVisibility(View.VISIBLE);
            }else {
                ivNeedVideo.setVisibility(View.GONE);
            }
            String add_num = data.getUser_num();
            String[] split = add_num.split(",");
            for (int i = 0; i < split.length; i++) {
                if (i == split.length - 1) {
                    String str = split[i];
                    if (!TextUtils.isEmpty(str)) {
                        String substring = str.substring(0, 1);
                        guessPick1=Integer.parseInt(substring);
                        picker1.setValue(Integer.parseInt(substring));
                    }
                    if (!TextUtils.isEmpty(str) && str.length() > 1) {
                        String substring = str.substring(1, 2);
                        guessPick2=Integer.parseInt(substring);
                        picker2.setValue(Integer.parseInt(substring));
                    }
                    if (!TextUtils.isEmpty(str) && str.length() > 2) {
                        String substring = str.substring(2, 3);
                        guessPick3=Integer.parseInt(substring);
                        picker3.setValue(Integer.parseInt(substring));
                    }
                    if (!TextUtils.isEmpty(str) && str.length() > 3) {
                        String substring = str.substring(3, 4);
                        guessPick4=Integer.parseInt(substring);
                        picker4.setValue(Integer.parseInt(substring));
                    }
                }
            }
            if (!TextUtils.isEmpty(add_num)) {
                add_num.replaceAll(",", "  ");
            }
            myGuessNums.setText(add_num.replaceAll(",", "   "));
            List<String> range = data.getRange();
            List<Integer> datass = new ArrayList<Integer>();
            for (int i = 0; i < range.size(); i++) {
                datass.add(Integer.parseInt(range.get(i)));
            }
            List<String> monthList = new ArrayList<String>();
            monthList.add("0");
            monthList.add("2000");
            monthList.add("4000");
            monthList.add("6000");
            monthList.add("8000");
            monthList.add("9999");
            barchartView.setMonthList(monthList);
            barchartView.setData(datass);
            barchartView.setOnDraw(true);
            barchartView.start();
        } else {
            nodData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void submitGuessNoSuccess(PostGuessNoBeans data) {
        guess_num = data.getGuess_num();
        if (guess_num==7||guess_num==4){
            ivNeedVideo.setVisibility(View.VISIBLE);
        }else {
            ivNeedVideo.setVisibility(View.GONE);
        }
        String num = data.getNum();
        String myGuess = myGuessNums.getText().toString();
        myGuessNums.setText(myGuess + "  " + num);
    }

    @Override
    public void getGuessDataError() {
        nodData.setVisibility(View.VISIBLE);
    }

    @Override
    public void updtreasureSuccess(UpQuanNumsBeans data) {

    }


    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId()+"");
        adPlatformSDK.showRewardVideoAd();
        loadVideo();
    }

    private void loadVideo(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_shuzijingcai",new AdCallback() {
            @Override
            public void onDismissed() {
                if (!TextUtils.isEmpty(guessNums)) {
                    if (!CommonUtils.isDestory(GuessingActivity.this)){
                        ToastUtilsViews.showCenterToast("1","");
                    }
                    mPresenter.submitGuessNo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", guessBeans.getInfo_id() + "", guessNums);
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

}