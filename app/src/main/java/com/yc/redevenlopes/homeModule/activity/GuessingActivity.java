package com.yc.redevenlopes.homeModule.activity;



import android.os.Bundle;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.GuessingContact;
import com.yc.redevenlopes.homeModule.present.GuessingPresenter;
/**
 *竞猜
 */
public class GuessingActivity extends BaseActivity<GuessingPresenter> implements GuessingContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_guessing;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
          getActivityComponent().inject(this);
    }
}