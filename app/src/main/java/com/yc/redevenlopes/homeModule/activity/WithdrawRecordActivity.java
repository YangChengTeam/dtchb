package com.yc.redevenlopes.homeModule.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.DisposeRecordAdapter;
import com.yc.redevenlopes.homeModule.contact.WithdrawRecordConstact;
import com.yc.redevenlopes.homeModule.module.bean.DisposeRecordInfo;
import com.yc.redevenlopes.homeModule.module.bean.WithDrawRecordBeans;
import com.yc.redevenlopes.homeModule.present.WithdrawRecordPresenter;
import com.yc.redevenlopes.homeModule.widget.EmptyView;
import com.yc.redevenlopes.utils.CacheDataUtils;

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
    SmartRefreshLayout srlRefresh;
    private DisposeRecordAdapter disposeRecordAdapter;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_common_view;
    }

    @Override
    public void initEventAndData() {
        setTitle("提现记录");
        initRecyclerView();
        initDatas();
    }


    private void initRecyclerView() {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewRecord.setLayoutManager(linearLayoutManager);
        disposeRecordAdapter = new DisposeRecordAdapter(null);
        recyclerViewRecord.setAdapter(disposeRecordAdapter);
    }

    private void initDatas() {
        mPresenter.getWithDrawRecord(CacheDataUtils.getInstance().getUserInfo().getGroup_id()+"",String.valueOf(page),"10");
    }


    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void getWithDrawRecordSuccess(List<WithDrawRecordBeans> datas) {
        srlRefresh.setNoMoreData(false);
        if (page == 1) {
            disposeRecordAdapter.setNewData(datas);
            srlRefresh.finishRefresh();
        } else {
            if (datas != null) {
                disposeRecordAdapter.addData(datas);
            }
            srlRefresh.finishLoadMore();
            if (datas != null&&datas.size() == 0) {
                srlRefresh.finishLoadMoreWithNoMoreData();
            }
        }
        disposeRecordAdapter.notifyDataSetChanged();
        if (disposeRecordAdapter.getData().size() == 0) {
            View empty = LayoutInflater.from(this).inflate(R.layout.empty_view,null,false);
            TextView tv=empty.findViewById(R.id.tv_no_data);
            tv.setText("暂无体现记录");
            disposeRecordAdapter.setEmptyView(empty);
        }
    }
}