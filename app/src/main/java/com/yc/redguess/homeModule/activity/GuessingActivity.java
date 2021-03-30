package com.yc.redguess.homeModule.activity;


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
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.constants.Constant;
import com.yc.redguess.dialog.GuessDialog;
import com.yc.redguess.homeModule.contact.GuessingContact;
import com.yc.redguess.homeModule.module.bean.AnswerBeans;
import com.yc.redguess.homeModule.module.bean.GuessBeans;
import com.yc.redguess.homeModule.module.bean.PostGuessNoBeans;
import com.yc.redguess.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redguess.homeModule.present.GuessingPresenter;
import com.yc.redguess.homeModule.widget.BarChartView;
import com.yc.redguess.homeModule.widget.NumberPickerView;
import com.yc.redguess.homeModule.widget.ToastShowViews;
import com.yc.redguess.utils.AppSettingUtils;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.CommonUtils;
import com.yc.redguess.utils.DisplayUtil;
import com.yc.redguess.utils.SoundPoolUtils;
import com.yc.redguess.utils.ToastUtilsViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        loadTx();
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

    @Override
    protected void onDestroy() {
        if (mRewardVideoAD != null) {
            mRewardVideoAD.destroy();
        }
        super.onDestroy();
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
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideo();
                    }else {
                        showTx();
                    }
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
    private int upTreasure=0;
    @Override
    public void updtreasureSuccess(UpQuanNumsBeans data) {
        if (data!=null){
            upTreasure=data.getRand_num();
        }
    }

    private String isLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private void showVideo() {
        isLoadAdSuccess="1";
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
                    if (upTreasure>0){
                        if (!CommonUtils.isDestory(GuessingActivity.this)) {
                            ToastUtilsViews.showCenterToast("1", "");
                        }
                    }
                    if (!CommonUtils.isDestory(GuessingActivity.this)){
                        ToastUtilsViews.showCenterToast("1","");
                    }
                    mPresenter.submitGuessNo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", guessBeans.getInfo_id() + "", guessNums);
                }
                if (!CommonUtils.isDestory(GuessingActivity.this)){
                    ToastShowViews.cancleToast();
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                if ("1".equals(isLoadAdSuccess)){
                    isLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showTx();
                    }else {
                        if (!CommonUtils.isDestory(GuessingActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }
                }
            }

            @Override
            public void onComplete() {
                if (!CommonUtils.isDestory(GuessingActivity.this)){
                    ToastShowViews.cancleToast();
                }
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onPresent() {
                isLoadAdSuccess="3";
                if (!CommonUtils.isDestory(GuessingActivity.this)){
                    ToastShowViews.showMyToast();
                }
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

    public void showTx(){
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                if (!CommonUtils.isDestory(GuessingActivity.this)) {
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
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        if (!CommonUtils.isDestory(GuessingActivity.this)) {
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
                            .showAD(GuessingActivity.this);
                    // 展示广告
                    break;
            }
        }

    }
    public void loadTxTwo(){
        if (mRewardVideoAD!=null){
            mIsLoaded=false;
            mRewardVideoAD.loadAD();
        }else {
            loadTx();
        }
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
                Log.i("ccc", "onVideoCached: ");
            }

            @Override
            public void onShow() {
                isTxLoadAdSuccess="3";
                AppSettingUtils.showTxShow("tx_ad_shuzijingcai");
                if (!CommonUtils.isDestory(GuessingActivity.this)){
                    ToastShowViews.showMyToast();
                }
            }

            @Override
            public void onExpose() {
                Log.i("ccc", "onExpose: ");
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
                AppSettingUtils.showTxClick("tx_ad_shuzijingcai");
            }

            @Override
            public void onVideoComplete() {
                if (!CommonUtils.isDestory(GuessingActivity.this)){
                    ToastShowViews.cancleToast();
                }
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onClose() {
                if (!TextUtils.isEmpty(guessNums)) {
                    if (upTreasure>0){
                        if (!CommonUtils.isDestory(GuessingActivity.this)) {
                            ToastUtilsViews.showCenterToast("1", "");
                        }
                    }
                    if (!CommonUtils.isDestory(GuessingActivity.this)){
                        ToastUtilsViews.showCenterToast("1","");
                    }
                    mPresenter.submitGuessNo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", guessBeans.getInfo_id() + "", guessNums);
                }
                if (!CommonUtils.isDestory(GuessingActivity.this)){
                    ToastShowViews.cancleToast();
                }
            }

            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                if ("1".equals(isTxLoadAdSuccess)){
                    isTxLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideo();
                    }else {
                        if (!CommonUtils.isDestory(GuessingActivity.this)) {
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