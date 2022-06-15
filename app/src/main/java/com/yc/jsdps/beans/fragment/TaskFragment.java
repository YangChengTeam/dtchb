package com.yc.jsdps.beans.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.yc.jsdps.R;
import com.yc.jsdps.application.MyApplication;
import com.yc.jsdps.base.BaseLazyFragment;
import com.yc.jsdps.beans.activity.MainActivity;
import com.yc.jsdps.beans.activity.RedWallActivity;
import com.yc.jsdps.beans.activity.VideoActivity;
import com.yc.jsdps.beans.adapter.LimitedAdapter;
import com.yc.jsdps.beans.adapter.TaskAdapter;
import com.yc.jsdps.beans.contact.TaskContract;
import com.yc.jsdps.beans.module.beans.LimitedBeans;
import com.yc.jsdps.beans.module.beans.LimitedRedBeans;
import com.yc.jsdps.beans.module.beans.OtherBeans;
import com.yc.jsdps.beans.module.beans.RedTaskBeans;
import com.yc.jsdps.beans.module.beans.TaskLineBean;
import com.yc.jsdps.beans.present.TaskPresenter;
import com.yc.jsdps.constants.Constant;
import com.yc.jsdps.dialog.PrizeDialog;
import com.yc.jsdps.dialog.SignDialog;
import com.yc.jsdps.dialog.SnatchDialog;
import com.yc.jsdps.utils.CacheDataUtils;
import com.yc.jsdps.utils.CommonUtils;
import com.yc.jsdps.utils.VUiKit;
import com.yc.jsdps.utils.ad.GromoreInsetAdShow;
import com.yc.jsdps.utils.adgromore.GromoreAdShowFour;
import com.yc.jsdps.widget.ScrollWithRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskFragment extends BaseLazyFragment<TaskPresenter> implements TaskContract.View {

    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.tv_titles)
    TextView tvTitles;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.line_moneyJunp)
    LinearLayout lineMoneyJunp;
    @BindView(R.id.line_moneyWall)
    LinearLayout lineMoneyWall;

    @BindView(R.id.limited_recyclerView)
    ScrollWithRecyclerView limitedRecyclerView;
    private TaskAdapter taskAdapter;
    private LimitedAdapter limitedAdapter;
    private int limitedId;
    private int taskId;
    private  MainActivity mActivity;
    private int videoType;//1倒计时视频  2限量视频
    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    protected android.view.View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        android.view.View rootView = (android.view.View) super.onCreateView(inflater, container, savedInstanceState);
        return (android.view.View) rootView;
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
        initRecyclerView();
        mPresenter.getLimitedData(CacheDataUtils.getInstance().getUserInfo().getId());
        mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
        mActivity = (MainActivity) getActivity();
        tvMoney.setText(((MyApplication) MyApplication.getInstance()).cash);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
    }

    private int type;
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        taskAdapter = new TaskAdapter(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.line_status:
                        List<RedTaskBeans.HbOnlineTaskBean> data = taskAdapter.getData();
                        taskId = data.get(position).getId();
                        int status = data.get(position).getStatus();
                        if (status==0){
                             VideoActivity.videoJump(getActivity());
                        }else if (status==1){
                            type=data.get(position).getType();
                            if (data.get(position).getType()==0){//账户余额
                                mPresenter.getTaskMoney(CacheDataUtils.getInstance().getUserInfo().getId(),taskId);
                            }else {
                                mPresenter.getTaskMoney(CacheDataUtils.getInstance().getUserInfo().getId(),taskId);
                                mActivity.setPositionFg(2);
                            }
                        }
                        break;
                }
            }
        });

        LinearLayoutManager linearLayoutManagerTwo = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        limitedAdapter = new LimitedAdapter(null);
        limitedRecyclerView.setLayoutManager(linearLayoutManagerTwo);
        limitedRecyclerView.setAdapter(limitedAdapter);
        limitedAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.line_status:
                        List<LimitedBeans> data = limitedAdapter.getData();
                        limitedId = data.get(position).getId();
                        int status = data.get(position).getStatus();
                        if (status==0){
                            showAds(data.get(position).getAd_code()+"");
                        }else if (status==1){
                            withDrawSuccessDialog();
                        }
                        break;
                }
            }
        });
    }


    public void showAdsyuan( ){

    }

    public void showAds(String codes){
        GromoreAdShowFour.getInstance().showjiliAd("", codes,new GromoreAdShowFour.OnAdShowCaback() {
            @Override
            public void onRewardedAdShow() {

            }

            @Override
            public void onRewardedAdShowFail() {
                limitipsDialog();
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
                mPresenter.getLimiteRed(CacheDataUtils.getInstance().getUserInfo().getId(),limitedId);
            }
        });
    }

    private PrizeDialog limitipsDialogs;
    public void limitipsDialog(){
        limitipsDialogs = new PrizeDialog(getActivity());
        View builder = limitipsDialogs.builder(R.layout.limitips_dialogs_item);
        if (!CommonUtils.isDestory(getActivity())){
            limitipsDialogs.setShow();
        }
    }


    @OnClick({R.id.line_moneyJunp,R.id.line_moneyWall})
    public void onViewClicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.line_moneyJunp:
                mActivity.setPositionFg(2);
                break;
            case R.id.line_moneyWall:
                RedWallActivity.redWallJump(getActivity());
                break;
        }
    }

    private boolean isFirst;
    @Override
    public void getLimitedDataSuccess(List<LimitedBeans> data) {
            if (data!=null){
                for (int i = 0; i < data.size(); i++) {
                    if (i==0){
                        int ad_code = data.get(0).getAd_code();
                        if (!isFirst){
                            isFirst=true;
                            GromoreAdShowFour.getInstance().setContexts(getActivity(),ad_code+"");
                        }
                    }
                }
                limitedAdapter.setNewData(data);
            }
    }

    @Override
    public void getRedTaskDataSuccess(RedTaskBeans data) {
        if (data!=null){
            List<RedTaskBeans.HbOnlineTaskBean> hb_online_task = data.getHb_online_task();
            if (hb_online_task!=null){
                List<RedTaskBeans.HbOnlineTaskBean> lists=new ArrayList<>();
                for (int i = 0; i < hb_online_task.size(); i++) {
                    int status = hb_online_task.get(i).getStatus();
                    if (status!=2){
                        lists.add(hb_online_task.get(i));
                    }
                }
                taskAdapter.setNewData(lists);
            }
        }
    }

    @Override
    public void getLimiteRedSuccess(LimitedRedBeans data) {
        mPresenter.getLimitedData(CacheDataUtils.getInstance().getUserInfo().getId());
    }

    @Override
    public void getTaskLineSuccess(TaskLineBean data) {
        mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
        if (data!=null){
            ((MyApplication) MyApplication.getInstance()).hb_Nums=data.getHb_num();
            redtipsDialog(data.getRed_money());
            if (!TextUtils.isEmpty(data.getCash())){
                ((MyApplication) MyApplication.getInstance()).cash=data.getCash();
                tvMoney.setText(data.getCash());
            }
        }
    }

    @Override
    public void getTaskMoneySuccess(TaskLineBean data) {
        mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
        if (data!=null){
            if (type==0){//账户余额
                redtipsDialog(data.getRed_money());
                if (!TextUtils.isEmpty(data.getCash())){
                    ((MyApplication) MyApplication.getInstance()).cash=data.getCash();
                    tvMoney.setText(data.getCash());
                }
            }else {
                mActivity.setPositionFg(2);
            }
        }
    }

    public void setVideoCallBacks(boolean isVideoClick) {
        if (videoType==2){

        }else if (videoType==1){
            mPresenter.getTaskLine(CacheDataUtils.getInstance().getUserInfo().getId());
        }
    }

    private SnatchDialog withDrawSuccessDialogs;
    public void withDrawSuccessDialog(){
        withDrawSuccessDialogs = new SnatchDialog(getActivity());
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
        if (!CommonUtils.isDestory(getActivity())){
            withDrawSuccessDialogs.setShow();
        }
    }
    private SignDialog redtipsDialogs;
    public void redtipsDialog(String moneys) {
        redtipsDialogs = new SignDialog(getActivity());
        //  View builder = redtipsDialogs.builder(R.layout.redtips_dialogs_item);
        View builder = redtipsDialogs.builder(R.layout.redtipstwo_dialogs_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_moneys = builder.findViewById(R.id.tv_moneys);
        ImageView iv_close  = builder.findViewById(R.id.iv_close);
        FrameLayout fl_ad_container_money  = builder.findViewById(R.id.fl_ad_container_money);
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
        if (!TextUtils.isEmpty(moneys)) {
            tv_moneys.setText(moneys);
        }
        if (!CommonUtils.isDestory(getActivity())) {
            loadExpressAd(fl_ad_container_money);
            redtipsDialogs.setShow();
            showInset();
        }
    }

    private void showInset() {
        VUiKit.postDelayed(1300,()->{
            if (!CommonUtils.isDestory(getActivity())) {
                GromoreInsetAdShow.getInstance().showInset(getActivity(), "", new GromoreInsetAdShow.OnInsetAdShowCaback() {
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
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
             tvMoney.setText(((MyApplication) MyApplication.getInstance()).cash);
        }
    }

    public void setRefresh(OtherBeans otherBeans) {
       if (tvMoney!=null){
           tvMoney.setText(((MyApplication) MyApplication.getInstance()).cash);
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