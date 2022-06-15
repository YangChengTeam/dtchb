package com.yc.jsdps.beans.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bytedance.applog.game.GameReportHelper;
import com.fc.tjcpl.sdk.TJSDK;
import com.lq.lianjibusiness.base_libary.App.GoagalInfo;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.umeng.analytics.MobclickAgent;
import com.yc.jsdps.R;
import com.yc.jsdps.application.MyApplication;
import com.yc.jsdps.base.BaseLazyFragment;
import com.yc.jsdps.beans.activity.AnswerActivity;
import com.yc.jsdps.beans.activity.ExpressActivity;
import com.yc.jsdps.beans.activity.HotActivity;
import com.yc.jsdps.beans.activity.InvationfriendActivity;
import com.yc.jsdps.beans.activity.MainActivity;
import com.yc.jsdps.beans.activity.RedWallActivity;
import com.yc.jsdps.beans.activity.VideoActivity;
import com.yc.jsdps.beans.contact.HomefgContract;
import com.yc.jsdps.beans.module.beans.GameInfoBeans;
import com.yc.jsdps.beans.module.beans.GamedolaBeans;
import com.yc.jsdps.beans.module.beans.NesRedBeans;
import com.yc.jsdps.beans.module.beans.OtherBeans;
import com.yc.jsdps.beans.module.beans.SavaMonyeHotBeans;
import com.yc.jsdps.beans.module.beans.SaveMoneysInfo;
import com.yc.jsdps.beans.present.HomefgPresenter;
import com.yc.jsdps.constants.Constant;
import com.yc.jsdps.dialog.NewRedDialog;
import com.yc.jsdps.dialog.RedDialogThree;
import com.yc.jsdps.dialog.RedDialogTwo;
import com.yc.jsdps.utils.CacheDataUtils;
import com.yc.jsdps.utils.ClickListenName;
import com.yc.jsdps.utils.CommonUtils;
import com.yc.jsdps.utils.SoundPoolUtils;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;

public class HomeFragment extends BaseLazyFragment<HomefgPresenter> implements HomefgContract.View {

    @BindView(R.id.iv_invations)
    ImageView ivInvations;
    @BindView(R.id.tv_titles)
    TextView tvTitles;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.line_moneyJunp)
    LinearLayout lineMoneyJunp;
    @BindView(R.id.line_red)
    LinearLayout lineRed;
    @BindView(R.id.line_lineAnswer)
    LinearLayout lineLineAnswer;
    @BindView(R.id.line_lineredwall)
    LinearLayout lineLineredwall;
    @BindView(R.id.iv_hot)
    ImageView ivHot;
    @BindView(R.id.iv_saveMoney)
    ImageView ivSaveMoney;
    @BindView(R.id.line_lineExpress)
    LinearLayout lineLineExpress;

    @BindView(R.id.line_game)
    LinearLayout lineGame;

    @BindView(R.id.view1)
    View view1;

    private GifDrawable gifDrawable = null;
    private  MainActivity mactivity;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = (View) super.onCreateView(inflater, container, savedInstanceState);
        return (View) rootView;
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initLazyData() {
         mactivity = (MainActivity) getActivity();
        if ("2".equals(Constant.ISJLYQ)){
            String withDraw = CacheDataUtils.getInstance().getWithDrawHome();
            if (TextUtils.isEmpty(withDraw)) {
                CacheDataUtils.getInstance().setWithDrawHome();
                GameReportHelper.onEventRegister("wechat",true);
            }
        }
        // mipmap 中有动态图 timg.gif
        try {
            gifDrawable = new GifDrawable(getActivity().getResources(), R.drawable.zhu);
            gifDrawable.setLoopCount(20000);
            ivSaveMoney.setImageDrawable(gifDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPresenter.getSaveMoneyInfos(CacheDataUtils.getInstance().getUserInfo().getId()+"");
        mPresenter.getGameloadInfo(CacheDataUtils.getInstance().getUserInfo().getId()+"");
        if (Constant.OPEN_EXPRESS==1){
            lineLineExpress.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
        }else {
            lineLineExpress.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
        }
        lineGame.setVisibility(View.GONE);
    }

    @OnClick({R.id.line_moneyJunp, R.id.line_red, R.id.line_lineAnswer, R.id.line_lineredwall,R.id.iv_invations,R.id.iv_hot,R.id.iv_saveMoney,R.id.line_lineExpress,R.id.line_game})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_lineExpress:
                ExpressActivity.expressJump(getActivity());
                break;
            case R.id.iv_saveMoney:
                if (ClickListenName.isFastClick()){
                    MobclickAgent.onEvent(getActivity(), "home_saveMone", "1");//参数二为当前统计的事件ID
                    mPresenter.getSaveMoneyInfos(CacheDataUtils.getInstance().getUserInfo().getId()+"");
                }
                break;
            case R.id.line_moneyJunp:
                MobclickAgent.onEvent(getActivity(), "money_moneyjump", "1");//参数二为当前统计的事件ID
                mactivity.setPositionFg(2);
                break;
            case R.id.line_red:
                if (CommonUtils.isProxyAndDe(getActivity())){
                    ToastUtil.showToast("出现未知错误，请稍后再试");
                    return;
                }
                MobclickAgent.onEvent(getActivity(), "money_video", "1");//参数二为当前统计的事件ID
                VideoActivity.videoJump(getActivity());
                break;
            case R.id.line_lineAnswer:
              if (CommonUtils.isProxyAndDe(getActivity())){
                    ToastUtil.showToast("出现未知错误，请稍后再试");
                    return;
                }
                MobclickAgent.onEvent(getActivity(), "money_answer", "1");//参数二为当前统计的事件ID
                AnswerActivity.answerJump(getActivity());
                break;
            case R.id.line_lineredwall:
                if (CommonUtils.isProxyAndDe(getActivity())){
                    ToastUtil.showToast("出现未知错误，请稍后再试");
                    return;
                }
                MobclickAgent.onEvent(getActivity(), "money_answer", "1");//参数二为当前统计的事件ID
                RedWallActivity.redWallJump(getActivity());
                break;
            case R.id.iv_invations:
                if (CommonUtils.isProxyAndDe(getActivity())){
                    ToastUtil.showToast("出现未知错误，请稍后再试");
                    return;
                }
                MobclickAgent.onEvent(getActivity(), "money_invation", "1");//参数二为当前统计的事件ID
                InvationfriendActivity.invationfriendJump(getActivity());
                break;
            case R.id.iv_hot:
                if (CommonUtils.isProxyAndDe(getActivity())){
                    ToastUtil.showToast("出现未知错误，请稍后再试");
                    return;
                }
                MobclickAgent.onEvent(getActivity(), "money_hot", "1");//参数二为当前统计的事件ID
                HotActivity.adhotJump(getActivity(),"1");
                break;
            case R.id.line_game:
                getGameDialogs();
                break;
        }
    }


    private NewRedDialog newRedDialog;
    public void newRedDialogs() {
        newRedDialog = new NewRedDialog(getActivity());
        View builder = newRedDialog.builder(R.layout.newreds_dialog_item);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        ImageView iv_bg = builder.findViewById(R.id.iv_bg);
        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "yindaored", "1");//参数二为当前统计的事件ID
                if (Constant.OPEN_EXPRESS==1){
                    mactivity.showjiliAd(0,"home");
                }else {
                    mPresenter.getNewRed(CacheDataUtils.getInstance().getUserInfo().getId());
                }
                newRedDialog.setDismiss();
            }
        });
        newRedDialog.setOutCancle(false);
        newRedDialog.setDismissListen(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (rotation!=null&&rotation.isRunning()){
                    rotation.cancel();
                }
                if (scalex!=null&&scalex.isRunning()){
                    scalex.cancel();
                }
                if (scaley!=null&&scaley.isRunning()){
                    scaley.cancel();
                }
                if (scaleSet!=null&&scaleSet.isRunning()){
                    scaleSet.cancel();
                }
            }
        });
        if (!CommonUtils.isDestory(getActivity())) {
            newRedDialog.setShow();
            initAnimotor(iv_bg,iv_open);
        }
    }

    private NewRedDialog getNewRedDialog;
    private   FrameLayout fl_ad_container_money;
    public void getNewRedDialogs(String money) {
        getNewRedDialog = new NewRedDialog(getActivity());
        View builder = getNewRedDialog.builder(R.layout.newred_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_moneys = builder.findViewById(R.id.tv_moneys);
        tv_moneys.setText(money);
        fl_ad_container_money=builder.findViewById(R.id.fl_ad_container_money);
        ImageView iv_bg = builder.findViewById(R.id.iv_bg);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getActivity(), "yindaogo", "1");//参数二为当前统计的事件ID
                mactivity.setPositionFg(2);
                getNewRedDialog.setDismiss();
            }
        });
        getNewRedDialog.setOutCancle(false);
        getNewRedDialog.setDismissListen(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (rotation!=null&&rotation.isRunning()){
                    rotation.cancel();
                }
                if (scalex!=null&&scalex.isRunning()){
                    scalex.cancel();
                }
                if (scaley!=null&&scaley.isRunning()){
                    scaley.cancel();
                }
                if (scaleSet!=null&&scaleSet.isRunning()){
                    scaleSet.cancel();
                }
            }
        });
        if (!CommonUtils.isDestory(getActivity())) {
            getNewRedDialog.setShow();
            initAnimotor(iv_bg,null);
            loadExpressAd(fl_ad_container_money);
        }
    }




    private ObjectAnimator rotation;
    private AnimatorSet scaleSet;
    private ObjectAnimator scalex,scaley;
    public void initAnimotor(ImageView ivbg,ImageView ivOpen) {
        if (ivbg!=null){
            rotation = ObjectAnimator.ofFloat(ivbg, "rotation", 0f, 360f);
            rotation.setRepeatCount(ValueAnimator.INFINITE);
            rotation.setDuration(2000);
            rotation.start();
        }

        if (ivOpen!=null){
            scalex = ObjectAnimator.ofFloat(ivOpen, "scaleX", 0.9f, 1.1f, 0.9f);
            scaley = ObjectAnimator.ofFloat(ivOpen, "scaleY", 0.9f, 1.1f, 0.9f);
            scaley.setRepeatCount(ValueAnimator.INFINITE);
            scalex.setRepeatCount(ValueAnimator.INFINITE);
            scaleSet = new AnimatorSet();
            scaleSet.setDuration(1500);
            scaleSet.playTogether(scalex,scaley);
            scaleSet.start();
        }
    }


    public void setRefresh(OtherBeans otherBeans) {
        if (tvMoney!=null&&otherBeans!=null){
            tvMoney.setText(otherBeans.getCash());
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            tvMoney.setText(((MyApplication) MyApplication.getInstance()).cash);
        }
    }


    //=============活跃值==========================================活跃值====================================================活跃值=======================================================================
    //=============活跃值==========================================活跃值====================================================活跃值======================================================================
    @Override
    public void getSaveMoneyInfosSuccess(SaveMoneysInfo data) {
        if (data!=null){
            int is_newhb = data.getIs_newhb();
            if (is_newhb==0){
                newRedDialogs();
            }else {
                int  yesterday_huoli = data.getYesterday_huoli();
                if (yesterday_huoli>0){
                    saveMoneysDialogs(1,yesterday_huoli);
                }else {
                    saveMoneysDialogs(2,data.getDay_huoli());
                }
            }
        }

    }

    @Override
    public void getNewRedSuccess(NesRedBeans data) {
        if (data!=null){
            if (!TextUtils.isEmpty(data.getCash())){
                ((MyApplication) MyApplication.getInstance()).cash=data.getCash();
                tvMoney.setText(data.getCash());
            }
            getNewRedDialogs(data.getMoney());
        }
    }

    private GameInfoBeans gameInfoBeans;
    @Override
    public void getGameloadInfoSuccess(GamedolaBeans gamedolaBeans) {
        if (gamedolaBeans!=null){
            this.gameInfoBeans=gamedolaBeans.getGame_info();
        }
    }

    @Override
    public void gameloadAddSuccess(GameInfoBeans data) {
        this.gameInfoBeans=data;
    }

    @Override
    public void getGamehotSuccess(GameInfoBeans data) {
        gameInfoBeans=null;
        if (gameLoadDialog!=null){
            gameLoadDialog.setDismiss();
        }
        getGameHotDialogs(data.getHuoli());
    }

    @Override
    public void getHomSaveMoneySuccess(SavaMonyeHotBeans data) {
        saveMoneyTwokDialogs(data.getHuoli());
    }
    private RedDialogThree saveMoneyTwokDialog;
    public void saveMoneyTwokDialogs(int hotNunm) {
        saveMoneyTwokDialog = new RedDialogThree(getActivity());
        View builder = saveMoneyTwokDialog.builder(R.layout.savemoneytwok_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_hotNums = builder.findViewById(R.id.tv_hotNums);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_hotNums.setText(hotNunm+"");
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                saveMoneyTwokDialog.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotActivity.adhotJump(getActivity(),"");
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                saveMoneyTwokDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(getActivity())) {
            saveMoneyTwokDialog.setShow();
        }
    }
    private RedDialogThree saveMoneysDialog;
    public void saveMoneysDialogs(int type,int hotNums) {
        saveMoneysDialog = new RedDialogThree(getActivity());
        View builder = saveMoneysDialog.builder(R.layout.savemoneys_dialog_item);
        LinearLayout line_sure = builder.findViewById(R.id.line_sure);
        LinearLayout line_sureTwo = builder.findViewById(R.id.line_sureTwo);
        LinearLayout line2 = builder.findViewById(R.id.line2);
        LinearLayout line1 = builder.findViewById(R.id.line1);
        LinearLayout line_hotnums = builder.findViewById(R.id.line_hotnums);
        TextView tv_hotNums =builder.findViewById(R.id.tv_hotNums);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_hotNums.setText(hotNums+"");
        if (type==1){
            line1.setVisibility(View.GONE);
            line2.setVisibility(View.VISIBLE);
            line_sure.setVisibility(View.VISIBLE);
            line_sureTwo.setVisibility(View.GONE);
        }else {
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.GONE);
            line_sure.setVisibility(View.GONE);
            line_sureTwo.setVisibility(View.VISIBLE);
        }
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                saveMoneysDialog.setDismiss();
            }
        });
        line_sureTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                saveMoneysDialog.setDismiss();
            }
        });
        line_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getHomSaveMoney(CacheDataUtils.getInstance().getUserInfo().getId()+"");
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                saveMoneysDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(getActivity())) {
            saveMoneysDialog.setShow();
        }
    }

    public void setVideoCallBack(boolean isVideoClick) {
        mPresenter.getNewRed(CacheDataUtils.getInstance().getUserInfo().getId());
    }



    private RedDialogTwo gameLoadDialog;
    public void getGameDialogs() {
        gameLoadDialog = new RedDialogTwo(getActivity());
        View builder = gameLoadDialog.builder(R.layout.gameload_dialog_item);
        TextView tv_go = builder.findViewById(R.id.tv_go);
        TextView tv_getHot = builder.findViewById(R.id.tv_getHot);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameLoadDialog.setDismiss();
            }
        });
        tv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化sdk,TJSDK.init(appId,appSecret,userId)
                //appId，appKey,创建媒体后获取
                //userId：媒体app中的用户id,媒体采用自己的提现系统时，必传，支持数字、字母混合，最长64位
                //TODO Demo使用AndroidID作为userID，开发者对接时请使用自己应用的用户ID。
                TJSDK.init("2867", "a0e06ab63edf8417fab0bda499214d91", CacheDataUtils.getInstance().getUserInfo().getId() + "");
                String oid = GoagalInfo.oaid;
                Log.d("ccc", "========oid: "+oid);
                jumpSDK(oid);
                mPresenter.gameloadAdd(CacheDataUtils.getInstance().getUserInfo().getId()+"");
            }
        });
        tv_getHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameInfoBeans!=null&&gameInfoBeans.getStatus()==0){//已经进入未领取
                    mPresenter.getGamehot(CacheDataUtils.getInstance().getUserInfo().getId()+"");
                }else {
                    ToastUtil.showToastTwo("完成游戏试玩才能领取活跃值");
                }
            }
        });
        if (!CommonUtils.isDestory(getActivity())) {
            gameLoadDialog.setShow();
        }
    }

    private void jumpSDK(String oaid) {
        //进入游戏列表
        TJSDK.show(getActivity(), oaid);
        //直接进入指定id的游戏任务
        //TJSDK.showDetail(this,"",oaid);
    }

    private RedDialogTwo getGameHotDialog;
    public void getGameHotDialogs(int huo) {
        getGameHotDialog = new RedDialogTwo(getActivity());
        View builder = getGameHotDialog.builder(R.layout.gameloadhot_dialog_item);
        TextView tv_hotNums = builder.findViewById(R.id.tv_hotNums);
        tv_hotNums.setText("+"+huo);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        ImageView iv_sure = builder.findViewById(R.id.iv_sure);
        iv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGameHotDialog.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGameHotDialog.setDismiss();
            }
        });

        if (!CommonUtils.isDestory(getActivity())) {
            getGameHotDialog.setShow();
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
        nativeExpressAD=new NativeExpressAD(getActivity(), new ADSize(acceptedWidth, 200), Constant.TXEXPRESS, new NativeExpressAD.NativeExpressADListener() {
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