package com.yc.redevenlopes.homeModule.activity;


import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.MemberCenterContact;
import com.yc.redevenlopes.homeModule.fragment.ShareFragment;
import com.yc.redevenlopes.homeModule.present.MemberCenterPresenter;
import com.yc.redevenlopes.homeModule.widget.MemberCenterView;

import butterknife.BindView;
import butterknife.OnClick;

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
    MemberCenterView memberCenterViewSound;
    @BindView(R.id.memberCenterView_version)
    MemberCenterView memberCenterViewVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_member_center);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_member_center;
    }

    @Override
    public void initEventAndData() {
        initData();
    }

    private void initData() {
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

    public static void memberCenterJump(Context context) {
        Intent intent = new Intent(context, MemberCenterActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.memberCenterView_wallet, R.id.memberCenterView_rank, R.id.tv_share_friend, R.id.tv_logout})
    public void onClick(View view) {
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
                shareFragment.show(getSupportFragmentManager(), "");
                break;
            case R.id.tv_logout:
                ActivityOptions opts = ActivityOptions.makeCustomAnimation(MemberCenterActivity.this,
                        R.anim.abc_fade_in, R.anim.abc_fade_out);

                Intent intent = new Intent(MemberCenterActivity.this, LoginActivity.class);
                startActivity(intent, opts.toBundle());
                finish();

                break;
        }
    }
}