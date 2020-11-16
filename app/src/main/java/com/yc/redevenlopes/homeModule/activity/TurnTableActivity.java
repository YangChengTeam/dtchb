package com.yc.redevenlopes.homeModule.activity;



import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.TurnTableContact;
import com.yc.redevenlopes.homeModule.present.TurnTablePresenter;
/**
 * 转盘
 */
public class TurnTableActivity extends BaseActivity<TurnTablePresenter> implements TurnTableContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_turn_table;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
           getActivityComponent().inject(this);
    }
}