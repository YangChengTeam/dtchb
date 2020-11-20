package com.yc.redevenlopes.homeModule.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.RobRedEvenlopesAdapter;
import com.yc.redevenlopes.homeModule.contact.RodRedEvenlopesContact;
import com.yc.redevenlopes.homeModule.module.bean.FrequencyFgBeans;
import com.yc.redevenlopes.homeModule.module.bean.RobRedEvenlopesBeans;
import com.yc.redevenlopes.homeModule.present.RodRedEvenlopesPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 抢红包
 */
public class RobRedEvenlopesActivity extends BaseActivity<RodRedEvenlopesPresenter> implements RodRedEvenlopesContact.View {

    @BindView(R.id.tv_redType)
    TextView tvRedType;
    @BindView(R.id.tv_redMoney)
    TextView tvRedMoney;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RobRedEvenlopesAdapter robRedEvenlopesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_rob_red_evenlopes;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        initRecyclerVeiw();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_withdraw:

                break;
        }
    }

    private void initRecyclerVeiw(){
        List<RobRedEvenlopesBeans> lists=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RobRedEvenlopesBeans beans=new RobRedEvenlopesBeans();
            lists.add(beans);
        }

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        robRedEvenlopesAdapter=new RobRedEvenlopesAdapter(lists);
        recyclerView.setAdapter(robRedEvenlopesAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}