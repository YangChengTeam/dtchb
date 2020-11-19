package com.yc.redevenlopes.homeModule.activity;

import android.os.Bundle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.WalletDetailAdapter;
import com.yc.redevenlopes.homeModule.module.bean.DisposeRecordInfo;
import com.yc.redevenlopes.homeModule.present.WalletDetailPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by suns  on 2020/11/18 09:13.
 */
public class WalletDetailActivity extends BaseActivity<WalletDetailPresenter> {
    @BindView(R.id.recyclerView_record)
    RecyclerView recyclerViewRecord;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private WalletDetailAdapter walletDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initEventAndData() {
        setTitle("钱包详情");
        initRecyclerView();
        initData();
    }

    private void initData() {
        List<DisposeRecordInfo> recordInfoList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            recordInfoList.add(new DisposeRecordInfo());
        }

        walletDetailAdapter.setNewData(recordInfoList);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewRecord.setLayoutManager(linearLayoutManager);
        walletDetailAdapter = new WalletDetailAdapter(null);
        recyclerViewRecord.setAdapter(walletDetailAdapter);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_common_view;
    }


}
