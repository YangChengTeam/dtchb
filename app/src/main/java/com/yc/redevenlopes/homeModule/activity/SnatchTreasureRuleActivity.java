package com.yc.redevenlopes.homeModule.activity;


import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureRuleContact;
import com.yc.redevenlopes.homeModule.present.SnatchTreasureRulePresenter;
/**
 *夺宝结果
 */
public class SnatchTreasureRuleActivity extends BaseActivity<SnatchTreasureRulePresenter> implements SnatchTreasureRuleContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_snatch_treasure_rule;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {

    }
}