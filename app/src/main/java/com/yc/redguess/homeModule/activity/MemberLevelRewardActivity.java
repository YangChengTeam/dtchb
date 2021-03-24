package com.yc.redguess.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.homeModule.adapter.VipLevelTaskAdapter;
import com.yc.redguess.homeModule.contact.MemberConstact;
import com.yc.redguess.homeModule.module.bean.RedReceiveInfo;
import com.yc.redguess.homeModule.module.bean.TaskUnLockResBeans;
import com.yc.redguess.homeModule.module.bean.UserInfo;
import com.yc.redguess.homeModule.module.bean.VipTaskInfo;
import com.yc.redguess.homeModule.module.bean.VipTaskInfoWrapper;
import com.yc.redguess.homeModule.present.MemberPresenter;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.CommonUtils;
import com.yc.redguess.utils.DisplayUtil;
import com.yc.redguess.utils.SoundPoolUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 会员等级奖励
 */
public class MemberLevelRewardActivity extends BaseActivity<MemberPresenter> implements MemberConstact.View {

    @BindView(R.id.tv_vip_level_title)
    TextView tvVipLevelTitle;

    @BindView(R.id.recyclerView_task)
    RecyclerView recyclerViewTask;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;

    private VipLevelTaskAdapter vipTaskAdapter;

    private int level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_vip_level_reward;
    }

    @Override
    public void initEventAndData() {
        Intent intent = getIntent();
        level = intent.getIntExtra("level", 0);
        tvUserLevel.setText(String.valueOf(level));
        initRecyclerView();
        initData();
        initListener();
        loadInsertView(null);
        showInsertVideo();
    }

    private void initListener() {
        vipTaskAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VipTaskInfo vipTaskInfo = vipTaskAdapter.getItem(position);
                if (vipTaskInfo != null) {
                    if (vipTaskInfo.status == 1) {
                        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                        mPresenter.getUpgradeRewardInfos(userInfo.getGroup_id(), vipTaskInfo.id, position);
                    }
                }
            }
        });
    }

    private void initData() {

        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        mPresenter.upgradeRewardInfos(userInfo.getGroup_id());
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewTask.setLayoutManager(linearLayoutManager);
        vipTaskAdapter = new VipLevelTaskAdapter(null);
        recyclerViewTask.setAdapter(vipTaskAdapter);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void memberJump(Context context, int level) {
        Intent intent = new Intent(context, MemberLevelRewardActivity.class);
        intent.putExtra("level", level);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_level_task,R.id.iv_back})
    public void onClick(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        switch (view.getId()) {
            case R.id.tv_level_task:
                MemberActivity.memberJump(MemberLevelRewardActivity.this);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void showVipTaskInfo(VipTaskInfoWrapper data) {

    }

    @Override
    public void showReceiveSuccess(RedReceiveInfo data) {

    }

    @Override
    public void showUpgradeInfos(List<VipTaskInfo> data) {
        vipTaskAdapter.setNewData(data);
    }

    @Override
    public void showUpdateRewardSuccess(List<VipTaskInfo> data, int position) {
        VipTaskInfo vipTaskInfo = vipTaskAdapter.getItem(position);
        if (vipTaskInfo != null) {
            vipTaskInfo.status = 2;
            vipTaskAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void getUnlockTaskSuccess(TaskUnLockResBeans data) {

    }

    @Override
    public void getUnlockTaskReeorState() {

    }

    private void showInsertVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setAdPosition("chapingdengji");
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        if(adPlatformSDK.showInsertAd()){
            loadInsertView(null);
        } else {
            loadInsertView( new Runnable() {
                @Override
                public void run() {
                    adPlatformSDK.showInsertAd();
                }
            });
        }
    }
    private void loadInsertView(Runnable runnable){
        int screenWidth = CommonUtils.getScreenWidth(this);
        int screenHeight = CommonUtils.getScreenHeight(this);
        int w = (int) (screenWidth)*9/10;
        int h = screenHeight*9/10;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(MemberLevelRewardActivity.this, w);
        int dph = DisplayUtil.px2dip(MemberLevelRewardActivity.this, h);
        adPlatformSDK.loadInsertAd(this, "chapingdengji", dpw, dph, new AdCallback() {
            @Override
            public void onDismissed() {

            }

            @Override
            public void onNoAd(AdError adError) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {

            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                if(runnable != null){
                    runnable.run();
                }
            }
        });
    }

}