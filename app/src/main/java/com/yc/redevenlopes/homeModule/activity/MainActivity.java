package com.yc.redevenlopes.homeModule.activity;

import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.present.MainPresenter;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContact.View{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
          getActivityComponent().inject(this);
    }
}