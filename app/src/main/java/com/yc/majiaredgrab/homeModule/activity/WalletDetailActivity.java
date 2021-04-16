package com.yc.majiaredgrab.homeModule.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.homeModule.adapter.WalletDetailAdapter;
import com.yc.majiaredgrab.homeModule.contact.WalletDetailContract;
import com.yc.majiaredgrab.homeModule.module.bean.WalletDetailBeans;
import com.yc.majiaredgrab.homeModule.present.WalletDetailPresenter;
import com.yc.majiaredgrab.utils.CacheDataUtils;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by suns  on 2020/11/18 09:13.
 */
public class WalletDetailActivity extends BaseActivity<WalletDetailPresenter> implements WalletDetailContract.View {
    @BindView(R.id.recyclerView_record)
    RecyclerView recyclerViewRecord;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout srlRefresh;
    private WalletDetailAdapter walletDetailAdapter;
    private int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initEventAndData() {
        setTitle("钱包详情");
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
        walletDetailAdapter = new WalletDetailAdapter(null);
        recyclerViewRecord.setAdapter(walletDetailAdapter);
    }

    private void initDatas() {
        mPresenter.getWalletDetailsData(CacheDataUtils.getInstance().getUserInfo().getGroup_id()+"",String.valueOf(page),"10");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_common_view;
    }


    @Override
    public void getWalletDetailsDataSuccess(List<WalletDetailBeans> datas) {
        srlRefresh.setNoMoreData(false);
        if (page == 1) {
            walletDetailAdapter.setNewData(datas);
            srlRefresh.finishRefresh();
        } else {
            if (datas != null) {
                walletDetailAdapter.addData(datas);
            }
            srlRefresh.finishLoadMore();
            if (datas != null&&datas.size() == 0) {
                srlRefresh.finishLoadMoreWithNoMoreData();
            }
        }
        walletDetailAdapter.notifyDataSetChanged();
        if (walletDetailAdapter.getData().size() == 0) {
            View empty = LayoutInflater.from(this).inflate(R.layout.empty_view,null,false);
            walletDetailAdapter.setEmptyView(empty);
        }
    }


}
