package com.yc.redevenlopes.homeModule.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.LeaderRankAdapter;
import com.yc.redevenlopes.homeModule.contact.AllLeaderBoarderContact;
import com.yc.redevenlopes.homeModule.module.bean.LeaderRankInfo;
import com.yc.redevenlopes.homeModule.present.AllLeaderBoarderPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 全服排行榜
 */
public class AllLeaderBoarderActivity extends BaseActivity<AllLeaderBoarderPresenter> implements AllLeaderBoarderContact.View {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private LeaderRankAdapter leaderRankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_all_leader_boarder;
    }

    @Override
    public void initEventAndData() {
        initRecyclerView();
        initData();
    }

    private void initData() {
        List<LeaderRankInfo> leaderRankInfos = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            leaderRankInfos.add(new LeaderRankInfo());
        }
        leaderRankAdapter.setNewData(leaderRankInfos);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        leaderRankAdapter = new LeaderRankAdapter(null);
        recyclerView.setAdapter(leaderRankAdapter);

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.rl_back_view})
    public void onClick(View view) {
        if (view.getId() == R.id.rl_back_view) {
            finish();
        }
    }
}