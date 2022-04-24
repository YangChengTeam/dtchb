package com.yc.wxchb.beans.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.wxchb.R;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.adapter.RedWallDrawAdapter;
import com.yc.wxchb.beans.contact.RedWallContract;
import com.yc.wxchb.beans.fragment.WithDrawFragment;
import com.yc.wxchb.beans.module.beans.EmptyBeans;
import com.yc.wxchb.beans.module.beans.HotWithDrawBeans;
import com.yc.wxchb.beans.module.beans.RedWallInfoBeans;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.beans.module.beans.WallMoneyBeans;
import com.yc.wxchb.beans.module.beans.WallMoneyBeansTwo;
import com.yc.wxchb.beans.present.RedWallPresenter;
import com.yc.wxchb.constants.Constant;
import com.yc.wxchb.dialog.PrizeDialog;
import com.yc.wxchb.dialog.SignDialog;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.utils.AppSettingUtils;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.SoundPoolUtils;
import com.yc.wxchb.utils.ToastShowViews;
import com.yc.wxchb.utils.VUiKit;
import com.yc.wxchb.utils.ad.GromoreInsetAdShow;
import com.yc.wxchb.utils.adgromore.GromoreAdShowThree;
import com.yc.wxchb.utils.adgromore.GromoreAdShowTwo;
import com.zzhoujay.richtext.RichText;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RedWallActivity extends BaseActivity<RedWallPresenter> implements RedWallContract.View {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_getRed)
    TextView tvGetRed;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv_gold)
    TextView tvGold;
    @BindView(R.id.tv_moneys)
    TextView tvMoneys;
    @BindView(R.id.tv_exchangeTips)
    TextView tvExchangeTips;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.iv_shou)
    ImageView ivShou;

    private RedWallDrawAdapter redWallDrawAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_red_wall;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        mPresenter.getWallInfo(CacheDataUtils.getInstance().getUserInfo().getId());
        init();
        initAnimotor();
        initrRedtipsDialog();
    }

    private void init() {
        GridLayoutManager hotgridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(hotgridLayoutManager);
        redWallDrawAdapter = new RedWallDrawAdapter(null);
        recyclerView.setAdapter(redWallDrawAdapter);
        redWallDrawAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                redWallDrawAdapter.notifyDataSetChanged();
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                List<RedWallInfoBeans.CashGoldBean.ConfigJsonBean> lists = redWallDrawAdapter.getData();
                for (int i = 0; i < lists.size(); i++) {
                    if (position == i) {
                        lists.get(i).setSelect(true);
                    } else {
                        lists.get(i).setSelect(false);
                    }
                }
                redWallDrawAdapter.notifyDataSetChanged();
            }
        });
    }

    public static void redWallJump(Context context){
        if (CommonUtils.isProxyAndDe(context)){
            ToastUtil.showToast("出现未知错误，请稍后再试");
            return;
        }
        Intent intent=new Intent(context,RedWallActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_close, R.id.tv_getRed, R.id.tv_exchangeTips, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_getRed:
                MobclickAgent.onEvent(RedWallActivity.this, "redwall", "1");//参数二为当前统计的事件ID
                showAd();
                break;
            case R.id.tv_exchangeTips:
                initSignRuleDialog(contents);
                break;
            case R.id.tv_sure:
                List<RedWallInfoBeans.CashGoldBean.ConfigJsonBean> lists = redWallDrawAdapter.getData();
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).isSelect()){
                        moneys=lists.get(i).getMoney();
                        String num = lists.get(i).getNum();
                        if (!TextUtils.isEmpty(num)){
                             if (gold>=Integer.parseInt(num)){//余额不够
                                 int finish_num = lists.get(i).getFinish_num();
                                 String daynum = lists.get(i).getDaynum();
                                 if (!TextUtils.isEmpty(daynum)&&Integer.parseInt(daynum)==finish_num){
                                     if (i==lists.size()-1){
                                         tipDialog(lists.get(i).getMoney()+"元","其他金额");
                                     }else {
                                         tipDialog(lists.get(i).getMoney()+"元",lists.get(i+1).getMoney());
                                     }
                                 }else {
                                     wxLogin();
                                 }
                             }else {//余额不够
                                 levelTipDialog();
                             }
                        }
                    }
                }
                break;
        }
    }
    private ObjectAnimator translationY;
    private ObjectAnimator translationX;
    private  ObjectAnimator scaleX;
    private  ObjectAnimator scaleY;
    private   AnimatorSet animatorSet;
    public void initAnimotor(){
         translationY = ObjectAnimator.ofFloat(ivShou, "translationY", 0, 50f, 0);
         translationX = ObjectAnimator.ofFloat(ivShou, "translationX", 0, 50f, 0);
         scaleX = ObjectAnimator.ofFloat(ivShou, "scaleX", 1f, 1.2f, 1f);
         scaleY = ObjectAnimator.ofFloat(ivShou, "scaleY", 1f, 1.2f, 1f);
        translationY.setRepeatCount(ValueAnimator.INFINITE);
        translationX.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
         animatorSet=new AnimatorSet();
        animatorSet.setDuration(1500);
        animatorSet.playTogether(translationY,translationX,scaleX,scaleY);
        animatorSet.start();

    }

    public void showAd() {
       GromoreAdShowThree.getInstance().showjiliAd("", new GromoreAdShowThree.OnAdShowCaback() {
            @Override
            public void onRewardedAdShow() {
                if (AppSettingUtils.commonYouTwo(RedWallActivity.this)){
                    ToastShowViews.showMyToastTwo();
                }
                MobclickAgent.onEvent(RedWallActivity.this, "moneytasksshow", "1");//参数二为当前统计的事件ID
            }

            @Override
            public void onRewardedAdShowFail() {
                moneyDialogs4();
            }

            @Override
            public void onRewardClick() {

            }

            @Override
            public void onVideoComplete() {
                ToastShowViews.cancleToastTwo();
            }

            @Override
            public void setVideoCallBacks() {

            }

            @Override
            public void onRewardedAdClosed(boolean isVideoClick, boolean isCompeter, String adNetworkRitId) {
                ToastShowViews.cancleToastTwo();
                if (isCompeter) {
                    mPresenter.getWallMoneys(adNetworkRitId);
                } else {
                    ToastUtil.showToast("请重新观看哦！");
                }
            }
        });
    }

    private SignDialog moneyDialogs4;

    public void moneyDialogs4() {
        moneyDialogs4 = new SignDialog(this);
        View builder = moneyDialogs4.builder(R.layout.money_task4_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                moneyDialogs4.setDismiss();
            }
        });
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                moneyDialogs4.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            moneyDialogs4.setShow();
        }
    }

    private SignDialog levelTipDialogs;
    public void levelTipDialog() {
        levelTipDialogs = new SignDialog(this);
        View builder = levelTipDialogs.builder(R.layout.leveltips_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_title = builder.findViewById(R.id.tv_title);
        TextView tv_des = builder.findViewById(R.id.tv_des);
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText("金币不足");
        tv_des.setText("请在此页面看视频获取金币哦！");
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelTipDialogs.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelTipDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            levelTipDialogs.setShow();
        }
    }


    private SignDialog redtipsDialogs;
    private TextView tv_sure,tv_moneys,tv_des;
    private ImageView iv_close;
    private FrameLayout fl_ad_container_money;
    public void initrRedtipsDialog() {
        redtipsDialogs = new SignDialog(this);
        View builder = redtipsDialogs.builder(R.layout.redtipstwo_dialogs_item);
         tv_sure = builder.findViewById(R.id.tv_sure);
         tv_moneys = builder.findViewById(R.id.tv_moneys);
         iv_close  = builder.findViewById(R.id.iv_close);
         tv_des  = builder.findViewById(R.id.tv_des);
         tv_des.setText("金币");
         fl_ad_container_money  = builder.findViewById(R.id.fl_ad_container_money);
    }

    public void redtipsDialog(int moneys) {
        if (redtipsDialogs!=null&&tv_des!=null){
            tv_des.setText("金币");
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redtipsDialogs.setDismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redtipsDialogs.setDismiss();
                }
            });
            tv_moneys.setText(moneys+"");
            if (!CommonUtils.isDestory(this)) {
                loadExpressAd(fl_ad_container_money);
                redtipsDialogs.setShow();
                showInset();
            }
        }
    }



    private void showInset() {
        VUiKit.postDelayed(1000,()->{
            GromoreInsetAdShow.getInstance().showInset(this, "", new GromoreInsetAdShow.OnInsetAdShowCaback() {
                @Override
                public void onRewardedAdShow() {

                }

                @Override
                public void onRewardedAdShowFail() {

                }

                @Override
                public void onRewardClick() {

                }

                @Override
                public void onVideoComplete() {

                }

                @Override
                public void setVideoCallBacks() {

                }

                @Override
                public void onRewardedAdClosed(boolean isVideoClick, boolean isCompeter) {

                }
            });
        });
    }

    private void wxLogin() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN, null);
        UMShareAPI.get(this).release();
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new MyAuthLoginListener());
    }

    public class MyAuthLoginListener implements UMAuthListener {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            String unionid = map.get("unionid");
            String wx_openid = map.get("openid");
            String name = map.get("name");
            String profile_image_url = map.get("profile_image_url");
            if (!TextUtils.isEmpty(wx_openid)) {
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                String appVersionCode = CommonUtils.getAppVersionCode(RedWallActivity.this);
                mPresenter.wallCash(userInfo.getId() + "", wx_openid, moneys, appVersionCode);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            ToastUtil.showToast("授权失败:" + throwable.getMessage() + i + "------getLocalizedMessage()" + throwable.getLocalizedMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            ToastUtil.showToast("授权取消");
        }
    }
    private boolean isFirst;
    private int gold;
    private String contents;
    private String codeId;
    private String moneys;
    @Override
    public void getWallInfoSuccess(RedWallInfoBeans data) {
           if (data!=null){
               RedWallInfoBeans.CashGoldBean cash_gold = data.getCash_gold();
               if (cash_gold!=null){
                   List<RedWallInfoBeans.CashGoldBean.ConfigJsonBean> config_json = cash_gold.getConfig_json();
                   if (config_json!=null){
                       if (config_json!=null){
                           for (int i = 0; i < config_json.size(); i++) {
                               if (i==0){
                                   config_json.get(i).setSelect(true);
                               }
                           }
                       }
                       redWallDrawAdapter.setNewData(config_json);
                   }
                   contents =cash_gold.getContent();
                   codeId=cash_gold.getAd_code();
                   if (!isFirst){
                       isFirst=true;

                   }
               }
               gold=data.getGold_num();
               tvMoneys.setText("≈"+data.getGold_cash()+"");
               tvGold.setText(data.getGold_num()+"");
           }
    }

    @Override
    public void wallCashSuccess(EmptyBeans data) {
        mPresenter.getWallInfo(CacheDataUtils.getInstance().getUserInfo().getId());
        withDrawSuccessDialog();
    }

    @Override
    public void getWallMoneysSuccess(WallMoneyBeansTwo data) {
        if (data!=null){
            mPresenter.getWallInfo(CacheDataUtils.getInstance().getUserInfo().getId());
            redtipsDialog(data.getGold_num());
        }
    }

    private SnatchDialog withDrawSuccessDialogs;

    public void withDrawSuccessDialog() {
        withDrawSuccessDialogs = new SnatchDialog(this);
        View builder = withDrawSuccessDialogs.builder(R.layout.withdrawsuccess_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawSuccessDialogs.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawSuccessDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            withDrawSuccessDialogs.setShow();
        }
    }

    private SnatchDialog signRule;

    public void initSignRuleDialog(String tips) {
        signRule = new SnatchDialog(this);
        View builder = signRule.builder(R.layout.signrule_dialog_item);
        TextView tv_contents = builder.findViewById(R.id.tv_contents);
        TextView tvSure1 = builder.findViewById(R.id.tvSure1);
        TextView tvSure2 = builder.findViewById(R.id.tvSure2);
        RelativeLayout rela_sure = builder.findViewById(R.id.rela_sure);
        tvSure1.setVisibility(View.VISIBLE);
        tvSure2.setVisibility(View.GONE);
        TextView tv_title = builder.findViewById(R.id.tv_title);
        tv_title.setText("兑换说明");
        if (!TextUtils.isEmpty(tips)) {
            RichText.from(tips).into(tv_contents);
        }
        rela_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                signRule.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            signRule.setShow();
        }
    }


    private SignDialog tipDialogs;
    public void tipDialog(String tips,String tips2) {
        tipDialogs = new SignDialog(this);
        View builder = tipDialogs.builder(R.layout.tips_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_des = builder.findViewById(R.id.tv_des);
        String insertedNumStr="今日"+tips+"提现名额用完，请提现"+tips2;
        SpannableString spannableString = new SpannableString(insertedNumStr);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1849")), 2, tips.length()+2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1849")), insertedNumStr.length()-tips2.length(), insertedNumStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_des.setText(spannableString);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialogs.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            tipDialogs.setShow();
        }
    }


    @Override
    protected void onDestroy() {
        if (translationY!=null){
            translationY.cancel();
            translationY=null;
        }
        if (translationX!=null){
            translationX.cancel();
            translationX=null;
        }
        if (scaleX!=null){
            scaleX.cancel();
            scaleX=null;
        }
        if (scaleY!=null){
            scaleY.cancel();
            scaleY=null;
        }
        if (animatorSet!=null){
            animatorSet.cancel();
            animatorSet=null;
        }
        super.onDestroy();
    }

    //=================start===================信息流=====================================================================================
    private NativeExpressADView nativeExpressADView;
    private NativeExpressAD nativeExpressAD;
    private boolean isPreloadVideo=false;
    private ViewGroup container;
    private void  loadExpressAd(ViewGroup container){
        this.container=container;
        int acceptedWidth = 380;
        nativeExpressAD=new NativeExpressAD(this, new ADSize(acceptedWidth, 200), Constant.TXEXPRESS, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(com.qq.e.comm.util.AdError adError) {

            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {

                // 释放前一个 NativeExpressADView 的资源
                if (nativeExpressADView != null) {
                    nativeExpressADView.destroy();
                }
                // 3.返回数据后，SDK 会返回可以用于展示 NativeExpressADView 列表
                nativeExpressADView = list.get(0);
                if (nativeExpressADView.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                    nativeExpressADView.setMediaListener(mediaListener);
                }
                if (container.getChildCount() > 0) {
                    container.removeAllViews();
                }

                if (nativeExpressADView.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                    nativeExpressADView.setMediaListener(mediaListener);
                    if(isPreloadVideo) {
                        // 预加载视频素材，加载成功会回调mediaListener的onVideoCached方法，失败的话回调onVideoError方法errorCode为702。
                        nativeExpressADView.preloadVideo();
                    }
                } else {
                    isPreloadVideo = false;
                }
                if(!isPreloadVideo) {
                    // 广告可见才会产生曝光，否则将无法产生收益。
                    container.addView(nativeExpressADView);
                    nativeExpressADView.render();
                }

            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }



        });

//       nativeExpressAD.setVideoOption(new VideoOption.Builder()
//               .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI) // WIFI 环境下可以自动播放视频
//               .setAutoPlayMuted(true) // 自动播放时为静音
//               .build()); //
//       nativeExpressAD.setVideoPlayPolicy(VideoOption.VideoPlayPolicy.AUTO); // 本次拉回的视频广告，从用户的角度看是自动播放的
        nativeExpressAD.loadAD(1);
    }


    private NativeExpressMediaListener mediaListener = new NativeExpressMediaListener() {
        @Override
        public void onVideoInit(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoLoading(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoCached(NativeExpressADView nativeExpressADView) {
            // 视频素材加载完成，此时展示视频广告不会有进度条。
            if(isPreloadVideo && nativeExpressADView != null) {
                if(container.getChildCount() > 0){
                    container.removeAllViews();
                }
                // 广告可见才会产生曝光，否则将无法产生收益。
                container.addView(nativeExpressADView);
                nativeExpressADView.render();
            }
        }

        @Override
        public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {

        }

        @Override
        public void onVideoStart(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoPause(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoComplete(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoError(NativeExpressADView nativeExpressADView, com.qq.e.comm.util.AdError adError) {

        }


        @Override
        public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoPageClose(NativeExpressADView nativeExpressADView) {

        }
    };


    //=================end===================信息流=====================================================================================
}