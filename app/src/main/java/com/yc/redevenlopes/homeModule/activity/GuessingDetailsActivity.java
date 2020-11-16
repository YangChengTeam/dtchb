package com.yc.redevenlopes.homeModule.activity;



import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.GuessingDetailsContact;
import com.yc.redevenlopes.homeModule.present.GuessingDetailsPresenter;
/**
 *竞猜详情
 */
public class GuessingDetailsActivity extends BaseActivity<GuessingDetailsPresenter> implements GuessingDetailsContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_guessing_details;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
         getActivityComponent().inject(this);
    }
}