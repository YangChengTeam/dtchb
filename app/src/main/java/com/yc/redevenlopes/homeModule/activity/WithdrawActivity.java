package com.yc.redevenlopes.homeModule.activity;


import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.WithdrawConstact;
import com.yc.redevenlopes.homeModule.present.WithdrawPresenter;
/**
 * 提现
 */
public class WithdrawActivity extends BaseActivity<WithdrawPresenter> implements WithdrawConstact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void initEventAndData() {

    }


    @Override
    public void initInject() {
          getActivityComponent().inject(this);
    }

}