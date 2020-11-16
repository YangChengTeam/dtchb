package com.yc.redevenlopes.homeModule.activity;

import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.GuessingResultContact;
import com.yc.redevenlopes.homeModule.present.GuessingResultPresenter;

/**
 *竞猜结果
 */
public class GuessingResultActivity extends BaseActivity<GuessingResultPresenter> implements GuessingResultContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_guessing_result;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
         getActivityComponent().inject(this);
    }
}