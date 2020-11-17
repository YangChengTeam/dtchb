package com.yc.redevenlopes.homeModule.activity;


import android.os.Bundle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.DisposeRecordAdapter;
import com.yc.redevenlopes.homeModule.contact.WithdrawRecordConstact;
import com.yc.redevenlopes.homeModule.module.bean.DisposeRecordInfo;
import com.yc.redevenlopes.homeModule.present.WithdrawRecordPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 提现记录
 */
public class WithdrawRecordActivity extends BaseActivity<WithdrawRecordPresenter> implements WithdrawRecordConstact.View {

    @BindView(R.id.recyclerView_record)
    RecyclerView recyclerViewRecord;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private DisposeRecordAdapter disposeRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_withdraw_record;
    }

    @Override
    public void initEventAndData() {
        setTitle("提现记录");
        initRecyclerView();
        initData();
    }

    private void initData() {
        List<DisposeRecordInfo> recordInfoList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            recordInfoList.add(new DisposeRecordInfo());
        }

        disposeRecordAdapter.setNewData(recordInfoList);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewRecord.setLayoutManager(linearLayoutManager);
        disposeRecordAdapter = new DisposeRecordAdapter(null);
        recyclerViewRecord.setAdapter(disposeRecordAdapter);
    }


    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }
}