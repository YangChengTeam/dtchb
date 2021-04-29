package com.yc.majiaredgrab.homeModule.activity;


import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.constants.Constant;
import com.yc.majiaredgrab.dialog.SnatchDialog;
import com.yc.majiaredgrab.homeModule.contact.MemberCenterContact;
import com.yc.majiaredgrab.homeModule.fragment.ShareFragment;
import com.yc.majiaredgrab.homeModule.module.bean.OtherBeans;
import com.yc.majiaredgrab.homeModule.module.bean.UserInfo;
import com.yc.majiaredgrab.homeModule.present.MemberCenterPresenter;
import com.yc.majiaredgrab.homeModule.widget.MemberCenterView;
import com.yc.majiaredgrab.homeModule.widget.MemberCenterViewSol;
import com.yc.majiaredgrab.homeModule.widget.ToastShowViews;
import com.yc.majiaredgrab.utils.AppSettingUtils;
import com.yc.majiaredgrab.utils.CacheDataUtils;
import com.yc.majiaredgrab.utils.CommonUtils;
import com.yc.majiaredgrab.utils.SoundPoolUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.lq.lianjibusiness.base_libary.utils.DialogUtils.dialogTipCancel;

/**
 * 会员中心
 */
public class MemberCenterActivity extends BaseActivity<MemberCenterPresenter> implements MemberCenterContact.View {

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.memberCenterView_wallet)
    MemberCenterView memberCenterViewWallet;
    @BindView(R.id.memberCenterView_group)
    MemberCenterView memberCenterViewGroup;
    @BindView(R.id.memberCenterView_person)
    MemberCenterView memberCenterViewPerson;
    @BindView(R.id.memberCenterView_rank)
    MemberCenterView memberCenterViewRank;
    @BindView(R.id.memberCenterView_sound)
    MemberCenterViewSol memberCenterViewSound;
    @BindView(R.id.memberCenterView_version)
    MemberCenterView memberCenterViewVersion;
    @BindView(R.id.tv_userid)
    TextView tvUserid;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_member_center;
    }

    @Override
    public void initEventAndData() {
        money = getIntent().getStringExtra("money");
        if (!TextUtils.isEmpty(money)) {
            memberCenterViewWallet.setContent("￥" + money);
        }
        memberCenterViewPerson.setContent("400人");
        memberCenterViewGroup.setContent(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
        mPresenter.getOtherInfo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", CacheDataUtils.getInstance().getUserInfo().getId() + "");
        loadVideo();
        loadTx();
        initData();
    }

    private void initData() {
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();

        Glide.with(this).load(userInfo.getFace()).apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                .error(R.mipmap.icon_default_image).into(ivAvatar);
        String nickname = userInfo.getNickname();
        if (TextUtils.isEmpty(nickname)) {
            nickname = "游客" + userInfo.getId();
        }
        tvNickname.setText(nickname);
        tvUserid.setText(String.format(getString(R.string.user_id), userInfo.getId()));

        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            memberCenterViewVersion.setContent("V" + packageInfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void memberCenterJump(Context context, String money) {
        Intent intent = new Intent(context, MemberCenterActivity.class);
        intent.putExtra("money", money);
        context.startActivity(intent);
    }


    @OnClick({R.id.memberCenterView_wallet, R.id.memberCenterView_rank, R.id.tv_share_friend, R.id.tv_logout, R.id.memberCenterView_contant, R.id.memberCenterView_help})
    public void onClick(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        super.onClick(view);
        switch (view.getId()) {
            case R.id.memberCenterView_wallet:
                startActivity(new Intent(MemberCenterActivity.this, WalletDetailActivity.class));
                break;
            case R.id.memberCenterView_rank:
                startActivity(new Intent(MemberCenterActivity.this, AllLeaderBoarderActivity.class));
                break;
            case R.id.tv_share_friend:
                ShareFragment shareFragment = new ShareFragment();
                shareFragment.setShareOnclickListen(new ShareFragment.ShareOnclickListen() {
                    @Override
                    public void weixinShare() {
                        shareWechatFriend(MemberCenterActivity.this, "http://m.hncj.com/sjrj/34282.html");
                    }

                    @Override
                    public void weixinCircleShare() {
                        shareQQ(MemberCenterActivity.this, "http://m.hncj.com/sjrj/34282.html");
                    }
                });
                shareFragment.show(getSupportFragmentManager(), "");
                break;
            case R.id.tv_logout:
                ActivityOptions opts = ActivityOptions.makeCustomAnimation(MemberCenterActivity.this,
                        R.anim.abc_fade_in, R.anim.abc_fade_out);

                Intent intent = new Intent(MemberCenterActivity.this, LoginActivity.class);
                startActivity(intent, opts.toBundle());
                finish();

                break;
            case R.id.memberCenterView_contant:
                showOutSign();
                break;
            case R.id.memberCenterView_help:
                HelpQuestionActivity.helpJump(MemberCenterActivity.this);
                break;
        }
    }

    private void showHelpDialog() {
        SnatchDialog snatchDialog = new SnatchDialog(this);
        View builder = snatchDialog.builder(R.layout.contants_dialog);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                snatchDialog.setDismiss();
            }
        });
        snatchDialog.setOutCancle(true);
        snatchDialog.setShow();
    }

    //推出登录
    private void showOutSign() {
        dialogTipCancel("如若遇到问题可以加客服QQ咨询哦！", "是否复制QQ号？", "取消", "复制", this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("1".equals(AppSettingUtils.getVideoType())) {//先头条
                    showVideo();
                } else {
                    showTx();
                }
            }
        }, v -> {

        });
    }

    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }

    /**
     * 直接分享纯文本内容至QQ好友
     *
     * @param mContext
     * @param content
     */
    public void shareQQ(Context mContext, String content) {
        if (isInstalled(MemberCenterActivity.this, "com.tencent.mobileqq")) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, content);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, "您需要安装QQ客户端", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 直接分享文本到微信好友
     *
     * @param context 上下文
     */
    public void shareWechatFriend(Context context, String content) {
        MobclickAgent.onEvent(MemberCenterActivity.this, "membershare");//参数二为当前统计的事件ID
        if (isInstalled(MemberCenterActivity.this, "com.tencent.mm")) {
            Intent intent = new Intent();
            ComponentName cop = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
            intent.setComponent(cop);
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra("android.intent.extra.TEXT", content);
//            intent.putExtra("sms_body", content);
            intent.putExtra("Kdescription", !TextUtils.isEmpty(content) ? content : "");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "您需要安装微信客户端", Toast.LENGTH_LONG).show();
        }
    }


    private ShareAction mShareAction;
    private UMImage mUMImage;
    private MyUMShareListener myUMShareListener;

    private void startShare(SHARE_MEDIA share_media) {
        UMWeb mUMWeb = new UMWeb("http://www.hncj.com/sjrj/34282.html");
        mUMWeb.setTitle(getResources().getString(R.string.app_name));
        mUMWeb.setDescription("进群一起抢红包");
        mUMImage = new UMImage(getApplicationContext(), R.mipmap.redlogo);
        mUMWeb.setThumb(mUMImage);
        mShareAction = new ShareAction(this);
        myUMShareListener = new MyUMShareListener();
        mShareAction.withMedia(mUMWeb);
        mShareAction.setPlatform(share_media);
        mShareAction.setCallback(myUMShareListener);
        mShareAction.share(); //share不带UM的面板  open带面板 如果带面板 就要setDisplayList
    }

    @Override
    protected void onDestroy() {
        if (mShareAction != null) {
            mShareAction = null;
        }
        if (mUMImage != null) {
            mUMImage = null;
        }
        if (myUMShareListener != null) {
            myUMShareListener = null;
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getOtherInfoSuccess(OtherBeans data) {
        memberCenterViewWallet.setContent("￥" + data.getCash());
    }

    public class MyUMShareListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    }


    public void showTx() {
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                copyQQ();
            } else {
                showVideo();
            }
        } else {
            VideoAdValidity validity = mRewardVideoAD.checkValidity();
            switch (validity) {
                case SHOWED:
                case OVERDUE:
                    loadTxTwo();
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                        copyQQ();
                    } else {
                        showVideo();
                    }
                    return;
                // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                case NONE_CACHE:
                    //  showToast("广告素材未缓存成功！");
//            return;
                case VALID:
                    // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                    isTxLoadAdSuccess = "1";
                    mRewardVideoAD
                            .showAD(MemberCenterActivity.this);
                    // 展示广告
                    break;
            }
        }

    }

    public void loadTxTwo() {
        mIsLoaded = false;
        loadTx();
    }


    private ExpressRewardVideoAD mRewardVideoAD;
    private boolean mIsLoaded;
    private boolean mIsCached;
    private String isTxLoadAdSuccess = "0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功

    public void loadTx() {
        mRewardVideoAD = new ExpressRewardVideoAD(this, Constant.TXRVIDEO, new ExpressRewardVideoAdListener() {
            @Override
            public void onAdLoaded() {
                mIsLoaded = true;
                isTxLoadAdSuccess = "3";
            }

            @Override
            public void onVideoCached() {
                // 在视频缓存完成之后再进行广告展示，以保证用户体验
                mIsCached = true;
                Log.i("ccc", "onVideoCached: ");
            }

            @Override
            public void onShow() {
                isTxLoadAdSuccess = "3";
                AppSettingUtils.showTxShow("tx_ad_dazhuangpan");
                if (!CommonUtils.isDestory(MemberCenterActivity.this)) {
                    videoCounts = 1;
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
                AppSettingUtils.showTxClick("tx_ad_dazhuangpan");
            }

            @Override
            public void onVideoComplete() {
                if (mRewardVideoAD.hasShown()) {
                    loadTxTwo();
                }
                if (!CommonUtils.isDestory(MemberCenterActivity.this)) {
                    ToastShowViews.cancleToastTwo();
                }
            }

            @Override
            public void onClose() {
                copyQQ();
                if (mRewardVideoAD.hasShown()) {
                    loadTxTwo();
                }
                if (!CommonUtils.isDestory(MemberCenterActivity.this)) {
                    ToastShowViews.cancleToastTwo();
                }
            }

            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                if ("1".equals(isTxLoadAdSuccess)) {
                    isTxLoadAdSuccess = "2";
                    //失败了播放腾讯的
                    if ("2".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                        showVideo();
                    } else {
                        copyQQ();
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


    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        isLoadAdSuccess = "1";
        loadVideo();
        adPlatformSDK.showRewardVideoAd();
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
    }

    private String isLoadAdSuccess = "0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private int videoCounts;

    private void loadVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_dazhuangpan", new AdCallback() {
            @Override
            public void onDismissed() {
                copyQQ();
                if (!CommonUtils.isDestory(MemberCenterActivity.this)) {
                    ToastShowViews.cancleToastTwo();
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                if ("1".equals(isLoadAdSuccess)) {
                    isLoadAdSuccess = "2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())) {//先头条
                        showTx();
                    } else {
                        copyQQ();
                    }
                }
            }

            @Override
            public void onComplete() {
                if (!CommonUtils.isDestory(MemberCenterActivity.this)) {
                    ToastShowViews.cancleToastTwo();
                }
            }

            @Override
            public void onPresent() {
                isLoadAdSuccess = "3";
                if (!CommonUtils.isDestory(MemberCenterActivity.this)) {
                    videoCounts = 1;
                }
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                isLoadAdSuccess = "3";
            }
        });
    }

    public void copyQQ() {
        try {
            tvQq.setText("963486383");
            ClipboardManager mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText(null, "963486383");
            mClipboardManager.setPrimaryClip(mClipData);
            ToastUtil.showToast("复制QQ成功！");
        } catch (Exception e) {
            tvQq.setText("963486383");
        }
    }
}