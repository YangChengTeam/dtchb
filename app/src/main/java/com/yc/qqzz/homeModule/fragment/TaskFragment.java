package com.yc.qqzz.homeModule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseLazyFragment;
import com.yc.qqzz.homeModule.adapter.VipTaskAdapter;
import com.yc.qqzz.homeModule.contact.TaskFgContract;
import com.yc.qqzz.homeModule.present.TaskFgPresenter;
import com.yc.qqzz.widget.ScrollWithRecyclerView;

import butterknife.BindView;


public class TaskFragment extends BaseLazyFragment<TaskFgPresenter> implements TaskFgContract.View {


    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.rela_avatar)
    RelativeLayout relaAvatar;
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    private VipTaskAdapter vipTaskAdapter;
    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
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
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        vipTaskAdapter = new VipTaskAdapter(null);
        recyclerView.setAdapter(vipTaskAdapter);
    }
}