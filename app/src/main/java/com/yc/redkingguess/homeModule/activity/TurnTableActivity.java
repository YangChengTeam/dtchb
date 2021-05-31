package com.yc.redkingguess.homeModule.activity;


import android.content.Context;
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
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redkingguess.R;
import com.yc.redkingguess.base.BaseActivity;
import com.yc.redkingguess.constants.Constant;
import com.yc.redkingguess.dialog.RedDialog;
import com.yc.redkingguess.dialog.SnatchDialog;
import com.yc.redkingguess.homeModule.contact.TurnTableContact;
import com.yc.redkingguess.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.redkingguess.homeModule.module.bean.TurnTablePrizeInfoBeans;
import com.yc.redkingguess.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redkingguess.homeModule.present.TurnTablePresenter;
import com.yc.redkingguess.homeModule.widget.LuckPanLayout;
import com.yc.redkingguess.homeModule.widget.RotatePan;
import com.yc.redkingguess.homeModule.widget.ToastShowViews;
import com.yc.redkingguess.service.event.Event;
import com.yc.redkingguess.utils.AppSettingUtils;
import com.yc.redkingguess.utils.CacheDataUtils;
import com.yc.redkingguess.utils.ClickListenNameTwo;
import com.yc.redkingguess.utils.CommonUtils;
import com.yc.redkingguess.utils.DisplayUtil;
import com.yc.redkingguess.utils.SoundPoolUtils;
import com.yc.redkingguess.utils.TimesUtils;
import com.yc.redkingguess.utils.ToastUtilsViews;
import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

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
    private String[] strs = {"0.01元", "0.02元", "0.05元", "3元", "50元", "夺宝券"};
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
        loadTx();
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
                            if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                                showVideo();
                            }else {
                                showTx();
                            }
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
        if (!TextUtils.isEmpty(duobaoquan)){
            showjiesuoTaskError();
        }else {
            showRedDialog();
        }
    }
    private SnatchDialog turnsnatchDialogs;
    private void showjiesuoTaskError() {
        CacheDataUtils.getInstance().setLevel("1");
        turnsnatchDialogs = new SnatchDialog(this);
        View builder = turnsnatchDialogs.builder(R.layout.jiesuotaskerror_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_des=builder.findViewById(R.id.tv_des);
        tv_des.setText("恭喜你中奖夺宝券，夺宝券数量+1");
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnsnatchDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(TurnTableActivity.this)) {
            turnsnatchDialogs.setShow();
        }
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
                          if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                              showVideo();
                          }else {
                              showTx();
                          }
                      } else {
                         long curr = System.currentTimeMillis();
                         String strTimessssss = TimesUtils.getStrTimessssss(curr);
                        if (!TextUtils.isEmpty(strTimessssss)) {
                          if (!strTimessssss.equals(turaFirstTimes)) {
                              videoType=2;
                              if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                                  showVideo();
                              }else {
                                  showTx();
                              }
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
    private String duobaoquan="";
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
        duobaoquan="";
        if (prize_info != null) {
            for (int i = 0; i < prize_info.size(); i++) {
                if (id == prize_info.get(i).getId()) {
                    if (i==prize_info.size()-1){
                        duobaoquan="1";
                    }
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
    private String isLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
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
                    if (turnGoPrizeBeans!=null){
                        RobRedEvenlopesActivity.robRedEvenlopesJump(TurnTableActivity.this, "3", "转盘红包", "", turnGoPrizeBeans.getMoney(),"","");
                    }
                }
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    ToastShowViews.cancleToastTwo();
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                if ("1".equals(isLoadAdSuccess)){
                    isLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        showTx();
                    }else {
                        if (!CommonUtils.isDestory(TurnTableActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
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
                    ToastShowViews.cancleToastTwo();
                }
            }

            @Override
            public void onPresent() {
                isLoadAdSuccess="3";
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    videoCounts=1;
                    ToastShowViews.showMyToastTwo("","9");
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

    @Override
    protected void onDestroy() {
        if (mRewardVideoAD != null) {
            mRewardVideoAD.destroy();
        }
        super.onDestroy();
    }

    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        isLoadAdSuccess="1";
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


    public void showTx(){
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                if (!CommonUtils.isDestory(TurnTableActivity.this)) {
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
                        if (!CommonUtils.isDestory(TurnTableActivity.this)) {
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
                            .showAD(TurnTableActivity.this);
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
                Log.i("ccc", "onVideoCached: ");
            }

            @Override
            public void onShow() {
                isTxLoadAdSuccess="3";
                AppSettingUtils.showTxShow("tx_ad_dazhuangpan");
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    videoCounts=1;
                    ToastShowViews.showMyToastTwo("点击下载，试玩1分钟快速升级","9");
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
                AppSettingUtils.showTxClick("tx_ad_dazhuangpan");
            }

            @Override
            public void onVideoComplete() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
                if (redDialogs != null) {
                    redDialogs.setDismiss();
                }
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    ToastShowViews.cancleToastTwo();
                }
            }

            @Override
            public void onClose() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
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
                    if (turnGoPrizeBeans!=null){
                        RobRedEvenlopesActivity.robRedEvenlopesJump(TurnTableActivity.this, "3", "转盘红包", "", turnGoPrizeBeans.getMoney(),"","");
                    }
                }
                if (!CommonUtils.isDestory(TurnTableActivity.this)){
                    ToastShowViews.cancleToastTwo();
                }
            }

            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                if ("1".equals(isTxLoadAdSuccess)){
                    isTxLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("2".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        showVideo();
                    }else {
                        if (!CommonUtils.isDestory(TurnTableActivity.this)) {
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