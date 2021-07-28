package com.yc.qqzz.homeModule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseLazyFragment;
import com.yc.qqzz.homeModule.adapter.WithDrawAdapter;
import com.yc.qqzz.homeModule.contact.WithDrawitemContract;
import com.yc.qqzz.homeModule.present.WithDrawitemPresenter;
import com.yc.qqzz.widget.ScrollWithRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;


public class WithDrawitemFragment extends BaseLazyFragment<WithDrawitemPresenter> implements WithDrawitemContract.View {

    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.rela_avatar)
    RelativeLayout relaAvatar;
    @BindView(R.id.progressbar_reward)
    ProgressBar progressbarReward;
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.tv_recodetwo)
    TextView tvRecodetwo;
    @BindView(R.id.dayuprecyclerView)
    ScrollWithRecyclerView dayuprecyclerView;
    private WithDrawAdapter withDrawAdapter;
    private WithDrawAdapter withDrawAdaptertwo;
    public WithDrawitemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_with_drawitem, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = (View) super.onCreateView(inflater, container, savedInstanceState);
        return (View) rootView;
    }

    @OnClick({R.id.tv_go, R.id.tv_recode, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go:
                break;
            case R.id.tv_recode:
                break;
            case R.id.tv_sure:
                break;
        }
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        withDrawAdapter = new WithDrawAdapter(null);
        recyclerView.setAdapter(withDrawAdapter);
        withDrawAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        GridLayoutManager gridLayoutManagertwo = new GridLayoutManager(getActivity(), 3);
        dayuprecyclerView.setLayoutManager(gridLayoutManagertwo);
        withDrawAdaptertwo = new WithDrawAdapter(null);
        dayuprecyclerView.setAdapter(withDrawAdaptertwo);
        withDrawAdaptertwo.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

    }

}