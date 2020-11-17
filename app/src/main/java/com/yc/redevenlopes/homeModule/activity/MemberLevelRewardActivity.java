package com.yc.redevenlopes.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.VipLevelTaskAdapter;
import com.yc.redevenlopes.homeModule.adapter.VipTaskAdapter;
import com.yc.redevenlopes.homeModule.contact.MemberConstact;
import com.yc.redevenlopes.homeModule.module.bean.VipTaskInfo;
import com.yc.redevenlopes.homeModule.present.MemberPresenter;

import java.util.ArrayList;
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
    private VipLevelTaskAdapter vipTaskAdapter;

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
        initRecyclerView();
        initData();
    }

    private void initData() {
        List<VipTaskInfo> vipTaskInfos = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            vipTaskInfos.add(new VipTaskInfo());
        }
        vipTaskAdapter.setNewData(vipTaskInfos);
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

    public static void memberJump(Context context) {
        Intent intent = new Intent(context, MemberLevelRewardActivity.class);
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
}