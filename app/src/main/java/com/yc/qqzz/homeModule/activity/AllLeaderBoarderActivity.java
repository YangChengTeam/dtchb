package com.yc.qqzz.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.homeModule.adapter.LeaderRankAdapter;
import com.yc.qqzz.homeModule.contact.AllLeaderBoarderContact;
import com.yc.qqzz.homeModule.module.bean.LeaderRankInfo;
import com.yc.qqzz.homeModule.present.AllLeaderBoarderPresenter;
import com.yc.qqzz.utils.CacheDataUtils;

import java.util.List;

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
    public static void AllLeaderJump(Context context){
        Intent intent=new Intent(context,AllLeaderBoarderActivity.class);
        context.startActivity(intent);
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