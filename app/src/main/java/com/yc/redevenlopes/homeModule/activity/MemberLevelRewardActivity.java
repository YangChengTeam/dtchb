package com.yc.redevenlopes.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.VipLevelTaskAdapter;
import com.yc.redevenlopes.homeModule.contact.MemberConstact;
import com.yc.redevenlopes.homeModule.module.bean.RedReceiveInfo;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfoWrapper;
import com.yc.redevenlopes.homeModule.present.MemberPresenter;
import com.yc.redevenlopes.utils.CacheDataUtils;

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
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_vip_level_reward;
    }

    @Override
    public void initEventAndData() {
        setTitle("等级奖励");
        Intent intent = getIntent();
        level = intent.getIntExtra("level", 0);
        tvUserLevel.setText(String.valueOf(level));
        initRecyclerView();
        initData();
        initListener();
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

    @OnClick({R.id.tv_level_task})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_level_task:
                MemberActivity.memberJump(MemberLevelRewardActivity.this);
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
}