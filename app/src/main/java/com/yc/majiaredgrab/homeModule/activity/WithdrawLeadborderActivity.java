package com.yc.majiaredgrab.homeModule.activity;



import android.os.Bundle;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.homeModule.contact.WithdrawLeadborderContact;
import com.yc.majiaredgrab.homeModule.present.WithdrawLeadborderPresenter;
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