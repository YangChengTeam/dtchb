package com.yc.qqzz.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.constants.Constant;
import com.yc.qqzz.dialog.SignDialog;
import com.yc.qqzz.dialog.SnatchDialog;
import com.yc.qqzz.homeModule.adapter.CashTaskAdapter;
import com.yc.qqzz.homeModule.bean.CashTaskBeans;
import com.yc.qqzz.homeModule.bean.EmptyBeans;
import com.yc.qqzz.homeModule.contact.CashTaskContract;
import com.yc.qqzz.homeModule.fragment.ExitTintFragment;
import com.yc.qqzz.homeModule.module.bean.DayCashTashBeans;
import com.yc.qqzz.homeModule.module.bean.HomeBeanszq;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.homeModule.present.CashTaskPresenter;
import com.yc.qqzz.service.event.Event;
import com.yc.qqzz.utils.AppSettingUtils;
import com.yc.qqzz.utils.CacheDataUtils;
import com.yc.qqzz.utils.CommonUtils;
import com.yc.qqzz.utils.TimesUtils;
import com.yc.qqzz.widget.ScrollWithRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CashTaskActivity extends BaseActivity<CashTaskPresenter> implements CashTaskContract.View {

    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.line_answer)
    LinearLayout lineAnswer;
    @BindView(R.id.line_withdraw)
    LinearLayout lineWithdraw;
    @BindView(R.id.line_snatchTreasure)
    LinearLayout lineSnatchTreasure;
    @BindView(R.id.tv_daywelfare)
    TextView tvDaywelfare;
    private String videpType;// 1 不翻倍  2每日福利翻倍
    private CashTaskAdapter cashTaskAdapter;
    private int info_id;
    private int taskId;//任务id
    private int dayhb;//是否领取每日福利
    private int dayhbType;//每日福利步骤1 不翻倍 2：翻倍
    private int postionIndex;
    private String downMoeys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_cash_task;
    }

    @Override
    public void initEventAndData() {
        EventBus.getDefault().register(this);
        setFullScreen();
        initRecyclerView();
        initDatas();
    }
    public void initDatas(){
        UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
        mPresenter.cashdown(userInfo.getImei(),userInfo.getGroup_id());
    }

    public static void CashTaskJump(Context context){
        Intent intent=new Intent(context,CashTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.line_answer, R.id.line_withdraw, R.id.line_snatchTreasure,R.id.line_dayWelfare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.line_answer:
                break;
            case R.id.line_withdraw:
                break;
            case R.id.line_snatchTreasure:
                break;
            case  R.id.line_dayWelfare:
                if (dayhb==0){
                    dayhbType=1;
                    videpType="1";
                    showjiliAd();
                }else {
                    ToastUtil.showToast("今日福利已领取");
                }
                break;
        }
    }

    public void initRecyclerView(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        cashTaskAdapter=new CashTaskAdapter(null);
        recyclerView.setAdapter(cashTaskAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        cashTaskAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                postionIndex=position;
                List<DayCashTashBeans.DownListBean> lists = adapter.getData();
                DayCashTashBeans.DownListBean downListBean = lists.get(position);
                taskId=downListBean.getId();
                if (downListBean.getStatus()==0){
                      LoadGameActivity.loadGameJump(CashTaskActivity.this,downListBean,position);
                }else if (downListBean.getStatus()==1){
                    downMoeys=downListBean.getMoney();
                    wxLogin();
                }
            }
        });
    }


    private SignDialog cashrewardDialog;
    public void withDrawDialog(String money, String cashMoney) {
        cashrewardDialog = new SignDialog(this);
        View builder = cashrewardDialog.builder(R.layout.cashreward_item_dialog);
        LinearLayout line_lookVideo=builder.findViewById(R.id.line_lookVideo);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        TextView tvMoneys=builder.findViewById(R.id.tv_moneys);
        TextView tv_cashMoney=builder.findViewById(R.id.tv_cashMoney);
        tvMoneys.setText(money+"元");
        tv_cashMoney.setText(cashMoney);
        line_lookVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videpType="2";
                showjiliAd();
                cashrewardDialog.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashrewardDialog.setDismiss();
            }
        });
        cashrewardDialog.setShow();
    }

    private SignDialog cashrewardDialogTwo;
    public void withDrawDialogTwo(String money) {
        cashrewardDialogTwo = new SignDialog(this);
        View builder = cashrewardDialogTwo.builder(R.layout.cashreward_itemtwo_dialog);
        TextView tvSure=builder.findViewById(R.id.tv_sure);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        TextView tvMoneys=builder.findViewById(R.id.tv_moneys);
        tvMoneys.setText(money+"元");
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashrewardDialogTwo.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashrewardDialogTwo.setDismiss();
            }
        });
        cashrewardDialogTwo.setShow();
    }

    @Override
    public void getCashdownSuccess(DayCashTashBeans data) {
        if (data!=null){
            dayhb = data.getDayhb();
            if (dayhb==1){
                tvDaywelfare.setText("每日福利（1/1）");
            }else {
                tvDaywelfare.setText("每日福利（0/1）");
            }
            List<DayCashTashBeans.DownListBean> down_list = data.getDown_list();
            cashTaskAdapter.setNewData(down_list);
            cashTaskAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDayhbSuccess(CashTaskBeans data) {
        tvDaywelfare.setText("每日福利（1/1）");
        dayhb=1;
        if (data!=null){
            info_id=data.getInfo_id();
            if (dayhbType==1){
                withDrawDialog(data.getMoney(),data.getCash());
            }else {
                withDrawDialogTwo(data.getMoney());
            }
        }
    }

    @Override
    public void getOutcashdownSuccess(EmptyBeans data) {
        initDatas();
        cashSuccessDialogs();
    }

    private SnatchDialog cashSuccessDialog;
    public void cashSuccessDialogs() {
        cashSuccessDialog = new SnatchDialog(this);
        View builder = cashSuccessDialog.builder(R.layout.cashsuccess_item_dialog);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashSuccessDialog.setDismiss();
            }
        });
        cashSuccessDialog.setShow();
    }
    private SnatchDialog cashSureDialog;
    private void showCacheDialog(String wx_openid) {
        cashSureDialog=new SnatchDialog(this);
        View builder = cashSureDialog.builder(R.layout.cashsure_dialog_item);
        TextView tv_moneys=builder.findViewById(R.id.tv_moneys);
        TextView tv_sure=builder.findViewById(R.id.tv_sure);
        tv_moneys.setText(downMoeys+"元");
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashSureDialog.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
                mPresenter.getOutcashdown(userInfo.getImei(),userInfo.getGroup_id(),taskId,wx_openid);
                cashSureDialog.setDismiss();
            }
        });
        cashSureDialog.setShow();
    }

    private void wxLogin() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN,null);
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
            String  name = map.get("name");
            String  profile_image_url = map.get("profile_image_url");
            if (!TextUtils.isEmpty(wx_openid)) {
                showCacheDialog(wx_openid);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            ToastUtil.showToast("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            ToastUtil.showToast("授权取消");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // QQ授权回调需要配置这里
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    private void showjiliAd(){
        if ("1".equals(AppSettingUtils.getVideoType())){//先头条
            showVideo();
        }else {
            showTx();
        }
    }
    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        isLoadAdSuccess="1";
        loadVideo();
        adPlatformSDK.showRewardVideoAd();
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId()+"");
    }

    private String isLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private int videoCounts;
    private void loadVideo(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_dazhuangpan",new AdCallback() {
            @Override
            public void onDismissed() {
                if ("2".equals(videpType)){
                    dayhbType=2;
                    UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
                    mPresenter.getDayhb(userInfo.getImei(),userInfo.getGroup_id(),String.valueOf(info_id));
                }else if ("1".equals(videpType)){
                    UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
                    mPresenter.getDayhb(userInfo.getImei(),userInfo.getGroup_id(),"");
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
                        if (!CommonUtils.isDestory(CashTaskActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }
                }
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {
                isLoadAdSuccess="3";
                if (!CommonUtils.isDestory(CashTaskActivity.this)){
                    videoCounts=1;
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
            if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                if (!CommonUtils.isDestory(CashTaskActivity.this)) {
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
                        if (!CommonUtils.isDestory(CashTaskActivity.this)) {
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
                            .showAD(CashTaskActivity.this);
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
                if (!CommonUtils.isDestory(CashTaskActivity.this)){
                    videoCounts=1;
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

            }

            @Override
            public void onClose() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
                if ("2".equals(videpType)){
                    dayhbType=2;
                    UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
                    mPresenter.getDayhb(userInfo.getImei(),userInfo.getGroup_id(),String.valueOf(info_id));
                }else if ("1".equals(videpType)){
                    UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
                    mPresenter.getDayhb(userInfo.getImei(),userInfo.getGroup_id(),"");
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
                        if (!CommonUtils.isDestory(CashTaskActivity.this)) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHomePage(Event event) {
        if (event instanceof Event.LoadApkEvent) {
            initDatas();
        }
    }

}