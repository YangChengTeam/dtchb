package com.yc.wxchb.beans.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bytedance.sdk.dp.DPPageState;
import com.bytedance.sdk.dp.DPWidgetDrawParams;
import com.bytedance.sdk.dp.IDPAdListener;
import com.bytedance.sdk.dp.IDPDrawListener;
import com.bytedance.sdk.dp.IDPWidget;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsContentPage;
import com.kwad.sdk.api.KsScene;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.umeng.analytics.MobclickAgent;
import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.contact.VideoContract;
import com.yc.wxchb.beans.fragment.ExitTintFragment;
import com.yc.wxchb.beans.module.beans.HotNumsInfoBeans;
import com.yc.wxchb.beans.module.beans.RedTaskBeans;
import com.yc.wxchb.beans.module.beans.TaskLineBean;
import com.yc.wxchb.beans.present.VideoPresenter;
import com.yc.wxchb.beans.widget.CircleProgressView;
import com.yc.wxchb.constants.Constant;
import com.yc.wxchb.dialog.PrizeDialog;
import com.yc.wxchb.dialog.SignDialog;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.service.event.Event;
import com.yc.wxchb.utils.AppSettingUtils;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.SoundPoolUtils;
import com.yc.wxchb.utils.VUiKit;
import com.yc.wxchb.utils.ad.GromoreAdShow;
import com.yc.wxchb.utils.ad.GromoreInsetAdShow;
import com.yc.wxchb.utils.video.DPHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class VideoActivity extends BaseActivity<VideoPresenter> implements VideoContract.View {

    @BindView(R.id.circleProgress)
    CircleProgressView circleProgress;
    @BindView(R.id.iv_red)
    ImageView ivRed;
    @BindView(R.id.iv_shou)
    ImageView ivShou;
    @BindView(R.id.times)
    TextView times;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.iv_jiangli2)
    ImageView ivJiangli2;


    private IDPWidget mIDPWidget;
    public FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_video;
    }

    private int ti;

    @Override
    public void initEventAndData() {
        setFullScreen();
        tv_money.setText(((MyApplication) MyApplication.getInstance()).cash);
        EventBus.getDefault().register(this);
        mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
        mPresenter.getHotInfo(CacheDataUtils.getInstance().getUserInfo().getId() + "", ((MyApplication) MyApplication.getInstance()).getAgentId());
        initRedtipsDialog();
        initRedDialog();
        circleProgress.setAnimotorListen(new CircleProgressView.AnimotorListen() {
            @Override
            public void ends() {
                redDialog();
            }

            @Override
            public void times(long time) {
                int tis = (int) (time / 1000);
                if (ti != tis) {
                    ti = tis;
                    times.setText(ti + "/" + time_out);
                }
            }
        });
        if (Constant.video_change==1) {//穿山甲
            initDrawWidget();
        } else {//快手
            initContentPage();
        }
        initAnimotor();
        initShouAnimotor();
    }

    private ObjectAnimator translationY;
    private ObjectAnimator translationX;
    private  ObjectAnimator scaleX;
    private  ObjectAnimator scaleY;
    private   AnimatorSet animatorSetshou;
    public void initShouAnimotor(){
        translationY = ObjectAnimator.ofFloat(ivShou, "translationY", 0, -20f, 0);
        translationX = ObjectAnimator.ofFloat(ivShou, "translationX", 0, 40f, 0);
        scaleX = ObjectAnimator.ofFloat(ivShou, "scaleX", 1f, 1.2f, 1f);
        scaleY = ObjectAnimator.ofFloat(ivShou, "scaleY", 1f, 1.2f, 1f);
        translationY.setRepeatCount(3);
        translationX.setRepeatCount(3);
        scaleX.setRepeatCount(3);
        scaleY.setRepeatCount(3);
        animatorSetshou=new AnimatorSet();
        animatorSetshou.setDuration(1500);
        animatorSetshou.playTogether(translationY,translationX,scaleX,scaleY);
        animatorSetshou.start();
        animatorSetshou.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ivShou.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    private ObjectAnimator rotation;
    private AnimatorSet animatorSet,scaleSet;
    private ObjectAnimator scalex,scaley;

    public void initAnimotor() {
        rotation = ObjectAnimator.ofFloat(ivRed, "rotation", 0f, 45f, 0f, -45f, 0f);
        rotation.setRepeatCount(ValueAnimator.INFINITE);
        animatorSet = new AnimatorSet();
        animatorSet.setStartDelay(3000);
        animatorSet.setDuration(1500);
        animatorSet.play(rotation);
        animatorSet.start();

        scalex = ObjectAnimator.ofFloat(ivJiangli2, "scaleX", 0.8f, 1.2f, 0.8f);
        scaley = ObjectAnimator.ofFloat(ivJiangli2, "scaleY", 0.8f, 1.2f, 0.8f);
        scaley.setRepeatCount(ValueAnimator.INFINITE);
        scalex.setRepeatCount(ValueAnimator.INFINITE);
        scaleSet = new AnimatorSet();
        scaleSet.setDuration(1500);
        scaleSet.playTogether(scalex,scaley);
        scaleSet.start();
    }


    private void initDrawWidget() {
        DPWidgetDrawParams obtain = DPWidgetDrawParams.obtain();
        obtain.drawContentType(DPWidgetDrawParams.DRAW_CONTENT_TYPE_ALL);
        obtain.adOffset(0);
        obtain.hideClose(true, null);
        obtain.listener(new IDPDrawListener() {
            @Override
            public void onDPRefreshFinish() {
                super.onDPRefreshFinish();
               // Log.d("ccc", "-------------onDPRefreshFinish: ");
            }

            @Override
            public void onDPPageChange(int i) {
                super.onDPPageChange(i);
               // Log.d("ccc", "-------------onDPPageChange: " + i);
            }

            @Override
            public void onDPPageChange(int i, Map<String, Object> map) {
                super.onDPPageChange(i, map);
              //  Log.d("ccc", "-------------onDPPageChange: " + i);
            }

            @Override
            public void onDPVideoPlay(Map<String, Object> map) {
                super.onDPVideoPlay(map);
                if (circleProgress != null) {
                    circleProgress.continueAnimotor(isDialogShow());
                }
              //  Log.d("ccc", "-------------onDPVideoPlay: " + map);
            }

            @Override
            public void onDPVideoPause(Map<String, Object> map) {
                super.onDPVideoPause(map);
                if (circleProgress != null) {
                    circleProgress.stopAnimotor();
                }
              //  Log.d("ccc", "-------------onDPVideoPause: " + map);
            }

            @Override
            public void onDPVideoContinue(Map<String, Object> map) {
                super.onDPVideoContinue(map);
                if (circleProgress != null) {
                    circleProgress.continueAnimotor(isDialogShow());
                }
              //  Log.d("ccc", "-------------onDPVideoContinue: " + map);
            }

            @Override
            public void onDPVideoCompletion(Map<String, Object> map) {
                super.onDPVideoCompletion(map);
              //  Log.d("ccc", "-------------onDPVideoCompletion: " + map);
            }

            @Override
            public void onDPVideoOver(Map<String, Object> map) {
                super.onDPVideoOver(map);
             //   Log.d("ccc", "-------------onDPVideoOver: " + map);
            }

            @Override
            public void onDPClose() {
                super.onDPClose();
             //   Log.d("ccc", "-------------onDPClose: ");
            }

            @Override
            public void onDPReportResult(boolean b) {
                super.onDPReportResult(b);
             //   Log.d("ccc", "-------------onDPReportResult: ");
            }

            @Override
            public void onDPReportResult(boolean b, Map<String, Object> map) {
                super.onDPReportResult(b, map);
              //  Log.d("ccc", "-------------onDPReportResult: " + map);
            }

            @Override
            public void onDPRequestStart(@Nullable Map<String, Object> map) {
                super.onDPRequestStart(map);

             //   Log.d("ccc", "-------------onDPRequestStart: " + map);
            }

            @Override
            public void onDPRequestFail(int i, String s, @Nullable Map<String, Object> map) {
                super.onDPRequestFail(i, s, map);
              //  Log.d("ccc", "-------------onDPRequestFail: " + map);
            }

            @Override
            public void onDPRequestSuccess(List<Map<String, Object>> list) {
                super.onDPRequestSuccess(list);
             //   Log.d("ccc", "-------------onDPRequestSuccess: " + list);
            }

            @Override
            public void onDPClickAvatar(Map<String, Object> map) {
                super.onDPClickAvatar(map);
            //    Log.d("ccc", "-------------onDPClickAvatar: " + map);
            }

            @Override
            public void onDPClickAuthorName(Map<String, Object> map) {
                super.onDPClickAuthorName(map);
            }

            @Override
            public void onDPClickComment(Map<String, Object> map) {
                super.onDPClickComment(map);
            }

            @Override
            public void onDPClickLike(boolean b, Map<String, Object> map) {
                super.onDPClickLike(b, map);
             //   Log.d("ccc", "-------------onDPClickLike: ");
            }

            @Override
            public void onDPPageStateChanged(DPPageState dpPageState) {
                super.onDPPageStateChanged(dpPageState);
             //   Log.d("ccc", "-------------onDPPageStateChanged: " + dpPageState.name());
            }
        });
        obtain.adListener(new IDPAdListener() {
            @Override
            public void onDPAdRequest(Map<String, Object> map) {
            //    Log.d("ccc", "-------------onDPAdRequest: " + map);
            }

            @Override
            public void onDPAdRequestSuccess(Map<String, Object> map) {
           //     Log.d("ccc", "-------------onDPAdRequestSuccess: " + map);
            }

            @Override
            public void onDPAdRequestFail(int code, String msg, Map<String, Object> map) {
            //    Log.d("ccc", "-------------onDPAdRequestFail: " + map);
            }

            @Override
            public void onDPAdFillFail(Map<String, Object> map) {
            //    Log.d("ccc", "-------------onDPAdFillFail: " + map);
            }

            @Override
            public void onDPAdShow(Map<String, Object> map) {
            //    Log.d("ccc", "-------------onDPAdShow: " + map);
            }

            @Override
            public void onDPAdPlayStart(Map<String, Object> map) {
                if (circleProgress != null) {
                    circleProgress.continueAnimotor(isDialogShow());
                }
            //    Log.d("ccc", "-------------onDPAdPlayStart: " + map);
            }

            @Override
            public void onDPAdPlayPause(Map<String, Object> map) {
                if (circleProgress != null) {
                    circleProgress.stopAnimotor();
                }
            //    Log.d("ccc", "-------------onDPAdPlayPause: " + map);
            }

            @Override
            public void onDPAdPlayContinue(Map<String, Object> map) {
                if (circleProgress != null) {
                    circleProgress.continueAnimotor(isDialogShow());
                }
             //   Log.d("ccc", "-------------onDPAdPlayContinue: " + map);
            }

            @Override
            public void onDPAdPlayComplete(Map<String, Object> map) {
             //   Log.d("ccc", "-------------onDPAdPlayComplete: " + map);
            }

            @Override
            public void onDPAdClicked(Map<String, Object> map) {
             //   Log.d("ccc", "-------------onDPAdClicked: " + map);
            }
        });
        mIDPWidget = DPHolder.getInstance().buildDrawWidget(obtain);
        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.fl_containss, mIDPWidget.getFragment()).commit();
    }

    public boolean isDialogShow(){
        boolean isDialogs=false;
         if (bigMeonyDialogs!=null&&bigMeonyDialogs.getIsShow()){
             isDialogs=true;
         }
        if (redtipsDialogs!=null&&redtipsDialogs.getIsShow()){
            isDialogs=true;
        }
        if (redDialog!=null&&redDialog.getIsShow()){
            isDialogs=true;
        }
        return isDialogs;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("android:support:fragments", null);
    }

    @Override
    public void onBackPressed() {
        if (mIDPWidget != null && !mIDPWidget.canBackPress()) {
            return;
        }

        if (mKsContentPage != null && mKsContentPage.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    public static void videoJump(Context context) {
        Intent intent = new Intent(context, VideoActivity.class);
        context.startActivity(intent);
    }

    private SignDialog jiasuDialogs;

    public void jiasuDialog() {
        jiasuDialogs = new SignDialog(this);
        View builder = jiasuDialogs.builder(R.layout.jisu_dialogs_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        ImageView ivCode = builder.findViewById(R.id.iv_code);
        TextView tvs = builder.findViewById(R.id.tvs);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jiasuDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(VideoActivity.this)) {
            jiasuDialogs.setShow();
        }
    }

    private SnatchDialog bigMeonyDialogs;
    public void bigMeonyDialog() {
        bigMeonyDialogs = new SnatchDialog(this);
        bigMeonyDialogs.setOutCancle(false);
        View builder = bigMeonyDialogs.builder(R.layout.bigmoney_dialogs_item);
        LinearLayout line_sure = builder.findViewById(R.id.line_sure);
        bigMeonyDialogs.setOutCancle(false);
        bigMeonyDialogs.setKey();
        line_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(VideoActivity.this, "video_youdao_sure", "1");//参数二为当前统计的事件ID
                bigMeonyDialogs.setDismiss();
                AdHotActivity.adhotJump(VideoActivity.this, "1");
            }
        });
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigMeonyDialogs.setDismiss();
                circleProgress.startAnimotor(time_out * 1000);
            }
        });
        if (!CommonUtils.isDestory(VideoActivity.this)) {
            MobclickAgent.onEvent(VideoActivity.this, "video_youdao", "1");//参数二为当前统计的事件ID
            bigMeonyDialogs.setShow();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHomePage(Event event) {
        if (event instanceof Event.HotVideoEvent) {
            if (circleProgress != null) {
                circleProgress.startAnimotor(time_out * 1000);
            }
        }
    }

    private SignDialog redtipsDialogs;
    private TextView tv_moneys,tv_sure;
    private  ImageView iv_closess;
    private FrameLayout fl_ad_container_money;

    public void initRedtipsDialog() {
        redtipsDialogs = new SignDialog(this);
        View builder = redtipsDialogs.builder(R.layout.redtipstwo_dialogs_item);
         tv_sure = builder.findViewById(R.id.tv_sure);
         tv_moneys = builder.findViewById(R.id.tv_moneys);
        iv_closess  = builder.findViewById(R.id.iv_close);
        fl_ad_container_money  = builder.findViewById(R.id.fl_ad_container_money);
        redtipsDialogs.setKey();
    }

    public void redtipsDialog(String moneys) {
        if (!CommonUtils.isDestory(VideoActivity.this)) {
            if (redtipsDialogs != null && tv_moneys != null) {
                tv_moneys.setText( moneys);
                showInset();
                redtipsDialogs.setDismissListen(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (AppSettingUtils.commonYou(VideoActivity.this)) {
                            if (hb_num >= first_video) {
                                if (hotShowIndexList.contains(String.valueOf(hb_num))) {
                                    bigMeonyDialog();
                                } else {
                                    circleProgress.startAnimotor(time_out * 1000);
                                }
                            } else {
                                circleProgress.startAnimotor(time_out * 1000);
                            }
                        } else {
                            circleProgress.startAnimotor(time_out * 1000);
                        }
                    }
                });
                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redtipsDialogs.setDismiss();
                    }
                });
                iv_closess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redtipsDialogs.setDismiss();
                    }
                });
                loadExpressAd(fl_ad_container_money);
                redtipsDialogs.setShow();
            }
        }
    }



    private void initContentPage() {
        String neirong="7864000166";
        KsScene adScene = new KsScene.Builder(Long.parseLong(neirong)).build();
        mKsContentPage = KsAdSDK.getLoadManager().loadContentPage(adScene);
        initContentPageListener();
        showContentPage();
    }


    private void showContentPage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_containss, mKsContentPage.getFragment())
                .commitAllowingStateLoss();
    }

    protected KsContentPage mKsContentPage;

    protected void initContentPageListener() {
        // 接口回调在主线程，误做耗时操作
        mKsContentPage.setPageListener(new KsContentPage.PageListener() {
            @Override
            public void onPageEnter(KsContentPage.ContentItem item) {
                Log.d("ContentPage", "页面Enter:" + item);

            }

            @Override
            public void onPageResume(KsContentPage.ContentItem item) {
                if (circleProgress != null) {
                    circleProgress.continueAnimotor(isDialogShow());
                }
                Log.d("ContentPage", "页面Resume:" + item);

            }

            @Override
            public void onPagePause(KsContentPage.ContentItem item) {
                if (circleProgress != null) {
                    circleProgress.stopAnimotor();
                }
                Log.d("ContentPage", "页面Pause" + item);

            }

            @Override
            public void onPageLeave(KsContentPage.ContentItem item) {
                Log.d("ContentPage", "页面Leave: " + item);

            }
        });

        // 接口回调在主线程，误做耗时操作
        mKsContentPage.setVideoListener(new KsContentPage.VideoListener() {
            @Override
            public void onVideoPlayStart(KsContentPage.ContentItem item) {
                Log.d("ContentPage", "视频PlayStart: " + item);

            }

            @Override
            public void onVideoPlayPaused(KsContentPage.ContentItem item) {
                Log.d("ContentPage", "视频PlayPaused: " + item);
                if (circleProgress != null) {
                    circleProgress.stopAnimotor();
                }
            }

            @Override
            public void onVideoPlayResume(KsContentPage.ContentItem item) {
                Log.d("ContentPage", "视频PlayResume: " + item);
                if (circleProgress != null) {
                    circleProgress.continueAnimotor(isDialogShow());
                }
            }

            @Override
            public void onVideoPlayCompleted(KsContentPage.ContentItem item) {
                Log.d("ContentPage", "视频PlayCompleted: " + item);

            }

            @Override
            public void onVideoPlayError(KsContentPage.ContentItem item, int what, int extra) {
                Log.d("ContentPage", "视频PlayError: " + item);
            }
        });

        mKsContentPage.setShareListener(new KsContentPage.KsShareListener() {
            @Override
            public void onClickShareButton(String shareData) {

            }
        });

    }

    private int time_out;

    @Override
    public void getRedTaskDataSuccess(RedTaskBeans data) {
        if (data != null) {
            time_out = data.getTime_out();
            List<RedTaskBeans.HbOnlineTaskBean> hb_online_task = data.getHb_online_task();
            circleProgress.startAnimotor(time_out * 1000);
        }
    }

    @Override
    public void getTaskLineSuccess(TaskLineBean data) {
        if (data != null) {
            hb_num = data.getHb_num();
            if (!TextUtils.isEmpty(data.getCash())) {
                ((MyApplication) MyApplication.getInstance()).cash = data.getCash();
                tv_money.setText(data.getCash());
            }
            ((MyApplication) MyApplication.getInstance()).hb_Nums = hb_num;
            redtipsDialog(data.getRed_money());
        }
    }

    //=============活跃值==========================================活跃值====================================================活跃值=======================================================================
    private int is_open;
    private int first_video;
    private int video_num;
    private int total;
    private int hotlevels;
    private int level_state;
    private HotNumsInfoBeans.DownloadConfigBean download_config;
    private List<String> hotShowIndexList = new ArrayList<>();
    private int hb_num;

    @Override
    public void getHotInfoSuccess(HotNumsInfoBeans data) {
        if (data != null) {
            hb_num = data.getHb_num();
            HotNumsInfoBeans.DownloadBean download = data.getDownload();
            download_config = data.getDownload_config();
            if (download != null) {
                is_open = download.getIs_open();
                Constant.IS_OPEN = is_open;
            }
            if (download_config != null) {
                first_video = download_config.getFirst_video();
                video_num = download_config.getVideo_num();
                total = download_config.getTotal();
                String ad_video = download_config.getAd_video();
                hotlevels = download_config.getLevel();
                level_state = download_config.getLevel_state();
                Constant.LEVEL_STATE = level_state;
                Constant.LEVEL = hotlevels;
                if (total > 0) {
                    for (int i = 0; i < total; i++) {
                        hotShowIndexList.add(String.valueOf(first_video + video_num * i));
                    }
                }
            }
        }
    }
    //=============活跃值==========================================活跃值====================================================活跃值======================================================================

    private SnatchDialog redDialog;
    private ImageView iv_close;
    private ImageView ivOpen;

    public void initRedDialog() {
        redDialog = new SnatchDialog(this);
        View builder = redDialog.builder(R.layout.reds_dialog_item);
        iv_close = builder.findViewById(R.id.iv_close);
        ivOpen = builder.findViewById(R.id.iv_open);
        redDialog.setOutCancle(false);
        redDialog.setKey();
    }

    public void redDialog() {
        if (!CommonUtils.isDestory(this) && redDialog != null) {
            ivOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    showjiliAd();
                    MobclickAgent.onEvent(VideoActivity.this, "video_red_sure", "1");//参数二为当前统计的事件ID
                    redDialog.setDismiss();
                }
            });
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    redDialog.setDismiss();
                    circleProgress.startAnimotor(time_out * 1000);
                }
            });
            if (!CommonUtils.isDestory(this)) {
                MobclickAgent.onEvent(VideoActivity.this, "video_red", "1");//参数二为当前统计的事件ID
                redDialog.setShow();
                showInset();
            }
        }
    }

    private void showInset() {
        if (!CommonUtils.isDestory(this)) {
            VUiKit.postDelayed(1000,()->{
                GromoreInsetAdShow.getInstance().showInset(this, "video_inset", new GromoreInsetAdShow.OnInsetAdShowCaback() {
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
    }

    public void showjiliAd() {
        if (!CommonUtils.isDestory(this)) {
            GromoreAdShow.getInstance().showjiliAd(this, 1, "video", new GromoreAdShow.OnAdShowCaback() {
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
                public void onRewardedAdClosed(boolean isVideoClick, boolean isCompete) {
                    mPresenter.getTaskLine(CacheDataUtils.getInstance().getUserInfo().getId());
                }

                @Override
                public void onFinshTask(String appPackage, String appName, String type) {

                }

                @Override
                public void onNoTask() {

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        if (circleProgress != null) {
            circleProgress.cancle();
            circleProgress = null;
        }
        if (rotation != null) {
            rotation.cancel();
            rotation = null;
        }
        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet = null;
        }

        if (translationY != null) {
            translationY.cancel();
            translationY = null;
        }
        if (translationX != null) {
            translationX.cancel();
            translationX = null;
        }
        if (scaleX != null) {
            scaleX.cancel();
            scaleX = null;
        }
        if (scaleY != null) {
            scaleY.cancel();
            scaleY = null;
        }
        if (animatorSetshou != null) {
            animatorSetshou.cancel();
            animatorSetshou = null;
        }

        if (scalex != null) {
            scalex.cancel();
            scalex = null;
        }
        if (scaley != null) {
            scaley.cancel();
            scaley = null;
        }
        if (scaleSet != null) {
            scaleSet.cancel();
            scaleSet = null;
        }

        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.iv_back,R.id.iv_jiangli,R.id.line_moneyJunp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_jiangli:
                RedWallActivity.redWallJump(this);
                break;
            case R.id.line_moneyJunp:
                Intent intent=new Intent(VideoActivity.this,MainActivity.class);
                intent.putExtra("position","2");
                startActivity(intent);
                finish();
                break;
        }
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