package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.RobRedEvenlopesAdapter;
import com.yc.redevenlopes.homeModule.contact.RodRedEvenlopesContact;
import com.yc.redevenlopes.homeModule.module.bean.RedDetailsBeans;
import com.yc.redevenlopes.homeModule.present.RodRedEvenlopesPresenter;
import com.yc.redevenlopes.utils.CacheDataUtils;

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
    @BindView(R.id.tv_hbNums)
    TextView tvHbNums;
    @BindView(R.id.line_hbNums)
    LinearLayout lineHbNums;
    @BindView(R.id.viewss)
    View view;
    private RobRedEvenlopesAdapter robRedEvenlopesAdapter;
    private String type;
    private String balance_money;
    private String money;
    private String typeName;
   private String id;
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
        type = getIntent().getStringExtra("type");
        typeName = getIntent().getStringExtra("typeName");
        money = getIntent().getStringExtra("money");
        balance_money = getIntent().getStringExtra("balance_money");
         id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(typeName)) {
            tvRedType.setText(typeName);
        }
        if (!TextUtils.isEmpty(money)) {
            tvRedMoney.setText(money);
        }
        view.setVisibility(View.GONE);
        lineHbNums.setVisibility(View.GONE);
        setFullScreen();
        initRecyclerVeiw();
        initData();
    }

    private void initData() {
        if (!TextUtils.isEmpty(id)){
            recyclerView.setVisibility(View.VISIBLE);
            mPresenter.getRedEvenlopesDetails(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "",id);
        }else {
            recyclerView.setVisibility(View.GONE);
        }
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
                WithdrawActivity.WithdrawJump(this);
                break;
        }
    }

    private void initRecyclerVeiw() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        robRedEvenlopesAdapter = new RobRedEvenlopesAdapter(null);
        recyclerView.setAdapter(robRedEvenlopesAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public static void robRedEvenlopesJump(Context context, String type, String typeName, String balance_money, String money,String id) {
        Intent intent = new Intent(context, RobRedEvenlopesActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("typeName", typeName);
        intent.putExtra("money", money);
        intent.putExtra("balance_money", balance_money);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    public void getRedEvenlopesDetailsSuccess(RedDetailsBeans data) {
        List<RedDetailsBeans.ListBean> list = data.getList();
        robRedEvenlopesAdapter.setNewData(list);
        robRedEvenlopesAdapter.notifyDataSetChanged();
        if (!TextUtils.isEmpty(data.getGet_info().getMoney())) {
            tvRedMoney.setText(data.getGet_info().getMoney());
        }
        if (list != null && list.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            if ("1".equals(type)) {//首页
                lineHbNums.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(balance_money)) {
                    tvHbNums.setText("已领取" + data.getList().size() + "/" + data.getTotal() + "个，共" + data.getSum_money() + "/" + balance_money + "元");
                } else {
                    tvHbNums.setText(data.getTotal() + "个红包，共" + data.getSum_money() + "元");
                }
            } else if ("2".equals(type)) {
                view.setVisibility(View.GONE);
                lineHbNums.setVisibility(View.GONE);
            } else if ("3".equals(type)) {
                view.setVisibility(View.GONE);
                lineHbNums.setVisibility(View.GONE);
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            lineHbNums.setVisibility(View.GONE);
        }

    }
}