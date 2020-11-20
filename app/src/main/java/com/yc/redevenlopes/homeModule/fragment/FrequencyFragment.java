package com.yc.redevenlopes.homeModule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseLazyFragment;
import com.yc.redevenlopes.homeModule.adapter.FrequencyFgAdapter;
import com.yc.redevenlopes.homeModule.contact.FrequencyfgContact;
import com.yc.redevenlopes.homeModule.module.bean.FrequencyFgBeans;
import com.yc.redevenlopes.homeModule.present.FrequencyfgPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FrequencyFragment extends BaseLazyFragment<FrequencyfgPresenter> implements FrequencyfgContact.View {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout srlRefresh;
    private String position;
    private FrequencyFgAdapter frequencyFgAdapter;
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
    }

    private void initRecyclerView(){
        List<FrequencyFgBeans> lists=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FrequencyFgBeans beans=new FrequencyFgBeans();
            lists.add(beans);
        }

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        frequencyFgAdapter=new FrequencyFgAdapter(lists);
        recyclerView.setAdapter(frequencyFgAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}