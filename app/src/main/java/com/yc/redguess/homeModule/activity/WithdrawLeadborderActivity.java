package com.yc.redguess.homeModule.activity;



import android.os.Bundle;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.homeModule.contact.WithdrawLeadborderContact;
import com.yc.redguess.homeModule.present.WithdrawLeadborderPresenter;
/**
 * 提现排行榜
 */
public class WithdrawLeadborderActivity extends BaseActivity<WithdrawLeadborderPresenter> implements WithdrawLeadborderContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_withdraw_leadborder;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
         getActivityComponent().inject(this);
    }
}