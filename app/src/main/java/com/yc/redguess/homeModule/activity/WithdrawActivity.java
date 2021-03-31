package com.yc.redguess.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.constants.Constant;
import com.yc.redguess.dialog.NesLoginDialog;
import com.yc.redguess.homeModule.adapter.DisposeMoneyAdapter;
import com.yc.redguess.homeModule.adapter.DisposeNoticeAdapter;
import com.yc.redguess.homeModule.contact.WithdrawConstact;
import com.yc.redguess.homeModule.fragment.DisposeTintFragment;
import com.yc.redguess.homeModule.fragment.ExitTintFragment;
import com.yc.redguess.homeModule.module.bean.AnswerBeans;
import com.yc.redguess.homeModule.module.bean.CashBeans;
import com.yc.redguess.homeModule.module.bean.TithDrawBeans;
import com.yc.redguess.homeModule.module.bean.UserInfo;
import com.yc.redguess.homeModule.module.bean.WeixinCashBeans;
import com.yc.redguess.homeModule.present.WithdrawPresenter;
import com.yc.redguess.homeModule.widget.TextViewSwitcher;
import com.yc.redguess.homeModule.widget.ToastShowViews;
import com.yc.redguess.service.event.Event;
import com.yc.redguess.utils.AppSettingUtils;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.CommonUtils;
import com.yc.redguess.utils.DisplayUtil;
import com.yc.redguess.utils.SoundPoolUtils;
import com.yc.redguess.utils.ToastUtilsViews;
import com.yc.redguess.utils.VUiKit;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 提现
 */
public class WithdrawActivity extends BaseActivity<WithdrawPresenter> implements WithdrawConstact.View {

    @BindView(R.id.tv_wallet_num)
    TextView tvWalletNum;
    @BindView(R.id.recyclerView_money)
    RecyclerView recyclerViewMoney;
    @BindView(R.id.textViewSwitcher)
    TextViewSwitcher textViewSwitcher;
    @BindView(R.id.ll_wx_pay)
    LinearLayout llWxPay;
    private String tx_id;
    private DisposeMoneyAdapter disposeMoneyAdapter;
    private TithDrawBeans.UserOtherBean user_other;
    private String cashMoney;
    public static WeakReference<WithdrawActivity> instance;
    private FrameLayout fl_ad_containe;
    private int isSign;
    private int isWithdraw;
    private int isNews;//0 第二次提现  1 第一次提现
    private  int userLevel;
    private int is_treasure;//是否夺宝中奖
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void initEventAndData() {
        fl_ad_containe=findViewById(R.id.fl_ad_containe);
        instance=new WeakReference<>(this);
        setTitle("钱包详情");
        initRecyclerView();
        mPresenter.getWithDrawData(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
        loadVideo();
        loadTx();
        loadVideojli();

        String withdraw = CacheDataUtils.getInstance().getWithdraw();
        if (TextUtils.isEmpty(withdraw)){
            mPresenter.getRegUserLog(CacheDataUtils.getInstance().getUserInfo().getId(),"4");
        }
    }




    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewMoney.setLayoutManager(gridLayoutManager);
        disposeMoneyAdapter = new DisposeMoneyAdapter(null);
        recyclerViewMoney.setAdapter(disposeMoneyAdapter);
        disposeMoneyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                List<TithDrawBeans.CashOutBean.OutamountBean> lists = adapter.getData();
                for (int i = 0; i < lists.size(); i++) {
                    if (position == i) {
                        lists.get(i).setSelect(true);
                    } else {
                        lists.get(i).setSelect(false);
                    }
                }
                disposeMoneyAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    public static void WithdrawJump(Context context) {
        Intent intent = new Intent(context, WithdrawActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.tv_dispose_record, R.id.ll_wx_pay})
    public void onClick(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_dispose_record:
              startActivity(new Intent(WithdrawActivity.this, WithdrawRecordActivity.class));
                break;
            case R.id.ll_wx_pay:
                List<TithDrawBeans.CashOutBean.OutamountBean> lists = disposeMoneyAdapter.getData();
                if (lists!=null&&lists.size()>0){
                    int total = lists.get(0).getTotal();
                    if (total>0){
                        isNews=0;
                        if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                            showVideojiLi();
                        }else {
                            showTx();
                        }
                    }else {
                        isNews=1;
                        initMoneyDia();
                    }
                }
                break;
           }
        }

        public void setWith(){
            WithdrawActivity.this.runOnUiThread(() -> {
                VUiKit.postDelayed(900, () -> {
                    initMoneyDia();
                });
            });
        }

        private void  initMoneyDia(){
            List<TithDrawBeans.CashOutBean.OutamountBean> lists = disposeMoneyAdapter.getData();
            String level = "2";
            float money = 0.01f;
            int other_num=0;
            if (user_other!=null&&!TextUtils.isEmpty(user_other.getCash())){
                float userCash = Float.parseFloat(user_other.getCash());
                int selectPosition=-1;

                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).isSelect()) {
                        other_num = lists.get(i).getOther_num();
                        level = lists.get(i).getOut_level();
                        money = Float.parseFloat(lists.get(i).getMoney());
                        if (userCash >= money) {//可提现
                             if (i==1){
                                 if (isWithdraw==0){//当天用户没有提现过一次0.3元
                                     if (userLevel<5){
                                         setDialogs(6, level);
                                     }else {
                                         setDialogs(7, level);
                                     }
                                     return;
                                 }else {
                                     setDialogs(5, level);
                                     return;
                                 }
                             }else if (i==2){
                                 if (isSign==0){//没有签到7天
                                     setDialogs(8, level);
                                 }else {
                                     setDialogs(9, level);
                                 }
                                 return;
                             }
                        }else {
                            setDialogs(2, level);
                            return;
                        }
                    }
                }
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).isSelect()) {
                        selectPosition=i;
                        other_num = lists.get(i).getOther_num();
                        level = lists.get(i).getOut_level();
                        money = Float.parseFloat(lists.get(i).getMoney());
                    }
                    if (selectPosition==-1){
                        if (lists.get(i).getOther_num()>0){
                            String tishi="完成"+lists.get(i).getNum()+"次"+lists.get(i).getMoney()+"元提现才能提现"+money+"元哦!";
                            //  完成9次0.3元提现才能提现100元哦！
                            setDialogs(4, tishi);
                            return;
                        }
                    }
                }

                if (user_other.getLevel() >= Integer.parseInt(level)) {//
                    if (other_num>0){//提现次数
                        cashMoney = String.valueOf(money);
                        if (!TextUtils.isEmpty(tx_id)) {
                            showCacheDialog();
                        } else {//绑定微信
                            setDialogs(3,"");
                        }
                    }else {
                        ToastUtil.showToast("今日提现次数已用完");
                    }
                } else {//等级不够
                    if (isNews==1){
                        mPresenter.getRegUserLog(CacheDataUtils.getInstance().getUserInfo().getId(),"8");
                    }
                    setDialogs(1, level);
                }
            }
        }

    public void setDialogs(int type, String level) {
        DisposeTintFragment disposeTintFragment = new DisposeTintFragment();
        if (type == 1) {
            if (isNews==1){
                disposeTintFragment.setViewStatus("达到" + level + "级可提现,1分钟完成任务试试吧", "去升级");
            }else {
                if (userLevel==2){
                    disposeTintFragment.setViewStatus("等级不足，升级提现", "去升级");
                }else {
                    disposeTintFragment.setViewStatus("达到" + level + "级可提现", "去升级");
                }
            }
        } else if ((type == 2)){
            disposeTintFragment.setViewStatus("余额不足，请达到后再来提现", "确定");
        } else if ((type == 3)){
            disposeTintFragment.setViewStatus("微信提现需要绑定微信", "确定");
        } else if ((type == 4)){//前面的还没有提完
            disposeTintFragment.setViewStatus(level, "去做任务");
        } else if ((type ==5)){//今日已提现
            disposeTintFragment.setViewStatus("今日已提现，请明日再来", "确定");
        }else if ((type ==6)){//达到5级提现，赶快去升级吧
            disposeTintFragment.setViewStatus("达到5级提现，赶快去升级吧", "确定");
        }else if ((type ==7)){//达到5级提现，赶快去升级吧
            if (is_treasure==1){
                disposeTintFragment.setViewStatus("奖金派发中,请注意查看账户余额！", "确定");
            }else {
                disposeTintFragment.setViewStatus("夺宝专属奖励，您还未中奖！", "确定");
            }
        }else if ((type ==8)){//签到专属奖励，请完成7天签到。
            disposeTintFragment.setViewStatus("签到专属奖励，请完成7天签到。", "确定");
        }else if ((type ==9)){//签到专属奖励，请完成7天签到。
            disposeTintFragment.setViewStatus("达到20级提现，赶快去升级吧", "去升级");
        }
        disposeTintFragment.setListenCash(new DisposeTintFragment.OnClickListenCash() {
            @Override
            public void sure() {
                if (type == 1) {
                    if (isNews==1){
                        mPresenter.getRegUserLog(CacheDataUtils.getInstance().getUserInfo().getId(),"9");
                    }
                    MemberActivity.memberJump(WithdrawActivity.this);
                }else if (type == 3){
                    wxLogin();
                }else if (type == 4){
                    MemberActivity.memberJump(WithdrawActivity.this);
                }else if (type == 6||type==9){
                    MemberActivity.memberJump(WithdrawActivity.this);
                }else if (type == 2){
                   Intent intent=new Intent(WithdrawActivity.this,MainActivity.class);
                   startActivity(intent);
                }
            }
        });
        disposeTintFragment.show(getSupportFragmentManager(), "");
    }


    @Override
    public void getWithDrawDataSuccess(TithDrawBeans data) {
        if (data.getCash_out().getTxstatus()==0){
            llWxPay.setVisibility(View.GONE);
        }else {
            if (data.getCash_out().getWxstatus()==0){
                llWxPay.setVisibility(View.GONE);
            }else {
                llWxPay.setVisibility(View.VISIBLE);
            }
        }
        user_other = data.getUser_other();
        isSign = data.getSigned_day();
        isWithdraw = data.getOut_today();
        userLevel = data.getUser_other().getLevel();
        is_treasure = data.getIs_treasure();

        tx_id = data.getUser_other().getTx_wxid();
        List<TithDrawBeans.CashOutBean.OutamountBean> outamount = data.getCash_out().getOutamount();
        for (int i = 0; i < outamount.size(); i++) {
            if (i == 0) {
                outamount.get(i).setSelect(true);
            } else {
                outamount.get(i).setSelect(false);
            }
        }
        disposeMoneyAdapter.setNewData(outamount);
        disposeMoneyAdapter.setCashInfo(data.getUser_other());
        disposeMoneyAdapter.notifyDataSetChanged();

        List<TithDrawBeans.UserRandBean> user_rand = data.getUser_rand();
        List<String> noticeList = new ArrayList<>();
        for (int i = 0; i < user_rand.size(); i++) {
            noticeList.add("玩家" + user_rand.get(i).getNickname() + "提现" + user_rand.get(i).getMoney() + "元");
        }
        DisposeNoticeAdapter disposeNoticeAdapter = new DisposeNoticeAdapter(noticeList);
        textViewSwitcher.setAdapter(disposeNoticeAdapter);
        tvWalletNum.setText("￥" + data.getUser_other().getCash() + "");
    }

    @Override
    public void weixinBindCashSuccess(CashBeans data) {
        tx_id=data.getTx_id();
        showCacheDialog();
    }

    @Override
    public void cashMoneySuccess(WeixinCashBeans data) {
        if (data.getStatus()==0){
            if (!TextUtils.isEmpty(data.getErr_msg())){
                ToastUtil.showToast(data.getErr_msg());
            }else {
                ToastUtil.showToast("提现失败");
            }
        }else {
            mPresenter.getWithDrawData(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
            if (isNews==1){
                EventBus.getDefault().post(new Event.NewsLoginCashEvent());
                initWithDrawSuccess();
            }else {
                ToastUtil.showToast("提现成功");
            }
        }
    }



    private void showCacheDialog() {
        ExitTintFragment exitTintFragment = new ExitTintFragment();
        exitTintFragment.setViewStatus("您是否确认提现？","确定","取消");
        exitTintFragment.show(getSupportFragmentManager(), "");
        exitTintFragment.setExitListener(new ExitTintFragment.OnExitListener() {
            @Override
            public void onExit() {

            }

            @Override
            public void onSure() {
                if (isNews==1){
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideojiLi();
                    }else {
                        showTx();
                    }
                }else {
                    mPresenter.cashMoney(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "wx", cashMoney);
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // QQ授权回调需要配置这里
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                userInfo.setWx_openid(wx_openid);
                CacheDataUtils.getInstance().saveUserInfo(userInfo);
                mPresenter.weixinCash(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "wx", wx_openid,name,profile_image_url);
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


    private void video() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showExpressAd();
    }

    private void loadVideo() {
        int screenWidth = CommonUtils.getScreenWidth(this);
        int w = (int) (screenWidth);
        int h = w * 2 / 3;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(WithdrawActivity.this, w);
        int dph = DisplayUtil.px2dip(WithdrawActivity.this, h);
        adPlatformSDK.loadExpressAd(this, "ad_tixian", dpw, dph, new AdCallback() {
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
                video();
            }
        }, fl_ad_containe);
    }

    private String isLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功

    private void showVideojiLi() {
        isLoadAdSuccess="1";
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId()+"");
        adPlatformSDK.showRewardVideoAd();
        loadVideojli();
    }


    private void loadVideojli(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_tixianjili",new AdCallback() {
            @Override
            public void onDismissed() {
                if (isNews==1){
                    mPresenter.cashMoney(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "wx", cashMoney);
                }else {
                    setWith();
                }
                if (!CommonUtils.isDestory(WithdrawActivity.this)){
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
                        if (!CommonUtils.isDestory(WithdrawActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }
                }
            }

            @Override
            public void onComplete() {
                if (!CommonUtils.isDestory(WithdrawActivity.this)){
                    ToastShowViews.cancleToast();
                }
              //  mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onPresent() {
                isLoadAdSuccess="3";
                if (!CommonUtils.isDestory(WithdrawActivity.this)){
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (withDraw!=null){
            withDraw.setDismiss();
        }
        if (mRewardVideoAD != null) {
            mRewardVideoAD.destroy();
        }
    }

    private  NesLoginDialog withDraw;
    public void initWithDrawSuccess(){
         withDraw = new NesLoginDialog(this);
        View builder = withDraw.builder(R.layout.withdraw_dialog_success);
        TextView tv_moneys=builder.findViewById(R.id.tv_moneys);
        TextView tv_goWithDraw=builder.findViewById(R.id.tv_goWithDraw);
        tv_goWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDraw.setDismiss();
               Intent intent=new Intent(WithdrawActivity.this,MainActivity.class);
               startActivity(intent);
            }
        });
        withDraw.setOutCancle(false);
        withDraw.setShow();
    }


    public void showTx(){
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                if (!CommonUtils.isDestory(WithdrawActivity.this)) {
                    ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                }
            }else {
                showVideojiLi();
            }
        }else {
            VideoAdValidity validity = mRewardVideoAD.checkValidity();
            switch (validity) {
                case SHOWED:
                case OVERDUE:
                    loadTxTwo();
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        if (!CommonUtils.isDestory(WithdrawActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }else {
                        showVideojiLi();
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
                            .showAD(WithdrawActivity.this);
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
        String posId="1081870061070830";
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
                AppSettingUtils.showTxShow("tx_ad_tixianjili");
                if (!CommonUtils.isDestory(WithdrawActivity.this)){
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
                AppSettingUtils.showTxClick("tx_ad_tixianjili");
            }

            @Override
            public void onVideoComplete() {
                if (!CommonUtils.isDestory(WithdrawActivity.this)){
                    ToastShowViews.cancleToast();
                }
            }

            @Override
            public void onClose() {
                if (isNews==1){
                    mPresenter.cashMoney(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "wx", cashMoney);
                }else {
                    setWith();
                }
                if (!CommonUtils.isDestory(WithdrawActivity.this)){
                    ToastShowViews.cancleToast();
                }
            }

            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                if ("1".equals(isTxLoadAdSuccess)){
                    isTxLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideojiLi();
                    }else {
                        if (!CommonUtils.isDestory(WithdrawActivity.this)) {
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