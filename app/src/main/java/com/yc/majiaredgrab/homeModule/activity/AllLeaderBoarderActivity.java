package com.yc.majiaredgrab.homeModule.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.homeModule.adapter.LeaderRankAdapter;
import com.yc.majiaredgrab.homeModule.contact.AllLeaderBoarderContact;
import com.yc.majiaredgrab.homeModule.module.bean.LeaderRankInfo;
import com.yc.majiaredgrab.homeModule.present.AllLeaderBoarderPresenter;
import com.yc.majiaredgrab.utils.CacheDataUtils;

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
        mPresenter.getAllLeaderList(CacheDataUtils.getInstance().getUserInfo().getGroup_id()+"");
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

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            finish();
        }
    }

    @Override
    public void getAllLeaderListSuccess(List<LeaderRankInfo> datas) {
        leaderRankAdapter.setNewData(datas);
        leaderRankAdapter.notifyDataSetChanged();
        if (leaderRankAdapter.getData().size() == 0) {
            View empty = LayoutInflater.from(this).inflate(R.layout.empty_view,null,false);
            leaderRankAdapter.setEmptyView(empty);
        }
    }
}