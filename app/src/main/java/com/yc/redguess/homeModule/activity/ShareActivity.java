package com.yc.redguess.homeModule.activity;


import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.homeModule.adapter.ShareAdapter;
import com.yc.redguess.homeModule.contact.ShareContact;
import com.yc.redguess.homeModule.present.SharePresenter;
import com.yc.redguess.homeModule.widget.garlly.CardScaleHelper;
import com.yc.redguess.utils.VUiKit;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observer;

public class ShareActivity extends BaseActivity<SharePresenter> implements ShareContact.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.tv_wx_circle)
    TextView tvWxCircle;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    private ShareAdapter shareAdapter;
    private int[] mPics = new int[]{R.drawable.bg_share1, R.drawable.bg_share2, R.drawable.bg_share3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    private CardScaleHelper mCardScaleHelper;

    @Override
    public int getLayout() {
        return R.layout.activity_share;
    }

    @Override
    public void initEventAndData() {
        setTitle("分享二维码");
        initRecyclerView();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        shareAdapter = new ShareAdapter(this, mPics);
        recyclerView.setAdapter(shareAdapter);
        // mRecyclerView绑定scale效果
        mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.setCurrentItemPos(1);
        mCardScaleHelper.attachToRecyclerView(recyclerView);
        int currentItemPos = mCardScaleHelper.getCurrentItemPos();

    }

    public static void shareJump(Context context) {
        Intent intent = new Intent(context, ShareActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_wx, R.id.tv_wx_circle, R.id.tv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wx:
                MobclickAgent.onEvent(ShareActivity.this, "share");//参数二为当前统计的事件ID
                int currentItemPos = mCardScaleHelper.getCurrentItemPos();
                Log.d("ccc", "---onViewClicked: "+currentItemPos);
                if (currentItemPos==0){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_share1);
                    shareWx(bitmap);
                }else if (currentItemPos==1){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_share2);
                    shareWx(bitmap);
                }else if (currentItemPos==2){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_share3);
                    shareWx(bitmap);
                }
                break;
            case R.id.tv_wx_circle:
                MobclickAgent.onEvent(ShareActivity.this, "share");//参数二为当前统计的事件ID
                int currentItemPoss = mCardScaleHelper.getCurrentItemPos();
                Log.d("ccc", "---onViewClicked: "+currentItemPoss);
                if (currentItemPoss==0){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_share1);
                    shareQQ(bitmap);
                }else if (currentItemPoss==1){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_share2);
                    shareQQ(bitmap);
                }else if (currentItemPoss==2){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_share3);
                    shareQQ(bitmap);
                }
                break;
            case R.id.tv_qq:

                break;
        }
    }


    private ShareAction mShareAction;
    private UMImage mUMImage;
    private MyUMShareListener myUMShareListener;
//    private void startShare(SHARE_MEDIA share_media) {
//        UMWeb mUMWeb = new UMWeb("http://www.hncj.com/sjrj/34282.html");
//        mUMWeb.setTitle(getResources().getString(R.string.app_name));
//        mUMWeb.setDescription("进群一起抢红包");
//        mUMImage = new UMImage(getApplicationContext(), R.mipmap.redlogo);
//        mUMWeb.setThumb(mUMImage);
//        mShareAction = new ShareAction(this);
//        myUMShareListener = new MyUMShareListener(this);
//        mShareAction.withMedia(mUMWeb);
//        mShareAction.setPlatform(share_media);
//        mShareAction.setCallback(myUMShareListener);
//        mShareAction.share(); //share不带UM的面板  open带面板 如果带面板 就要setDisplayList
//    }



    public void shareWx(Bitmap resultPosterBitmap) {
        //Uri tempUri = getImageContentUri(InviteFriendsActivity.this, new File(resultPosterPath));
        Uri tempUri = null;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                tempUri = getUri(this, resultPosterBitmap, Bitmap.CompressFormat.PNG, "image/png", "share_img.png", "caicai");
            } else {
                tempUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), resultPosterBitmap, null, null));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempUri != null) {
            shareToWx(ShareActivity.this, tempUri);
        } else {
            ToastUtil.showToast("分享失败");
        }
    }




    public void shareQQ(Bitmap resultPosterBitmap) {
        //Uri tempUri = getImageContentUri(InviteFriendsActivity.this, new File(resultPosterPath));
        Uri tempUri = null;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                tempUri = getUri(this, resultPosterBitmap, Bitmap.CompressFormat.PNG, "image/png", "share_img.png", null);
            } else {
                tempUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), resultPosterBitmap, null, null));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempUri != null) {
            shareToQQ(ShareActivity.this, tempUri);
        } else {
            ToastUtil.showToast("分享失败");
            return;
        }
    }


    /**
     * 分享图片到QQ
     *
     * @param context
     * @param uri
     */
    public void shareToQQ(Context context, Uri uri) {
        if (isInstalled(ShareActivity.this,"com.tencent.mobileqq")) {
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


    /**
     * 直接分享纯文本内容至QQ好友
     * @param mContext
     * @param content
     */
    public  void shareQQ(Context mContext, String content) {
        if (isInstalled(ShareActivity.this,"com.tencent.mobileqq")) {
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
        if (isInstalled(ShareActivity.this,"com.tencent.mm")) {
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
     * 分享图片到微信
     *
     * @param context
     * @param uri
     */
    public void shareToWx(Context context, Uri uri) {
        if (isInstalled(ShareActivity.this,"com.tencent.mm")) {
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



    private void startShare(SHARE_MEDIA share_media,String type) {
        UMImage mUMImage = null;
        if ("0".equals(type)){
            mUMImage = new UMImage(ShareActivity.this, R.drawable.bg_share1);
            mUMImage.setThumb(new UMImage(ShareActivity.this, R.drawable.bg_share1));
        }else if ("1".equals(type)){
            mUMImage = new UMImage(ShareActivity.this, R.drawable.bg_share2);
            mUMImage.setThumb(new UMImage(ShareActivity.this, R.drawable.bg_share2));
        }else if ("2".equals(type)){
            mUMImage = new UMImage(ShareActivity.this, R.drawable.bg_share3);
            mUMImage.setThumb(new UMImage(ShareActivity.this, R.drawable.bg_share3));
        }
        if (mUMImage!=null){
            ShareAction mShareAction = new ShareAction(ShareActivity.this);
            mShareAction.withMedia(mUMImage);
            mShareAction.setPlatform(share_media);
            mShareAction.setCallback(new MyUMShareListener(this));
            mShareAction.share();
        }
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
    }

    public static class MyUMShareListener implements UMShareListener {
        private Context mContext;

        public MyUMShareListener(Context context) {
            this.mContext = context;
        }

        ;

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            MobclickAgent.onEvent(mContext, "sharesuccess");//参数二为当前统计的事件ID
            Toast.makeText(mContext, "分享到" + getShareMedia(share_media) + "成功！", Toast.LENGTH_SHORT).show();


        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(mContext, "分享到" + getShareMedia(share_media) + "失败！", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(mContext, "取消分享到" + getShareMedia(share_media), Toast.LENGTH_SHORT).show();
        }
    }

    private static String getShareMedia(SHARE_MEDIA share_media) {
        switch (share_media) {
            case QQ:
                return "QQ";

            case WEIXIN:
                return "微信";

            case WEIXIN_CIRCLE:
                return "朋友圈";

            default:
                return "";
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}