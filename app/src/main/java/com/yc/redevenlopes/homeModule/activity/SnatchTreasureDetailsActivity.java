package com.yc.redevenlopes.homeModule.activity;



import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureDeatilsContact;
import com.yc.redevenlopes.homeModule.present.SnatchTreasurePresenter;
/**
 *夺宝详情
 */
public class SnatchTreasureDetailsActivity extends BaseActivity<SnatchTreasurePresenter> implements SnatchTreasureDeatilsContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_snatch_treasure_details;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }
}