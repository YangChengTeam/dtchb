package com.yc.majiaredgrab.homeModule.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.homeModule.contact.RedCountContact;
import com.yc.majiaredgrab.homeModule.present.RedCountPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class RedCountActivity extends BaseActivity<RedCountPresenter> implements RedCountContact.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_min)
    TextView tvMin;
    @BindView(R.id.tv_second)
    TextView tvSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_red_count;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

                break;
        }
    }
}