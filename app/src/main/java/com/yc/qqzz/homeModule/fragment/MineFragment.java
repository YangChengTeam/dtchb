package com.yc.qqzz.homeModule.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseLazyFragment;
import com.yc.qqzz.constants.Constant;
import com.yc.qqzz.homeModule.activity.AboutActivity;
import com.yc.qqzz.homeModule.activity.AllLeaderBoarderActivity;
import com.yc.qqzz.homeModule.activity.HelpQuestionActivity;
import com.yc.qqzz.homeModule.activity.LoginActivity;

import com.yc.qqzz.homeModule.contact.MineContract;
import com.yc.qqzz.homeModule.module.bean.OtherBeanszq;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.homeModule.present.MinePresenter;
import com.yc.qqzz.utils.AppSettingUtils;
import com.yc.qqzz.utils.CacheDataUtils;
import com.yc.qqzz.utils.CommonUtils;
import com.yc.qqzz.utils.SoundPoolUtils;
import com.yc.qqzz.widget.MemberCenterViewSolzq;
import com.yc.qqzz.widget.MemberCenterViewzq;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.lq.lianjibusiness.base_libary.utils.DialogUtils.dialogTipCancel;
import static com.umeng.socialize.utils.ContextUtil.getPackageName;

public class MineFragment extends BaseLazyFragment<MinePresenter> implements MineContract.View {
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.memberCenterView_wallet)
    MemberCenterViewzq memberCenterViewWallet;
    @BindView(R.id.memberCenterView_group)
    MemberCenterViewzq memberCenterViewGroup;
    @BindView(R.id.memberCenterView_person)
    MemberCenterViewzq memberCenterViewPerson;
    @BindView(R.id.memberCenterView_rank)
    MemberCenterViewzq memberCenterViewRank;
    @BindView(R.id.memberCenterView_sound)
    MemberCenterViewSolzq memberCenterViewSound;
    @BindView(R.id.memberCenterView_version)
    MemberCenterViewzq memberCenterViewVersion;
    @BindView(R.id.tv_userid)
    TextView tvUserid;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    private String money;
    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
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
        memberCenterViewPerson.setContent("400人");
        memberCenterViewGroup.setContent(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
        mPresenter.getOtherInfo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", CacheDataUtils.getInstance().getUserInfo().getId() + "");
        loadVideo();
        loadTx();
        initData();
    }
    private void initData() {
        UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();

        Glide.with(this).load(userInfo.getFace()).apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                .error(R.mipmap.icon_default_image).into(ivAvatar);
        String nickname = userInfo.getNickname();
        if (TextUtils.isEmpty(nickname)) {
            nickname = "游客" + userInfo.getId();
        }
        tvNickname.setText(nickname);
        tvUserid.setText(String.format(getString(R.string.user_id), userInfo.getId()));

        PackageManager packageManager = getActivity().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            memberCenterViewVersion.setContent("V" + packageInfo.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }



    @OnClick({R.id.memberCenterView_wallet, R.id.memberCenterView_rank, R.id.tv_share_friend, R.id.tv_logout, R.id.memberCenterView_contant, R.id.memberCenterView_help,R.id.rela_about})
    public void onClick(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        super.onClick(view);
        switch (view.getId()) {
            case R.id.memberCenterView_wallet:
                // startActivity(new Intent(PersonInfoActivity.this, WalletDetailActivity.class));
                break;
            case R.id.memberCenterView_rank:
                AllLeaderBoarderActivity.AllLeaderJump(getActivity());
                break;
            case R.id.tv_share_friend:
                ShareFragmentzq shareFragment = new ShareFragmentzq();
                shareFragment.setShareOnclickListen(new ShareFragmentzq.ShareOnclickListen() {
                    @Override
                    public void weixinShare() {
                        shareWechatFriend(getActivity(), "http://m.k1u.com/az/2349.html");
                    }

                    @Override
                    public void weixinCircleShare() {
                        shareQQ(getActivity(), "http://m.k1u.com/az/2349.html");
                    }
                });
                shareFragment.show(getActivity().getSupportFragmentManager(), "");
                break;
            case R.id.tv_logout:
                ActivityOptions opts = ActivityOptions.makeCustomAnimation(getActivity(),
                        R.anim.abc_fade_in, R.anim.abc_fade_out);

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent, opts.toBundle());

                break;
            case R.id.memberCenterView_contant:
                showOutSign();
                break;
            case R.id.memberCenterView_help:
                HelpQuestionActivity.helpJump(getActivity());
                break;
            case R.id.rela_about:
                AboutActivity.aboutJump(getActivity());
                break;
        }
    }

    //推出登录
    private void showOutSign() {
        dialogTipCancel("如若遇到问题可以加客服QQ咨询哦！", "是否复制QQ号？", "取消", "复制", getActivity(), new View.OnClickListener() {
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
        if (isInstalled(getActivity(), "com.tencent.mobileqq")) {
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
        MobclickAgent.onEvent(getActivity(), "membershare");//参数二为当前统计的事件ID
        if (isInstalled(getActivity(), "com.tencent.mm")) {
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
        UMWeb mUMWeb = new UMWeb("http://m.k1u.com/az/2349.html");
        mUMWeb.setTitle(getResources().getString(R.string.app_name));
        mUMWeb.setDescription("进群一起抢红包");
        mUMImage = new UMImage(getActivity().getApplicationContext(), R.drawable.ic_launcher);
        mUMWeb.setThumb(mUMImage);
        mShareAction = new ShareAction(getActivity());
        myUMShareListener = new MyUMShareListener();
        mShareAction.withMedia(mUMWeb);
        mShareAction.setPlatform(share_media);
        mShareAction.setCallback(myUMShareListener);
        mShareAction.share(); //share不带UM的面板  open带面板 如果带面板 就要setDisplayList
    }

    @Override
    public void onDestroyView() {
        if (mShareAction != null) {
            mShareAction = null;
        }
        if (mUMImage != null) {
            mUMImage = null;
        }
        if (myUMShareListener != null) {
            myUMShareListener = null;
        }
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void getOtherInfoSuccess(OtherBeanszq data) {
        memberCenterViewWallet.setContent("" + data.getCash());
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
                            .showAD(getActivity());
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
        mRewardVideoAD = new ExpressRewardVideoAD(getActivity(), Constant.TXRVIDEO, new ExpressRewardVideoAdListener() {
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
                if (!CommonUtils.isDestory(getActivity())) {
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
            }

            @Override
            public void onClose() {
                copyQQ();
                if (mRewardVideoAD.hasShown()) {
                    loadTxTwo();
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
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(getActivity());
        isLoadAdSuccess = "1";
        loadVideo();
        adPlatformSDK.showRewardVideoAd();
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
    }

    private String isLoadAdSuccess = "0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private int videoCounts;

    private void loadVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(getActivity());
        adPlatformSDK.loadRewardVideoVerticalAd(getActivity(), "ad_dazhuangpan", new AdCallback() {
            @Override
            public void onDismissed() {
                copyQQ();
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
            }

            @Override
            public void onPresent() {
                isLoadAdSuccess = "3";
                if (!CommonUtils.isDestory(getActivity())) {
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
            ClipboardManager mClipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText(null, "963486383");
            mClipboardManager.setPrimaryClip(mClipData);
            ToastUtil.showToast("复制QQ成功！");
        } catch (Exception e) {
            tvQq.setText("963486383");
        }
    }
    public void setVideoCallBack(boolean isVideoClick) {

    }
}