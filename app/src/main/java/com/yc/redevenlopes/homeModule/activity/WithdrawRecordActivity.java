package com.yc.redevenlopes.homeModule.activity;


import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.WithdrawRecordConstact;
import com.yc.redevenlopes.homeModule.present.WithdrawRecordPresenter;

/**
 * 提现记录
 */
public class WithdrawRecordActivity extends BaseActivity<WithdrawRecordPresenter> implements WithdrawRecordConstact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_withdraw_record;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }
}