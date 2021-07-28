package com.yc.qqzz.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.dialog.SignDialog;
import com.yc.qqzz.homeModule.adapter.CashTaskAdapter;
import com.yc.qqzz.homeModule.bean.CashTaskBeans;
import com.yc.qqzz.homeModule.contact.CashTaskContract;
import com.yc.qqzz.homeModule.present.CashTaskPresenter;
import com.yc.qqzz.widget.ScrollWithRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CashTaskActivity extends BaseActivity<CashTaskPresenter> implements CashTaskContract.View {


    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.line_answer)
    LinearLayout lineAnswer;
    @BindView(R.id.line_withdraw)
    LinearLayout lineWithdraw;
    @BindView(R.id.line_snatchTreasure)
    LinearLayout lineSnatchTreasure;
    private CashTaskAdapter cashTaskAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_cash_task;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        initRecyclerView();
    }

    public static void CashTaskJump(Context context){
        Intent intent=new Intent(context,CashTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.line_answer, R.id.line_withdraw, R.id.line_snatchTreasure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.line_answer:
                break;
            case R.id.line_withdraw:
                break;
            case R.id.line_snatchTreasure:
                break;
        }
    }

    public void initRecyclerView(){
        List<CashTaskBeans> list=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new CashTaskBeans());
        }

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        cashTaskAdapter=new CashTaskAdapter(list);
        recyclerView.setAdapter(cashTaskAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    private SignDialog cashrewardDialog;
    public void withDrawDialog() {
        cashrewardDialog = new SignDialog(this);
        View builder = cashrewardDialog.builder(R.layout.cashreward_item_dialog);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashrewardDialog.setShow();
            }
        });
        cashrewardDialog.setShow();
    }
}