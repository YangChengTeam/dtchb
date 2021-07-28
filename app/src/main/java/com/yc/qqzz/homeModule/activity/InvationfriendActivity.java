package com.yc.qqzz.homeModule.activity;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.dialog.CenterDialog;
import com.yc.qqzz.dialog.CenterRelaDialog;
import com.yc.qqzz.homeModule.adapter.InvitationDialogAdapter;
import com.yc.qqzz.homeModule.contact.InvationFriendContract;
import com.yc.qqzz.homeModule.module.bean.InvitationCodeBeans;
import com.yc.qqzz.homeModule.module.bean.InvitationInfoBeans;
import com.yc.qqzz.homeModule.module.bean.InvitationShareBeans;
import com.yc.qqzz.homeModule.present.InvationFriendPresenter;
import com.yc.qqzz.utils.CacheDataUtils;
import com.zzhoujay.richtext.RichText;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InvationfriendActivity extends BaseActivity<InvationFriendPresenter> implements InvationFriendContract.View {
    @BindView(R.id.line_qq)
    LinearLayout lineQq;
    @BindView(R.id.line_weixin)
    LinearLayout lineWeixin;
    @BindView(R.id.line_haibao)
    LinearLayout lineHaibao;
    @BindView(R.id.line_lianjie)
    LinearLayout lineLianjie;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_invitation)
    TextView tvInvitation;
    @BindView(R.id.tv_invitation_nums)
    TextView tvInvitationNums;
    @BindView(R.id.tv_invitation_gold)
    TextView tvInvitationGold;
    @BindView(R.id.tv_contents)
    TextView tvContents;
    private InvitationDialogAdapter invitationDialogAdapter;
    private int be_gold;
    private String be_money;
    private String invite_code;
    private String top_img;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_invationfriend;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.line_qq, R.id.line_weixin, R.id.line_haibao, R.id.line_lianjie, R.id.tv_all, R.id.iv_back, R.id.line_inputCode, R.id.tv_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_qq:
                startShare(SHARE_MEDIA.QQ, "心神旅行");
                break;
            case R.id.line_weixin:
                startShare(SHARE_MEDIA.WEIXIN, "心神旅行");
                break;
            case R.id.line_haibao:
               // InvitationOrcodeActivity.invitationJump(InvationfriendActivity.this, top_img, invite_code);
                break;
            case R.id.line_lianjie:
                ClipboardManager mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText(null, url);
                mClipboardManager.setPrimaryClip(mClipData);
                ToastUtil.showToast("复制下载链接成功！");
                break;
            case R.id.tv_all:
                if (CacheDataUtils.getInstance().isLogin()) {
                   // mPresenter.getShareList(CacheDataUtils.getInstance().getUserInfo().getId(), CacheDataUtils.getInstance().getUserInfo().getMobile(), "1", "30");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.line_inputCode:
                showInCodeDialog();
                break;
            case R.id.tv_copy:
                if (!TextUtils.isEmpty(invite_code)) {
                    ClipboardManager mClipboardManager1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipData1 = ClipData.newPlainText(null, invite_code);
                    mClipboardManager1.setPrimaryClip(mClipData1);
                    ToastUtil.showToast("复制邀请码成功！");
                }
                break;
        }
    }

    private CenterDialog invitationDialogs;
    private CenterDialog inCodeDialogs;
    private CenterRelaDialog inCodeSuccessDialogs;

    public void invitaitonDialog(List<InvitationShareBeans> data) {
        invitationDialogs = new CenterDialog(InvationfriendActivity.this);
        View builder = invitationDialogs.builder(R.layout.invitation_dialog);
        RecyclerView recyclerView = builder.findViewById(R.id.recyclerView);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InvationfriendActivity.this, LinearLayoutManager.VERTICAL, false);
        invitationDialogAdapter = new InvitationDialogAdapter(data);
        recyclerView.setAdapter(invitationDialogAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (data == null || data.size() == 0) {
            View empty = LayoutInflater.from(this).inflate(R.layout.empty_viewfour, null, false);
            TextView tv_des = empty.findViewById(R.id.tv_description);
            tv_des.setText("快去邀请好友吧！");
            invitationDialogAdapter.setEmptyView(empty);
        }
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invitationDialogs.setDismiss();
            }
        });
        invitationDialogs.setShow();
    }


    public void showInCodeDialog() {
        inCodeDialogs = new CenterDialog(InvationfriendActivity.this);
        View builder = inCodeDialogs.builder(R.layout.incode_dialog);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        EditText editText = builder.findViewById(R.id.et_code);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString().replaceAll(" ", "");
                if (!TextUtils.isEmpty(code)) {
                  //  mPresenter.getInvitationCode(CacheDataUtils.getInstance().getUserInfo().getId(), CacheDataUtils.getInstance().getUserInfo().getMobile(), code);
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
        inCodeDialogs.setShow();
    }

    public void showInCodeSuccessDialog() {
//        inCodeSuccessDialogs = new CenterRelaDialog(InvationfriendActivity.this);
//        View builder = inCodeSuccessDialogs.builder(R.layout.incodesuccess_dialog);
//        TextView tv_sure = builder.findViewById(R.id.cons_yiban);
//        tv_sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inCodeSuccessDialogs.setDismiss();
//            }
//        });
//        inCodeSuccessDialogs.setShow();
    }

    @Override
    public void getShareListSuccess(List<InvitationShareBeans> data) {
        invitaitonDialog(data);
    }

    @Override
    public void getInvitationInfoSuccess(InvitationInfoBeans data) {
        if (data != null) {
            url = data.getUrl();
            invite_code = data.getInvite_code();
            top_img = data.getTop_img();
            int invite_num = data.getInvite_num();
            int invite_gold = data.getInvite_gold();
            be_gold = data.getBe_gold();
            be_money = data.getBe_money();
            tvInvitationNums.setText(invite_num + "");
            tvInvitationGold.setText(invite_gold + "");
            if (!TextUtils.isEmpty(invite_code)) {
                tvInvitation.setText(invite_code);
            }

            if (!TextUtils.isEmpty(data.getContent())) {
                RichText.fromHtml(data.getContent()).into(tvContents);
            }
        }
    }

    @Override
    public void getInvitationCodeSuccess(InvitationCodeBeans data) {
        if (inCodeDialogs != null) {
            inCodeDialogs.setDismiss();
            //showInCodeSuccessDialog();
        }
    }


    private ShareAction mShareAction;
    private UMImage mUMImage;
    private MyUMShareListener myUMShareListener;

    private void startShare(SHARE_MEDIA share_media, String title) {
        UMWeb mUMWeb = new UMWeb("http://m.k1u.com/xinshen/");
        mUMWeb.setTitle(title);
        mUMWeb.setDescription(title);
        mUMImage = new UMImage(getApplicationContext(), R.mipmap.ic_launcher);
        mUMWeb.setThumb(mUMImage);
        mShareAction = new ShareAction(this);
        myUMShareListener = new MyUMShareListener();
        mShareAction.withMedia(mUMWeb);
        mShareAction.setPlatform(share_media);
        mShareAction.setCallback(myUMShareListener);
        mShareAction.share(); //share不带UM的面板  open带面板 如果带面板 就要setDisplayList
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShareAction != null) {
            mShareAction = null;
        }
        if (mUMImage != null) {
            mUMImage = null;
        }
        if (myUMShareListener != null) {
            myUMShareListener = null;
        }
    }

    /**
     * @param requestCode 请求代码
     * @param resultCode  返回的代码
     * @param data        返回的数据
     *                    需要在此处设置友盟回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}