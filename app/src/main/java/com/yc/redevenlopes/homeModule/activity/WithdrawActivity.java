package com.yc.redevenlopes.homeModule.activity;


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
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.NesLoginDialog;
import com.yc.redevenlopes.dialog.SignDialog;
import com.yc.redevenlopes.dialog.SnatchDialog;
import com.yc.redevenlopes.homeModule.adapter.DisposeMoneyAdapter;
import com.yc.redevenlopes.homeModule.adapter.DisposeNoticeAdapter;
import com.yc.redevenlopes.homeModule.contact.WithdrawConstact;
import com.yc.redevenlopes.homeModule.fragment.DisposeTintFragment;
import com.yc.redevenlopes.homeModule.fragment.ExitTintFragment;
import com.yc.redevenlopes.homeModule.module.bean.CashBeans;
import com.yc.redevenlopes.homeModule.module.bean.TithDrawBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.module.bean.WeixinCashBeans;
import com.yc.redevenlopes.homeModule.present.WithdrawPresenter;
import com.yc.redevenlopes.homeModule.widget.TextViewSwitcher;
import com.yc.redevenlopes.homeModule.widget.ToastShowViews;
import com.yc.redevenlopes.service.event.Event;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.DisplayUtil;
import com.yc.redevenlopes.utils.SoundPoolUtils;
import com.yc.redevenlopes.utils.ToastUtilsViews;
import com.yc.redevenlopes.utils.VUiKit;

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

    private int isNews;//0 第二次提现  1 第一次提现

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
                        showVideojiLi();
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
            if (user_other!=null){
                float userCash = Float.parseFloat(user_other.getCash());
                int selectPosition=-1;

                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).isSelect()) {
                        other_num = lists.get(i).getOther_num();
                        level = lists.get(i).getOut_level();
                        money = Float.parseFloat(lists.get(i).getMoney());
                        if (userCash >= money) {//可提现

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
                disposeTintFragment.setViewStatus("达到" + level + "级可提现", "去升级");
            }
        } else if ((type == 2)){
            disposeTintFragment.setViewStatus("红包余额不足", "确定");
        } else if ((type == 3)){
            disposeTintFragment.setViewStatus("微信提现需要绑定微信", "确定");
        } else if ((type == 4)){//前面的还没有提完
            disposeTintFragment.setViewStatus(level, "去做任务");
        }
        disposeTintFragment.setListenCash(new DisposeTintFragment.OnClickListenCash() {
            @Override
            public void sure() {
                if (type == 1) {
                    MemberActivity.memberJump(WithdrawActivity.this);
                }else if (type == 3){
                    wxLogin();
                }else if (type == 4){
                    MemberActivity.memberJump(WithdrawActivity.this);
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
                    showVideojiLi();
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



    private void showVideojiLi() {
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
                Log.d("ccc", "----------onDismissed: ");
                if (isNews==1){
                    mPresenter.cashMoney(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "wx", cashMoney);
                }else {
                    setWith();
                }
                if (!CommonUtils.isDestory(WithdrawActivity.this)){
                    ToastShowViews.getInstance().cancleToast();
                }
            }

            @Override
            public void onNoAd(AdError adError) {

            }

            @Override
            public void onComplete() {
                if (!CommonUtils.isDestory(WithdrawActivity.this)){
                    ToastShowViews.getInstance().cancleToast();
                }
              //  mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onPresent() {
                if (!CommonUtils.isDestory(WithdrawActivity.this)){
                    ToastShowViews.getInstance().showMyToast();
                }
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (withDraw!=null){
            withDraw.setDismiss();
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
                if (RobRedEvenlopesActivity.instances != null && RobRedEvenlopesActivity.instances.get() != null) {
                    WithdrawActivity.instance.get().finish();
                }
                finish();
            }
        });
        withDraw.setOutCancle(false);
        withDraw.setShow();
    }


}