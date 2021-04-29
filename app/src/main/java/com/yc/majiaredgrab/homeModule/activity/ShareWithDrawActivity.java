package com.yc.majiaredgrab.homeModule.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.dialog.NesLoginDialog;
import com.yc.majiaredgrab.dialog.SignDialog;
import com.yc.majiaredgrab.dialog.SnatchDialog;
import com.yc.majiaredgrab.homeModule.adapter.ShareExchangeAdapter;
import com.yc.majiaredgrab.homeModule.adapter.ShareWithDrawAdapter;
import com.yc.majiaredgrab.homeModule.contact.ShareWithDrawContact;
import com.yc.majiaredgrab.homeModule.fragment.ExitTintFragment;
import com.yc.majiaredgrab.homeModule.module.bean.CashBeans;
import com.yc.majiaredgrab.homeModule.module.bean.InvationsShareBeans;
import com.yc.majiaredgrab.homeModule.module.bean.ShareWithExChangeBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UserInfo;
import com.yc.majiaredgrab.homeModule.present.ShareWithDrawPresenter;
import com.yc.majiaredgrab.utils.CacheDataUtils;
import com.yc.majiaredgrab.utils.CommonUtils;
import com.yc.majiaredgrab.utils.SoundPoolUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ShareWithDrawActivity extends BaseActivity<ShareWithDrawPresenter> implements ShareWithDrawContact.View {
    @BindView(R.id.tv_invation_people)
    TextView tvInvationPeople;
    @BindView(R.id.withDraw_recyclerView)
    RecyclerView withDrawRecyclerView;
    @BindView(R.id.exchange_recyclerView)
    RecyclerView exchangeRecyclerView;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    private ShareWithDrawAdapter shareWithDrawAdapter;
    private ShareExchangeAdapter shareExchangeAdapter;
    private String code;
    private int isType;//0 升级券 1 提现不审核  2提现审核
    private String exchangeId;
    private String exchangeLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_share_with_draw2;
    }

    @Override
    public void initEventAndData() {
        code = getIntent().getStringExtra("code");
        isType=0;
        mPresenter.getExchangeInfoData(CacheDataUtils.getInstance().getUserInfo().getId(),code);
        setFullScreen();
        initRecyclerView();
        initTipsDialogs();
    }

    @Override
    public void initInject() {
          getActivityComponent().inject(this);
    }


    @OnClick({R.id.iv_back, R.id.tv_sure,R.id.tv_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_rule:
                showRuleDialog();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sure:
                List<InvationsShareBeans.InviteListBean> data=null;
                if (isType==0){//升级券
                    data = shareExchangeAdapter.getData();
                }else {
                    data = shareWithDrawAdapter.getData();
                }
                if (data!=null){
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).isSelect()){
                            InvationsShareBeans.InviteListBean inviteListBean = data.get(i);
                            int exchange_numss = inviteListBean.getExchange_num();
                            String cash_exchange = inviteListBean.getLevel_exchange();
                            if (TextUtils.isEmpty(cash_exchange)||!cash_exchange.contains(",")){
                                ToastUtil.showToast("未找到等级范围");
                                return;
                            }else {
                                String[] split = cash_exchange.split(",");
                                if (split.length>1){
                                    String lowLevel=split[0];
                                    String heLevel=split[1];
                                    if (user_level<Integer.parseInt(lowLevel)){
                                        String tips=lowLevel+"级可兑换";
                                        showDialogs(1,tips);
                                        return;
                                    }else if (user_level>Integer.parseInt(heLevel)){
                                        showDialogs(1,"当前等级过高，请兑换其他奖品");
                                        return;
                                    }else {
                                        if (this.exchange_num <exchange_numss){
                                            String tips="邀请人数不足，前往邀请";
                                            showDialogs(2,tips);
                                            return;
                                        }else {
                                            if (isType==0){//升级券
                                                if (!TextUtils.isEmpty(exchangeId)){
                                                    mPresenter.exchange(CacheDataUtils.getInstance().getUserInfo().getId(),exchangeId,"0");
                                                }else {
                                                    ToastUtil.showToast("请选择兑换奖励");
                                                }
                                            }else {//提现
                                                if (TextUtils.isEmpty(tx_wxid)){
                                                    String tips="微信提现需要绑定微信";
                                                    showDialogs(3,tips);
                                                }else {
                                                    showCacheDialog();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                break;
        }
    }
    private SignDialog snatchDialog;
    private void showRuleDialog() {
        snatchDialog = new SignDialog(this);
        View builder = snatchDialog.builder(R.layout.invation_rule_two_item);
        LinearLayout lin_tixian=builder.findViewById(R.id.lin_tixian);
        lin_tixian.setVisibility(View.VISIBLE);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snatchDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(ShareWithDrawActivity.this)) {
            snatchDialog.setShow();
        }
    }



    private void showDialogsTwo() {
        CacheDataUtils.getInstance().setLevel("1");
        SnatchDialog snatchDialog = new SnatchDialog(this);
        View builder = snatchDialog.builder(R.layout.upgrade_item);
        TextView tv_know_btn = builder.findViewById(R.id.tv_know_btn);
        TextView tv_know_btnTwo = builder.findViewById(R.id.tv_know_btnTwo);
        TextView tv_jishu = builder.findViewById(R.id.tv_jishu);
        TextView tv_des=builder.findViewById(R.id.tv_des);
        tv_know_btn.setVisibility(View.GONE);
        tv_know_btnTwo.setVisibility(View.VISIBLE);
        tv_des.setVisibility(View.GONE);
        tv_jishu.setText(exchangeLevel);
        tv_know_btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snatchDialog.setDismiss();
                finish();
            }
        });

        if (!CommonUtils.isDestory(ShareWithDrawActivity.this)) {
            snatchDialog.setShow();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                mPresenter.exchange(CacheDataUtils.getInstance().getUserInfo().getId(),exchangeId,"1");
            }
        });
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // QQ授权回调需要配置这里
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    public static void ShareWithDrawJump(Context context,String code){
        Intent intent=new Intent(context,ShareWithDrawActivity.class);
        intent.putExtra("code",code);
        context.startActivity(intent);
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        withDrawRecyclerView.setLayoutManager(gridLayoutManager);
        shareWithDrawAdapter = new ShareWithDrawAdapter(null);
        withDrawRecyclerView.setAdapter(shareWithDrawAdapter);
        shareWithDrawAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                exchangeId=null;
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                if (isType==0){
                    if (shareExchangeAdapter!=null){
                        List<InvationsShareBeans.InviteListBean> data = shareExchangeAdapter.getData();
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setSelect(false);
                        }
                        shareExchangeAdapter.notifyDataSetChanged();
                    }
                }
                List<InvationsShareBeans.InviteListBean> lists = shareWithDrawAdapter.getData();
                for (int i = 0; i < lists.size(); i++) {
                    if (position == i) {
                        exchangeId=String.valueOf(lists.get(i).getId());
                        lists.get(i).setSelect(true);
                        String cash_exchange = lists.get(i).getCash_exchange();
                        if ("0.3".equals(cash_exchange)||"1".equals(cash_exchange)||"1.0".equals(cash_exchange)||"1.00".equals(cash_exchange)){//不需要审核
                            isType=1;
                        }else {
                            isType=2;
                        }
                    } else {
                        lists.get(i).setSelect(false);
                    }
                }
                shareWithDrawAdapter.notifyDataSetChanged();
            }
        });

        GridLayoutManager gridLayoutManagerTwo = new GridLayoutManager(this, 3);
        exchangeRecyclerView.setLayoutManager(gridLayoutManagerTwo);
        shareExchangeAdapter = new ShareExchangeAdapter(null);
        exchangeRecyclerView.setAdapter(shareExchangeAdapter);
        shareExchangeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                exchangeId=null;
                exchangeLevel=null;
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                if (isType==1){
                    if (shareWithDrawAdapter!=null){
                        List<InvationsShareBeans.InviteListBean> data = shareWithDrawAdapter.getData();
                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setSelect(false);
                        }
                        shareWithDrawAdapter.notifyDataSetChanged();
                    }
                }
                isType=0;
                List<InvationsShareBeans.InviteListBean> lists = shareExchangeAdapter.getData();
                for (int i = 0; i < lists.size(); i++) {
                    if (position == i) {
                        exchangeId=String.valueOf(lists.get(i).getId());
                        exchangeLevel=String.valueOf(lists.get(i).getLevel_upgrade());
                        lists.get(i).setSelect(true);
                    } else {
                        lists.get(i).setSelect(false);
                    }
                }
                shareExchangeAdapter.notifyDataSetChanged();
            }
        });
    }
    private  int exchange_num;
    private  String tx_wxid;
    private    int user_level;
    @Override
    public void getExchangeInfoDataSuccess(InvationsShareBeans data) {
        exchange_num = data.getExchange_num();
        tvInvationPeople.setText(exchange_num+"");
        user_level = data.getUser_level();
        tx_wxid = data.getTx_wxid();
        List<InvationsShareBeans.InviteListBean> invite_list = data.getInvite_list();
        List<InvationsShareBeans.InviteListBean> withDrawList = deepCopy(invite_list);

        for (int i = 0; i < withDrawList.size(); i++) {
            withDrawList.get(i).setSelect(false);
        }
        for (int i = 0; i < invite_list.size(); i++) {
            if (i==0){
                invite_list.get(i).setSelect(true);
                exchangeId=String.valueOf(invite_list.get(i).getId());
            }else {
                invite_list.get(i).setSelect(false);
            }
        }

        if (invite_list!=null){
            shareExchangeAdapter.setNewData(invite_list);
            shareExchangeAdapter.notifyDataSetChanged();
            shareWithDrawAdapter.setNewData(withDrawList);
            shareWithDrawAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void exchangeSuccess(ShareWithExChangeBeans data) {
        if (isType==0){
            if (!TextUtils.isEmpty(exchangeLevel)){
                showDialogsTwo();
            }
        }else if (isType==1){//不需要审核
            initWithDrawSuccess();
        }else {
            initWithDrawSuccess();
        }
    }

    @Override
    public void weixinBindCashSuccess(CashBeans data) {
        tx_wxid=data.getTx_id();
        showCacheDialog();
    }


    private NesLoginDialog withDraw;
    public void initWithDrawSuccess(){
        withDraw = new NesLoginDialog(this);
        View builder = withDraw.builder(R.layout.withdraw_dialog_share_success);
        TextView tv_moneys=builder.findViewById(R.id.tv_moneys);
        TextView tv_des=builder.findViewById(R.id.tv_des);
        if (isType==1){
            tv_des.setText("继续邀请");
            tv_moneys.setText("继续赚钱");
        }else {
            tv_des.setText("提现成功后");
            tv_moneys.setText("工作日24小时内到账");
        }

        TextView tv_goWithDraw=builder.findViewById(R.id.tv_goWithDraw);
        tv_goWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDraw.setDismiss();
                finish();
            }
        });
        withDraw.setOutCancle(false);
        withDraw.setShow();
    }


    /**
     2      * 对集合进行深拷贝
     3      * 注意需要岁泛型类进行序列化（实现serializable）
     4      */
    public List<InvationsShareBeans.InviteListBean> deepCopy(List<InvationsShareBeans.InviteListBean> src) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream outputStream = new ObjectOutputStream(byteOut);
        ) {
            outputStream.writeObject(src);
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
                 ObjectInputStream inputStream = new ObjectInputStream(byteIn);
            ) {
                return (List<InvationsShareBeans.InviteListBean>) inputStream.readObject();
            }
        } catch (Exception e) {

        }
        return Collections.emptyList();
    }


    private FrameLayout fl_ad_container;
    private  SnatchDialog tipsDialog;
    private  TextView tv_sure,tv_des;
    private void initTipsDialogs(){
        tipsDialog = new SnatchDialog(this);
        View builder = tipsDialog.builder(R.layout.share_tips_item);
        tv_des=builder.findViewById(R.id.tv_contents);
        tv_sure=builder.findViewById(R.id.tv_sure);
        fl_ad_container=builder.findViewById(R.id.fl_ad_containerss);
        loadVideo();
    }

    private void showDialogs(int type ,String tips) {
        if (tipsDialog!=null&&tv_sure!=null){
            if (type == 1) {
                tv_des.setText(tips);
                tv_sure.setText("确定");
            }else if (type==3){//绑定微信
                tv_des.setText("微信提现需要绑定微信");
                tv_sure.setText("确定");
            }else {
                tv_des.setText(tips);
                tv_sure.setText("确定");
            }
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type==1){

                    }else if (type==3){
                        wxLogin();
                    }
                    tipsDialog.setDismiss();
                }
            });
            final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
            loadVideo();
            adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId()+"");
            tipsDialog.setDismissListen(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
            isshow = adPlatformSDK.showExpressAd();
            if (!CommonUtils.isDestory(ShareWithDrawActivity.this)){
                tipsDialog.setShow();
            }
        }
    }

    private boolean isshow;
    private void loadVideo(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadExpressAd(this,"ad_share", 300,200,new AdCallback() {
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
                if(!isshow){
                    adPlatformSDK.showExpressAd();
                }
            }
        }, fl_ad_container);
    }
}