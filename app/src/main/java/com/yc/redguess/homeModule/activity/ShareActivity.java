package com.yc.redguess.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
                int currentItemPos = mCardScaleHelper.getCurrentItemPos();
                Log.d("ccc", "---onViewClicked: "+currentItemPos);
                if (currentItemPos==0){
                    startShare(SHARE_MEDIA.WEIXIN,"0");
                }else if (currentItemPos==1){
                    startShare(SHARE_MEDIA.WEIXIN,"1");
                }else if (currentItemPos==2){
                    startShare(SHARE_MEDIA.WEIXIN,"2");
                }
                break;
            case R.id.tv_wx_circle:
                int currentItemPoss = mCardScaleHelper.getCurrentItemPos();
                Log.d("ccc", "---onViewClicked: "+currentItemPoss);
                if (currentItemPoss==0){
                    startShare(SHARE_MEDIA.WEIXIN_CIRCLE,"0");
                }else if (currentItemPoss==1){
                    startShare(SHARE_MEDIA.WEIXIN_CIRCLE,"1");
                }else if (currentItemPoss==2){
                    startShare(SHARE_MEDIA.WEIXIN_CIRCLE,"2");
                }
                break;
            case R.id.tv_qq:
               // startShare(SHARE_MEDIA.QQ);
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

    public void saveImageToDCMI(Bitmap bmp, String name) {
        String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        File file = new File(dir, name);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null,
                    (path, uri) -> {
                        Log.i("saveImageToSD", "插入图片到图库并更新图库完成");
                        VUiKit.post(() -> {
                            Toast.makeText(this, "图片保存成功", Toast.LENGTH_SHORT).show();
                        });
                    });
        } catch (IOException e) {
            e.printStackTrace();
            VUiKit.post(() -> {
                Toast.makeText(this, "图片保存失败", Toast.LENGTH_SHORT).show();
            });
        }
    }
}