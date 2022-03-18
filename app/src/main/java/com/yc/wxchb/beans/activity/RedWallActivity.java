package com.yc.wxchb.beans.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
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
import com.yc.wxchb.dialog.PrizeDialog;
import com.yc.wxchb.dialog.SignDialog;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.SoundPoolUtils;
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
                                 wxLogin();
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

            }

            @Override
            public void setVideoCallBacks() {

            }

            @Override
            public void onRewardedAdClosed(boolean isVideoClick, boolean isCompeter, String adNetworkRitId) {
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

    private PrizeDialog redtipsDialogs;
    public void redtipsDialog(int moneys) {
        redtipsDialogs = new PrizeDialog(this);
        View builder = redtipsDialogs.builder(R.layout.wallgold_dialogs_item);
        TextView tv_moneys = builder.findViewById(R.id.tv_moneys);
        tv_moneys.setText("+"+moneys+"");
        if (!CommonUtils.isDestory(this)) {
            showInset();
            redtipsDialogs.setShow();
        }
    }

    private void showInset() {
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
                       GromoreAdShowThree.getInstance().setContexts(RedWallActivity.this,codeId);
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
}