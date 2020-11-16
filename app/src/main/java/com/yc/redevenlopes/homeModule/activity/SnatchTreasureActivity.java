package com.yc.redevenlopes.homeModule.activity;


import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureContact;
import com.yc.redevenlopes.homeModule.present.SnatchTreasurePresenter;
/**
 *夺宝
 */
public class SnatchTreasureActivity extends BaseActivity<SnatchTreasurePresenter> implements SnatchTreasureContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snatch_treasure);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_snatch_treasure;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
         getActivityComponent().inject(this);
    }
}