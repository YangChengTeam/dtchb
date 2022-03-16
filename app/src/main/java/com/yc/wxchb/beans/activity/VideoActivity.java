package com.yc.wxchb.beans.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bytedance.sdk.dp.DPPageState;
import com.bytedance.sdk.dp.DPWidgetDrawParams;
import com.bytedance.sdk.dp.IDPAdListener;
import com.bytedance.sdk.dp.IDPDrawListener;
import com.bytedance.sdk.dp.IDPWidget;
import com.kwad.sdk.api.KsAdSDK;
import com.kwad.sdk.api.KsContentPage;
import com.kwad.sdk.api.KsScene;
import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.contact.VideoContract;
import com.yc.wxchb.beans.module.beans.HotNumsInfoBeans;
import com.yc.wxchb.beans.module.beans.RedTaskBeans;
import com.yc.wxchb.beans.module.beans.TaskLineBean;
import com.yc.wxchb.beans.module.beans.UserInfo;
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
import com.yc.wxchb.utils.video.DPHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class VideoActivity extends BaseActivity<VideoPresenter> implements VideoContract.View {

    @BindView(R.id.circleProgress)
    CircleProgressView circleProgress;
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

    @Override
    public void initEventAndData() {
        setFullScreen();
        EventBus.getDefault().register(this);
        mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
        mPresenter.getHotInfo(CacheDataUtils.getInstance().getUserInfo().getId()+"",((MyApplication) MyApplication.getInstance()).getAgentId());
        initRedtipsDialog();
        initRedDialog();
        circleProgress.setAnimotorListen(new CircleProgressView.AnimotorListen() {
            @Override
            public void ends() {
                redDialog();
            }
        });


    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
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
                Log.d("ccc", "-------------onDPRefreshFinish: ");
            }

            @Override
            public void onDPPageChange(int i) {
                super.onDPPageChange(i);
                Log.d("ccc", "-------------onDPPageChange: " + i);
            }

            @Override
            public void onDPPageChange(int i, Map<String, Object> map) {
                super.onDPPageChange(i, map);
                Log.d("ccc", "-------------onDPPageChange: " + i);
            }

            @Override
            public void onDPVideoPlay(Map<String, Object> map) {
                super.onDPVideoPlay(map);
                Log.d("ccc", "-------------onDPVideoPlay: " + map);
            }

            @Override
            public void onDPVideoPause(Map<String, Object> map) {
                super.onDPVideoPause(map);
                Log.d("ccc", "-------------onDPVideoPause: " + map);
            }

            @Override
            public void onDPVideoContinue(Map<String, Object> map) {
                super.onDPVideoContinue(map);
                Log.d("ccc", "-------------onDPVideoContinue: " + map);
            }

            @Override
            public void onDPVideoCompletion(Map<String, Object> map) {
                super.onDPVideoCompletion(map);
                Log.d("ccc", "-------------onDPVideoCompletion: " + map);
            }

            @Override
            public void onDPVideoOver(Map<String, Object> map) {
                super.onDPVideoOver(map);
                Log.d("ccc", "-------------onDPVideoOver: " + map);
            }

            @Override
            public void onDPClose() {
                super.onDPClose();
                Log.d("ccc", "-------------onDPClose: ");
            }

            @Override
            public void onDPReportResult(boolean b) {
                super.onDPReportResult(b);
                Log.d("ccc", "-------------onDPReportResult: ");
            }

            @Override
            public void onDPReportResult(boolean b, Map<String, Object> map) {
                super.onDPReportResult(b, map);
                Log.d("ccc", "-------------onDPReportResult: " + map);
            }

            @Override
            public void onDPRequestStart(@Nullable Map<String, Object> map) {
                super.onDPRequestStart(map);
                Log.d("ccc", "-------------onDPRequestStart: " + map);
            }

            @Override
            public void onDPRequestFail(int i, String s, @Nullable Map<String, Object> map) {
                super.onDPRequestFail(i, s, map);
                Log.d("ccc", "-------------onDPRequestFail: " + map);
            }

            @Override
            public void onDPRequestSuccess(List<Map<String, Object>> list) {
                super.onDPRequestSuccess(list);
                Log.d("ccc", "-------------onDPRequestSuccess: " + list);
            }

            @Override
            public void onDPClickAvatar(Map<String, Object> map) {
                super.onDPClickAvatar(map);
                Log.d("ccc", "-------------onDPClickAvatar: " + map);
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
                Log.d("ccc", "-------------onDPClickLike: ");
            }

            @Override
            public void onDPPageStateChanged(DPPageState dpPageState) {
                super.onDPPageStateChanged(dpPageState);
                Log.d("ccc", "-------------onDPPageStateChanged: " + dpPageState.name());
            }
        });
        obtain.adListener(new IDPAdListener() {
            @Override
            public void onDPAdRequest(Map<String, Object> map) {
                Log.d("ccc", "-------------onDPAdRequest: " + map);
            }

            @Override
            public void onDPAdRequestSuccess(Map<String, Object> map) {
                Log.d("ccc", "-------------onDPAdRequestSuccess: " + map);
            }

            @Override
            public void onDPAdRequestFail(int code, String msg, Map<String, Object> map) {
                Log.d("ccc", "-------------onDPAdRequestFail: " + map);
            }

            @Override
            public void onDPAdFillFail(Map<String, Object> map) {
                Log.d("ccc", "-------------onDPAdFillFail: " + map);
            }

            @Override
            public void onDPAdShow(Map<String, Object> map) {
                Log.d("ccc", "-------------onDPAdShow: " + map);
            }

            @Override
            public void onDPAdPlayStart(Map<String, Object> map) {
                Log.d("ccc", "-------------onDPAdPlayStart: " + map);
            }

            @Override
            public void onDPAdPlayPause(Map<String, Object> map) {
                Log.d("ccc", "-------------onDPAdPlayPause: " + map);
            }

            @Override
            public void onDPAdPlayContinue(Map<String, Object> map) {
                Log.d("ccc", "-------------onDPAdPlayContinue: " + map);
            }

            @Override
            public void onDPAdPlayComplete(Map<String, Object> map) {
                Log.d("ccc", "-------------onDPAdPlayComplete: " + map);
            }

            @Override
            public void onDPAdClicked(Map<String, Object> map) {
                Log.d("ccc", "-------------onDPAdClicked: " + map);
            }
        });
        mIDPWidget = DPHolder.getInstance().buildDrawWidget(obtain);
        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.fl_containss, mIDPWidget.getFragment()).commit();
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
        LinearLayout line_sure=builder.findViewById(R.id.line_sure);
        line_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigMeonyDialogs.setDismiss();
                AdHotActivity.adhotJump(VideoActivity.this,"1");
            }
        });
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigMeonyDialogs.setDismiss();
                circleProgress.startAnimotor(time_out*1000);
            }
        });
        if (!CommonUtils.isDestory(VideoActivity.this)) {
            bigMeonyDialogs.setShow();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHomePage(Event event) {
        if (event instanceof Event.HotVideoEvent) {
             if (circleProgress!=null){
                 circleProgress.startAnimotor(time_out*1000);
             }
        }
    }

    private PrizeDialog redtipsDialogs;
    private TextView tv_moneys;

    public void initRedtipsDialog() {
        redtipsDialogs = new PrizeDialog(this);
        View builder = redtipsDialogs.builder(R.layout.redtips_dialogs_item);
        tv_moneys = builder.findViewById(R.id.tv_moneys);
    }

    public void redtipsDialog(String moneys) {
        if (!CommonUtils.isDestory(VideoActivity.this)) {
            if (redtipsDialogs != null && tv_moneys != null) {
                tv_moneys.setText(moneys);
                redtipsDialogs.setShow();
                redtipsDialogs.setDismissListen(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (AppSettingUtils.commonYou(VideoActivity.this)){
                            if (hb_num>=first_video){
                                if (hotShowIndexList.contains(String.valueOf(hb_num))){
                                    bigMeonyDialog();
                                }else {
                                    circleProgress.startAnimotor(time_out*1000);
                                }
                            }else {
                                circleProgress.startAnimotor(time_out*1000);
                            }
                        }else {
                            circleProgress.startAnimotor(time_out*1000);
                        }
                    }
                });
                VUiKit.postDelayed(3000,()->{
                    if (!CommonUtils.isDestory(VideoActivity.this)&&redtipsDialogs!=null) {
                        redtipsDialogs.setDismiss();
                    }
                });
            }
        }
    }

    private void initContentPage() {
        KsScene adScene = new KsScene.Builder(1522).build();
        mKsContentPage = KsAdSDK.getLoadManager().loadContentPage(adScene);
    }


    private void showContentPage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, mKsContentPage.getFragment())
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
                Log.d("ContentPage", "页面Resume:" + item);

            }

            @Override
            public void onPagePause(KsContentPage.ContentItem item) {
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

            }

            @Override
            public void onVideoPlayResume(KsContentPage.ContentItem item) {
                Log.d("ContentPage", "视频PlayResume: " + item);

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
            circleProgress.startAnimotor(time_out*1000);
        }
    }

    @Override
    public void getTaskLineSuccess(TaskLineBean data) {
        if (data != null) {
            hb_num=data.getHb_num();
            if (!TextUtils.isEmpty(data.getCash())){
                ((MyApplication) MyApplication.getInstance()).cash=data.getCash();
            }
            ((MyApplication) MyApplication.getInstance()).hb_Nums=hb_num;
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
    private  HotNumsInfoBeans.DownloadConfigBean download_config;
    private List<String> hotShowIndexList=new ArrayList<>();
    private int hb_num;

    @Override
    public void getHotInfoSuccess(HotNumsInfoBeans data) {
        if (data!=null){
            hb_num = data.getHb_num();
            HotNumsInfoBeans.DownloadBean download = data.getDownload();
            download_config = data.getDownload_config();
            if (download!=null){
                is_open = download.getIs_open();
                Constant.IS_OPEN=is_open;
            }
            if (download_config!=null){
                first_video = download_config.getFirst_video();
                video_num = download_config.getVideo_num();
                total = download_config.getTotal();
                String ad_video = download_config.getAd_video();
                hotlevels = download_config.getLevel();
                level_state = download_config.getLevel_state();
                Constant.LEVEL_STATE=level_state;
                Constant.LEVEL=hotlevels;
                if (total>0){
                    for (int i = 0; i < total; i++) {
                        hotShowIndexList.add(String.valueOf(first_video+video_num*i));
                        Log.d("ccc", "-----ddd-----getHotInfoSuccess: "+String.valueOf(first_video+video_num*i));
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
    }

    public void redDialog() {
        if (!CommonUtils.isDestory(this) && redDialog != null) {
            ivOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    showjiliAd();
                    redDialog.setDismiss();
                }
            });
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    redDialog.setDismiss();
                    circleProgress.startAnimotor(time_out*1000);
                }
            });
            if (!CommonUtils.isDestory(this)) {
                redDialog.setShow();
            }
        }
    }
    public void showjiliAd(){
        if (!CommonUtils.isDestory(this)){
            GromoreAdShow.getInstance().showjiliAd(this,1,"video", new GromoreAdShow.OnAdShowCaback() {
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
                public void onRewardedAdClosed(boolean isVideoClick,boolean isCompete) {
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
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}