package com.yc.qqzz.homeModule.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.homeModule.contact.WithDrawSuccessContract;
import com.yc.qqzz.homeModule.present.WithDrawSuccessPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawSuccessActivity extends BaseActivity<WithDrawSuccessPresenter> implements WithDrawSuccessContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_with_draw_success;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.tv_tel, R.id.line_account, R.id.line_money, R.id.line_type, R.id.tv_sure,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_tel:
                break;
            case R.id.line_account:
                break;
            case R.id.line_money:
                break;
            case R.id.line_type:
                break;
            case R.id.tv_sure:
                break;
        }
    }
}