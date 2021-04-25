package com.yc.majiaredgrab.homeModule.activity;

import android.os.Bundle;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.homeModule.contact.InvationContact;
import com.yc.majiaredgrab.homeModule.present.InvationPresenter;

public class InvationActivity extends BaseActivity<InvationPresenter> implements InvationContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_invation;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
          getActivityComponent().inject(this);
    }
}