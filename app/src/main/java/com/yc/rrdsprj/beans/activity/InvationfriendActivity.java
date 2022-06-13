package com.yc.rrdsprj.beans.activity;


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
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.rrdsprj.R;
import com.yc.rrdsprj.base.BaseActivity;
import com.yc.rrdsprj.beans.adapter.InvitationDialogAdapter;
import com.yc.rrdsprj.beans.adapter.ShareWithDrawAdapter;
import com.yc.rrdsprj.beans.contact.InvationFriendContract;
import com.yc.rrdsprj.beans.module.beans.EmptyBeans;
import com.yc.rrdsprj.beans.module.beans.InvationFriendExchangeBeans;
import com.yc.rrdsprj.beans.module.beans.InvationPeopleListBeans;
import com.yc.rrdsprj.beans.module.beans.InvitationShareBeans;
import com.yc.rrdsprj.beans.present.InvationFriendPresenter;
import com.yc.rrdsprj.constants.Constant;
import com.yc.rrdsprj.dialog.CenterDialog;
import com.yc.rrdsprj.dialog.SignDialog;
import com.yc.rrdsprj.dialog.SnatchDialog;
import com.yc.rrdsprj.utils.CacheDataUtils;
import com.yc.rrdsprj.utils.CommonUtils;
import com.yc.rrdsprj.utils.SoundPoolUtils;
import com.zzhoujay.richtext.RichText;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class InvationfriendActivity extends BaseActivity<InvationFriendPresenter> implements InvationFriendContract.View {
    @BindView(R.id.line_qq)
    LinearLayout lineQq;
    @BindView(R.id.line_weixin)
    LinearLayout lineWeixin;

    @BindView(R.id.line_lianjie)
    LinearLayout lineLianjie;
    @BindView(R.id.tv_invitation)
    TextView tvInvitation;
    @BindView(R.id.tv_invitation_nums)
    TextView tvInvitationNums;

    @BindView(R.id.withDraw_recyclerView)
    RecyclerView withDrawRecyclerView;
    @BindView(R.id.tv_exchange)
    TextView tvExchange;
    @BindView(R.id.tv_efficientNum)
    TextView tvEfficientNum;
    @BindView(R.id.tv_canExchangeNum)
    TextView tvCanExchangeNum;
    @BindView(R.id.rela_shareImg)
    RelativeLayout relaShareImg;
    @BindView(R.id.ivCode)
    ImageView tv_share_bg5;
    private InvitationDialogAdapter invitationDialogAdapter;
    private String invite_code;
    private ShareWithDrawAdapter shareWithDrawAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_invationfriend;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        initInvitaitonDialog();
        initRecyclerView();
        getInvationPeople();
        mPresenter.getShareList(CacheDataUtils.getInstance().getUserInfo().getId());
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.line_qq, R.id.line_weixin,  R.id.line_lianjie, R.id.iv_back, R.id.line_inputCode,R.id.line_codes,R.id.tv_exchange,R.id.line_rule,R.id.line_invation_people})
    public void onViewClicked(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        Intent intent;
        switch (view.getId()) {
            case R.id.line_invation_people:
                invitaitonDialog(peopleList);
                break;
            case R.id.line_qq:
                if (relaShareImg != null) {
                    Bitmap bitmap = drawMeasureView(relaShareImg);
                    if (bitmap != null) {
                        shareQQ(bitmap);
                    }
                }
                break;
            case R.id.line_weixin:
                if (relaShareImg != null) {
                    Bitmap bitmap = drawMeasureView(relaShareImg);
                    if (bitmap != null) {
                        shareWx(bitmap);
                    }
                }
                break;
            case R.id.line_lianjie:
                ClipboardManager mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText(null, Constant.SHAREURL);
                mClipboardManager.setPrimaryClip(mClipData);
                ToastUtil.showToast("复制下载链接成功！");
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.line_inputCode:
                showInCodeDialog();
                break;
            case R.id.line_codes:
                if (!TextUtils.isEmpty(invite_code)) {
                    ClipboardManager mClipboardManager1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData1 = ClipData.newPlainText(null, invite_code);
                    mClipboardManager1.setPrimaryClip(mClipData1);
                    ToastUtil.showToast("复制邀请码成功！");
                }
                break;
            case R.id.tv_exchange:
                if (is_open==1){
                    List<InvitationShareBeans.InviteListBean> lists = shareWithDrawAdapter.getData();
                    for (int j = 0; j < lists.size(); j++) {
                        if (lists.get(j).isSelect()){
                            exchangeId= String.valueOf(lists.get(j).getId());
                            int exchange_num = lists.get(j).getExchange_num();
                            if (this.invite_canexchanege_num <exchange_num){
                                String tips="邀请人数不足，前往邀请";
                                exchangeifDialogs();
                                return;
                            }
                            wxLogin();
                        }
                    }
                }else {
                    ToastUtil.showToast("兑换功能暂未开启，请联系客服");
                }
                break;
            case R.id.line_rule:
                initSignRuleDialog(contents);
                break;
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
        tv_title.setText("规则说明");
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

    public static void invationfriendJump(Context context){
         Intent intent=new Intent(context,InvationfriendActivity.class);
         context.startActivity(intent);
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
            String appVersionCode = CommonUtils.getAppVersionCode(InvationfriendActivity.this);
            if (!TextUtils.isEmpty(wx_openid)) {
                mPresenter.getExchangeadd(CacheDataUtils.getInstance().getUserInfo().getId(),exchangeId,wx_openid,appVersionCode);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            ToastUtil.showToast("授权失败"+"----:"+throwable.getMessage()+"----:"+throwable.getLocalizedMessage());
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

    public void shareCircle(Bitmap resultPosterBitmap) {
        Uri tempUri = null;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                tempUri = getUri(this, resultPosterBitmap, Bitmap.CompressFormat.PNG, "image/png", "share_img.png", "caicai");
            } else {
                try {
                    tempUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), resultPosterBitmap, null, null));
                } catch (Exception e) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempUri != null) {
            Intent intent = new Intent();
            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, "你好 ");
            intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.i("uri", "" + tempUri.getScheme());
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image");
            Log.i("image", " " + tempUri);
            intent.putExtra(Intent.EXTRA_STREAM, tempUri);
            startActivity(intent);
        } else {
            ToastUtil.showToast("分享失败");
        }
    }

    /**
     * 分享图片到微信
     *
     * @param context
     * @param uri
     */
    public void shareToWx(Context context, Uri uri) {
        if (isInstalled(InvationfriendActivity.this,"com.tencent.mm")) {
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



    public void shareWx(Bitmap resultPosterBitmap) {
        //Uri tempUri = getImageContentUri(InviteFriendsActivity.this, new File(resultPosterPath));
        Uri tempUri = null;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                String displayNames="share_img"+System.currentTimeMillis()+"png";
                tempUri = getUri(this, resultPosterBitmap, Bitmap.CompressFormat.PNG, "image/png", displayNames, "caicai");
            } else {
                try {
                    tempUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), resultPosterBitmap, null, null));
                }catch (Exception e){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempUri != null) {
            shareToWx(InvationfriendActivity.this, tempUri);
        } else {
            ToastUtil.showToast("分享失败");
        }
    }




    public void shareQQ(Bitmap resultPosterBitmap) {
        //Uri tempUri = getImageContentUri(InviteFriendsActivity.this, new File(resultPosterPath));
        Uri tempUri = null;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                String displayNames="share_img"+System.currentTimeMillis()+"png";
                tempUri = getUri(this, resultPosterBitmap, Bitmap.CompressFormat.PNG, "image/png", displayNames, null);
            } else {
                try {
                    tempUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), resultPosterBitmap, null, null));
                }catch (Exception e){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempUri != null) {
            shareToQQ(InvationfriendActivity.this, tempUri);
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
        if (isInstalled(InvationfriendActivity.this,"com.tencent.mobileqq")) {
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

    public void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        shareWithDrawAdapter = new ShareWithDrawAdapter(null);
        withDrawRecyclerView.setLayoutManager(gridLayoutManager);
        withDrawRecyclerView.setAdapter(shareWithDrawAdapter);
        shareWithDrawAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<InvitationShareBeans.InviteListBean> lists = adapter.getData();
                for (int i = 0; i < lists.size(); i++) {
                    if (position==i){
                        lists.get(i).setSelect(true);
                    }else {
                        lists.get(i).setSelect(false);
                    }
                }
                shareWithDrawAdapter.notifyDataSetChanged();
            }
        });
        if (!TextUtils.isEmpty(Constant.SHAREIMG)){
            Glide.with(this).load(Constant.SHAREIMG).into(tv_share_bg5);
        }
    }

    private CenterDialog invitationDialogs;
    private SignDialog inCodeDialogs;
    private  RecyclerView recyclerView;

    public void initInvitaitonDialog() {
        invitationDialogs = new CenterDialog(InvationfriendActivity.this);
        View builder = invitationDialogs.builder(R.layout.invitation_dialog);
        recyclerView = builder.findViewById(R.id.recyclerView);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InvationfriendActivity.this, LinearLayoutManager.VERTICAL, false);
        invitationDialogAdapter = new InvitationDialogAdapter(null);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invitationDialogs.setDismiss();
            }
        });
        recyclerView.setAdapter(invitationDialogAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    public void invitaitonDialog(List<InvationPeopleListBeans> datas) {
        if (invitationDialogAdapter!=null&&invitationDialogs!=null){
            invitationDialogAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    getInvationPeople();
                }
            }, recyclerView);
            if (datas == null || datas.size() == 0) {
                View empty = LayoutInflater.from(this).inflate(R.layout.empty_viewfour, null, false);
                TextView tv_des = empty.findViewById(R.id.tv_description);
                tv_des.setText("快去邀请好友吧！");
                invitationDialogAdapter.setEmptyView(empty);
            }

            if (!CommonUtils.isDestory(this)){
                invitationDialogs.setShow();
            }
        }
    }




    public void showInCodeDialog() {
        inCodeDialogs = new SignDialog(InvationfriendActivity.this);
        View builder = inCodeDialogs.builder(R.layout.incode_dialog);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        EditText editText = builder.findViewById(R.id.et_code);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString().replaceAll(" ", "");
                if (!TextUtils.isEmpty(code)) {
                      mPresenter.getInvitationCode(CacheDataUtils.getInstance().getUserInfo().getId(),code);
                } else {
                    ToastUtil.showToast("请输入邀请码");
                }
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inCodeDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)){
            inCodeDialogs.setShow();
        }
    }
    private SnatchDialog exchangeifDialog;
    public void exchangeifDialogs() {
        exchangeifDialog = new SnatchDialog(InvationfriendActivity.this);
        View builder = exchangeifDialog.builder(R.layout.exchangeif_dialog);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exchangeifDialog.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exchangeifDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)){
            exchangeifDialog.setShow();
        }
    }

    private String contents;
    private int invite_num;
    private String exchangeId;
    private int is_open;
    private int exchange_day;
    private int invite_canexchanege_num;
    @Override
    public void getShareListSuccess(InvitationShareBeans data) {
        if (data != null) {
            InvitationShareBeans.InviteConfigBean invite_config = data.getInvite_config();
            if (invite_config != null) {
                is_open = invite_config.getIs_open();
                exchange_day = invite_config.getExchange_day();
                contents = invite_config.getContent();
            }
            invite_num = data.getInvite_num();
            int invite_exchange_num = data.getInvite_exchange_num();
            tvInvitationNums.setText(invite_num + "");
            invite_canexchanege_num=data.getInvite_meet_num()-invite_exchange_num;
            tvCanExchangeNum.setText(invite_canexchanege_num+"");
            invite_code = data.getInvite_code();
            tvInvitation.setText(invite_code+"");
            List<InvitationShareBeans.InviteListBean> invite_list = data.getInvite_list();
            for (int i = 0; i < invite_list.size(); i++) {
                if (i == 0) {
                    invite_list.get(i).setSelect(true);
                    exchangeId = String.valueOf(invite_list.get(i).getId());
                } else {
                    invite_list.get(i).setSelect(false);
                }
            }
            shareWithDrawAdapter.setNewData(invite_list);
            shareWithDrawAdapter.notifyDataSetChanged();
        }
    }



    @Override
    public void getInvitationCodeSuccess(EmptyBeans data) {
        ToastUtil.showToast("邀请码输入成功");
        if (inCodeDialogs != null) {
            inCodeDialogs.setDismiss();
        }
    }

    @Override
    public void getExchangeaddSuccess(InvationFriendExchangeBeans data) {
        mPresenter.getShareList(CacheDataUtils.getInstance().getUserInfo().getId());
        cashSuccessDialogs();
    }

    private  int page=1;
    private void getInvationPeople() {
        mPresenter.getPeople(CacheDataUtils.getInstance().getUserInfo().getId(),page,"15");
    }
    private List<InvationPeopleListBeans> peopleList=new ArrayList<>();
    @Override
    public void getPeopleSuccess(List<InvationPeopleListBeans> data) {
        if (page==1){
            if (invitationDialogAdapter!=null){
                peopleList.clear();
                peopleList.addAll(data);
                invitationDialogAdapter.setNewData(peopleList);
            }
        }else {
            peopleList.addAll(data);
            invitationDialogAdapter.addData(data);
        }

        if (data.size() >= 15) {
            invitationDialogAdapter.loadMoreComplete();
            page++;
        } else {
            invitationDialogAdapter.loadMoreEnd();
        }
        invitationDialogAdapter.notifyDataSetChanged();
    }

    private SnatchDialog cashSuccessDialog;
    public void cashSuccessDialogs() {
        cashSuccessDialog = new SnatchDialog(this);
        View builder = cashSuccessDialog.builder(R.layout.cashsuccess_item_dialog);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        TextView tv_title=builder.findViewById(R.id.tv_title);
        tv_title.setText("兑换成功");
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashSuccessDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)){
            cashSuccessDialog.setShow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}