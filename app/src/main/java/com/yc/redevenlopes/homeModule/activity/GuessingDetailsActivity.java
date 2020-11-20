package com.yc.redevenlopes.homeModule.activity;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

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
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_guessing_details;
    }

    @Override
    public void initEventAndData() {
        setRightTitle("竞猜规则");
    }

    @Override
    public void initInject() {
         getActivityComponent().inject(this);
    }

    public static void  guessingDetailsJump(Context context){
        Intent intent=new Intent(context, GuessingDetailsActivity.class);
        context.startActivity(intent);
    }
}