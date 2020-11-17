package com.yc.redevenlopes.homeModule.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.present.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContact.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
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

    @OnClick({R.id.line_members, R.id.line_activitys, R.id.line_snatchTreasure, R.id.line_withdraw, R.id.iv_avatar, R.id.iv_red})
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
            case R.id.iv_avatar:
                MemberCenterActivity.memberCenterJump(this);
                break;
            case R.id.iv_red:

                break;

        }
    }

    public void showRedDialog() {
        RedDialog redDialog = new RedDialog(this);
        redDialog.builder(R.layout.red_dialog_item);
        redDialog.setShow();
    }


}