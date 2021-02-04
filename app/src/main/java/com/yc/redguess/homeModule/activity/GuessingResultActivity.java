package com.yc.redguess.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.homeModule.adapter.GuessingReultAdapter;
import com.yc.redguess.homeModule.contact.GuessingResultContact;
import com.yc.redguess.homeModule.module.bean.GuessHistoryBeans;
import com.yc.redguess.homeModule.present.GuessingResultPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * 竞猜结果
 */
public class GuessingResultActivity extends BaseActivity<GuessingResultPresenter> implements GuessingResultContact.View {

    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_people)
    TextView tvPeople;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    private GuessingReultAdapter guessingReultAdapter;
    private int page=1;
    private String info_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_guessing_result;
    }

    @Override
    public void initEventAndData() {
        info_id = getIntent().getStringExtra("info_id");
        setTitle("历史竞猜结果");
        initRecyclerView();
        initDatas();
    }
    private void initDatas(){
        mPresenter.getGuessHistory(info_id,page,"10");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }
    private void initRecyclerView(){
        srlRefresh.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        srlRefresh.setRefreshFooter(new ClassicsFooter(this));
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page=1;
                initDatas();
            }
        });
        srlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                initDatas();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        guessingReultAdapter=new GuessingReultAdapter(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(guessingReultAdapter);
    }

    public static void GuessingResultJump(Context context,String info_id){
        Intent intent=new Intent(context,GuessingResultActivity.class);
        intent.putExtra("info_id",info_id);
        context.startActivity(intent);
    }

    @Override
    public void submitGuessNoSuccess(GuessHistoryBeans data) {
        if (data!=null){
            if (!TextUtils.isEmpty(data.getMoney())){
                tvMoney.setText(data.getMoney()+"元");
            }else {
                tvMoney.setText("");
            }
            tvPeople.setText(data.getTotal()+"人");
        }
        List<GuessHistoryBeans.ListBean> lists = data.getList();
        srlRefresh.setNoMoreData(false);
        if (page == 1) {
            guessingReultAdapter.setNewData(lists);
            srlRefresh.finishRefresh();
        } else {
            if (lists != null) {
                guessingReultAdapter.addData(lists);
            }
            srlRefresh.finishLoadMore();
            if (lists != null&&lists.size() == 0) {
                srlRefresh.finishLoadMoreWithNoMoreData();
            }
        }
        guessingReultAdapter.notifyDataSetChanged();
        if (guessingReultAdapter.getData().size() == 0) {
            View empty = LayoutInflater.from(this).inflate(R.layout.empty_view,null,false);
            guessingReultAdapter.setEmptyView(empty);
        }
    }
}