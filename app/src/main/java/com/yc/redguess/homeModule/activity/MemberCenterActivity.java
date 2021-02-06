package com.yc.redguess.homeModule.activity;


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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.dialog.SnatchDialog;
import com.yc.redguess.homeModule.contact.MemberCenterContact;
import com.yc.redguess.homeModule.fragment.ShareFragment;
import com.yc.redguess.homeModule.module.bean.OtherBeans;
import com.yc.redguess.homeModule.module.bean.UserInfo;
import com.yc.redguess.homeModule.present.MemberCenterPresenter;
import com.yc.redguess.homeModule.widget.MemberCenterView;
import com.yc.redguess.homeModule.widget.MemberCenterViewSol;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.SoundPoolUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
        if (!TextUtils.isEmpty(money)){
            memberCenterViewWallet.setContent("￥"+money);
        }
        memberCenterViewPerson.setContent("400人");
        memberCenterViewGroup.setContent(CacheDataUtils.getInstance().getUserInfo().getGroup_id()+"");
        mPresenter.getOtherInfo(CacheDataUtils.getInstance().getUserInfo().getGroup_id()+"",CacheDataUtils.getInstance().getUserInfo().getId()+"");
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

    public static void memberCenterJump(Context context,String money) {
        Intent intent = new Intent(context, MemberCenterActivity.class);
        intent.putExtra("money",money);
        context.startActivity(intent);
    }


    @OnClick({R.id.memberCenterView_wallet, R.id.memberCenterView_rank, R.id.tv_share_friend, R.id.tv_logout,R.id.memberCenterView_contant,R.id.memberCenterView_help})
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
                        shareWechatFriend(MemberCenterActivity.this,"http://m.hncj.com/sjrj/34282.html");
                    }
                    @Override
                    public void weixinCircleShare() {
                        shareQQ(MemberCenterActivity.this,"http://m.hncj.com/sjrj/34282.html");
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
                ClipboardManager mClipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText(null, "3484150153");
                mClipboardManager.setPrimaryClip(mClipData);
                ToastUtil.showToast("复制QQ成功！");
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
     * @param mContext
     * @param content
     */
    public  void shareQQ(Context mContext, String content) {
        if (isInstalled(MemberCenterActivity.this,"com.tencent.mobileqq")) {
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
        if (isInstalled(MemberCenterActivity.this,"com.tencent.mm")) {
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
        memberCenterViewWallet.setContent("￥"+data.getCash());
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

}