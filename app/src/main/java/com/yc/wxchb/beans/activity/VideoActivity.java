package com.yc.wxchb.beans.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.yc.wxchb.R;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.contact.VideoContract;
import com.yc.wxchb.beans.present.VideoPresenter;
import com.yc.wxchb.dialog.PrizeDialog;
import com.yc.wxchb.dialog.SignDialog;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.video.DPHolder;
import java.util.List;
import java.util.Map;


public class VideoActivity extends BaseActivity<VideoPresenter> implements VideoContract.View {

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
        //circlecountView.initAni();
        // initDrawWidget();

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
    public void jiasuDialog(){
        jiasuDialogs = new SignDialog(this);
        View builder = jiasuDialogs.builder(R.layout.jisu_dialogs_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        ImageView ivCode=builder.findViewById(R.id.iv_code);
        TextView tvs=builder.findViewById(R.id.tvs);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jiasuDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(VideoActivity.this)){
            jiasuDialogs.setShow();
        }
    }
    private SnatchDialog bigMeonyDialogs;
    public void bigMeonyDialog(){
        bigMeonyDialogs = new SnatchDialog(this);
        View builder = bigMeonyDialogs.builder(R.layout.bigmoney_dialogs_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bigMeonyDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(VideoActivity.this)){
            bigMeonyDialogs.setShow();
        }
    }

    private PrizeDialog redtipsDialogs;
    public void redtipsDialog(){
        redtipsDialogs = new PrizeDialog(this);
        View builder = redtipsDialogs.builder(R.layout.redtips_dialogs_item);
        if (!CommonUtils.isDestory(VideoActivity.this)){
            redtipsDialogs.setShow();
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
}