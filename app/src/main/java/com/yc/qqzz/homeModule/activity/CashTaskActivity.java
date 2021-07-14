package com.yc.qqzz.homeModule.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.homeModule.adapter.CashTaskAdapter;
import com.yc.qqzz.homeModule.bean.CashTaskBeans;
import com.yc.qqzz.homeModule.contact.CashTaskContract;
import com.yc.qqzz.homeModule.present.CashTaskPresenter;
import com.yc.qqzz.widget.ScrollWithRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CashTaskActivity extends BaseActivity<CashTaskPresenter> implements CashTaskContract.View {

    @BindView(R.id.line_down)
    LinearLayout lineDown;
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.line_answer)
    LinearLayout lineAnswer;
    @BindView(R.id.line_withdraw)
    LinearLayout lineWithdraw;
    @BindView(R.id.line_snatchTreasure)
    LinearLayout lineSnatchTreasure;
    private CashTaskAdapter cashTaskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_cash_task;
    }

    @Override
    public void initEventAndData() {
        initRecyclerView();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.line_down, R.id.line_answer, R.id.line_withdraw, R.id.line_snatchTreasure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.line_down:
                break;
            case R.id.line_answer:
                break;
            case R.id.line_withdraw:
                break;
            case R.id.line_snatchTreasure:
                break;
        }
    }

    public void initRecyclerView(){
        List<CashTaskBeans> list=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new CashTaskBeans());
        }

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        cashTaskAdapter=new CashTaskAdapter(list);
        recyclerView.setAdapter(cashTaskAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}