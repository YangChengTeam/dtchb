package com.yc.wxchb.beans.fragment;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseLazyFragment;
import com.yc.wxchb.beans.activity.HelpQuestionActivity;
import com.yc.wxchb.beans.activity.LoginActivity;
import com.yc.wxchb.beans.activity.MainActivity;
import com.yc.wxchb.beans.contact.MineContract;
import com.yc.wxchb.beans.module.beans.OtherBeans;
import com.yc.wxchb.beans.module.beans.TelBeans;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.beans.present.MinePresenter;
import com.yc.wxchb.constants.Constant;
import com.yc.wxchb.dialog.SignDialog;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.SoundPoolUtils;
import com.yc.wxchb.utils.VUiKit;
import com.yc.wxchb.widget.MemberCenterViewSolzq;
import com.yc.wxchb.widget.MemberCenterViewzq;
import com.zzhoujay.richtext.RichText;

import com.yc.wxchb.service.event.Event;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.umeng.socialize.utils.ContextUtil.getPackageName;

public class MineFragment extends BaseLazyFragment<MinePresenter> implements MineContract.View {
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.memberCenterView_wallet)
    MemberCenterViewzq memberCenterViewWallet;

    @BindView(R.id.memberCenterView_sound)
    MemberCenterViewSolzq memberCenterViewSound;
    @BindView(R.id.memberCenterView_version)
    MemberCenterViewzq memberCenterViewVersion;
    @BindView(R.id.tv_userid)
    TextView tvUserid;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.rela_shareImg)
    RelativeLayout relaShareImg;
    @BindView(R.id.ivCode)
    ImageView ivCode;
    @BindView(R.id.rela_about)
    RelativeLayout relaAbout;
    @BindView(R.id.tv_level)
    TextView tvLevel;

    private boolean isFirst;
    private MainActivity activity;
    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    protected android.view.View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
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
        EventBus.getDefault().register(this);
        activity = (MainActivity) getActivity();
        mPresenter.getOtherInfo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", CacheDataUtils.getInstance().getUserInfo().getId() + "");
        if (!TextUtils.isEmpty(Constant.SHAREIMG)){
            Glide.with(this).load(Constant.SHAREIMG).into(ivCode);
        }
        initData();
        mPresenter.getTel(CacheDataUtils.getInstance().getUserInfo().getId());
    }

    private void initData() {
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();

        Glide.with(this).load(userInfo.getFace()).apply(RequestOptions.bitmapTransform(new CenterCrop()))
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



    @OnClick({R.id.memberCenterView_wallet, R.id.tv_share_friend, R.id.tv_logout, R.id.memberCenterView_contant, R.id.memberCenterView_help,R.id.rela_about})
    public void onClick(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        super.onClick(view);
        switch (view.getId()) {
            case R.id.memberCenterView_wallet:
                //startActivity(new Intent(getActivity(), Wall.class));
                break;

            case R.id.tv_share_friend:
                MobclickAgent.onEvent(getActivity(), "mine_share", "1");//参数二为当前统计的事件ID
                ShareFragmentzq shareFragment = new ShareFragmentzq();
                shareFragment.setShareOnclickListen(new ShareFragmentzq.ShareOnclickListen() {
                    @Override
                    public void weixinShare() {
                        if (relaShareImg != null) {
                            Bitmap bitmap = drawMeasureView(relaShareImg);
                            if (bitmap != null) {
                                shareWx(bitmap);
                            }
                        }
                    }

                    @Override
                    public void weixinCircleShare() {
                        if (relaShareImg != null) {
                            Bitmap bitmap = drawMeasureView(relaShareImg);
                            if (bitmap != null) {
                                shareQQ(bitmap);
                            }
                        }
                    }
                });
                shareFragment.show(getActivity().getSupportFragmentManager(), "");
                break;
            case R.id.tv_logout:
                CacheDataUtils.getInstance().cleanUserInfo();
                UMShareAPI.get(getActivity()).deleteOauth(getActivity(), SHARE_MEDIA.WEIXIN,null);
                UMShareAPI.get(getActivity()).release();
                ActivityOptions opts = ActivityOptions.makeCustomAnimation(getActivity(),
                        R.anim.abc_fade_in, R.anim.abc_fade_out);

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent, opts.toBundle());

                break;
            case R.id.memberCenterView_contant:
                MobclickAgent.onEvent(getActivity(), "mine_tel", "1");//参数二为当前统计的事件ID
                redDialog();
                break;
            case R.id.memberCenterView_help:
                MobclickAgent.onEvent(getActivity(), "mine_help", "1");//参数二为当前统计的事件ID
                HelpQuestionActivity.helpJump(getActivity());
                break;
            case R.id.rela_about:
               // AboutActivity.aboutJump(getActivity());
                break;
        }
    }

    /**
     * 绘制已经测量过的View
     */
    private static Bitmap drawMeasureView(View view) {
        Bitmap bitmap=null;
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
        }catch (Exception e){
            return null;
        }
        return bitmap;
    }

    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    public  boolean isInstalled(Context context, String packageName) {
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
    public void getOtherInfoSuccess(OtherBeans data) {
        ((MyApplication) MyApplication.getInstance()).cash=data.getCash();
        ((MyApplication) MyApplication.getInstance()).hb_Nums=data.getHb_num();
        memberCenterViewWallet.setContent("" + data.getCash());
        tvLevel.setText("LV."+data.getLevel());
    }


    public void setRefresh() {
        if (isFirst){
            VUiKit.postDelayed(400, () -> {

            });
        }else {
            VUiKit.postDelayed(1500, () -> {

            });
        }
    }

    public void setRefreshs(OtherBeans otherBeans) {
        if (memberCenterViewWallet!=null){
            memberCenterViewWallet.setContent(((MyApplication) MyApplication.getInstance()).cash);
        }
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

    private TelBeans beans;
    @Override
    public void getTelSuccess(TelBeans data) {
        this.beans=data;
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
        redDialog();
    }

    public SignDialog redDialogs;
    public void redDialog() {
        if (beans!=null&&!TextUtils.isEmpty(beans.getTop_img())){
            redDialogs = new SignDialog(getActivity());
            View builder = redDialogs.builder(R.layout.kefu_dialog_item);
            ImageView iv_close = builder.findViewById(R.id.iv_close);
            ImageView ivCode=builder.findViewById(R.id.iv_code);
            TextView tvs=builder.findViewById(R.id.tvs);
            if (!TextUtils.isEmpty(beans.getContent())){
                RichText.from(beans.getContent()).into(tvs);
            }
            Glide.with(getActivity()).load(beans.getTop_img()).into(ivCode);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redDialogs.setDismiss();
                }
            });
            if (!CommonUtils.isDestory(getActivity())){
                redDialogs.setShow();
            }
        }else {
            ToastUtil.showToast("暂未获取到客服联系方式");
        }
    }



    /**
     * 分享图片到微信
     *
     * @param context
     * @param uri
     */
    public void shareToWx(Context context, Uri uri) {
        if (isInstalled(getActivity(),"com.tencent.mm")) {
            try {
                Intent intent = new Intent();
                ComponentName cop = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
                intent.setComponent(cop);
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, "Share"));
            } catch (Exception e) {
                ToastUtil.showToast("邀请失败，请稍后重试");
            }
        } else {
            ToastUtil.showToast("请您先安装微信！");
        }
    }





    public void shareWx(Bitmap resultPosterBitmap) {
        //Uri tempUri = getImageContentUri(InviteFriendsActivity.this, new File(resultPosterPath));
        Uri tempUri = null;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                String path=System.currentTimeMillis()+"share_img.png";
                tempUri = getUri(getActivity(), resultPosterBitmap, Bitmap.CompressFormat.PNG, "image/png", path, "caicai");
            } else {
                try {
                    tempUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), resultPosterBitmap, null, null));
                }catch (Exception e){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempUri != null) {
            shareToWx(getActivity(), tempUri);
        } else {
            ToastUtil.showToast("分享失败");
        }
    }




    public void shareQQ(Bitmap resultPosterBitmap) {
        //Uri tempUri = getImageContentUri(InviteFriendsActivity.this, new File(resultPosterPath));
        Uri tempUri = null;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                String path=System.currentTimeMillis()+"share_img.png";
                tempUri = getUri(getActivity(), resultPosterBitmap, Bitmap.CompressFormat.PNG, "image/png", path, null);
            } else {
                try {
                    tempUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), resultPosterBitmap, null, null));
                }catch (Exception e){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempUri != null) {
            shareToQQ(getActivity(), tempUri);
        } else {
            ToastUtil.showToast("分享失败");
            return;
        }
    }
    @NonNull
    private Uri getUri(@NonNull final Context context, @NonNull final Bitmap bitmap,
                       @NonNull final Bitmap.CompressFormat format, @NonNull final String mimeType,
                       @NonNull final String displayName, @Nullable final String subFolder) throws IOException {
        String relativeLocation = Environment.DIRECTORY_DCIM;

        if (!TextUtils.isEmpty(subFolder)) {
            relativeLocation += File.separator + subFolder;
        }

        final ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation);

        final ContentResolver resolver = context.getContentResolver();

        OutputStream stream = null;
        Uri uri = null;

        try {
            final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            uri = resolver.insert(contentUri, contentValues);

            if (uri == null) {
                throw new IOException("Failed to create new MediaStore record.");
            }

            stream = resolver.openOutputStream(uri);

            if (stream == null) {
                throw new IOException("Failed to get output stream.");
            }

            if (bitmap.compress(format, 95, stream) == false) {
                throw new IOException("Failed to save bitmap.");
            }

            return uri;
        } catch (IOException e) {
            if (uri != null) {
                // Don't leave an orphan entry in the MediaStore
                resolver.delete(uri, null, null);
            }

            throw e;
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    /**
     * 分享图片到QQ
     *
     * @param context
     * @param uri
     */
    public void shareToQQ(Context context, Uri uri) {
        if (isInstalled(getActivity(),"com.tencent.mobileqq")) {
            try {
                Intent intent = new Intent();
                ComponentName cop = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
                intent.setComponent(cop);
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent, "Share"));
            } catch (Exception e) {
                ToastUtil.showToast("分享失败，请稍后重试");
            }
        } else {
            ToastUtil.showToast("请您先安装QQ！");
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            if (memberCenterViewWallet!=null){
                memberCenterViewWallet.setContent("" + ((MyApplication) MyApplication.getInstance()).cash);
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                Glide.with(this).load(userInfo.getFace()).apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                        .error(R.mipmap.icon_default_image).into(ivAvatar);
                String nickname = userInfo.getNickname();
                if (TextUtils.isEmpty(nickname)) {
                    nickname = "游客" + userInfo.getId();
                }
                tvNickname.setText(nickname);
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHomePage(Event event) {
        if (event instanceof Event.LoginEvent) {
            UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
            Glide.with(this).load(userInfo.getFace()).apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))
                    .error(R.mipmap.icon_default_image).into(ivAvatar);
            String nickname = userInfo.getNickname();
            if (TextUtils.isEmpty(nickname)) {
                nickname = "游客" + userInfo.getId();
            }
            tvNickname.setText(nickname);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}