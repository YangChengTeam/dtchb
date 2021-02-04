package com.yc.redguess.homeModule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseLazyFragment;
import com.yc.redguess.homeModule.activity.SnatchTreasureDetailsActivity;
import com.yc.redguess.homeModule.adapter.FrequencyFgAdapter;
import com.yc.redguess.homeModule.contact.FrequencyfgContact;
import com.yc.redguess.homeModule.module.bean.FrequencyFgBeans;
import com.yc.redguess.homeModule.present.FrequencyfgPresenter;
import com.yc.redguess.utils.CacheDataUtils;

import java.util.List;

import butterknife.BindView;

public class FrequencyFragment extends BaseLazyFragment<FrequencyfgPresenter> implements FrequencyfgContact.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    private String position;
    private FrequencyFgAdapter frequencyFgAdapter;
    private int page=1;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frequency_fragment, container, false);
    }

    public static FrequencyFragment newInstance(String position) {
        FrequencyFragment frequencyFragment = new FrequencyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("position", position);
        frequencyFragment.setArguments(bundle);
        return frequencyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = (View) super.onCreateView(inflater, container, savedInstanceState);
        return (View) rootView;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initLazyData() {
        position = getArguments().getString("position");
        initRecyclerView();
        initData();
    }

    private void initData() {
           if ("0".equals(position)){
               mPresenter.getSnatchNums(CacheDataUtils.getInstance().getUserInfo().getGroup_id()+"",page,"10");
           }else {
               mPresenter.getNearSnatchNums("10");
           }
    }

    private void initRecyclerView(){
        srlRefresh.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        srlRefresh.setRefreshFooter(new ClassicsFooter(getActivity()));
        srlRefresh.setEnableRefresh(false);
        srlRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page=1;
                initData();
            }
        });
        srlRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                initData();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        frequencyFgAdapter=new FrequencyFgAdapter(null);
        recyclerView.setAdapter(frequencyFgAdapter);
        frequencyFgAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<FrequencyFgBeans> lists = adapter.getData();
                SnatchTreasureDetailsActivity.snatchTreasureDetailsJump(getActivity(),lists.get(position).getId()+"");
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void getSnatchNumsSuccess(List<FrequencyFgBeans> datass) {
        srlRefresh.setNoMoreData(false);
        if (page == 1) {
            frequencyFgAdapter.setNewData(datass);
            srlRefresh.finishRefresh();
        } else {
            if (datass != null) {
                frequencyFgAdapter.addData(datass);
            }
            srlRefresh.finishLoadMore();
            if (datass != null&&datass.size() == 0) {
                srlRefresh.finishLoadMoreWithNoMoreData();
            }
        }
        frequencyFgAdapter.notifyDataSetChanged();
        if (frequencyFgAdapter.getData().size() == 0) {
            View empty = LayoutInflater.from(getActivity()).inflate(R.layout.empty_view,null,false);
            TextView textView=empty.findViewById(R.id.tv_no_data);
            textView.setText("暂无数据");
            frequencyFgAdapter.setEmptyView(empty);
        }
    }

}