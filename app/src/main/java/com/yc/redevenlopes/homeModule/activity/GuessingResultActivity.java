package com.yc.redevenlopes.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.GuessingReultAdapter;
import com.yc.redevenlopes.homeModule.contact.GuessingResultContact;
import com.yc.redevenlopes.homeModule.present.GuessingResultPresenter;

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
        setRightTitle("竞猜规则");
        initRecyclerView();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }
    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        guessingReultAdapter=new GuessingReultAdapter(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(guessingReultAdapter);
    }

    public static void GuessingResultJump(Context context){
        Intent intent=new Intent(context,GuessingResultActivity.class);
        context.startActivity(intent);
    }
}