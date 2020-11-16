package com.yc.redevenlopes.homeModule.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.present.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContact.View {
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.line_members)
    LinearLayout lineMembers;
    @BindView(R.id.line_activitys)
    LinearLayout lineActivitys;
    @BindView(R.id.line_snatchTreasure)
    LinearLayout lineSnatchTreasure;
    @BindView(R.id.line_withdraw)
    LinearLayout lineWithdraw;

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
   
       lineMembers.setVisibility(View.GONE);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.line_members, R.id.line_activitys, R.id.line_snatchTreasure, R.id.line_withdraw})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.line_members:

                MemberActivity.memberJump(this);
                break;
            case R.id.line_activitys:
                break;
            case R.id.line_snatchTreasure:
                SnatchTreasureActivity.snatchTreasureJump(this);
                break;
            case R.id.line_withdraw:
                WithdrawActivity.WithdrawJump(this);
                break;
        }
    }
}